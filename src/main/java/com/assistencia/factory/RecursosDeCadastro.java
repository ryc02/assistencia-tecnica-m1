package com.assistencia.factory;

import com.assistencia.api.recurso.*;
import static com.assistencia.factory.FabricaDeClientes.*;
import static com.assistencia.factory.FabricaDeEquipamentos.*;
import static com.assistencia.factory.RecursoFactory.*;

/** Registra os recursos REST de clientes e equipamentos (CRUD completo). */
final class RecursosDeCadastro {
    private RecursosDeCadastro() { }

    static void registrar() {
        ClienteRecurso clientes = new ClienteRecurso(clienteService(), consultaCliente());
        CONSULTAVEIS.registrar("clientes", clientes);
        CRIAVEIS.registrar("clientes", clientes);
        ATUALIZAVEIS.registrar("clientes", clientes);
        EXCLUIVEIS.registrar("clientes", clientes);
        EquipamentoRecurso equipamentos = new EquipamentoRecurso(equipamentoService(), consultaEquipamento());
        CONSULTAVEIS.registrar("equipamentos", equipamentos);
        CRIAVEIS.registrar("equipamentos", equipamentos);
        ATUALIZAVEIS.registrar("equipamentos", equipamentos);
        EXCLUIVEIS.registrar("equipamentos", equipamentos);
    }
}
