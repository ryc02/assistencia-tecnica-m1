package com.assistencia.integracao;

import com.assistencia.dados.*;
import com.assistencia.dao.jdbc.*;
import com.assistencia.infra.ConnectionFactory;
import java.sql.Connection;

/** Consultas e comandos SQL diretos no H2 de teste, usados para conferir o estado do banco. */
final class BancoDeTeste {
    private BancoDeTeste() { }

    static Object valor(Entidade entidade, String chave) {
        Campos dados = entidade.exportar();
        return dados.valor(chave);
    }

    static Long id(Entidade entidade) {
        Campos dados = entidade.exportar();
        return dados.inteiroLongo("id");
    }

    static Long contar(String sql, Object... valores) throws Exception {
        try (Connection conexao = ConnectionFactory.abrirConexao()) {
            Linhas linhas = ComandoSql.consultar(conexao, sql, valores);
            return linhas.total();
        }
    }

    static void executar(String sql, Object... valores) throws Exception {
        try (Connection conexao = ConnectionFactory.abrirConexao()) {
            ComandoSql.executar(conexao, sql, valores);
        }
    }
}
