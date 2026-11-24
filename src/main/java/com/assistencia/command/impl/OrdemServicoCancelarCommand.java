package com.assistencia.command.impl;

import com.assistencia.command.*;
import com.assistencia.service.OrdemServicoService;
import javax.servlet.http.*;

/** [Command - Aula 07] Cancela a ordem. */
public class OrdemServicoCancelarCommand implements ICommand {
    private final OrdemServicoService servico;

    public OrdemServicoCancelarCommand(OrdemServicoService servico) {
        this.servico = servico;
    }

    @Override
    public String execute(HttpServletRequest requisicao, HttpServletResponse resposta) throws Exception {
        Long id = ParametrosHttp.idObrigatorio(requisicao, "id", "ordem de serviço");
        servico.cancelar(id);
        return Destino.redirecionar("ordemServico.consultar", id);
    }
}
