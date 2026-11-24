package com.assistencia.dados;


/** Converte valores vindos de formulários, JSON ou banco de dados para os tipos esperados. */
public final class Conversor {
    private Conversor() { }

    public static boolean preenchido(Object valor) {
        return valor != null && !comoTexto(valor).isBlank();
    }

    public static String texto(Object valor) {
        if (!preenchido(valor)) {
            return null;
        }
        return comoTexto(valor).trim();
    }

    public static Boolean logico(Object valor) {
        if (!preenchido(valor)) {
            return null;
        }
        return Boolean.valueOf(texto(valor));
    }

    private static String comoTexto(Object valor) {
        return String.valueOf(valor);
    }
}
