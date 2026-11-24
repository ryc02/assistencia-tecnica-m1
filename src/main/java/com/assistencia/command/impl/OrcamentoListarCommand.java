package com.assistencia.command.impl;

import com.assistencia.command.*;
import com.assistencia.service.*;
import javax.servlet.http.*;

/** [Command - Aula 07] Lista os orçamentos (todos ou de um equipamento). */
public class OrcamentoListarCommand implements ICommand {
    private final TelasDeOrcamento telas;

    public OrcamentoListarCommand(TelasDeOrcamento telas) {
        this.telas = telas;
    }

    @Override
    public String execute(HttpServletRequest requisicao, HttpServletResponse resposta) throws Exception {
        Long equipamentoId = ParametrosHttp.id(requisicao, "equipamentoId");
        Tela tela = telas.listagem(equipamentoId);
        tela.publicar(requisicao::setAttribute);
        return Destino.view("orcamento-listar");
    }
}
