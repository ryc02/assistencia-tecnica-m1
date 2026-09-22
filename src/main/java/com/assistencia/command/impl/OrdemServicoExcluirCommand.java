package com.assistencia.command.impl;

import com.assistencia.command.ICommand;
import com.assistencia.service.OrcamentoService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * [Requisito 8: Padrão Command & Seção 7 Excluir Ordem Aberta]
 * Comando para excluir ordem ABERTA (remove ficha, ordem e retorna orçamento a PENDENTE em transação única).
 */
public class OrdemServicoExcluirCommand implements ICommand {

    private final OrcamentoService orcamentoService = new OrcamentoService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long id = Long.parseLong(request.getParameter("id"));
        orcamentoService.excluirOrdemAberta(id);
        return "redirect:/controle?acao=ordemServico.listar";
    }
}
