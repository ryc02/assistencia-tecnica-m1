package com.assistencia.service;

import com.assistencia.dao.ClienteDAO;
import com.assistencia.exception.ConflictException;
import com.assistencia.model.cliente.Cliente;
import java.sql.Connection;

/** Busca um cliente que precisa existir (404 quando não encontrado) e confere se pode ser excluído. */
final class ClienteExistente {
    private ClienteExistente() { }

    static Cliente buscar(ClienteDAO clientes, Connection conexao, Long id) throws Exception {
        Existencia.exigirId(id, "cliente");
        Cliente cliente = clientes.buscarPorId(conexao, id);
        return Existencia.exigir(cliente, "Cliente não encontrado com ID: " + id);
    }

    static void exigirSemEquipamentos(ClienteDAO clientes, Connection conexao, Long id) throws Exception {
        buscar(clientes, conexao, id);
        Long equipamentos = clientes.contarEquipamentos(conexao, id);
        if (equipamentos > 0) {
            throw new ConflictException("Não é possível excluir o cliente pois ele possui " + equipamentos
                    + " equipamento(s) cadastrado(s).");
        }
    }
}
