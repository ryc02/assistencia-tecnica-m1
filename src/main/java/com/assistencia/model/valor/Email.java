package com.assistencia.model.valor;

import com.assistencia.dados.*;
import com.assistencia.exception.ValidationException;
import java.util.regex.*;

/** Endereço de e-mail validado (opcional para clientes, obrigatório para usuários). */
public final class Email implements Exportavel {
    private static final Pattern FORMATO = Pattern.compile("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");

    private final String endereco;

    private Email(String endereco) {
        this.endereco = endereco;
    }

    public static Email opcional(Campos dados) {
        String endereco = dados.texto("email");
        if (endereco == null) {
            return new Email(null);
        }
        return validado(endereco);
    }

    public static Email obrigatorio(Campos dados) {
        if (dados.texto("email") == null) {
            throw new ValidationException("E-mail é obrigatório.");
        }
        return opcional(dados);
    }

    private static Email validado(String endereco) {
        LimiteDeTamanho.exigir(endereco, 254, "E-mail");
        Matcher correspondencia = FORMATO.matcher(endereco);
        if (!correspondencia.matches()) {
            throw new ValidationException("Formato de e-mail inválido: " + endereco);
        }
        return new Email(endereco);
    }

    @Override
    public void exportar(Campos destino) {
        destino.com("email", endereco);
    }
}
