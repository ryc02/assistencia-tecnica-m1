package com.assistencia.infra;

import java.io.InputStream;
import java.sql.*;
import java.util.Scanner;
import static java.nio.charset.StandardCharsets.UTF_8;

/** Executa um arquivo .sql dos recursos do projeto, comando por comando (separados por ";"). */
final class ScriptSql {
    private ScriptSql() { }

    static void executar(Connection conexao, String caminho) throws SQLException {
        Class<ScriptSql> classe = ScriptSql.class;
        InputStream entrada = classe.getResourceAsStream(caminho);
        if (entrada == null) {
            return;
        }
        try (Scanner leitor = new Scanner(entrada, UTF_8); Statement comando = conexao.createStatement()) {
            leitor.useDelimiter(";");
            executarComandos(leitor, comando);
        }
    }

    private static void executarComandos(Scanner leitor, Statement comando) throws SQLException {
        while (leitor.hasNext()) {
            String instrucao = leitor.next();
            executarUm(comando, instrucao);
        }
    }

    private static void executarUm(Statement comando, String instrucao) throws SQLException {
        String limpa = instrucao.trim();
        if (!limpa.isEmpty()) {
            comando.execute(limpa);
        }
    }
}
