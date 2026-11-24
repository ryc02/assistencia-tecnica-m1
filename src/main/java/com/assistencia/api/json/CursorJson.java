package com.assistencia.api.json;

/** Cursor de leitura do texto JSON: pula espaços e confere os símbolos esperados ({, }, [, ], :, ,). */
final class CursorJson extends PosicaoJson {
    CursorJson(String texto) {
        super(texto);
    }

    void pularEspacos() {
        while (Character.isWhitespace(atual())) {
            avancar();
        }
    }

    boolean consumir(char esperado) {
        pularEspacos();
        if (atual() != esperado) {
            return false;
        }
        avancar();
        return true;
    }

    void exigir(char esperado) {
        if (!consumir(esperado)) {
            throw erro();
        }
    }
}
