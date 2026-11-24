package com.assistencia.dao.jdbc;

import com.assistencia.dados.Campos;
import java.sql.*;
import static java.sql.Statement.RETURN_GENERATED_KEYS;

/**
 * Executa comandos SQL com PreparedStatement e try-with-resources, centralizando o código repetido dos DAOs (abrir
 * comando, preencher parâmetros, ler resultado, fechar recursos).
 */
public final class ComandoSql {
    private ComandoSql() { }

    public static Long inserir(Connection conexao, String sql, Campos dados, String... chaves) throws SQLException {
        try (PreparedStatement comando = conexao.prepareStatement(sql, RETURN_GENERATED_KEYS)) {
            ParametrosSql.vincularCampos(comando, dados, chaves);
            comando.executeUpdate();
            return ResultadoSql.chaveGerada(comando);
        }
    }

    public static void alterar(Connection conexao, String sql, Campos dados, String... chaves) throws SQLException {
        try (PreparedStatement comando = conexao.prepareStatement(sql)) {
            ParametrosSql.vincularCampos(comando, dados, chaves);
            comando.executeUpdate();
        }
    }

    public static void executar(Connection conexao, String sql, Object... valores) throws SQLException {
        try (PreparedStatement comando = conexao.prepareStatement(sql)) {
            ParametrosSql.vincularValores(comando, valores);
            comando.executeUpdate();
        }
    }

    public static Linhas consultar(Connection conexao, String sql, Object... valores) throws SQLException {
        try (PreparedStatement comando = conexao.prepareStatement(sql)) {
            ParametrosSql.vincularValores(comando, valores);
            return ResultadoSql.linhas(comando);
        }
    }
}
