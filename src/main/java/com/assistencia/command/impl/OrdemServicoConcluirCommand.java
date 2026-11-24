package com.assistencia.command.impl;

import com.assistencia.command.*;
import com.assistencia.service.OrdemServicoService;
import javax.servlet.http.*;

/** [Command - Aula 07] Conclui a ordem (EM_ANDAMENTO para CONCLUIDA). */
public class OrdemServicoConcluirCommand implements ICommand {
    private final OrdemServicoService servico;

    public OrdemServicoConcluirCommand(OrdemServicoService servico) {
        this.servico = servico;
    }

    @Override
    public String execute(HttpServletRequest requisicao, HttpServletResponse resposta) throws Exception {
        Long id = ParametrosHttp.idObrigatorio(requisicao, "id", "ordem de serviço");
        servico.concluir(id);
        return Destino.redirecionar("ordemServico.consultar", id);
    }
}
