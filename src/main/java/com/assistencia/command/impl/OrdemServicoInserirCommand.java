package com.assistencia.command.impl;

import com.assistencia.command.ICommand;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * [Requisito 4: Semântica do CRUD / Seção 10]
 * O formulário "Inserir Ordem" chama o mesmo serviço de aprovação automática do orçamento.
 */
public class OrdemServicoInserirCommand implements ICommand {

    private final OrcamentoAprovarCommand aprovarCommand = new OrcamentoAprovarCommand();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return aprovarCommand.execute(request, response);
    }
}
