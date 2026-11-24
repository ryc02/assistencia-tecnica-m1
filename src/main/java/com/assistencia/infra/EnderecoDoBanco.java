package com.assistencia.infra;

import java.sql.*;

/** URL JDBC do banco de dados (H2 em arquivo, H2 em memória nos testes ou a informada em DB_URL). */
final class EnderecoDoBanco {
    private final String url;

    EnderecoDoBanco(String url) {
        this.url = url;
    }

    Connection conectar(Credencial credencial) throws SQLException {
        return credencial.conectar(url);
    }
}
