package com.assistencia.model.orcamento;

import com.assistencia.dados.*;
import com.assistencia.exception.ValidationException;
import com.assistencia.model.valor.Texto;

/** Problema relatado pelo cliente e diagnóstico técnico. */
public final class Relato implements Exportavel {
    private final Texto descricaoProblema;
    private final Texto diagnostico;

    public Relato(Texto descricaoProblema, Texto diagnostico) {
        this.descricaoProblema = descricaoProblema;
        this.diagnostico = diagnostico;
    }

    public static Relato de(Campos dados) {
        Texto descricaoProblema = Texto.obrigatorio(dados, "descricaoProblema", 2000, "Descrição do problema");
        Texto diagnostico = Texto.opcional(dados, "diagnostico", 2000, "Diagnóstico");
        return new Relato(descricaoProblema, diagnostico);
    }

    @Override
    public void exportar(Campos destino) {
        descricaoProblema.exportar(destino);
        diagnostico.exportar(destino);
    }

    void exigirDiagnostico() {
        if (diagnostico.emBranco()) {
            throw new ValidationException("Informe um diagnóstico técnico antes de aprovar o orçamento.");
        }
    }
}
