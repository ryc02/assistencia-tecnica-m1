package com.assistencia.integracao;

import com.assistencia.exception.*;
import com.assistencia.infra.ConnectionFactory;
import com.assistencia.service.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.function.Executable;
import static com.assistencia.factory.FabricaDeClientes.*;
import static com.assistencia.integracao.BancoDeTeste.*;
import static com.assistencia.integracao.CenarioDeIntegracao.*;
import static org.junit.jupiter.api.Assertions.*;

/** Integração real com o H2: T15 (cliente com equipamento não pode ser excluído). */
class ExclusaoDeClienteIntegracaoTest {
    @BeforeEach
    void prepararBanco() {
        ConnectionFactory.prepararBancoDeTestes();
    }

    @Test
    @DisplayName("T15: excluir cliente com equipamento é bloqueado")
    void t15ExcluirClienteComEquipamento() throws Exception {
        // Arrange
        Long id = cliente("22233344455");
        equipamento(id);
        ClienteService servico = clienteService();
        // Act
        Executable acao = () -> servico.excluir(id);
        // Assert
        assertThrows(ConflictException.class, acao);
    }
}
