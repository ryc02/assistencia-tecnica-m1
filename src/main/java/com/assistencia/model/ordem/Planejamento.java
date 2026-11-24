package com.assistencia.model.ordem;

import com.assistencia.dados.*;
import com.assistencia.model.valor.DataHora;

/** Responsabilidade e compromisso assumidos na ordem de serviço. */
public final class Planejamento implements Exportavel {
    private final Responsabilidade responsabilidade;
    private final Compromisso compromisso;

    public Planejamento(Responsabilidade responsabilidade, Compromisso compromisso) {
        this.responsabilidade = responsabilidade;
        this.compromisso = compromisso;
    }

    public static Planejamento de(Campos dados) {
        Responsabilidade responsabilidade = Responsabilidade.de(dados);
        Compromisso compromisso = Compromisso.de(dados);
        return new Planejamento(responsabilidade, compromisso);
    }

    @Override
    public void exportar(Campos destino) {
        responsabilidade.exportar(destino);
        compromisso.exportar(destino);
    }

    void exigirPrevisaoApos(DataHora abertura) {
        compromisso.exigirApos(abertura);
    }
}
