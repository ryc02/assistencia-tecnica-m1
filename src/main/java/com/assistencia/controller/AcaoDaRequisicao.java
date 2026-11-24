package com.assistencia.controller;

import javax.servlet.http.HttpServletRequest;

/** Nome da ação pedida (parâmetro "acao"); sem ação, abre a lista de clientes. */
public final class AcaoDaRequisicao {
    private static final String PADRAO = "cliente.listar";

    private AcaoDaRequisicao() { }

    public static String de(HttpServletRequest requisicao) {
        String acao = requisicao.getParameter("acao");
        if (acao == null || acao.isBlank()) {
            return PADRAO;
        }
        return acao;
    }
}
