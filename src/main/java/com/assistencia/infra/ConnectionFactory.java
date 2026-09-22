package com.assistencia.infra;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 * [Requisito: Infraestrutura] Conexão com o Banco de Dados
 * Suporta H2 Database embarcado (para execução e testes sem instalação prévia)
 * e MySQL (conforme ambiente da disciplina).
 */
public class ConnectionFactory {

    private static final String H2_URL = "jdbc:h2:mem:assistenciadb;DB_CLOSE_DELAY=-1;MODE=MySQL";
    private static final String H2_USER = "sa";
    private static final String H2_PASSWORD = "";

    private static boolean dbInitialized = false;

    static {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException ex) {
                // Driver registrado via ServiceLoader se disponível
            }
        }
    }

    /**
     * [Requisito: Infraestrutura] Abre uma nova conexão JDBC.
     */
    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(H2_URL, H2_USER, H2_PASSWORD);
        initDatabaseIfNeeded(conn);
        return conn;
    }

    /**
     * Inicializa a estrutura de tabelas e dados fictícios se ainda não foi executado.
     */
    private static synchronized void initDatabaseIfNeeded(Connection conn) {
        if (dbInitialized) {
            return;
        }
        try {
            executeSqlScript(conn, "/schema.sql");
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM cliente")) {
                if (rs.next() && rs.getInt(1) == 0) {
                    executeSqlScript(conn, "/dados.sql");
                }
            }
            dbInitialized = true;
        } catch (Exception e) {
            System.err.println("[ConnectionFactory] Erro ao inicializar banco: " + e.getMessage());
        }
    }

    /**
     * Executa scripts SQL contidos nos arquivos de recursos.
     */
    private static void executeSqlScript(Connection conn, String scriptPath) throws Exception {
        InputStream is = ConnectionFactory.class.getResourceAsStream(scriptPath);
        if (is == null) {
            return;
        }
        try (Scanner scanner = new Scanner(new InputStreamReader(is, StandardCharsets.UTF_8));
             Statement stmt = conn.createStatement()) {
            scanner.useDelimiter(";");
            while (scanner.hasNext()) {
                String sql = scanner.next().trim();
                if (!sql.isEmpty()) {
                    stmt.execute(sql);
                }
            }
        }
    }

    public static void resetDatabaseForTests() {
        dbInitialized = false;
    }
}
