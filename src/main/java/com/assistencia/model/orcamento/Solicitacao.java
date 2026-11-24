package com.assistencia.model.orcamento;

import com.assistencia.dados.*;
import com.assistencia.model.equipamento.Equipamento;
import com.assistencia.model.valor.Vinculo;

/** Equipamento avaliado (relacionamento Equipamento 1:N Orçamento) e a avaliação. */
public final class Solicitacao implements Exportavel {
    private final Vinculo<Equipamento> equipamento;
    private final Avaliacao avaliacao;

    public Solicitacao(Vinculo<Equipamento> equipamento, Avaliacao avaliacao) {
        this.equipamento = equipamento;
        this.avaliacao = avaliacao;
    }

    public static Solicitacao de(Campos dados) {
        Vinculo<Equipamento> equipamento = Vinculo.obrigatorio(dados, "equipamento",
                "Equipamento é obrigatório para o orçamento.");
        Avaliacao avaliacao = Avaliacao.de(dados);
        return new Solicitacao(equipamento, avaliacao);
    }

    @Override
    public void exportar(Campos destino) {
        equipamento.exportar(destino);
        avaliacao.exportar(destino);
    }

    void mudar(MudancaDeOrcamento mudanca) {
        avaliacao.mudar(mudanca);
    }

    void exigir(ExigenciaDoOrcamento exigencia) {
        avaliacao.exigir(exigencia);
    }

    boolean aprovado() {
        return avaliacao.aprovado();
    }
}
