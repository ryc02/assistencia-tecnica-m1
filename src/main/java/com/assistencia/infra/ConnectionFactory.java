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

    private static final String DEFAULT_H2_USER = "sa";
    private static final String DEFAULT_H2_PASSWORD = "";

    private static boolean dbInitialized = false;
    private static String testDbUrl = null;

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
     * Prioridade: testDbUrl (testes) > DB_URL (env) > DATABASE_FILE (H2 arquivo) > H2 arquivo padrão.
     */
    public static Connection getConnection() throws SQLException {
        String url;
        if (testDbUrl != null) {
            url = testDbUrl;
        } else {
            url = System.getenv("DB_URL");
            if (url == null || url.trim().isEmpty()) {
                String dbFile = System.getenv("DATABASE_FILE");
                if (dbFile == null || dbFile.trim().isEmpty()) {
                    dbFile = "./data/assistenciadb";
                }
                url = "jdbc:h2:file:" + dbFile + ";DB_CLOSE_DELAY=-1;MODE=MySQL;AUTO_SERVER=TRUE";
            }
        }
        
        String user = System.getenv("DB_USER");
        if (user == null) user = DEFAULT_H2_USER;
        
        String password = System.getenv("DB_PASSWORD");
        if (password == null) password = DEFAULT_H2_PASSWORD;

        Connection conn = DriverManager.getConnection(url, user, password);
        initDatabaseIfNeeded(conn);
        return conn;
    }

    /**
     * Inicializa a estrutura de tabelas e dados iniciais se ainda não foi executado.
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

    /**
     * Reseta estado para testes. Usa H2 em memória para não criar arquivos no disco.
     */
    public static void resetDatabaseForTests() {
        testDbUrl = "jdbc:h2:mem:testdb_" + System.nanoTime() + ";DB_CLOSE_DELAY=-1;MODE=MySQL";
        dbInitialized = false;
    }
}

