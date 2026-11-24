package com.assistencia.dao.jdbc;

import com.assistencia.dados.*;
import java.sql.Connection;

/** Executa consultas e transforma as linhas em entidades usando a montagem informada (reuso entre os DAOs). */
final class LeitorDeEntidades<T extends Entidade> {
    private final MontagemDeEntidade<T> montagem;

    LeitorDeEntidades(MontagemDeEntidade<T> montagem) {
        this.montagem = montagem;
    }

    T um(Connection conexao, String sql, Object... valores) throws Exception {
        Linhas linhas = ComandoSql.consultar(conexao, sql, valores);
        return linhas.primeira(conversor(conexao));
    }

    Registros<T> varios(Connection conexao, String sql, Object... valores) throws Exception {
        Linhas linhas = ComandoSql.consultar(conexao, sql, valores);
        return linhas.converter(conversor(conexao));
    }

    private ConversorDeLinha<T> conversor(Connection conexao) {
        return linha -> montagem.montar(conexao, linha);
    }
}
