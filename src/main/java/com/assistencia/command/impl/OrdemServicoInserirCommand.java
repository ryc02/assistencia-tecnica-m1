package com.assistencia.command.impl;

import com.assistencia.command.ICommand;
import javax.servlet.http.*;

/** [Command - Aula 07] Inserir ordem = aprovar o orçamento (a ordem só nasce da aprovação). */
public class OrdemServicoInserirCommand implements ICommand {
    private final ICommand aprovar;

    public OrdemServicoInserirCommand(ICommand aprovar) {
        this.aprovar = aprovar;
    }

    @Override
    public String execute(HttpServletRequest requisicao, HttpServletResponse resposta) throws Exception {
        return aprovar.execute(requisicao, resposta);
    }
}
