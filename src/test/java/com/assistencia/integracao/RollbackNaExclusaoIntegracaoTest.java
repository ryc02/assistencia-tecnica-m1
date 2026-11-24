package com.assistencia.integracao;

import com.assistencia.dao.jdbc.ComandoSql;
import com.assistencia.infra.*;
import java.sql.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;
import static com.assistencia.integracao.BancoDeTeste.*;
import static com.assistencia.integracao.CenarioDeIntegracao.*;
import static org.junit.jupiter.api.Assertions.*;

/** Integração real com o H2: T14 (falha no meio da exclusão desfaz tudo). */
class RollbackNaExclusaoIntegracaoTest {
    @BeforeEach
    void prepararBanco() {
        ConnectionFactory.prepararBancoDeTestes();
    }

    @Test
    @DisplayName("T14: falha no meio da exclusão mantém a ficha gravada")
    void t14FalhaNaExclusao() throws Exception {
        // Arrange
        Long ordemId = aprovar(orcamentoNovo("67867867822"));
        GerenciadorTransacao transacao = new TransactionManager();
        // Act
        Executable acao = () -> transacao.executar(conexao -> excluirEFalhar(conexao, ordemId));
        // Assert
        assertThrows(IllegalStateException.class, acao);
        assertEquals(1L, contar("SELECT COUNT(*) AS total FROM ficha_tecnica WHERE ordem_servico_id = ?", ordemId));
    }

    private static Void excluirEFalhar(Connection conexao, Long ordemId) throws SQLException {
        ComandoSql.executar(conexao, "DELETE FROM ficha_tecnica WHERE ordem_servico_id = ?", ordemId);
        throw new IllegalStateException("Simulação de falha no meio da exclusão");
    }
}
