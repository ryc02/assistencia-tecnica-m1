package com.assistencia.factory;

import com.assistencia.command.impl.*;
import static com.assistencia.factory.FabricaDeClientes.*;
import static com.assistencia.factory.FabricaDeEquipamentos.telasDeEquipamento;

/** Registra os Commands de clientes. */
final class ComandosDeCliente {
    private ComandosDeCliente() { }

    static void registrar(RegistroDeComandos registro) {
        registro.registrar("cliente.listar", new ClienteListarCommand(consultaCliente()));
        registro.registrar("cliente.consultar", new ClienteConsultarCommand(telasDeEquipamento()));
        registro.registrar("cliente.inserir", new ClienteInserirCommand(clienteService()));
        registro.registrar("cliente.atualizar", new ClienteAtualizarCommand(clienteService()));
        registro.registrar("cliente.excluir", new ClienteExcluirCommand(clienteService()));
    }
}
