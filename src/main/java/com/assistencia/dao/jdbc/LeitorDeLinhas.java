package com.assistencia.dao.jdbc;

import com.assistencia.dados.Campos;
import java.sql.*;
import java.util.*;

/** Lê o ResultSet e transforma cada linha em Campos (coluna data_cadastro vira a chave dataCadastro). */
final class LeitorDeLinhas {
    private LeitorDeLinhas() { }

    static Linhas ler(ResultSet resultado) throws SQLException {
        List<Campos> linhas = new ArrayList<>();
        while (resultado.next()) {
            linhas.add(linha(resultado));
        }
        return new Linhas(linhas);
    }

    private static Campos linha(ResultSet resultado) throws SQLException {
        Campos linha = new Campos();
        ResultSetMetaData metadados = resultado.getMetaData();
        for (int coluna = 1; coluna <= metadados.getColumnCount(); coluna++) {
            String rotulo = metadados.getColumnLabel(coluna);
            String chave = NomeDeColuna.paraChave(rotulo);
            linha.com(chave, valor(resultado, coluna));
        }
        return linha;
    }

    private static Object valor(ResultSet resultado, int coluna) throws SQLException {
        Object valor = resultado.getObject(coluna);
        if (valor instanceof Timestamp) {
            return ((Timestamp) valor).toLocalDateTime();
        }
        return valor;
    }
}
