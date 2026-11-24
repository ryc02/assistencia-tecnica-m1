package com.assistencia.unitario;

import com.assistencia.dados.*;
import com.assistencia.dao.UsuarioDAO;
import com.assistencia.exception.ConflictException;
import com.assistencia.model.usuario.Usuario;
import com.assistencia.service.UsuarioService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static com.assistencia.unitario.FixturesCadastro.usuario;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

/** Testes unitários das regras de exclusão de usuários. */
@ExtendWith(MockitoExtension.class)
class ExclusaoDeUsuarioTest {
    @Test
    @DisplayName("excluir: o usuário conectado não pode excluir a si mesmo")
    void excluirProprioUsuario(@Mock UsuarioDAO usuarios) throws Exception {
        // Arrange
        UsuarioService servico = new UsuarioService(usuarios, new TransacaoFake());
        Usuario logado = usuario(1L, "Administrador", "senha123");
        // Act
        Executable acao = () -> servico.excluir(1L, logado);
        // Assert
        assertThrows(ConflictException.class, acao);
    }

    @Test
    @DisplayName("excluir: o único Administrador não pode ser removido")
    void excluirUnicoAdministrador(@Mock UsuarioDAO usuarios) throws Exception {
        // Arrange
        UsuarioService servico = new UsuarioService(usuarios, new TransacaoFake());
        Usuario administrador = usuario(2L, "Administrador", "senha123");
        Registros<Usuario> todos = new Registros<>(asList(administrador, usuario(3L, "Técnico", "x")));
        when(usuarios.buscarPorId(null, 2L))
                .thenReturn(administrador);
        when(usuarios.listarTodos(null))
                .thenReturn(todos);
        // Act
        Executable acao = () -> servico.excluir(2L, usuario(3L, "Técnico", "x"));
        // Assert
        assertThrows(ConflictException.class, acao);
    }
}
