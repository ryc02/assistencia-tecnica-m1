package com.assistencia.controller;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import static com.assistencia.controller.StatusHttp.SEE_OTHER;

/** Segue o destino devolvido pelo Command: redireciona (Post/Redirect/Get) ou encaminha para a JSP. */
final class Navegacao {
    private static final String REDIRECIONAR = "redirect:";
    private static final int TAMANHO_DO_PREFIXO = REDIRECIONAR.length();

    private Navegacao() { }

    static void seguir(String destino, HttpServletRequest requisicao, HttpServletResponse resposta)
            throws ServletException, IOException {
        if (destino.startsWith(REDIRECIONAR)) {
            redirecionar(destino, requisicao, resposta);
            return;
        }
        RequestDispatcher pagina = requisicao.getRequestDispatcher(destino);
        pagina.forward(requisicao, resposta);
    }

    private static void redirecionar(String destino, HttpServletRequest requisicao, HttpServletResponse resposta) {
        String endereco = destino.substring(TAMANHO_DO_PREFIXO);
        String contexto = requisicao.getContextPath();
        SEE_OTHER.aplicarEm(resposta);
        resposta.setHeader("Location", contexto + endereco);
    }
}
