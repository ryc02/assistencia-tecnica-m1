package com.assistencia.integracao;

import com.assistencia.exception.ValidationException;
import com.assistencia.infra.ConnectionFactory;
import com.assistencia.model.usuario.Usuario;
import com.assistencia.service.AutenticacaoService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import static com.assistencia.factory.FabricaDeClientes.autenticacaoService;
import static com.assistencia.integracao.BancoDeTeste.*;
import static org.junit.jupiter.api.Assertions.*;

/** Integração real com o H2: T21 (login com os usuários iniciais). */
class AutenticacaoIntegracaoTest {
    @BeforeEach
    void prepararBanco() {
        ConnectionFactory.prepararBancoDeTestes();
    }

    @Test
    @DisplayName("T21: credenciais válidas autenticam e inválidas são rejeitadas")
    void t21Login() throws Exception {
        // Arrange
        AutenticacaoService servico = autenticacaoService();
        // Act
        Usuario administrador = servico.efetuarLogin("admin@m1.com", "admin123");
        Executable invalida = () -> servico.efetuarLogin("admin@m1.com", "senhaErrada");
        // Assert
        assertEquals("Administrador", valor(administrador, "cargo"));
        assertThrows(ValidationException.class, invalida);
    }
}
