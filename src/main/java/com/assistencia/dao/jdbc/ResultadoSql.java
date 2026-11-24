package com.assistencia.dao.jdbc;

import java.sql.*;

/** Lê o resultado de um PreparedStatement já executado: linhas da consulta ou a chave gerada no INSERT. */
final class ResultadoSql {
    private ResultadoSql() { }

    static Linhas linhas(PreparedStatement comando) throws SQLException {
        try (ResultSet resultado = comando.executeQuery()) {
            return LeitorDeLinhas.ler(resultado);
        }
    }

    static Long chaveGerada(PreparedStatement comando) throws SQLException {
        try (ResultSet chaves = comando.getGeneratedKeys()) {
            chaves.next();
            return chaves.getLong(1);
        }
    }
}
