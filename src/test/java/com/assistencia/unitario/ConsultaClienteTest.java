package com.assistencia.unitario;

import com.assistencia.dao.ClienteDAO;
import com.assistencia.exception.*;
import com.assistencia.model.cliente.Cliente;
import com.assistencia.service.ConsultaCliente;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static com.assistencia.unitario.FixturesCadastro.cliente;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/** Testes unitários da consulta de clientes por ID. */
@ExtendWith(MockitoExtension.class)
class ConsultaClienteTest {
    @Test
    @DisplayName("buscarPorId: devolve o cliente encontrado")
    void buscarComSucesso(@Mock ClienteDAO clientes) throws Exception {
        // Arrange
        ConsultaCliente consulta = new ConsultaCliente(clientes, new TransacaoFake());
        when(clientes.buscarPorId(null, 1L))
                .thenReturn(cliente(1L));
        // Act
        Cliente encontrado = consulta.buscarPorId(1L);
        // Assert
        assertTrue(encontrado.possuiId(1L));
    }

    @Test
    @DisplayName("buscarPorId: id inexistente gera NotFoundException e id nulo gera ValidationException")
    void buscarInexistenteOuNulo(@Mock ClienteDAO clientes) {
        // Arrange
        ConsultaCliente consulta = new ConsultaCliente(clientes, new TransacaoFake());
        // Act
        Executable inexistente = () -> consulta.buscarPorId(5L);
        Executable nulo = () -> consulta.buscarPorId(null);
        // Assert
        assertThrows(NotFoundException.class, inexistente);
        assertThrows(ValidationException.class, nulo);
    }
}
