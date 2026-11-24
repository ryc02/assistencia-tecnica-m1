package com.assistencia.dados;

import com.assistencia.exception.ValidationException;
import java.math.BigDecimal;

/** Converte valores de formulários, JSON ou banco de dados em números. */
public final class ConversorNumerico {
    private ConversorNumerico() { }

    public static BigDecimal decimal(Object valor, String chave) {
        if (!Conversor.preenchido(valor)) {
            return null;
        }
        try {
            return new BigDecimal(Conversor.texto(valor));
        } catch (NumberFormatException excecao) {
            throw new ValidationException("Valor numérico inválido para " + chave + ".");
        }
    }

    public static Long inteiroLongo(Object valor, String chave) {
        BigDecimal numero = decimal(valor, chave);
        if (numero == null) {
            return null;
        }
        return numero.longValue();
    }

    public static Integer inteiro(Object valor, String chave) {
        BigDecimal numero = decimal(valor, chave);
        if (numero == null) {
            return null;
        }
        return numero.intValue();
    }
}
