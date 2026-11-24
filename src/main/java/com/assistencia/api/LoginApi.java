package com.assistencia.api;

import com.assistencia.dados.Campos;
import com.assistencia.factory.FabricaDeClientes;
import com.assistencia.infra.*;
import com.assistencia.model.usuario.Usuario;
import com.assistencia.service.AutenticacaoService;
import javax.servlet.http.*;
import static com.assistencia.controller.StatusHttp.OK;

/** POST /api/login: autentica o usuário e devolve nome, cargo e o token CSRF da nova sessão. */
final class LoginApi {
    private LoginApi() { }

    static RespostaRest entrar(HttpServletRequest requisicao, Campos corpo) throws Exception {
        AutenticacaoService autenticacao = FabricaDeClientes.autenticacaoService();
        String email = corpo.texto("email");
        String senha = corpo.texto("senha");
        Usuario usuario = autenticacao.efetuarLogin(email, senha);
        SessaoUsuario.registrar(requisicao, usuario);
        Campos resposta = usuario.exportar();
        resposta.com("csrfToken", token(requisicao));
        return new RespostaRest(OK, resposta.comoMapa());
    }

    static String token(HttpServletRequest requisicao) {
        HttpSession sessao = requisicao.getSession(true);
        return TokenCsrf.obter(sessao);
    }
}
