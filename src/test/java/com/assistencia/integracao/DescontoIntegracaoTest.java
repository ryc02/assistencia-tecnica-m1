package com.assistencia.integracao;

import com.assistencia.exception.ValidationException;
import com.assistencia.infra.ConnectionFactory;
import com.assistencia.model.orcamento.Orcamento;
import com.assistencia.service.ConsultaOrcamento;
import java.math.BigDecimal;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import static com.assistencia.factory.FabricaDeOrcamentos.consultaOrcamento;
import static com.assistencia.integracao.BancoDeTeste.*;
import static com.assistencia.integracao.CenarioDeIntegracao.*;
import static org.junit.jupiter.api.Assertions.*;

/** Integração real com o H2: T19 (limites do percentual de desconto). */
class DescontoIntegracaoTest {
    @BeforeEach
    void prepararBanco() {
        ConnectionFactory.prepararBancoDeTestes();
    }

    @Test
    @DisplayName("T19: desconto de 100% zera o total")
    void t19DescontoTotal() throws Exception {
        // Arrange
        Long id = orcamento(equipamento(cliente("66677788899")), "100");
        ConsultaOrcamento consulta = consultaOrcamento();
        // Act
        Orcamento gravado = consulta.buscarPorId(id);
        // Assert
        assertEquals(new BigDecimal("0.00"), valor(gravado, "valorTotal"));
    }

    @Test
    @DisplayName("T19: desconto acima de 100% é rejeitado")
    void t19DescontoInvalido() throws Exception {
        // Arrange
        Long equipamentoId = equipamento(cliente("77788899900"));
        // Act
        Executable acao = () -> orcamento(equipamentoId, "101");
        // Assert
        assertThrows(ValidationException.class, acao);
    }
}
