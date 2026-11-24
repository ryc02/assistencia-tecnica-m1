package com.assistencia.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import static com.assistencia.controller.StatusHttp.PROIBIDO;

/** Resposta a um formulário com token CSRF inválido: sessão perdida volta ao login; senão, 403. */
final class RecusaDeFormulario {
    private static final String LOGIN_COM_AVISO = "/controle?acao=login&sessao=expirada";

    private RecusaDeFormulario() { }

    static void responder(HttpServletRequest requisicao, HttpServletResponse resposta)
            throws ServletException, IOException {
        if (requisicao.getSession(false) == null) {
            String contexto = requisicao.getContextPath();
            resposta.sendRedirect(contexto + LOGIN_COM_AVISO);
            return;
        }
        PaginaDeErro.exibir(requisicao, resposta, PROIBIDO, "403 Acesso Negado (CSRF)",
                "Token CSRF ausente ou inválido. A operação foi bloqueada por motivos de segurança.");
    }
}
