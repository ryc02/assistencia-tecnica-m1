package com.assistencia.infra;

import java.sql.*;
import java.util.Properties;
import static com.assistencia.infra.Ambiente.ou;

/** Usuário e senha do banco de dados, lidos das variáveis de ambiente (propriedades "user" e "password" do JDBC). */
final class Credencial {
    private final Properties propriedades;

    private Credencial(Properties propriedades) {
        this.propriedades = propriedades;
    }

    static Credencial doAmbiente() {
        Properties propriedades = new Properties();
        propriedades.setProperty("user", ou("DB_USER", "sa"));
        propriedades.setProperty("password", ou("DB_PASSWORD", ""));
        return new Credencial(propriedades);
    }

    Connection conectar(String url) throws SQLException {
        return DriverManager.getConnection(url, propriedades);
    }
}
