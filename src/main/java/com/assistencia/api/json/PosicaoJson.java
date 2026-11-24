package com.assistencia.api.json;

import com.assistencia.exception.ValidationException;
import java.text.StringCharacterIterator;
import static java.text.CharacterIterator.DONE;

/** Posição de leitura dentro do texto JSON; a subclasse CursorJson acrescenta a leitura de símbolos esperados. */
abstract class PosicaoJson {
    private final StringCharacterIterator iterador;

    PosicaoJson(String texto) {
        iterador = new StringCharacterIterator(texto);
    }

    char atual() {
        return iterador.current();
    }

    char avancar() {
        char lido = iterador.current();
        iterador.next();
        return lido;
    }

    boolean terminou() {
        return atual() == DONE;
    }

    boolean fimDoTexto() {
        char caractere = atual();
        return caractere == '"' || caractere == DONE;
    }

    ValidationException erro() {
        return new ValidationException("JSON inválido na posição " + iterador.getIndex() + ".");
    }
}
