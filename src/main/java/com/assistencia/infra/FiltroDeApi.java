package com.assistencia.infra;

import com.assistencia.api.RespostaRest;
import com.assistencia.controller.StatusHttp;
import com.assistencia.model.usuario.Usuario;
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import static com.assistencia.controller.StatusHttp.*;

/**

 * API REST: sem sessão responde 401; Técnico em operação de Administrador responde 403. /api/login e /api/csrf

 * ficam liberados para permitir a autenticação.

 */
final class FiltroDeApi {
    private FiltroDeApi() { }

    static void filtrar(HttpServletRequest requisicao, HttpServletResponse resposta, FilterChain cadeia,
                        String recurso) throws IOException, ServletException {
        if (RegrasDeAcesso.apiPublica(recurso)) {
            cadeia.doFilter(requisicao, resposta);
            return;
        }
        Usuario usuario = SessaoUsuario.logado(requisicao);
        if (usuario == null) {
            responder(requisicao, resposta, NAO_AUTENTICADO, "Autenticação necessária. Faça POST /api/login.");
            return;
        }
        String metodo = requisicao.getMethod();
        boolean exigeAdministrador = RegrasDeAcesso.apiDoAdministrador(metodo, recurso);
        if (exigeAdministrador && !usuario.ehAdministrador()) {
            responder(requisicao, resposta, PROIBIDO, "Operação permitida somente para Administrador.");
            return;
        }
        cadeia.doFilter(requisicao, resposta);
    }

    private static void responder(HttpServletRequest requisicao, HttpServletResponse resposta, StatusHttp status,
                                  String mensagem) throws IOException {
        RespostaRest erro = RespostaRest.erro(status, mensagem);
        erro.enviar(requisicao, resposta);
    }
}
