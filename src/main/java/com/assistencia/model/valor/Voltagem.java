package com.assistencia.model.valor;

import com.assistencia.dados.*;
import com.assistencia.exception.ValidationException;
import java.util.Set;

/** Voltagem do equipamento: 110V, 127V, 220V, BIVOLT ou NAO_APLICAVEL. */
public final class Voltagem implements Exportavel {
    private static final Set<String> PERMITIDAS = Set.of("110V", "127V", "220V", "BIVOLT", "NAO_APLICAVEL");
    private static final String MENSAGEM =
            "Voltagem inválida. Valores permitidos: 110V, 127V, 220V, BIVOLT ou NAO_APLICAVEL.";

    private final String valor;

    private Voltagem(String valor) {
        this.valor = valor;
    }

    public static Voltagem de(Campos dados) {
        String informada = dados.texto("voltagem");
        if (informada == null) {
            throw new ValidationException(MENSAGEM);
        }
        return permitida(informada.toUpperCase());
    }

    private static Voltagem permitida(String valor) {
        if (!PERMITIDAS.contains(valor)) {
            throw new ValidationException(MENSAGEM);
        }
        return new Voltagem(valor);
    }

    @Override
    public void exportar(Campos destino) {
        destino.com("voltagem", valor);
    }
}
