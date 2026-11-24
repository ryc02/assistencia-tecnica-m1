package com.assistencia.infra;

import java.sql.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Abre conexões JDBC (H2 embarcado por padrão ou o banco definido por variáveis de ambiente) e garante que as
 * tabelas existam antes do primeiro uso.
 */
public final class ConnectionFactory {
    private static final AtomicReference<String> URL_DE_TESTE = new AtomicReference<>();

    private ConnectionFactory() { }

    public static Connection abrirConexao() throws SQLException {
        String urlDeTeste = URL_DE_TESTE.get();
        ConfiguracaoConexao configuracao = ConfiguracaoConexao.atual(urlDeTeste);
        Connection conexao = configuracao.conectar();
        InicializadorBanco.inicializarSeNecessario(conexao);
        return conexao;
    }

    /** Usa um banco H2 novo em memória (testes de integração). */
    public static void prepararBancoDeTestes() {
        long instante = System.nanoTime();
        URL_DE_TESTE.set("jdbc:h2:mem:testdb_" + instante + ";DB_CLOSE_DELAY=-1;MODE=MySQL");
        InicializadorBanco.reiniciar();
    }
}
