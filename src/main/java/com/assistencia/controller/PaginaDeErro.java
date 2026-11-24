package com.assistencia.controller;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;

/** Exibe a página de erro com o código HTTP adequado (400, 403, 404, 405, 409 ou 500). */
public final class PaginaDeErro {
    private PaginaDeErro() { }

    public static void exibir(HttpServletRequest requisicao, HttpServletResponse resposta, StatusHttp status,
                              String titulo, String mensagem) throws ServletException, IOException {
        status.aplicarEm(resposta);
        status.registrarEm(requisicao);
        requisicao.setAttribute("erroTitulo", titulo);
        requisicao.setAttribute("erroMensagem", mensagem);
        RequestDispatcher pagina = requisicao.getRequestDispatcher("/WEB-INF/views/erro.jsp");
        pagina.forward(requisicao, resposta);
    }
}
