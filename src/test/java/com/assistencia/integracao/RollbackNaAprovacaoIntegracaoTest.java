package com.assistencia.integracao;

import com.assistencia.Dados;
import com.assistencia.dados.Campos;
import com.assistencia.exception.ValidationException;
import com.assistencia.infra.*;
import com.assistencia.service.AprovacaoService;
import java.sql.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import static com.assistencia.factory.FabricaDeAtendimentos.aprovacaoService;
import static com.assistencia.integracao.BancoDeTeste.*;
import static com.assistencia.integracao.CenarioDeIntegracao.*;
import static org.junit.jupiter.api.Assertions.*;

/** Integração real com o H2: T13 (falha na ficha desfaz toda a aprovação). */
class RollbackNaAprovacaoIntegracaoTest {
    @BeforeEach
    void prepararBanco() {
        ConnectionFactory.prepararBancoDeTestes();
    }

    @Test
    @DisplayName("T13: falha ao criar a ficha desfaz a ordem e mantém o orçamento PENDENTE")
    void t13FalhaNaFicha() throws Exception {
        // Arrange
        Long orcamentoId = orcamentoNovo("67867867811");
        Campos ordem = Dados.de("responsavel", "Técnico Lucas");
        Campos fichaInvalida = Dados.de("possuiAvarias", true);
        AprovacaoService servico = aprovacaoService();
        // Act
        Executable acao = () -> servico.aprovarOrcamento(orcamentoId, ordem, fichaInvalida);
        // Assert
        assertThrows(ValidationException.class, acao);
        assertEquals(0L, contar("SELECT COUNT(*) AS total FROM ordem_servico"));
        assertEquals(1L, contar("SELECT COUNT(*) AS total FROM orcamento WHERE status = 'PENDENTE'"));
    }
}
