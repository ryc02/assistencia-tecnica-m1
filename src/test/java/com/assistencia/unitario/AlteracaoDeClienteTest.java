package com.assistencia.unitario;

import com.assistencia.dao.ClienteDAO;
import com.assistencia.service.ClienteService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static com.assistencia.unitario.FixturesCadastro.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/** Testes unitários da alteração de clientes. */
@ExtendWith(MockitoExtension.class)
class AlteracaoDeClienteTest {
    @Test
    @DisplayName("atualizar: o próprio cliente pode manter o mesmo CPF")
    void atualizarMantendoCpf(@Mock ClienteDAO clientes) throws Exception {
        // Arrange
        ClienteService servico = new ClienteService(clientes, new TransacaoFake());
        when(clientes.buscarPorId(null, 1L))
                .thenReturn(cliente(1L));
        when(clientes.buscarPorCpf(null, "11122233344"))
                .thenReturn(cliente(1L));
        // Act
        servico.atualizar(1L, dadosDeCliente());
        // Assert
        verify(clientes).atualizar(isNull(), any());
    }
}
