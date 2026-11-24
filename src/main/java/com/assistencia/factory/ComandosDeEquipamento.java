package com.assistencia.factory;

import com.assistencia.command.impl.*;
import static com.assistencia.factory.FabricaDeClientes.consultaCliente;
import static com.assistencia.factory.FabricaDeEquipamentos.*;
import static com.assistencia.factory.FabricaDeOrcamentos.telasDeOrcamento;

/** Registra os Commands de equipamentos. */
final class ComandosDeEquipamento {
    private ComandosDeEquipamento() { }

    static void registrar(RegistroDeComandos registro) {
        registro.registrar("equipamento.listar", new EquipamentoListarCommand(telasDeEquipamento()));
        registro.registrar("equipamento.consultar",
                new EquipamentoConsultarCommand(telasDeOrcamento(), consultaCliente()));
        registro.registrar("equipamento.inserir", new EquipamentoInserirCommand(equipamentoService()));
        registro.registrar("equipamento.atualizar", new EquipamentoAtualizarCommand(equipamentoService()));
        registro.registrar("equipamento.excluir", new EquipamentoExcluirCommand(equipamentoService()));
    }
}
