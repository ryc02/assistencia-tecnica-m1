package com.assistencia.dao.jdbc;

import com.assistencia.dados.Campos;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

/** Preenche os parâmetros "?" do PreparedStatement (proteção contra SQL Injection). */
final class ParametrosSql {
    private ParametrosSql() { }

    static void vincularCampos(PreparedStatement comando, Campos dados, String[] chaves) throws SQLException {
        Object[] valores = new Object[chaves.length];
        for (int indice = 0; indice < chaves.length; indice++) {
            valores[indice] = dados.valor(chaves[indice]);
        }
        vincularValores(comando, valores);
    }

    static void vincularValores(PreparedStatement comando, Object[] valores) throws SQLException {
        for (int indice = 0; indice < valores.length; indice++) {
            comando.setObject(indice + 1, paraBanco(valores[indice]));
        }
    }

    private static Object paraBanco(Object valor) {
        if (valor instanceof LocalDateTime) {
            return Timestamp.valueOf((LocalDateTime) valor);
        }
        if (valor instanceof Enum) {
            return ((Enum<?>) valor).name();
        }
        if (valor instanceof List) {
            return TextoDeLista.juntar((List<?>) valor);
        }
        return valor;
    }
}
