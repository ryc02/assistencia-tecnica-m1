package com.assistencia.service;

import com.assistencia.dados.Registros;
import com.assistencia.dao.*;
import com.assistencia.model.cliente.Cliente;
import com.assistencia.model.equipamento.Equipamento;
import java.sql.Connection;

/** Consultas dos casos de uso de equipamento; a subclasse RepositorioEquipamento acrescenta a gravação. */
abstract class ConsultasDeEquipamento {
    protected final EquipamentoDAO equipamentos;
    protected final ClienteDAO clientes;

    ConsultasDeEquipamento(EquipamentoDAO equipamentos, ClienteDAO clientes) {
        this.equipamentos = equipamentos;
        this.clientes = clientes;
    }

    Cliente dono(Connection conexao, Long clienteId) throws Exception {
        return ClienteExistente.buscar(clientes, conexao, clienteId);
    }

    Equipamento existente(Connection conexao, Long id) throws Exception {
        return EquipamentoExistente.buscar(equipamentos, conexao, id);
    }

    Long contarOrcamentos(Connection conexao, Long id) throws Exception {
        return equipamentos.contarOrcamentos(conexao, id);
    }

    Registros<Equipamento> listarTodos(Connection conexao) throws Exception {
        return equipamentos.listarTodos(conexao);
    }

    Registros<Equipamento> listarPorCliente(Connection conexao, Long clienteId) throws Exception {
        return equipamentos.listarPorCliente(conexao, clienteId);
    }

    Registros<Cliente> listarClientes(Connection conexao) throws Exception {
        return clientes.listarTodos(conexao);
    }
}
