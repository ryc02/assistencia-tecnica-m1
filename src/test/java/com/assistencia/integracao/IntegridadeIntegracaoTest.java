package com.assistencia.integracao;

import com.assistencia.infra.ConnectionFactory;
import com.assistencia.service.AprovacaoService;
import org.junit.jupiter.api.*;
import static com.assistencia.factory.FabricaDeAtendimentos.aprovacaoService;
import static com.assistencia.integracao.BancoDeTeste.*;
import static com.assistencia.integracao.CenarioDeIntegracao.*;
import static org.junit.jupiter.api.Assertions.*;

/** Integração real com o H2: T20 (nenhuma ordem sem ficha e nenhuma ficha órfã). */
class IntegridadeIntegracaoTest {
    private static final String ORDENS_SEM_FICHA = "SELECT COUNT(*) AS total FROM ordem_servico o "
            + "LEFT JOIN ficha_tecnica f ON f.ordem_servico_id = o.id WHERE f.id IS NULL";
    private static final String FICHAS_ORFAS = "SELECT COUNT(*) AS total FROM ficha_tecnica f "
            + "LEFT JOIN ordem_servico o ON o.id = f.ordem_servico_id WHERE o.id IS NULL";

    @BeforeEach
    void prepararBanco() {
        ConnectionFactory.prepararBancoDeTestes();
    }

    @Test
    @DisplayName("T20: nenhuma ordem sem ficha e nenhuma ficha órfã após aprovações e exclusões")
    void t20InvarianteFinal() throws Exception {
        // Arrange
        aprovar(orcamentoNovo("45645645622"));
        Long excluida = aprovar(orcamentoNovo("45645645633"));
        AprovacaoService servico = aprovacaoService();
        // Act
        servico.excluirOrdemAberta(excluida);
        // Assert
        assertEquals(0L, contar(ORDENS_SEM_FICHA));
        assertEquals(0L, contar(FICHAS_ORFAS));
    }
}
