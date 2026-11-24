package com.assistencia.command.impl;

import com.assistencia.command.*;
import com.assistencia.service.OrdemServicoService;
import javax.servlet.http.*;

/** [Command - Aula 07] Inicia a ordem (ABERTA para EM_ANDAMENTO). */
public class OrdemServicoIniciarCommand implements ICommand {
    private final OrdemServicoService servico;

    public OrdemServicoIniciarCommand(OrdemServicoService servico) {
        this.servico = servico;
    }

    @Override
    public String execute(HttpServletRequest requisicao, HttpServletResponse resposta) throws Exception {
        Long id = ParametrosHttp.idObrigatorio(requisicao, "id", "ordem de serviço");
        servico.iniciar(id);
        return Destino.redirecionar("ordemServico.consultar", id);
    }
}
