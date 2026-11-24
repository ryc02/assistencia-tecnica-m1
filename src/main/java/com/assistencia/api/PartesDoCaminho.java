package com.assistencia.api;

import com.assistencia.dados.ConversorNumerico;
import com.assistencia.exception.*;

/** Separa o caminho da URL (/recurso/id/acao) em até três partes. */
final class PartesDoCaminho {
    private PartesDoCaminho() { }

    static String[] separar(String caminho) {
        String limpo = String.valueOf(caminho);
        String semBarrasNasPontas = limpo.replaceAll("^/+|/+$", "");
        String[] partes = semBarrasNasPontas.split("/");
        if (partes.length > 3) {
            throw new NotFoundException("Caminho inválido.");
        }
        return partes;
    }

    static String parte(String[] partes, int posicao) {
        if (partes.length <= posicao) {
            return null;
        }
        return partes[posicao];
    }

    static Long id(String[] partes) {
        String texto = parte(partes, 1);
        Long id = ConversorNumerico.inteiroLongo(texto, "id");
        if (texto != null && (id == null || id <= 0)) {
            throw new ValidationException("ID inválido: " + texto);
        }
        return id;
    }
}
