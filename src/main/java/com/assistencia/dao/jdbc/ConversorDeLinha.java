package com.assistencia.dao.jdbc;

import com.assistencia.dados.Campos;

/** Transforma uma linha lida do banco em uma entidade (normalmente por meio de um Builder). */
@FunctionalInterface
public interface ConversorDeLinha<T> {
    T converter(Campos linha) throws Exception;
}
