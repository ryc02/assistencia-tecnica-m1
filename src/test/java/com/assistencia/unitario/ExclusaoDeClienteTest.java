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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/** Testes unitários da exclusão de clientes (Cliente 1:N Equipamento). */
@ExtendWith(MockitoExtension.class)
class ExclusaoDeClienteTest {
    @Test
    @DisplayName("excluir: cliente sem equipamentos é excluído")
    void excluirSemEquipamentos(@Mock ClienteDAO clientes) throws Exception {
        // Arrange
        ClienteService servico = new ClienteService(clientes, new TransacaoFake());
        when(clientes.buscarPorId(null, 1L))
                .thenReturn(cliente(1L));
        when(clientes.contarEquipamentos(null, 1L))
                .thenReturn(0L);
        // Act
        servico.excluir(1L);
        // Assert
        verify(clientes).excluir(null, 1L);
    }

    @Test
    @DisplayName("excluir: cliente com equipamento gera ConflictException")
    void excluirComEquipamento(@Mock ClienteDAO clientes) throws Exception {
        // Arrange
        ClienteService servico = new ClienteService(clientes, new TransacaoFake());
        when(clientes.buscarPorId(null, 1L))
                .thenReturn(cliente(1L));
        when(clientes.contarEquipamentos(null, 1L))
                .thenReturn(2L);
        // Act
        Executable acao = () -> servico.excluir(1L);
        // Assert
        assertThrows(ConflictException.class, acao);
        verify(clientes, never()).excluir(any(), any());
    }
}
