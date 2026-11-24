package com.assistencia.infra;

import javax.servlet.FilterChain;
import javax.servlet.http.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;

/** Acentos dos formulários: o filtro define UTF-8 antes de ler o primeiro parâmetro. */
@ExtendWith(MockitoExtension.class)
class CodificacaoDaRequisicaoTest {
    @Test
    @DisplayName("filtro: UTF-8 é definido antes de ler a ação, para \"Técnico\" não virar \"TÃ©cnico\"")
    void utf8AntesDosParametros(@Mock HttpServletRequest requisicao, @Mock HttpServletResponse resposta,
            @Mock FilterChain cadeia) throws Exception {
        // Arrange
        AuthFilter filtro = new AuthFilter();
        when(requisicao.getRequestURI())
                .thenReturn("/controle");
        when(requisicao.getContextPath())
                .thenReturn("");
        when(requisicao.getParameter("acao"))
                .thenReturn("login");
        // Act
        filtro.doFilter(requisicao, resposta, cadeia);
        // Assert
        InOrder ordem = inOrder(requisicao);
        HttpServletRequest primeiro = ordem.verify(requisicao);
        primeiro.setCharacterEncoding("UTF-8");
        HttpServletRequest depois = ordem.verify(requisicao);
        depois.getParameter("acao");
        verify(cadeia).doFilter(requisicao, resposta);
    }
}
