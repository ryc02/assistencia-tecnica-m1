package com.assistencia.infra;

import com.assistencia.controller.AcaoDaRequisicao;
import com.assistencia.model.usuario.Usuario;
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;

/** Telas: sem login redireciona para a tela de login; Técnico em ação de Administrador vai para acesso negado. */
final class FiltroDeTelas {
    private FiltroDeTelas() { }

    static void filtrar(HttpServletRequest requisicao, HttpServletResponse resposta, FilterChain cadeia)
            throws IOException, ServletException {
        String acao = AcaoDaRequisicao.de(requisicao);
        Usuario usuario = SessaoUsuario.logado(requisicao);
        if (usuario == null && !RegrasDeAcesso.telaPublica(acao)) {
            redirecionar(requisicao, resposta, "login");
            return;
        }
        boolean exigeAdministrador = RegrasDeAcesso.telaDoAdministrador(acao);
        if (exigeAdministrador && !usuario.ehAdministrador()) {
            redirecionar(requisicao, resposta, "acesso.negado");
            return;
        }
        cadeia.doFilter(requisicao, resposta);
    }

    private static void redirecionar(HttpServletRequest requisicao, HttpServletResponse resposta, String acao)
            throws IOException {
        String contexto = requisicao.getContextPath();
        resposta.sendRedirect(contexto + "/controle?acao=" + acao);
    }
}
