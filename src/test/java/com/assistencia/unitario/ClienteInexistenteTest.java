package com.assistencia.unitario;

import com.assistencia.dao.ClienteDAO;
import com.assistencia.exception.*;
import com.assistencia.service.ClienteService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static com.assistencia.unitario.FixturesCadastro.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/** Testes unitários: alterar cliente inexistente gera NotFoundException. */
@ExtendWith(MockitoExtension.class)
class ClienteInexistenteTest {
    @Test
    @DisplayName("atualizar: cliente inexistente gera NotFoundException")
    void atualizarInexistente(@Mock ClienteDAO clientes) {
        // Arrange
        ClienteService servico = new ClienteService(clientes, new TransacaoFake());
        // Act
        Executable acao = () -> servico.atualizar(99L, dadosDeCliente());
        // Assert
        assertThrows(NotFoundException.class, acao);
    }
}
