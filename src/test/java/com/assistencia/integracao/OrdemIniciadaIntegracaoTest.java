package com.assistencia.integracao;

import com.assistencia.exception.ConflictException;
import com.assistencia.infra.ConnectionFactory;
import com.assistencia.service.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import static com.assistencia.factory.FabricaDeAtendimentos.*;
import static com.assistencia.integracao.BancoDeTeste.*;
import static com.assistencia.integracao.CenarioDeIntegracao.*;
import static org.junit.jupiter.api.Assertions.*;

/** Integração real com o H2: T07 (ordem iniciada não pode ser excluída). */
class OrdemIniciadaIntegracaoTest {
    @BeforeEach
    void prepararBanco() {
        ConnectionFactory.prepararBancoDeTestes();
    }

    @Test
    @DisplayName("T07: excluir ordem já iniciada é bloqueado")
    void t07ExcluirOrdemIniciada() throws Exception {
        // Arrange
        Long ordemId = aprovar(orcamentoNovo("23423423422"));
        OrdemServicoService ordens = ordemServicoService();
        ordens.iniciar(ordemId);
        AprovacaoService servico = aprovacaoService();
        // Act
        Executable acao = () -> servico.excluirOrdemAberta(ordemId);
        // Assert
        assertThrows(ConflictException.class, acao);
    }
}
