package com.assistencia.model.orcamento;

import com.assistencia.dados.Campos;
import com.assistencia.model.EntidadeComId;
import com.assistencia.model.valor.Identificador;

/** [Entidade] Orçamento: equipamento avaliado, relato, situação e proposta (com adicionais - Decorator). */
public final class Orcamento extends EntidadeComId {
    private final ConteudoOrcamento conteudo;

    private Orcamento(Identificador identificador, ConteudoOrcamento conteudo) {
        super(identificador);
        this.conteudo = conteudo;
    }

    public static Orcamento de(Campos dados) {
        Identificador identificador = Identificador.de(dados);
        return new Orcamento(identificador, ConteudoOrcamento.de(dados));
    }

    /** Aprovar exige diagnóstico e status diferente de RECUSADO; recusar exige PENDENTE. */
    public void mudar(MudancaDeOrcamento mudanca) {
        conteudo.mudar(mudanca);
    }

    public void exigir(ExigenciaDoOrcamento exigencia) {
        conteudo.exigir(exigencia);
    }

    public boolean aprovado() {
        return conteudo.aprovado();
    }

    @Override
    protected void exportarDados(Campos destino) {
        conteudo.exportar(destino);
    }
}
