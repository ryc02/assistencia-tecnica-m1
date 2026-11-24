package com.assistencia.model.valor;

import com.assistencia.dados.Campos;
import com.assistencia.exception.ValidationException;
import java.math.BigDecimal;
import static java.math.BigDecimal.*;
import static java.math.RoundingMode.HALF_UP;

/** Percentual de desconto entre 0% e 100%. */
public final class Percentual {
    private static final BigDecimal CEM = new BigDecimal("100");
    private static final String FORA_DO_INTERVALO = "Percentual de desconto deve estar entre 0% e 100%.";

    private final BigDecimal valor;

    private Percentual(BigDecimal taxa) {
        valor = taxa.setScale(2, HALF_UP);
    }

    public static Percentual de(Campos dados, String chave) {
        BigDecimal informado = dados.decimal(chave);
        if (informado == null) {
            return new Percentual(ZERO);
        }
        if (informado.signum() < 0) {
            throw new ValidationException(FORA_DO_INTERVALO);
        }
        if (informado.compareTo(CEM) > 0) {
            throw new ValidationException(FORA_DO_INTERVALO);
        }
        return new Percentual(informado);
    }

    BigDecimal descontoSobre(BigDecimal base) {
        BigDecimal fracao = valor.divide(CEM, 4, HALF_UP);
        BigDecimal fator = ONE.subtract(fracao);
        return base.multiply(fator);
    }

    public void exportarComo(String chave, Campos destino) {
        destino.com(chave, valor);
    }
}
