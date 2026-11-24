package com.assistencia.infra;

import java.util.UUID;
import javax.servlet.http.*;

/**

 * [Segurança] Token CSRF por sessão: formulários enviam o campo csrfToken e a API envia o cabeçalho X-CSRF-

 * Token. Requisições de alteração sem o token correto são bloqueadas.

 */
public final class TokenCsrf {
    public static final String PARAMETRO = "csrfToken";
    public static final String CABECALHO = "X-CSRF-Token";
    private static final String ATRIBUTO_DA_SESSAO = "SESSION_CSRF_TOKEN";

    private TokenCsrf() { }

    public static String obter(HttpSession sessao) {
        Object atual = sessao.getAttribute(ATRIBUTO_DA_SESSAO);
        if (atual != null) {
            return (String) atual;
        }
        UUID aleatorio = UUID.randomUUID();
        String novo = aleatorio.toString();
        sessao.setAttribute(ATRIBUTO_DA_SESSAO, novo);
        return novo;
    }

    public static boolean validoNoFormulario(HttpServletRequest requisicao) {
        return confere(requisicao, requisicao.getParameter(PARAMETRO));
    }

    public static boolean validoNoCabecalho(HttpServletRequest requisicao) {
        return confere(requisicao, requisicao.getHeader(CABECALHO));
    }

    private static boolean confere(HttpServletRequest requisicao, String informado) {
        HttpSession sessao = requisicao.getSession(false);
        if (sessao == null) {
            return false;
        }
        Object esperado = sessao.getAttribute(ATRIBUTO_DA_SESSAO);
        return esperado != null && esperado.equals(informado);
    }
}
