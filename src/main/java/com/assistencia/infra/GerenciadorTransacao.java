package com.assistencia.infra;

import java.sql.Connection;

/** [DIP - Aula 14] Abstração do controle de transações. */
public interface GerenciadorTransacao {
    @FunctionalInterface
    interface Acao<T> {
        T executar(Connection conexao) throws Exception;
    }

    <T> T executar(Acao<T> acao) throws Exception;
}
