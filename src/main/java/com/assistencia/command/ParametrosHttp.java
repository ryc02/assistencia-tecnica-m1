package com.assistencia.command;

import com.assistencia.dados.*;
import com.assistencia.service.Existencia;
import java.util.*;
import javax.servlet.http.HttpServletRequest;

/** Converte os parâmetros da requisição em Campos. Campos com vários valores (checkboxes) viram lista. */
public final class ParametrosHttp {
    private ParametrosHttp() { }

    public static Campos campos(HttpServletRequest requisicao) {
        return comPrefixo(requisicao, "");
    }

    /** Somente os parâmetros que começam com o prefixo (ex.: "ficha."), sem o prefixo. */
    public static Campos comPrefixo(HttpServletRequest requisicao, String prefixo) {
        Campos campos = new Campos();
        Map<String, String[]> parametros = requisicao.getParameterMap();
        parametros.forEach((nome, valores) -> copiar(campos, prefixo, nome, valores));
        return campos;
    }

    public static Long id(HttpServletRequest requisicao, String nome) {
        String valor = requisicao.getParameter(nome);
        return ConversorNumerico.inteiroLongo(valor, nome);
    }

    public static Long idObrigatorio(HttpServletRequest requisicao, String nome, String entidade) {
        return Existencia.exigirId(id(requisicao, nome), entidade);
    }

    private static void copiar(Campos campos, String prefixo, String nome, String[] valores) {
        if (!nome.startsWith(prefixo)) {
            return;
        }
        int inicio = prefixo.length();
        String chave = nome.substring(inicio);
        campos.com(chave, valorDe(valores));
    }

    private static Object valorDe(String[] valores) {
        if (valores.length == 1) {
            return valores[0];
        }
        return List.of(valores);
    }
}
