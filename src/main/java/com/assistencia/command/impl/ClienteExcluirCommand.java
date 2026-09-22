package com.assistencia.command.impl;

import com.assistencia.command.ICommand;
import com.assistencia.service.ClienteService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * [Requisito 8: Padrão Command & Requisito 10: HTTP 303 Redirect]
 * Comando para excluir um cliente.
 */
public class ClienteExcluirCommand implements ICommand {

    private final ClienteService clienteService = new ClienteService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long id = Long.parseLong(request.getParameter("id"));
        clienteService.excluir(id);
        return "redirect:/controle?acao=cliente.listar";
    }
}
