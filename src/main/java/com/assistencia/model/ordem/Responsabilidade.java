package com.assistencia.model.ordem;

import com.assistencia.dados.*;
import com.assistencia.model.valor.Texto;
import static com.assistencia.model.ordem.Prioridade.NORMAL;

/** Técnico responsável e prioridade da ordem. */
public final class Responsabilidade implements Exportavel {
    private final Texto responsavel;
    private final Prioridade prioridade;

    public Responsabilidade(Texto responsavel, Prioridade prioridade) {
        this.responsavel = responsavel;
        this.prioridade = prioridade;
    }

    public static Responsabilidade de(Campos dados) {
        Texto responsavel = Texto.obrigatorio(dados, "responsavel", 120, "Responsável técnico");
        Prioridade prioridade = dados.opcaoOuPadrao("prioridade", NORMAL);
        return new Responsabilidade(responsavel, prioridade);
    }

    @Override
    public void exportar(Campos destino) {
        responsavel.exportar(destino);
        destino.com("prioridade", prioridade);
    }
}
