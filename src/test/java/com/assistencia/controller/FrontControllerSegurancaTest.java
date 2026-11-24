package com.assistencia.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/** T18: bloqueio de alteração via GET (405) no Front Controller. */
@ExtendWith(MockitoExtension.class)
class FrontControllerSegurancaTest {
    @Test
    @DisplayName("T18: ação de alteração via GET é bloqueada com 405")
    void alteracaoViaGet(@Mock HttpServletRequest requisicao, @Mock HttpServletResponse resposta,
            @Mock HttpSession sessao, @Mock RequestDispatcher pagina) throws Exception {
        // Arrange
        FrontControllerServlet controlador = new FrontControllerServlet();
        when(requisicao.getSession(true))
                .thenReturn(sessao);
        when(requisicao.getParameter("acao"))
                .thenReturn("cliente.excluir");
        when(requisicao.getMethod())
                .thenReturn("GET");
        when(requisicao.getRequestDispatcher(anyString()))
                .thenReturn(pagina);
        // Act
        controlador.doGet(requisicao, resposta);
        // Assert
        verify(resposta).setStatus(405);
    }
}
