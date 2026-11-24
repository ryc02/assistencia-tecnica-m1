package com.assistencia.command.impl;

import com.assistencia.command.*;
import com.assistencia.service.GestaoOrcamentoService;
import javax.servlet.http.*;

/** [Command - Aula 07] Exclui um orçamento sem ordem de serviço. */
public class OrcamentoExcluirCommand implements ICommand {
    private final GestaoOrcamentoService servico;

    public OrcamentoExcluirCommand(GestaoOrcamentoService servico) {
        this.servico = servico;
    }

    @Override
    public String execute(HttpServletRequest requisicao, HttpServletResponse resposta) throws Exception {
        Long id = ParametrosHttp.idObrigatorio(requisicao, "id", "orçamento");
        servico.excluir(id);
        return Destino.redirecionar("orcamento.listar");
    }
}
