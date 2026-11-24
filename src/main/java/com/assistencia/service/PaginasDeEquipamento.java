package com.assistencia.service;

import com.assistencia.dados.Registros;
import com.assistencia.model.cliente.Cliente;
import com.assistencia.model.equipamento.Equipamento;
import java.sql.Connection;

/** Conteúdo das telas de equipamento, montado dentro da transação aberta por TelasDeEquipamento. */
final class PaginasDeEquipamento {
    private PaginasDeEquipamento() { }

    static Tela paginaDeListagem(RepositorioEquipamento repositorio, Connection conexao, Long clienteId)
            throws Exception {
        Tela tela = new Tela();
        Registros<Cliente> clientes = repositorio.listarClientes(conexao);
        tela.comRegistros("clientes", clientes);
        if (clienteId == null) {
            Registros<Equipamento> todos = repositorio.listarTodos(conexao);
            return tela.comRegistros("equipamentos", todos);
        }
        Cliente filtro = repositorio.dono(conexao, clienteId);
        tela.comEntidade("clienteFiltro", filtro);
        return comEquipamentosDoCliente(repositorio, conexao, tela, clienteId);
    }

    static Tela paginaDoCliente(RepositorioEquipamento repositorio, Connection conexao, Long clienteId)
            throws Exception {
        Cliente cliente = repositorio.dono(conexao, clienteId);
        Tela tela = new Tela();
        tela.comEntidade("cliente", cliente);
        return comEquipamentosDoCliente(repositorio, conexao, tela, clienteId);
    }

    private static Tela comEquipamentosDoCliente(RepositorioEquipamento repositorio, Connection conexao, Tela tela,
                                                 Long clienteId) throws Exception {
        Registros<Equipamento> doCliente = repositorio.listarPorCliente(conexao, clienteId);
        return tela.comRegistros("equipamentos", doCliente);
    }
}
