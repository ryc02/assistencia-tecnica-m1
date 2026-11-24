package com.assistencia.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/** T18: formulário sem token CSRF válido é recusado (403) ou, sem sessão, volta ao login. */
@ExtendWith(MockitoExtension.class)
class CsrfDoFormularioTest {
    @Test
    @DisplayName("T18: POST com sessão ativa e sem token CSRF é bloqueado com 403")
    void postSemTokenCsrf(@Mock HttpServletRequest requisicao, @Mock HttpServletResponse resposta,
            @Mock HttpSession sessao, @Mock RequestDispatcher pagina) throws Exception {
        // Arrange
        FrontControllerServlet controlador = new FrontControllerServlet();
        when(requisicao.getSession(false))
                .thenReturn(sessao);
        when(requisicao.getRequestDispatcher(anyString()))
                .thenReturn(pagina);
        // Act
        controlador.doPost(requisicao, resposta);
        // Assert
        verify(resposta).setStatus(403);
    }

    @Test
    @DisplayName("T18: POST de sessão expirada não executa nada e volta ao login com aviso")
    void postComSessaoExpirada(@Mock HttpServletRequest requisicao, @Mock HttpServletResponse resposta)
            throws Exception {
        // Arrange
        FrontControllerServlet controlador = new FrontControllerServlet();
        when(requisicao.getContextPath())
                .thenReturn("");
        // Act
        controlador.doPost(requisicao, resposta);
        // Assert
        verify(resposta).sendRedirect("/controle?acao=login&sessao=expirada");
    }
}
