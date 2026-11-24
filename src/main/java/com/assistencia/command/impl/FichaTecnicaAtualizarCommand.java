package com.assistencia.command.impl;

import com.assistencia.command.*;
import com.assistencia.dados.Campos;
import com.assistencia.model.ficha.FichaTecnica;
import com.assistencia.service.FichaTecnicaService;
import javax.servlet.http.*;

/** [Command - Aula 07] Atualiza a ficha técnica de uma ordem ABERTA ou EM_ANDAMENTO. */
public class FichaTecnicaAtualizarCommand implements ICommand {
    private final FichaTecnicaService servico;

    public FichaTecnicaAtualizarCommand(FichaTecnicaService servico) {
        this.servico = servico;
    }

    @Override
    public String execute(HttpServletRequest requisicao, HttpServletResponse resposta) throws Exception {
        Long id = ParametrosHttp.idObrigatorio(requisicao, "id", "ficha técnica");
        Campos dados = ParametrosHttp.campos(requisicao);
        FichaTecnica atualizada = servico.atualizar(id, dados);
        Long ordemId = IdDaOrdem.de(atualizada);
        return Destino.redirecionar("ordemServico.consultar", ordemId);
    }
}
