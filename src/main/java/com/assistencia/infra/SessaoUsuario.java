package com.assistencia.infra;

import com.assistencia.dados.Campos;
import com.assistencia.model.usuario.Usuario;
import java.util.Map;
import javax.servlet.http.*;

/**

 * Guarda o usuário autenticado na sessão: a entidade (para regras de acesso) e os dados exportados em

 * "usuarioLogado" (para as telas). Gera um novo id de sessão no login (contra fixação de sessão).

 */
public final class SessaoUsuario {
    private static final String USUARIO = "usuario";

    private SessaoUsuario() { }

    public static void registrar(HttpServletRequest requisicao, Usuario usuario) {
        requisicao.getSession(true);
        requisicao.changeSessionId();
        HttpSession sessao = requisicao.getSession();
        Campos dados = usuario.exportar();
        sessao.setAttribute(USUARIO, usuario);
        Map<String, Object> visao = dados.comoMapa();
        sessao.setAttribute("usuarioLogado", visao);
    }

    public static Usuario logado(HttpServletRequest requisicao) {
        HttpSession sessao = requisicao.getSession(false);
        if (sessao == null) {
            return null;
        }
        return (Usuario) sessao.getAttribute(USUARIO);
    }

    public static void encerrar(HttpServletRequest requisicao) {
        HttpSession sessao = requisicao.getSession(false);
        if (sessao != null) {
            sessao.invalidate();
        }
    }
}
