package com.assistencia.integracao;

import com.assistencia.infra.ConnectionFactory;
import com.assistencia.model.orcamento.Orcamento;
import com.assistencia.service.*;
import org.junit.jupiter.api.*;
import static com.assistencia.factory.FabricaDeAtendimentos.*;
import static com.assistencia.factory.FabricaDeOrcamentos.consultaOrcamento;
import static com.assistencia.integracao.BancoDeTeste.*;
import static com.assistencia.integracao.CenarioDeIntegracao.*;
import static com.assistencia.model.orcamento.StatusOrcamento.PENDENTE;
import static org.junit.jupiter.api.Assertions.*;

/** Integração real com o H2: T06 (excluir ordem ABERTA restaura o orçamento). */
class ExclusaoDeOrdemIntegracaoTest {
    @BeforeEach
    void prepararBanco() {
        ConnectionFactory.prepararBancoDeTestes();
    }

    @Test
    @DisplayName("T06: excluir ordem ABERTA remove ficha e ordem e volta o orçamento para PENDENTE")
    void t06ExcluirOrdemAberta() throws Exception {
        // Arrange
        Long orcamentoId = orcamentoNovo("23423423411");
        Long ordemId = aprovar(orcamentoId);
        AprovacaoService servico = aprovacaoService();
        ConsultaOrcamento consulta = consultaOrcamento();
        // Act
        servico.excluirOrdemAberta(ordemId);
        // Assert
        Orcamento restaurado = consulta.buscarPorId(orcamentoId);
        assertEquals(PENDENTE, valor(restaurado, "status"));
        assertEquals(0L, contar("SELECT COUNT(*) AS total FROM ficha_tecnica WHERE ordem_servico_id = ?", ordemId));
    }
}
