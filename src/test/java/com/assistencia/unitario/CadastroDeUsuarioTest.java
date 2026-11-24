package com.assistencia.unitario;

import com.assistencia.Dados;
import com.assistencia.dados.*;
import com.assistencia.dao.UsuarioDAO;
import com.assistencia.exception.ConflictException;
import com.assistencia.service.UsuarioService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static com.assistencia.unitario.FixturesCadastro.usuario;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

/** Testes unitários do cadastro de usuários (e-mail único). */
@ExtendWith(MockitoExtension.class)
class CadastroDeUsuarioTest {
    @Test
    @DisplayName("inserir: e-mail já cadastrado gera ConflictException")
    void inserirEmailDuplicado(@Mock UsuarioDAO usuarios) throws Exception {
        // Arrange
        UsuarioService servico = new UsuarioService(usuarios, new TransacaoFake());
        when(usuarios.buscarPorEmail(null, "u5@m1.com"))
                .thenReturn(usuario(5L, "Técnico", "x"));
        Campos novo = Dados.de("nome", "Novo", "email", "u5@m1.com", "senha", "senha123", "cargo", "Técnico");
        // Act
        Executable acao = () -> servico.inserir(novo);
        // Assert
        assertThrows(ConflictException.class, acao);
    }
}
