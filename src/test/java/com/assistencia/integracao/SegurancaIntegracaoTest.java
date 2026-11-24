package com.assistencia.integracao;

import com.assistencia.dados.Campos;
import com.assistencia.infra.ConnectionFactory;
import com.assistencia.model.cliente.Cliente;
import com.assistencia.service.*;
import org.junit.jupiter.api.*;
import static com.assistencia.factory.FabricaDeClientes.*;
import static com.assistencia.integracao.BancoDeTeste.*;
import static com.assistencia.integracao.CenarioDeIntegracao.*;
import static com.assistencia.unitario.FixturesCadastro.dadosDeCliente;
import static org.junit.jupiter.api.Assertions.assertEquals;

/** Integração real com o H2: T17 (SQL Injection e XSS tratados como texto comum). */
class SegurancaIntegracaoTest {
    private static final String INJECAO = "Ana'); DROP TABLE cliente; -- <script>alert(1)</script>";

    @BeforeEach
    void prepararBanco() {
        ConnectionFactory.prepararBancoDeTestes();
    }

    @Test
    @DisplayName("T17: PreparedStatement grava a tentativa de injeção como texto, sem executar SQL")
    void t17InjecaoSql() throws Exception {
        // Arrange
        Campos dados = dadosDeCliente();
        dados.com("nome", INJECAO);
        ClienteService servico = clienteService();
        ConsultaCliente consulta = consultaCliente();
        // Act
        Long clienteId = id(servico.cadastrar(dados));
        // Assert
        Cliente gravado = consulta.buscarPorId(clienteId);
        assertEquals(INJECAO, valor(gravado, "nome"));
        assertEquals(1L, contar("SELECT COUNT(*) AS total FROM cliente"));
    }
}
