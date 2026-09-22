package com.assistencia.command.impl;

import com.assistencia.command.ICommand;
import com.assistencia.service.ClienteService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * [Requisito 8: Padrão Command & Requisito 10: Contrato HTTP]
 * Comando para listar clientes.
 */
public class ClienteListarCommand implements ICommand {

    private final ClienteService clienteService = new ClienteService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute("clientes", clienteService.listarTodos());
        return "/WEB-INF/views/cliente-listar.jsp";
    }
}
