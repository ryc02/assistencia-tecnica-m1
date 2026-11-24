package com.assistencia.infra;

import com.assistencia.dao.jdbc.*;
import java.sql.Connection;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

/** Cria as tabelas (schema.sql) e os usuários iniciais (dados.sql) uma única vez. */
final class InicializadorBanco {
    private static final AtomicBoolean INICIALIZADO = new AtomicBoolean(false);
    private static final Logger REGISTRO = Logger.getLogger("assistencia.banco");

    private InicializadorBanco() { }

    static synchronized void inicializarSeNecessario(Connection conexao) {
        if (INICIALIZADO.get()) {
            return;
        }
        try {
            inicializar(conexao);
        } catch (Exception excecao) {
            String detalhe = excecao.getMessage();
            REGISTRO.warning("Erro ao inicializar banco: " + detalhe);
        }
    }

    static void reiniciar() {
        INICIALIZADO.set(false);
    }

    private static void inicializar(Connection conexao) throws Exception {
        ScriptSql.executar(conexao, "/schema.sql");
        Linhas usuarios = ComandoSql.consultar(conexao, "SELECT COUNT(*) AS total FROM usuario");
        if (usuarios.total() == 0) {
            ScriptSql.executar(conexao, "/dados.sql");
        }
        INICIALIZADO.set(true);
    }
}
