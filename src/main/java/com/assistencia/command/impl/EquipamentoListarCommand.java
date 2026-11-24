package com.assistencia.command.impl;

import com.assistencia.command.*;
import com.assistencia.service.*;
import javax.servlet.http.*;

/** [Command - Aula 07] Lista os equipamentos (todos ou de um cliente). */
public class EquipamentoListarCommand implements ICommand {
    private final TelasDeEquipamento telas;

    public EquipamentoListarCommand(TelasDeEquipamento telas) {
        this.telas = telas;
    }

    @Override
    public String execute(HttpServletRequest requisicao, HttpServletResponse resposta) throws Exception {
        Long clienteId = ParametrosHttp.id(requisicao, "clienteId");
        Tela tela = telas.listagem(clienteId);
        tela.publicar(requisicao::setAttribute);
        return Destino.view("equipamento-listar");
    }
}
