package com.assistencia.command.impl;

import com.assistencia.command.*;
import com.assistencia.dados.Campos;
import com.assistencia.service.OrcamentoService;
import javax.servlet.http.*;

/** [Command - Aula 07] Atualiza um orçamento PENDENTE. */
public class OrcamentoAtualizarCommand implements ICommand {
    private final OrcamentoService servico;

    public OrcamentoAtualizarCommand(OrcamentoService servico) {
        this.servico = servico;
    }

    @Override
    public String execute(HttpServletRequest requisicao, HttpServletResponse resposta) throws Exception {
        Long id = ParametrosHttp.idObrigatorio(requisicao, "id", "orçamento");
        Campos dados = ParametrosHttp.campos(requisicao);
        servico.atualizar(id, dados);
        return Destino.redirecionar("orcamento.consultar", id);
    }
}
