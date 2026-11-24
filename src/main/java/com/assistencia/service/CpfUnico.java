package com.assistencia.service;

import com.assistencia.dados.Campos;
import com.assistencia.dao.ClienteDAO;
import com.assistencia.exception.ConflictException;
import com.assistencia.model.cliente.Cliente;
import java.sql.Connection;

/** Regra de negócio: dois clientes não podem ter o mesmo CPF. */
final class CpfUnico {
    private CpfUnico() { }

    static void exigir(ClienteDAO clientes, Connection conexao, Cliente cliente, String mensagem) throws Exception {
        Campos dados = cliente.exportar();
        Long id = dados.inteiroLongo("id");
        String cpf = dados.texto("cpf");
        Cliente comMesmoCpf = clientes.buscarPorCpf(conexao, cpf);
        if (comMesmoCpf != null && !comMesmoCpf.possuiId(id)) {
            throw new ConflictException(mensagem);
        }
    }
}
