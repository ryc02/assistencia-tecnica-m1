package com.assistencia.service;

import com.assistencia.infra.GerenciadorTransacao;
import static com.assistencia.service.PaginasDeAtendimento.*;

/** Monta os dados das telas de ordens de serviço (lista e atendimento completo com a ficha). */
public class TelasDeAtendimento {
    private final LeituraDeAtendimento leitura;
    private final GerenciadorTransacao transacao;

    public TelasDeAtendimento(LeituraDeAtendimento leitura, GerenciadorTransacao transacao) {
        this.leitura = leitura;
        this.transacao = transacao;
    }

    /** Todas as ordens ou somente a ordem de um orçamento. */
    public Tela listagem(Long orcamentoIdFiltro) throws Exception {
        return transacao.executar(conexao -> paginaDeListagem(leitura, conexao, orcamentoIdFiltro));
    }

    /** Ordem de serviço e sua ficha técnica (relacionamento 1:1). */
    public Tela detalhesDaOrdem(Long id) throws Exception {
        return transacao.executar(conexao -> paginaDaOrdem(leitura, conexao, id));
    }

    /** Ordem e ficha geradas para um orçamento (vazio enquanto não aprovado). */
    public Tela doOrcamento(Long orcamentoId) throws Exception {
        return transacao.executar(conexao -> paginaDoOrcamento(leitura, conexao, orcamentoId));
    }
}
