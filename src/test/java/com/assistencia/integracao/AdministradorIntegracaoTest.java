package com.assistencia.integracao;

import com.assistencia.exception.ConflictException;
import com.assistencia.infra.ConnectionFactory;
import com.assistencia.model.usuario.Usuario;
import com.assistencia.service.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import static com.assistencia.factory.FabricaDeClientes.*;
import static com.assistencia.integracao.BancoDeTeste.*;
import static org.junit.jupiter.api.Assertions.*;

/** Integração real com o H2: T22 (o único Administrador não pode ser excluído). */
class AdministradorIntegracaoTest {
    @BeforeEach
    void prepararBanco() {
        ConnectionFactory.prepararBancoDeTestes();
    }

    @Test
    @DisplayName("T22: o único Administrador não pode ser excluído")
    void t22UnicoAdministrador() throws Exception {
        // Arrange
        AutenticacaoService autenticacao = autenticacaoService();
        Usuario tecnico = autenticacao.efetuarLogin("tecnico@m1.com", "123456");
        Long administradorId = contar("SELECT id AS total FROM usuario WHERE email = ?", "admin@m1.com");
        UsuarioService servico = usuarioService();
        // Act
        Executable acao = () -> servico.excluir(administradorId, tecnico);
        // Assert
        assertThrows(ConflictException.class, acao);
    }
}
