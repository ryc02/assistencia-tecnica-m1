package com.assistencia.integracao;

import com.assistencia.infra.ConnectionFactory;
import com.assistencia.model.ficha.FichaTecnica;
import com.assistencia.model.orcamento.Orcamento;
import com.assistencia.service.*;
import org.junit.jupiter.api.*;
import static com.assistencia.factory.FabricaDeAtendimentos.consultaAtendimento;
import static com.assistencia.factory.FabricaDeOrcamentos.consultaOrcamento;
import static com.assistencia.integracao.BancoDeTeste.*;
import static com.assistencia.integracao.CenarioDeIntegracao.*;
import static com.assistencia.model.orcamento.StatusOrcamento.APROVADO;
import static org.junit.jupiter.api.Assertions.assertEquals;

/** Integração real com o H2: T04 e T11 (Orçamento 1:1 Ordem 1:1 Ficha). */
class AprovacaoIntegracaoTest {
    @BeforeEach
    void prepararBanco() {
        ConnectionFactory.prepararBancoDeTestes();
    }

    @Test
    @DisplayName("T04: aprovar orçamento gera o par Ordem de Serviço e Ficha Técnica")
    void t04AprovarGeraPar() throws Exception {
        // Arrange
        Long orcamentoId = orcamentoNovo("12312312311");
        ConsultaAtendimento atendimentos = consultaAtendimento();
        ConsultaOrcamento orcamentos = consultaOrcamento();
        // Act
        Long ordemId = aprovar(orcamentoId);
        // Assert
        FichaTecnica ficha = atendimentos.fichaDaOrdem(ordemId);
        Orcamento aprovado = orcamentos.buscarPorId(orcamentoId);
        assertEquals(ordemId, valor(ficha, "ordemServicoId"));
        assertEquals(APROVADO, valor(aprovado, "status"));
    }

    @Test
    @DisplayName("T11: aprovar novamente é idempotente e devolve a mesma ordem")
    void t11AprovarNovamente() throws Exception {
        // Arrange
        Long orcamentoId = orcamentoNovo("12312312322");
        Long primeira = aprovar(orcamentoId);
        // Act
        Long segunda = aprovar(orcamentoId);
        // Assert
        assertEquals(primeira, segunda);
    }
}
