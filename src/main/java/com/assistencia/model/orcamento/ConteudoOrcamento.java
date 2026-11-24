package com.assistencia.model.orcamento;

import com.assistencia.dados.*;

/** Solicitação (equipamento, relato e situação) e proposta financeira do orçamento. */
public final class ConteudoOrcamento implements Exportavel {
    private final Solicitacao solicitacao;
    private final Proposta proposta;

    private ConteudoOrcamento(Solicitacao solicitacao, Proposta proposta) {
        this.solicitacao = solicitacao;
        this.proposta = proposta;
    }

    public static ConteudoOrcamento de(Campos dados) {
        Solicitacao solicitacao = Solicitacao.de(dados);
        return new ConteudoOrcamento(solicitacao, Proposta.de(dados));
    }

    @Override
    public void exportar(Campos destino) {
        solicitacao.exportar(destino);
        proposta.exportar(destino);
    }

    void mudar(MudancaDeOrcamento mudanca) {
        solicitacao.mudar(mudanca);
    }

    void exigir(ExigenciaDoOrcamento exigencia) {
        solicitacao.exigir(exigencia);
    }

    boolean aprovado() {
        return solicitacao.aprovado();
    }
}
