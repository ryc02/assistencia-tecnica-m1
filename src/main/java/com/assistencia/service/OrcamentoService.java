package com.assistencia.service;

import com.assistencia.builder.FichaTecnicaBuilder;
import com.assistencia.builder.OrcamentoBuilder;
import com.assistencia.builder.OrdemServicoBuilder;
import com.assistencia.dao.EquipamentoDAO;
import com.assistencia.dao.FichaTecnicaDAO;
import com.assistencia.dao.OrcamentoDAO;
import com.assistencia.dao.OrdemServicoDAO;
import com.assistencia.dao.jdbc.EquipamentoDAOJDBC;
import com.assistencia.dao.jdbc.FichaTecnicaDAOJDBC;
import com.assistencia.dao.jdbc.OrcamentoDAOJDBC;
import com.assistencia.dao.jdbc.OrdemServicoDAOJDBC;
import com.assistencia.exception.ConflictException;
import com.assistencia.exception.NotFoundException;
import com.assistencia.exception.ValidationException;
import com.assistencia.infra.TransactionManager;
import com.assistencia.model.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * [Requisito 4: RF03, RF04, RF05 & Seção 7]
 * Serviços de Orçamento e automação transacional da aprovação com geração de OrdemServico e FichaTecnica.
 */
public class OrcamentoService {

    private final OrcamentoDAO orcamentoDAO = new OrcamentoDAOJDBC();
    private final EquipamentoDAO equipamentoDAO = new EquipamentoDAOJDBC();
    private final OrdemServicoDAO ordemServicoDAO = new OrdemServicoDAOJDBC();
    private final FichaTecnicaDAO fichaTecnicaDAO = new FichaTecnicaDAOJDBC();

    public Orcamento cadastrar(Long equipamentoId, String descricaoProblema, String diagnostico,
                               BigDecimal valorPecas, BigDecimal valorMaoDeObra, BigDecimal percentualDesconto) throws Exception {
        return TransactionManager.executeInTransaction(conn -> {
            Equipamento eq = equipamentoDAO.buscarPorId(conn, equipamentoId);
            if (eq == null) {
                throw new NotFoundException("Equipamento não encontrado.");
            }

            Orcamento orc = new OrcamentoBuilder()
                    .equipamento(eq)
                    .descricaoProblema(descricaoProblema)
                    .diagnostico(diagnostico)
                    .valorPecas(valorPecas)
                    .valorMaoDeObra(valorMaoDeObra)
                    .percentualDesconto(percentualDesconto)
                    .status(StatusOrcamento.PENDENTE)
                    .build();

            return orcamentoDAO.inserir(conn, orc);
        });
    }

    public void atualizar(Long id, Long equipamentoId, String descricaoProblema, String diagnostico,
                          BigDecimal valorPecas, BigDecimal valorMaoDeObra, BigDecimal percentualDesconto) throws Exception {
        if (id == null) throw new ValidationException("ID do orçamento é obrigatório.");

        TransactionManager.executeInTransaction(conn -> {
            Orcamento existente = orcamentoDAO.buscarPorId(conn, id);
            if (existente == null) {
                throw new NotFoundException("Orçamento não encontrado.");
            }

            // [Requisito 6] Não permitir alteração de equipamento ou valores de orçamento aprovado ou recusado
            if (existente.getStatus() != StatusOrcamento.PENDENTE) {
                throw new ConflictException("Não é permitido alterar um orçamento com status " + existente.getStatus());
            }

            Equipamento eq = equipamentoDAO.buscarPorId(conn, equipamentoId);
            if (eq == null) throw new NotFoundException("Equipamento não encontrado.");

            Orcamento orc = new OrcamentoBuilder()
                    .id(id)
                    .equipamento(eq)
                    .descricaoProblema(descricaoProblema)
                    .diagnostico(diagnostico)
                    .valorPecas(valorPecas)
                    .valorMaoDeObra(valorMaoDeObra)
                    .percentualDesconto(percentualDesconto)
                    .status(existente.getStatus())
                    .dataCriacao(existente.getDataCriacao())
                    .build();

            orcamentoDAO.atualizar(conn, orc);
            return null;
        });
    }

    public void recusar(Long id) throws Exception {
        if (id == null) throw new ValidationException("ID é obrigatório.");

        TransactionManager.executeInTransaction(conn -> {
            Orcamento orc = orcamentoDAO.buscarPorIdParaUpdate(conn, id);
            if (orc == null) throw new NotFoundException("Orçamento não encontrado.");
            if (orc.getStatus() != StatusOrcamento.PENDENTE) {
                throw new ConflictException("Apenas orçamentos PENDENTES podem ser recusados.");
            }
            orc.setStatus(StatusOrcamento.RECUSADO);
            orcamentoDAO.atualizar(conn, orc);
            return null;
        });
    }

    public void excluir(Long id) throws Exception {
        if (id == null) throw new ValidationException("ID é obrigatório.");

        TransactionManager.executeInTransaction(conn -> {
            Orcamento orc = orcamentoDAO.buscarPorId(conn, id);
            if (orc == null) throw new NotFoundException("Orçamento não encontrado.");

            // [Requisito 6] Excluir orçamento somente se PENDENTE ou RECUSADO e sem ordem
            OrdemServico os = ordemServicoDAO.buscarPorOrcamentoId(conn, id);
            if (os != null) {
                throw new ConflictException("Não é possível excluir o orçamento pois ele possui uma ordem de serviço vinculada.");
            }
            if (orc.getStatus() == StatusOrcamento.APROVADO) {
                throw new ConflictException("Não é possível excluir diretamente um orçamento APROVADO.");
            }

            orcamentoDAO.excluir(conn, id);
            return null;
        });
    }

    /**
     * [Transação e Regra de Negócio]
     * Este é o coração do sistema. Quando aprovamos um orçamento, três coisas precisam 
     * acontecer ao mesmo tempo, ou nenhuma delas acontece (Transação):
     * 1. O orçamento muda para APROVADO.
     * 2. Uma Ordem de Serviço (OS) é criada.
     * 3. Uma Ficha Técnica (FT) é gerada e colada na OS.
     * Se acabar a luz ou o banco falhar no passo 3, o sistema desfaz os passos 1 e 2.
     * É o famoso conceito de "Tudo ou Nada" (Rollback Integral).
     */
    public OrdemServico aprovarOrcamento(Long orcamentoId, String responsavel, Prioridade prioridade,
                                          LocalDateTime previsaoConclusao, String observacoesOrdem,
                                          EstadoConservacao estadoConservacao, String acessoriosEntregues,
                                          Boolean ligaNormalmente, Boolean possuiAvarias, String descricaoAvarias,
                                          String testeInicial, String observacoesFicha) throws Exception {
        if (orcamentoId == null) throw new ValidationException("ID do orçamento é obrigatório.");

        return TransactionManager.executeInTransaction(conn -> {
            // 1. Bloqueia linha do orçamento
            Orcamento orc = orcamentoDAO.buscarPorIdParaUpdate(conn, orcamentoId);
            if (orc == null) {
                throw new NotFoundException("Orçamento não encontrado com ID: " + orcamentoId);
            }

            // 2. Idempotência: Se já estiver APROVADO, retorna o atendimento existente (T11)
            if (orc.getStatus() == StatusOrcamento.APROVADO) {
                OrdemServico osExistente = ordemServicoDAO.buscarPorOrcamentoId(conn, orcamentoId);
                if (osExistente != null) {
                    FichaTecnica ftExistente = fichaTecnicaDAO.buscarPorOrdemServicoId(conn, osExistente.getId());
                    if (ftExistente != null) {
                        return osExistente;
                    }
                }
            }

            // 3. Rejeita se RECUSADO
            if (orc.getStatus() == StatusOrcamento.RECUSADO) {
                throw new ConflictException("Orçamento foi RECUSADO e não pode ser aprovado.");
            }

            // 4. Garante que possui diagnóstico para aprovação
            if (orc.getDiagnostico() == null || orc.getDiagnostico().trim().isEmpty()) {
                throw new ValidationException("Informe um diagnóstico técnico antes de aprovar o orçamento.");
            }

            // Recalcula total no servidor
            orc.setValorTotal(orc.calcularTotal());

            // 5. Constrói e insere a Ordem de Serviço (ABERTA)
            OrdemServico os = new OrdemServicoBuilder()
                    .orcamento(orc)
                    .responsavel(responsavel)
                    .prioridade(prioridade)
                    .status(StatusOrdem.ABERTA)
                    .previsaoConclusao(previsaoConclusao)
                    .observacoes(observacoesOrdem)
                    .build();

            OrdemServico osSalva = ordemServicoDAO.inserir(conn, os);

            // 6. Constrói e insere a Ficha Técnica vinculada à ordem
            FichaTecnica ft = new FichaTecnicaBuilder()
                    .ordemServico(osSalva)
                    .estadoConservacao(estadoConservacao)
                    .acessoriosEntregues(acessoriosEntregues)
                    .ligaNormalmente(ligaNormalmente)
                    .possuiAvarias(possuiAvarias)
                    .descricaoAvarias(descricaoAvarias)
                    .testeInicial(testeInicial)
                    .observacoesRecebimento(observacoesFicha)
                    .build();

            fichaTecnicaDAO.inserir(conn, ft);

            // 7. Atualiza orçamento para APROVADO
            orc.setStatus(StatusOrcamento.APROVADO);
            orcamentoDAO.atualizar(conn, orc);

            return osSalva;
        });
    }

    /**
     * [Seção 7 / T06 / T14]
     * EXCLUIR ORDEM ABERTA:
     * Remove FichaTecnica e OrdemServico e retorna Orcamento para PENDENTE em uma única transação JDBC.
     */
    public void excluirOrdemAberta(Long ordemServicoId) throws Exception {
        if (ordemServicoId == null) throw new ValidationException("ID da Ordem de Serviço é obrigatório.");

        TransactionManager.executeInTransaction(conn -> {
            OrdemServico os = ordemServicoDAO.buscarPorIdParaUpdate(conn, ordemServicoId);
            if (os == null) {
                throw new NotFoundException("Ordem de Serviço não encontrada.");
            }

            // [T07] Apenas ordem ABERTA pode ser excluída
            if (os.getStatus() != StatusOrdem.ABERTA) {
                throw new ConflictException("Ordem de serviço no status " + os.getStatus() + " não pode ser excluída. Somente ordens ABERTAS podem ser removidas.");
            }

            Long orcamentoId = os.getOrcamento().getId();
            Orcamento orc = orcamentoDAO.buscarPorIdParaUpdate(conn, orcamentoId);

            // 1. Exclui ficha técnica vinculada
            fichaTecnicaDAO.excluirPorOrdemServicoId(conn, ordemServicoId);

            // 2. Exclui ordem de serviço
            ordemServicoDAO.excluir(conn, ordemServicoId);

            // 3. Restaura orçamento para PENDENTE
            if (orc != null) {
                orc.setStatus(StatusOrcamento.PENDENTE);
                orcamentoDAO.atualizar(conn, orc);
            }

            return null;
        });
    }

    public Orcamento buscarPorId(Long id) throws Exception {
        if (id == null) throw new ValidationException("ID é obrigatório.");
        return TransactionManager.executeInTransaction(conn -> {
            Orcamento orc = orcamentoDAO.buscarPorId(conn, id);
            if (orc == null) throw new NotFoundException("Orçamento não encontrado com ID: " + id);
            return orc;
        });
    }

    public List<Orcamento> listarTodos() throws Exception {
        return TransactionManager.executeInTransaction(orcamentoDAO::listarTodos);
    }

    public List<Orcamento> listarPorEquipamento(Long equipamentoId) throws Exception {
        return TransactionManager.executeInTransaction(conn -> orcamentoDAO.listarPorEquipamento(conn, equipamentoId));
    }
}
