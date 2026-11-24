package com.assistencia.command.impl;

import com.assistencia.command.*;
import com.assistencia.model.ficha.FichaTecnica;
import com.assistencia.service.ConsultaAtendimento;
import javax.servlet.http.*;

/** [Command - Aula 07] Redireciona para o atendimento (ordem + ficha) da ficha informada. */
public class FichaTecnicaConsultarCommand implements ICommand {
    private final ConsultaAtendimento consulta;

    public FichaTecnicaConsultarCommand(ConsultaAtendimento consulta) {
        this.consulta = consulta;
    }

    @Override
    public String execute(HttpServletRequest requisicao, HttpServletResponse resposta) throws Exception {
        Long id = ParametrosHttp.id(requisicao, "id");
        if (id == null) {
            return Destino.redirecionar("ordemServico.listar");
        }
        FichaTecnica ficha = consulta.ficha(id);
        Long ordemId = IdDaOrdem.de(ficha);
        return Destino.redirecionar("ordemServico.consultar", ordemId);
    }
}
