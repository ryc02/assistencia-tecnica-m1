package com.assistencia.service;

import com.assistencia.builder.ClienteBuilder;
import com.assistencia.dados.Campos;
import com.assistencia.dao.ClienteDAO;
import com.assistencia.model.cliente.Cliente;
import java.sql.Connection;
import java.time.LocalDateTime;

/** Monta o Cliente (via Builder) para cadastro ou alteração, preservando a data de cadastro original. */
final class ClienteMontado {
    private ClienteMontado() { }

    static Cliente novo(Campos dados) {
        return montar(dados, null, null);
    }

    static Cliente atualizado(ClienteDAO clientes, Connection conexao, Long id, Campos dados) throws Exception {
        Cliente existente = ClienteExistente.buscar(clientes, conexao, id);
        Campos atuais = existente.exportar();
        return montar(dados, id, atuais.dataHora("dataCadastro"));
    }

    private static Cliente montar(Campos dados, Long id, LocalDateTime dataCadastro) {
        return new ClienteBuilder()
                .dados(dados)
                .id(id)
                .dataCadastro(dataCadastro)
                .build();
    }
}
