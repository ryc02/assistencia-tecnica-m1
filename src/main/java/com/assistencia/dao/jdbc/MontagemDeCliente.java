package com.assistencia.dao.jdbc;

import com.assistencia.builder.ClienteBuilder;
import com.assistencia.dados.Campos;
import com.assistencia.model.cliente.Cliente;
import java.sql.Connection;

/** Monta o Cliente a partir de uma linha da tabela. */
final class MontagemDeCliente implements MontagemDeEntidade<Cliente> {
    @Override
    public Cliente montar(Connection conexao, Campos linha) {
        return new ClienteBuilder()
                .dados(linha)
                .build();
    }
}
