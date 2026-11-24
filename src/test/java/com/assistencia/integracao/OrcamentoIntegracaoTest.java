package com.assistencia.integracao;

import com.assistencia.exception.ConflictException;
import com.assistencia.infra.ConnectionFactory;
import com.assistencia.model.orcamento.Orcamento;
import com.assistencia.service.*;
import java.math.BigDecimal;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import static com.assistencia.factory.FabricaDeOrcamentos.*;
import static com.assistencia.integracao.BancoDeTeste.*;
import static com.assistencia.integracao.CenarioDeIntegracao.*;
import static com.assistencia.unitario.FixturesAtendimento.dadosDeOrcamento;
import static org.junit.jupiter.api.Assertions.*;

/** Integração real com o H2: T03 e T10 (total calculado no servidor). */
class OrcamentoIntegracaoTest {
    @BeforeEach
    void prepararBanco() {
        ConnectionFactory.prepararBancoDeTestes();
    }

    @Test
    @DisplayName("T03: orçamento aprovado não pode ser alterado")
    void t03AlterarAprovado() throws Exception {
        // Arrange
        Long id = orcamentoNovo("44455566677");
        aprovar(id);
        OrcamentoService servico = orcamentoService();
        // Act
        Executable acao = () -> servico.atualizar(id, dadosDeOrcamento());
        // Assert
        assertThrows(ConflictException.class, acao);
    }

    @Test
    @DisplayName("T10: R$ 100 de peças + R$ 50 de mão de obra com 10% de desconto = R$ 135,00")
    void t10TotalComDesconto() throws Exception {
        // Arrange
        Long equipamentoId = equipamento(cliente("55566677788"));
        Long id = orcamento(equipamentoId, "10");
        ConsultaOrcamento consulta = consultaOrcamento();
        // Act
        Orcamento gravado = consulta.buscarPorId(id);
        // Assert
        assertEquals(new BigDecimal("135.00"), valor(gravado, "valorTotal"));
    }
}
