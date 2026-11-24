package com.assistencia.integracao;

import com.assistencia.Dados;
import com.assistencia.dados.Campos;
import com.assistencia.exception.ConflictException;
import com.assistencia.infra.ConnectionFactory;
import com.assistencia.service.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import static com.assistencia.factory.FabricaDeClientes.*;
import static com.assistencia.integracao.BancoDeTeste.*;
import static org.junit.jupiter.api.Assertions.*;

/** Integração real com o H2: T22 (hash PBKDF2 e e-mail único). */
class UsuarioIntegracaoTest {
    @BeforeEach
    void prepararBanco() {
        ConnectionFactory.prepararBancoDeTestes();
    }

    @Test
    @DisplayName("T22: senha gravada em hash PBKDF2 e e-mail duplicado rejeitado")
    void t22HashEEmailDuplicado() throws Exception {
        // Arrange
        Campos novo = Dados.de("nome", "Gabriele", "email", "gabi@m1.com", "senha", "segura123", "cargo", "Técnico");
        UsuarioService servico = usuarioService();
        // Act
        servico.inserir(novo);
        Executable duplicado = () -> servico.inserir(novo);
        // Assert
        assertEquals(1L, contar("SELECT COUNT(*) AS total FROM usuario WHERE email = ? AND senha LIKE ?",
                "gabi@m1.com", "$pbkdf2$%"));
        assertThrows(ConflictException.class, duplicado);
    }
}
