package com.assistencia.dao.jdbc;

/** Converte o nome da coluna (snake_case) para a chave usada nas entidades (camelCase). */
final class NomeDeColuna {
    private NomeDeColuna() { }

    static String paraChave(String coluna) {
        String minusculo = coluna.toLowerCase();
        String[] partes = minusculo.split("_");
        StringBuilder chave = new StringBuilder(partes[0]);
        for (int indice = 1; indice < partes.length; indice++) {
            chave.append(capitalizar(partes[indice]));
        }
        return chave.toString();
    }

    private static String capitalizar(String parte) {
        String inicial = parte.substring(0, 1);
        String restante = parte.substring(1);
        return inicial.toUpperCase() + restante;
    }
}
