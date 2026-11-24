package com.assistencia.integracao;

import com.assistencia.exception.*;
import com.assistencia.infra.ConnectionFactory;
import com.assistencia.model.cliente.Cliente;
import com.assistencia.service.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.function.Executable;
import static com.assistencia.factory.FabricaDeClientes.*;
import static com.assistencia.integracao.BancoDeTeste.*;
import static com.assistencia.integracao.CenarioDeIntegracao.*;
import static org.junit.jupiter.api.Assertions.*;

/** Integração real com o H2: T01 e T16. */
class CadastroDeClienteIntegracaoTest {
    @BeforeEach
    void prepararBanco() {
        ConnectionFactory.prepararBancoDeTestes();
    }

    @Test
    @DisplayName("T01: cadastra e consulta cliente; CPF duplicado é rejeitado")
    void t01CrudECpfDuplicado() throws Exception {
        // Arrange
        Long id = cliente("11122233344");
        ConsultaCliente consulta = consultaCliente();
        // Act
        Cliente encontrado = consulta.buscarPorId(id);
        Executable duplicado = () -> cliente("11122233344");
        // Assert
        assertEquals("11122233344", valor(encontrado, "cpf"));
        assertThrows(ConflictException.class, duplicado);
    }

    @Test
    @DisplayName("T16: consulta de ID inexistente lança NotFoundException")
    void t16IdInexistente() {
        // Arrange
        ConsultaCliente consulta = consultaCliente();
        // Act
        Executable acao = () -> consulta.buscarPorId(999L);
        // Assert
        assertThrows(NotFoundException.class, acao);
    }
}
