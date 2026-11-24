package com.assistencia.unitario;

import com.assistencia.dados.Campos;
import com.assistencia.dao.UsuarioDAO;
import com.assistencia.exception.ValidationException;
import com.assistencia.model.usuario.Usuario;
import com.assistencia.service.AutenticacaoService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static com.assistencia.unitario.FixturesCadastro.usuario;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/** Testes unitários do login com senha em hash PBKDF2. */
@ExtendWith(MockitoExtension.class)
class AutenticacaoServiceTest {
    @Test
    @DisplayName("login: senha correta autentica o usuário")
    void loginComSucesso(@Mock UsuarioDAO usuarios) throws Exception {
        // Arrange
        AutenticacaoService servico = new AutenticacaoService(usuarios, new TransacaoFake());
        when(usuarios.buscarPorEmail(null, "u1@m1.com"))
                .thenReturn(usuario(1L, "Administrador", "senha123"));
        // Act
        Usuario logado = servico.efetuarLogin(" u1@m1.com ", "senha123");
        // Assert
        Campos dados = logado.exportar();
        assertEquals("u1@m1.com", dados.texto("email"));
    }

    @Test
    @DisplayName("login: senha errada gera ValidationException")
    void loginSenhaErrada(@Mock UsuarioDAO usuarios) throws Exception {
        // Arrange
        AutenticacaoService servico = new AutenticacaoService(usuarios, new TransacaoFake());
        when(usuarios.buscarPorEmail(null, "u1@m1.com"))
                .thenReturn(usuario(1L, "Administrador", "senha123"));
        // Act
        Executable acao = () -> servico.efetuarLogin("u1@m1.com", "errada");
        // Assert
        assertThrows(ValidationException.class, acao);
    }
}
