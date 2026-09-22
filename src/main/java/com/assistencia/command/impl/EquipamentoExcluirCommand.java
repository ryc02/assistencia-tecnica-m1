package com.assistencia.command.impl;

import com.assistencia.command.ICommand;
import com.assistencia.service.EquipamentoService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * [Requisito 8: Padrão Command & Requisito 10: HTTP 303 Redirect]
 * Comando para excluir equipamento.
 */
public class EquipamentoExcluirCommand implements ICommand {

    private final EquipamentoService equipamentoService = new EquipamentoService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long id = Long.parseLong(request.getParameter("id"));
        equipamentoService.excluir(id);
        return "redirect:/controle?acao=equipamento.listar";
    }
}
