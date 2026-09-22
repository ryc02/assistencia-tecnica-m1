package com.assistencia.service;

import com.assistencia.builder.OrdemServicoBuilder;
import com.assistencia.dao.OrdemServicoDAO;
import com.assistencia.dao.jdbc.OrdemServicoDAOJDBC;
import com.assistencia.exception.ConflictException;
import com.assistencia.exception.NotFoundException;
import com.assistencia.exception.ValidationException;
import com.assistencia.infra.TransactionManager;
import com.assistencia.model.OrdemServico;
import com.assistencia.model.Prioridade;
import com.assistencia.model.StatusOrdem;

import java.time.LocalDateTime;
import java.util.List;

/**
 * [Requisito 4: RF04 Gerenciar Ordens de Serviço & Seção 6: Estados]
 * Gerencia a atualização, consulta e transições de estado das Ordens de Serviço.
 */
public class OrdemServicoService {

    private final OrdemServicoDAO ordemServicoDAO = new OrdemServicoDAOJDBC();

    public void atualizar(Long id, String responsavel, Prioridade prioridade,
                          LocalDateTime previsaoConclusao, String observacoes, Integer prazoGarantiaDias) throws Exception {
        if (id == null) throw new ValidationException("ID da ordem é obrigatório.");

        TransactionManager.executeInTransaction(conn -> {
            OrdemServico existente = ordemServicoDAO.buscarPorId(conn, id);
            if (existente == null) throw new NotFoundException("Ordem de Serviço não encontrada.");

            // [Requisito 6] Editar dados apenas em ABERTA ou EM_ANDAMENTO
            if (existente.getStatus() == StatusOrdem.CONCLUIDA || existente.getStatus() == StatusOrdem.CANCELADA) {
                throw new ConflictException("Ordens de serviço " + existente.getStatus() + " não podem ter seus dados alterados.");
            }

            OrdemServico os = new OrdemServicoBuilder()
                    .id(id)
                    .orcamento(existente.getOrcamento())
                    .responsavel(responsavel)
                    .prioridade(prioridade)
                    .status(existente.getStatus())
                    .dataAbertura(existente.getDataAbertura())
                    .previsaoConclusao(previsaoConclusao)
                    .dataConclusao(existente.getDataConclusao())
                    .observacoes(observacoes)
                    .prazoGarantiaDias(prazoGarantiaDias)
                    .build();

            ordemServicoDAO.atualizar(conn, os);
            return null;
        });
    }

    public void iniciar(Long id) throws Exception {
        alterarStatus(id, StatusOrdem.ABERTA, StatusOrdem.EM_ANDAMENTO, null);
    }

    public void concluir(Long id) throws Exception {
        alterarStatus(id, StatusOrdem.EM_ANDAMENTO, StatusOrdem.CONCLUIDA, LocalDateTime.now());
    }

    public void cancelar(Long id) throws Exception {
        TransactionManager.executeInTransaction(conn -> {
            OrdemServico os = ordemServicoDAO.buscarPorIdParaUpdate(conn, id);
            if (os == null) throw new NotFoundException("Ordem de serviço não encontrada.");
            if (os.getStatus() == StatusOrdem.CONCLUIDA || os.getStatus() == StatusOrdem.CANCELADA) {
                throw new ConflictException("Ordem de serviço já está no estado final " + os.getStatus());
            }
            os.setStatus(StatusOrdem.CANCELADA);
            ordemServicoDAO.atualizar(conn, os);
            return null;
        });
    }

    private void alterarStatus(Long id, StatusOrdem origemEsperada, StatusOrdem novoStatus, LocalDateTime dataConclusao) throws Exception {
        if (id == null) throw new ValidationException("ID é obrigatório.");

        TransactionManager.executeInTransaction(conn -> {
            OrdemServico os = ordemServicoDAO.buscarPorIdParaUpdate(conn, id);
            if (os == null) throw new NotFoundException("Ordem de Serviço não encontrada com ID: " + id);

            if (os.getStatus() != origemEsperada) {
                throw new ConflictException("Transição inválida: Ordem está " + os.getStatus() + " mas deveria estar " + origemEsperada + " para passar para " + novoStatus);
            }

            os.setStatus(novoStatus);
            if (dataConclusao != null) {
                os.setDataConclusao(dataConclusao);
            }
            ordemServicoDAO.atualizar(conn, os);
            return null;
        });
    }

    public OrdemServico buscarPorId(Long id) throws Exception {
        if (id == null) throw new ValidationException("ID é obrigatório.");
        return TransactionManager.executeInTransaction(conn -> {
            OrdemServico os = ordemServicoDAO.buscarPorId(conn, id);
            if (os == null) throw new NotFoundException("Ordem de serviço não encontrada.");
            return os;
        });
    }

    public OrdemServico buscarPorOrcamentoId(Long orcamentoId) throws Exception {
        return TransactionManager.executeInTransaction(conn -> ordemServicoDAO.buscarPorOrcamentoId(conn, orcamentoId));
    }

    public List<OrdemServico> listarTodas() throws Exception {
        return TransactionManager.executeInTransaction(ordemServicoDAO::listarTodas);
    }
}
