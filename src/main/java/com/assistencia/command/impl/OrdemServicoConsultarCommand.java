package com.assistencia.command.impl;

import com.assistencia.command.*;
import com.assistencia.service.*;
import javax.servlet.http.*;

/** [Command - Aula 07] Exibe a ordem de serviço com a ficha técnica (atendimento completo). */
public class OrdemServicoConsultarCommand implements ICommand {
    private final TelasDeAtendimento telas;

    public OrdemServicoConsultarCommand(TelasDeAtendimento telas) {
        this.telas = telas;
    }

    @Override
    public String execute(HttpServletRequest requisicao, HttpServletResponse resposta) throws Exception {
        Long id = ParametrosHttp.id(requisicao, "id");
        if (id == null) {
            return Destino.redirecionar("ordemServico.listar");
        }
        Tela tela = telas.detalhesDaOrdem(id);
        tela.publicar(requisicao::setAttribute);
        return Destino.view("ordem-detalhes");
    }
}
