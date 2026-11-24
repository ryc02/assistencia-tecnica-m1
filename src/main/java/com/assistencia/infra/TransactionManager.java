package com.assistencia.infra;

import java.sql.*;
import java.util.logging.Logger;

/**

 * Executa um bloco de código dentro de uma transação JDBC: tudo é confirmado (commit) ou, em caso de erro, tudo

 * é desfeito (rollback). Ex.: aprovar orçamento e gerar a ordem.

 */
public class TransactionManager implements GerenciadorTransacao {
    private static final Logger REGISTRO = Logger.getLogger("assistencia.transacao");

    @Override
    public <T> T executar(Acao<T> acao) throws Exception {
        try (Connection conexao = ConnectionFactory.abrirConexao()) {
            conexao.setAutoCommit(false);
            return executarNa(conexao, acao);
        }
    }

    private <T> T executarNa(Connection conexao, Acao<T> acao) throws Exception {
        try {
            T resultado = acao.executar(conexao);
            conexao.commit();
            return resultado;
        } catch (Exception excecao) {
            desfazer(conexao);
            throw excecao;
        }
    }

    private void desfazer(Connection conexao) {
        try {
            conexao.rollback();
        } catch (SQLException falha) {
            String detalhe = falha.getMessage();
            REGISTRO.warning("Rollback falhou: " + detalhe);
        }
    }
}
