package com.assistencia.unitario;

import com.assistencia.dao.UsuarioDAO;
import com.assistencia.exception.ValidationException;
import com.assistencia.service.AutenticacaoService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

/** Testes unitários: login com e-mail em branco é rejeitado. */
@ExtendWith(MockitoExtension.class)
class LoginSemEmailTest {
    @Test
    @DisplayName("login: e-mail em branco gera ValidationException")
    void loginSemEmail(@Mock UsuarioDAO usuarios) {
        // Arrange
        AutenticacaoService servico = new AutenticacaoService(usuarios, new TransacaoFake());
        // Act
        Executable acao = () -> servico.efetuarLogin(" ", "senha123");
        // Assert
        assertThrows(ValidationException.class, acao);
    }
}
