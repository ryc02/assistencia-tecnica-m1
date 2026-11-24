package com.assistencia.infra;

/** Leitura de variáveis de ambiente com valor padrão. */
final class Ambiente {
    private Ambiente() { }

    static String ou(String variavel, String padrao) {
        String valor = System.getenv(variavel);
        if (valor == null || valor.isBlank()) {
            return padrao;
        }
        return valor;
    }
}
