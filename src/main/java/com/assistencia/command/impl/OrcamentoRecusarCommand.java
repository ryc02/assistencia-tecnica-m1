package com.assistencia.command.impl;

import com.assistencia.command.ICommand;
import com.assistencia.service.OrcamentoService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * [Requisito 8: Padrão Command & RF03]
 * Comando para marcar orçamento como RECUSADO.
 */
public class OrcamentoRecusarCommand implements ICommand {

    private final OrcamentoService orcamentoService = new OrcamentoService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long id = Long.parseLong(request.getParameter("id"));
        orcamentoService.recusar(id);
        return "redirect:/controle?acao=orcamento.consultar&id=" + id;
    }
}
