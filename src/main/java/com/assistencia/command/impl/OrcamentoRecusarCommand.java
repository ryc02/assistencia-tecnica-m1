package com.assistencia.command.impl;

import com.assistencia.command.*;
import com.assistencia.service.GestaoOrcamentoService;
import javax.servlet.http.*;

/** [Command - Aula 07] Recusa um orçamento PENDENTE. */
public class OrcamentoRecusarCommand implements ICommand {
    private final GestaoOrcamentoService servico;

    public OrcamentoRecusarCommand(GestaoOrcamentoService servico) {
        this.servico = servico;
    }

    @Override
    public String execute(HttpServletRequest requisicao, HttpServletResponse resposta) throws Exception {
        Long id = ParametrosHttp.idObrigatorio(requisicao, "id", "orçamento");
        servico.recusar(id);
        return Destino.redirecionar("orcamento.consultar", id);
    }
}
