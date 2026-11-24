package com.assistencia.integracao;

import com.assistencia.infra.ConnectionFactory;
import java.util.List;
import java.util.concurrent.*;
import org.junit.jupiter.api.*;
import static com.assistencia.integracao.BancoDeTeste.*;
import static com.assistencia.integracao.CenarioDeIntegracao.*;
import static java.util.Collections.nCopies;
import static java.util.concurrent.Executors.newFixedThreadPool;
import static org.junit.jupiter.api.Assertions.assertEquals;

/** Integração real com o H2: T12 (aprovações concorrentes do mesmo orçamento). */
class ConcorrenciaIntegracaoTest {
    private static final int TENTATIVAS = 5;

    @BeforeEach
    void prepararBanco() {
        ConnectionFactory.prepararBancoDeTestes();
    }

    @Test
    @DisplayName("T12: aprovações concorrentes persistem apenas uma ordem e uma ficha")
    void t12AprovacoesConcorrentes() throws Exception {
        // Arrange
        Long orcamentoId = orcamentoNovo("56756756711");
        Callable<Long> aprovacao = () -> aprovar(orcamentoId);
        List<Callable<Long>> tarefas = nCopies(TENTATIVAS, aprovacao);
        ExecutorService executor = newFixedThreadPool(TENTATIVAS);
        // Act
        executor.invokeAll(tarefas);
        executor.shutdown();
        // Assert
        assertEquals(1L, contar("SELECT COUNT(*) AS total FROM ordem_servico WHERE orcamento_id = ?", orcamentoId));
        assertEquals(1L, contar("SELECT COUNT(*) AS total FROM ficha_tecnica"));
    }
}
