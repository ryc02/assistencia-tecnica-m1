package com.assistencia.model.orcamento;

import com.assistencia.dados.*;

/** Relato do problema e situação (estado) do orçamento. */
public final class Avaliacao implements Exportavel {
    private final Relato relato;
    private final Situacao situacao;

    public Avaliacao(Relato relato, Situacao situacao) {
        this.relato = relato;
        this.situacao = situacao;
    }

    public static Avaliacao de(Campos dados) {
        Relato relato = Relato.de(dados);
        Situacao situacao = Situacao.de(dados);
        return new Avaliacao(relato, situacao);
    }

    @Override
    public void exportar(Campos destino) {
        relato.exportar(destino);
        situacao.exportar(destino);
    }

    void mudar(MudancaDeOrcamento mudanca) {
        mudanca.conferir(relato);
        situacao.mudar(mudanca);
    }

    void exigir(ExigenciaDoOrcamento exigencia) {
        situacao.exigir(exigencia);
    }

    boolean aprovado() {
        return situacao.aprovado();
    }
}
