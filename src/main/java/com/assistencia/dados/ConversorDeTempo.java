package com.assistencia.dados;

import com.assistencia.exception.ValidationException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

/** Converte datas/horas recebidas como texto (formato yyyy-MM-ddTHH:mm) ou já tipadas. */
public final class ConversorDeTempo {
    private ConversorDeTempo() { }

    public static LocalDateTime dataHora(Object valor, String chave) {
        if (valor instanceof LocalDateTime) {
            return (LocalDateTime) valor;
        }
        if (!Conversor.preenchido(valor)) {
            return null;
        }
        return interpretar(Conversor.texto(valor), chave);
    }

    private static LocalDateTime interpretar(String texto, String chave) {
        try {
            return LocalDateTime.parse(texto);
        } catch (DateTimeParseException excecao) {
            throw new ValidationException("Formato de data/hora inválido para " + chave);
        }
    }
}
