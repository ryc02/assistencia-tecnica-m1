package com.assistencia.unitario;

import com.assistencia.dados.Registros;
import com.assistencia.dao.ClienteDAO;
import com.assistencia.exception.*;
import com.assistencia.model.cliente.Cliente;
import com.assistencia.service.ConsultaCliente;
import java.util.List;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static com.assistencia.unitario.FixturesCadastro.cliente;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/** Testes unitários da listagem de clientes. */
@ExtendWith(MockitoExtension.class)
class ListagemDeClientesTest {
    @Test
    @DisplayName("listarTodos: devolve os clientes do DAO")
    void listarTodos(@Mock ClienteDAO clientes) throws Exception {
        // Arrange
        ConsultaCliente consulta = new ConsultaCliente(clientes, new TransacaoFake());
        List<Cliente> dois = List.of(cliente(1L), cliente(2L));
        when(clientes.listarTodos(null))
                .thenReturn(new Registros<>(dois));
        // Act
        Registros<Cliente> lista = consulta.listarTodos();
        // Assert
        assertEquals(2, lista.quantidade());
    }
}
