package com.assistencia.service;

import com.assistencia.infra.GerenciadorTransacao;
import static com.assistencia.service.PaginasDeOrcamento.*;

/** Monta os dados das telas de orçamentos e de detalhes do equipamento. */
public class TelasDeOrcamento {
    private final RepositorioOrcamento repositorio;
    private final GerenciadorTransacao transacao;

    public TelasDeOrcamento(RepositorioOrcamento repositorio, GerenciadorTransacao transacao) {
        this.repositorio = repositorio;
        this.transacao = transacao;
    }

    /** Orçamentos (todos ou de um equipamento), equipamentos e adicionais disponíveis. */
    public Tela listagem(Long equipamentoIdFiltro) throws Exception {
        return transacao.executar(conexao -> paginaDeListagem(repositorio, conexao, equipamentoIdFiltro));
    }

    /** Orçamento, equipamentos (para troca enquanto PENDENTE) e adicionais disponíveis. */
    public Tela detalhes(Long id) throws Exception {
        return transacao.executar(conexao -> paginaDoOrcamento(repositorio, conexao, id));
    }

    /** Equipamento e seus orçamentos (relacionamento 1:N). */
    public Tela detalhesDoEquipamento(Long equipamentoId) throws Exception {
        return transacao.executar(conexao -> paginaDoEquipamento(repositorio, conexao, equipamentoId));
    }
}
