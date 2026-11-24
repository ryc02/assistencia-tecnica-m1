package com.assistencia.dao.jdbc;

import com.assistencia.dados.Campos;
import java.sql.Connection;

/** Monta uma entidade a partir de uma linha do banco (pode buscar entidades relacionadas na mesma conexão). */
@FunctionalInterface
public interface MontagemDeEntidade<T> {
    T montar(Connection conexao, Campos linha) throws Exception;
}
