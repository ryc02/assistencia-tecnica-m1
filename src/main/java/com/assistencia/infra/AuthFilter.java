package com.assistencia.infra;

import com.assistencia.model.Usuario;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Set;

/**
 * [Segurança] Filtro de autenticação e controle de acesso por perfil (cargo).
 * Bloqueia ações exclusivas do Administrador para usuários com cargo "Técnico".
 */
@WebFilter("/*")
public class AuthFilter implements Filter {

    /** Ações que exigem o cargo "Administrador". Técnicos receberão HTTP 403. */
    private static final Set<String> APENAS_ADMIN = Set.of(
        "usuario.listar",
        "usuario.inserir",
        "usuario.excluir",
        "cliente.inserir",
        "cliente.atualizar",
        "cliente.excluir",
        "equipamento.excluir"
    );

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest  request  = (HttpServletRequest)  req;
        HttpServletResponse response = (HttpServletResponse) res;

        String uri  = request.getRequestURI();
        String acao = request.getParameter("acao");

        // Permite recursos estáticos sem verificação
        if (uri.contains("/css/") || uri.contains("/js/") || uri.contains("/img/")) {
            chain.doFilter(req, res);
            return;
        }

        if (acao == null) acao = "cliente.listar";

        boolean isLoginAction = "login".equals(acao) || "efetuarLogin".equals(acao);

        HttpSession session   = request.getSession(false);
        boolean     isLoggedIn = session != null && session.getAttribute("usuarioLogado") != null;

        // 1. Usuário não autenticado → redireciona para login
        if (!isLoggedIn && !isLoginAction) {
            response.sendRedirect(request.getContextPath() + "/controle?acao=login");
            return;
        }

        // 2. Usuário autenticado → verifica se tem permissão pelo cargo
        if (isLoggedIn && APENAS_ADMIN.contains(acao)) {
            Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
            if (!"Administrador".equalsIgnoreCase(usuario.getCargo())) {
                response.sendRedirect(request.getContextPath() + "/controle?acao=acesso.negado");
                return;
            }
        }

        chain.doFilter(req, res);
    }

    @Override
    public void destroy() {}
}
