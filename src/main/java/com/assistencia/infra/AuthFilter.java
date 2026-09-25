package com.assistencia.infra;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String uri = request.getRequestURI();
        String acao = request.getParameter("acao");

        // Permite recursos estáticos
        if (uri.contains("/css/") || uri.contains("/js/") || uri.contains("/img/")) {
            chain.doFilter(req, res);
            return;
        }

        // Se nenhuma ação for passada e estiver acessando a raiz do /controle, vamos tratar a validação depois
        if (acao == null) acao = "cliente.listar"; 

        boolean isLoginAction = "login".equals(acao) || "efetuarLogin".equals(acao);
        
        HttpSession session = request.getSession(false);
        boolean isLoggedIn = (session != null && session.getAttribute("usuarioLogado") != null);

        // Se não estiver logado e não for página de login, redireciona
        if (!isLoggedIn && !isLoginAction) {
            response.sendRedirect(request.getContextPath() + "/controle?acao=login");
            return;
        }

        chain.doFilter(req, res);
    }

    @Override
    public void destroy() {}
}
