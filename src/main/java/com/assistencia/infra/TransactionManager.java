package com.assistencia.infra;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * [Requisito: Automação e Transações] Gerenciador de Transações JDBC
 * Garante o controle de commit e rollback indivisível nas operações do serviço.
 */
public class TransactionManager {

    @FunctionalInterface
    public interface TransactionAction<T> {
        T execute(Connection conn) throws Exception;
    }

    /**
     * [Requisito 7] Executa um bloco de código dentro de uma transação JDBC indivisível.
     */
    public static <T> T executeInTransaction(TransactionAction<T> action) throws Exception {
        Connection conn = ConnectionFactory.getConnection();
        boolean originalAutoCommit = conn.getAutoCommit();
        try {
            conn.setAutoCommit(false);
            T result = action.execute(conn);
            conn.commit();
            return result;
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException rollbackEx) {
                System.err.println("[TransactionManager] Rollback falhou: " + rollbackEx.getMessage());
            }
            throw e;
        } finally {
            try {
                conn.setAutoCommit(originalAutoCommit);
                conn.close();
            } catch (SQLException closeEx) {
                System.err.println("[TransactionManager] Fechamento de conexão falhou: " + closeEx.getMessage());
            }
        }
    }
}
