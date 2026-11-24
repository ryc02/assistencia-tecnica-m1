package com.assistencia.model.valor;

import com.assistencia.dados.*;
import com.assistencia.exception.ValidationException;

/** Prazo de garantia do serviço em dias (padrão: 90). */
public final class PrazoGarantia implements Exportavel {
    private static final Integer PADRAO = 90;

    private final Integer dias;

    private PrazoGarantia(Integer dias) {
        this.dias = dias;
    }

    public static PrazoGarantia de(Campos dados) {
        Integer informado = dados.inteiro("prazoGarantiaDias");
        if (informado == null) {
            return new PrazoGarantia(PADRAO);
        }
        if (informado < 0) {
            throw new ValidationException("Prazo de garantia não pode ser negativo.");
        }
        return new PrazoGarantia(informado);
    }

    @Override
    public void exportar(Campos destino) {
        destino.com("prazoGarantiaDias", dias);
    }
}
