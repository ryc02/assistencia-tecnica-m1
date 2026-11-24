package com.assistencia.integracao;

import com.assistencia.dados.Registros;
import com.assistencia.exception.NotFoundException;
import com.assistencia.infra.ConnectionFactory;
import com.assistencia.model.equipamento.Equipamento;
import com.assistencia.service.ConsultaEquipamento;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import static com.assistencia.factory.FabricaDeEquipamentos.consultaEquipamento;
import static com.assistencia.integracao.BancoDeTeste.*;
import static com.assistencia.integracao.CenarioDeIntegracao.*;
import static org.junit.jupiter.api.Assertions.*;

/** Integração real com o H2: T02 e T08 (Cliente 1:N Equipamento). */
class EquipamentoIntegracaoTest {
    @BeforeEach
    void prepararBanco() {
        ConnectionFactory.prepararBancoDeTestes();
    }

    @Test
    @DisplayName("T02: equipamento de cliente inexistente é rejeitado")
    void t02ClienteInexistente() {
        // Act
        Executable acao = () -> equipamento(999L);
        // Assert
        assertThrows(NotFoundException.class, acao);
    }

    @Test
    @DisplayName("T08: dois equipamentos do mesmo cliente (associação 1:N)")
    void t08DoisEquipamentos() throws Exception {
        // Arrange
        Long clienteId = cliente("33344455566");
        equipamento(clienteId);
        equipamento(clienteId);
        ConsultaEquipamento consulta = consultaEquipamento();
        // Act
        Registros<Equipamento> doCliente = consulta.listarPorCliente(clienteId);
        // Assert
        assertEquals(2, doCliente.quantidade());
    }
}
