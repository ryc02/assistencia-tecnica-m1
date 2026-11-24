package com.assistencia.integracao;

import com.assistencia.infra.ConnectionFactory;
import java.sql.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import static com.assistencia.integracao.BancoDeTeste.*;
import static com.assistencia.integracao.CenarioDeIntegracao.*;
import static org.junit.jupiter.api.Assertions.*;

/** Integração real com o H2: T09 (UNIQUE rejeita segunda ficha para a mesma ordem). */
class UnicidadeDaFichaIntegracaoTest {
    @BeforeEach
    void prepararBanco() {
        ConnectionFactory.prepararBancoDeTestes();
    }

    @Test
    @DisplayName("T09: a restrição UNIQUE rejeita uma segunda ficha para a mesma ordem")
    void t09SegundaFichaRejeitada() throws Exception {
        // Arrange
        Long ordemId = aprovar(orcamentoNovo("45645645611"));
        // Act
        Executable acao = () -> executar("INSERT INTO ficha_tecnica (ordem_servico_id) VALUES (?)", ordemId);
        // Assert
        assertThrows(SQLException.class, acao);
    }
}
