package com.assistencia.command.impl;

import com.assistencia.command.*;
import com.assistencia.service.ConsultaAtendimento;
import javax.servlet.http.*;

/** [Command - Aula 07] As fichas são exibidas junto com as ordens: redireciona para a ordem ou para a lista. */
public class FichaTecnicaListarCommand implements ICommand {
    private final ConsultaAtendimento consulta;

    public FichaTecnicaListarCommand(ConsultaAtendimento consulta) {
        this.consulta = consulta;
    }

    @Override
    public String execute(HttpServletRequest requisicao, HttpServletResponse resposta) throws Exception {
        Long ordemId = ParametrosHttp.id(requisicao, "ordemServicoId");
        if (ordemId != null && consulta.fichaDaOrdem(ordemId) != null) {
            return Destino.redirecionar("ordemServico.consultar", ordemId);
        }
        return Destino.redirecionar("ordemServico.listar");
    }
}
