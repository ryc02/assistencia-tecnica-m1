package com.assistencia.dao.jdbc;

import com.assistencia.builder.EquipamentoBuilder;
import com.assistencia.dados.Campos;
import com.assistencia.dao.ClienteDAO;
import com.assistencia.model.cliente.Cliente;
import com.assistencia.model.equipamento.Equipamento;
import java.sql.Connection;

/** Monta o Equipamento a partir de uma linha da tabela, carregando o Cliente relacionado pelo seu DAO. */
final class MontagemDeEquipamento implements MontagemDeEntidade<Equipamento> {
    private final ClienteDAO clientes;

    MontagemDeEquipamento(ClienteDAO clientes) {
        this.clientes = clientes;
    }

    @Override
    public Equipamento montar(Connection conexao, Campos linha) throws Exception {
        Long clienteId = linha.inteiroLongo("clienteId");
        Cliente cliente = clientes.buscarPorId(conexao, clienteId);
        return new EquipamentoBuilder()
                .dados(linha)
                .cliente(cliente)
                .build();
    }
}
