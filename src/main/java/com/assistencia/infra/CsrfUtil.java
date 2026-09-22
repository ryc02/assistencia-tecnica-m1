package com.assistencia.infra;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * [Requisito 11: Segurança] Utilitário para geração e validação de tokens CSRF.
 */
public class CsrfUtil {

    public static final String CSRF_PARAM = "csrfToken";
    public static final String CSRF_SESSION_ATTR = "SESSION_CSRF_TOKEN";

    /**
     * Obtém ou gera o token CSRF atual da sessão HTTP.
     */
    public static String getToken(HttpSession session) {
        if (session == null) {
            return "";
        }
        String token = (String) session.getAttribute(CSRF_SESSION_ATTR);
        if (token == null || token.trim().isEmpty()) {
            token = UUID.randomUUID().toString();
            session.setAttribute(CSRF_SESSION_ATTR, token);
        }
        return token;
    }

    /**
     * [Requisito 11] Valida se o token enviado na requisição POST bate com o token da sessão.
     */
    public static boolean isValid(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return false;
        }
        String sessionToken = (String) session.getAttribute(CSRF_SESSION_ATTR);
        String requestToken = request.getParameter(CSRF_PARAM);
        return sessionToken != null && sessionToken.equals(requestToken);
    }
}
