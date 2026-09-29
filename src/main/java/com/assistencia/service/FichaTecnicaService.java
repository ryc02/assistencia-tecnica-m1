package com.assistencia.service;

import com.assistencia.builder.FichaTecnicaBuilder;
import com.assistencia.dao.FichaTecnicaDAO;
import com.assistencia.dao.jdbc.FichaTecnicaDAOJDBC;
import com.assistencia.dao.OrdemServicoDAO;
import com.assistencia.dao.jdbc.OrdemServicoDAOJDBC;
import com.assistencia.dao.OrcamentoDAO;
import com.assistencia.dao.jdbc.OrcamentoDAOJDBC;
import com.assistencia.dao.EquipamentoDAO;
import com.assistencia.dao.jdbc.EquipamentoDAOJDBC;
import com.assistencia.dao.ClienteDAO;
import com.assistencia.dao.jdbc.ClienteDAOJDBC;
import java.sql.Connection;
import com.assistencia.model.OrdemServico;
import com.assistencia.model.Orcamento;
import com.assistencia.model.Equipamento;
import com.assistencia.exception.ConflictException;
import com.assistencia.exception.NotFoundException;
import com.assistencia.exception.ValidationException;
import com.assistencia.infra.TransactionManager;
import com.assistencia.model.EstadoConservacao;
import com.assistencia.model.FichaTecnica;
import com.assistencia.model.StatusOrdem;

import java.util.List;

/**
 * [Requisito 4: RF07 Gerenciar Fichas Técnicas]
 * Atualização e consulta de FichaTecnica associada à OrdemServico.
 * (A inserção e exclusão ocorrem exclusivamente junto com a OrdemServico).
 */
public class FichaTecnicaService {

    private final FichaTecnicaDAO fichaTecnicaDAO = new FichaTecnicaDAOJDBC();
    private final OrdemServicoDAO ordemServicoDAO = new OrdemServicoDAOJDBC();
    private final OrcamentoDAO orcamentoDAO = new OrcamentoDAOJDBC();
    private final EquipamentoDAO equipamentoDAO = new EquipamentoDAOJDBC();
    private final ClienteDAO clienteDAO = new ClienteDAOJDBC();

    private void stitch(Connection conn, FichaTecnica ft) throws Exception {
        if (ft != null && ft.getOrdemServico() != null && ft.getOrdemServico().getId() != null) {
            OrdemServico os = ordemServicoDAO.buscarPorId(conn, ft.getOrdemServico().getId());
            if (os != null && os.getOrcamento() != null && os.getOrcamento().getId() != null) {
                Orcamento orc = orcamentoDAO.buscarPorId(conn, os.getOrcamento().getId());
                if (orc != null && orc.getEquipamento() != null && orc.getEquipamento().getId() != null) {
                    Equipamento eq = equipamentoDAO.buscarPorId(conn, orc.getEquipamento().getId());
                    if(eq != null && eq.getCliente() != null && eq.getCliente().getId() != null) {
                         eq.setCliente(clienteDAO.buscarPorId(conn, eq.getCliente().getId()));
                    }
                    orc.setEquipamento(eq);
                }
                os.setOrcamento(orc);
            }
            ft.setOrdemServico(os);
        }
    }

    public void atualizar(Long id, EstadoConservacao estadoConservacao, String acessoriosEntregues,
                          Boolean ligaNormalmente, Boolean possuiAvarias, String descricaoAvarias,
                          String testeInicial, String observacoesRecebimento) throws Exception {
        if (id == null) throw new ValidationException("ID da Ficha Técnica é obrigatório.");

        TransactionManager.executeInTransaction(conn -> {
            FichaTecnica existente = fichaTecnicaDAO.buscarPorId(conn, id);
            if (existente == null) throw new NotFoundException("Ficha Técnica não encontrada.");

            // [Requisito 6] Editar dados apenas em ABERTA ou EM_ANDAMENTO
            StatusOrdem st = existente.getOrdemServico().getStatus();
            if (st == StatusOrdem.CONCLUIDA || st == StatusOrdem.CANCELADA) {
                throw new ConflictException("Ficha técnica não pode ser alterada pois a ordem de serviço já está " + st);
            }

            FichaTecnica ft = new FichaTecnicaBuilder()
                    .id(id)
                    .ordemServico(existente.getOrdemServico())
                    .estadoConservacao(estadoConservacao)
                    .acessoriosEntregues(acessoriosEntregues)
                    .ligaNormalmente(ligaNormalmente)
                    .possuiAvarias(possuiAvarias)
                    .descricaoAvarias(descricaoAvarias)
                    .testeInicial(testeInicial)
                    .observacoesRecebimento(observacoesRecebimento)
                    .dataRegistro(existente.getDataRegistro())
                    .build();

            fichaTecnicaDAO.atualizar(conn, ft);
            return null;
        });
    }

    public FichaTecnica buscarPorId(Long id) throws Exception {
        if (id == null) throw new ValidationException("ID é obrigatório.");
        return TransactionManager.executeInTransaction(conn -> {
            FichaTecnica ft = fichaTecnicaDAO.buscarPorId(conn, id);
            if (ft == null) throw new NotFoundException("Ficha Técnica não encontrada.");
            return ft;
        });
    }

    public FichaTecnica buscarPorOrdemServicoId(Long ordemServicoId) throws Exception {
        return TransactionManager.executeInTransaction(conn -> {
            FichaTecnica ft = fichaTecnicaDAO.buscarPorOrdemServicoId(conn, ordemServicoId);
            stitch(conn, ft);
            return ft;
        });
    }

    public List<FichaTecnica> listarTodas() throws Exception {
        return TransactionManager.executeInTransaction(conn -> {
            List<FichaTecnica> list = fichaTecnicaDAO.listarTodas(conn);
            for(FichaTecnica o : list) stitch(conn, o);
            return list;
        });
    }
}
