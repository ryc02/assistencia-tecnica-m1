package com.assistencia.infra;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Centraliza o controle de transações do banco. 
 * A ideia aqui é garantir que operações complexas, como aprovar um orçamento 
 * e gerar uma ordem de serviço, aconteçam de forma atômica (ou vai tudo, ou não vai nada).
 */
public class TransactionManager {

    @FunctionalInterface
    public interface TransactionAction<T> {
        T execute(Connection conn) throws Exception;
    }

    /**
     * Recebe um bloco de código (ação) e o executa dentro de uma transação.
     * Se der qualquer erro no meio do caminho, ele faz o rollback automático.
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
