package com.assistencia.command.impl;

import com.assistencia.command.*;
import com.assistencia.dados.Campos;
import com.assistencia.service.OrdemServicoService;
import javax.servlet.http.*;

/** [Command - Aula 07] Altera responsável, prioridade, previsão, observações e garantia. */
public class OrdemServicoAtualizarCommand implements ICommand {
    private final OrdemServicoService servico;

    public OrdemServicoAtualizarCommand(OrdemServicoService servico) {
        this.servico = servico;
    }

    @Override
    public String execute(HttpServletRequest requisicao, HttpServletResponse resposta) throws Exception {
        Long id = ParametrosHttp.idObrigatorio(requisicao, "id", "ordem de serviço");
        Campos dados = ParametrosHttp.campos(requisicao);
        servico.reprogramar(id, dados);
        return Destino.redirecionar("ordemServico.consultar", id);
    }
}
