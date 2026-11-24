package com.assistencia.model.valor;

import com.assistencia.dados.*;
import com.assistencia.exception.ValidationException;

/** CPF do cliente: guarda somente os 11 dígitos. */
public final class Cpf implements Exportavel {
    private final String digitos;

    private Cpf(String digitos) {
        this.digitos = digitos;
    }

    public static Cpf de(Campos dados) {
        String informado = dados.texto("cpf");
        if (informado == null) {
            throw new ValidationException("CPF é obrigatório.");
        }
        return comDigitos(informado.replaceAll("\\D", ""));
    }

    private static Cpf comDigitos(String digitos) {
        if (digitos.length() != 11) {
            throw new ValidationException("CPF deve conter exatamente 11 dígitos numéricos.");
        }
        return new Cpf(digitos);
    }

    @Override
    public void exportar(Campos destino) {
        destino.com("cpf", digitos);
    }
}
