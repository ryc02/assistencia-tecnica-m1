package com.assistencia.command.impl;

import com.assistencia.command.ICommand;
import com.assistencia.service.ClienteService;
import com.assistencia.service.EquipamentoService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * [Requisito 8: Padrão Command & Requisito 10: Filtro clienteId]
 * Comando para listar equipamentos (com filtro opcional por clienteId).
 */
public class EquipamentoListarCommand implements ICommand {

    private final EquipamentoService equipamentoService = new EquipamentoService();
    private final ClienteService clienteService = new ClienteService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String clienteIdStr = request.getParameter("clienteId");
        if (clienteIdStr != null && !clienteIdStr.trim().isEmpty()) {
            Long clienteId = Long.parseLong(clienteIdStr);
            request.setAttribute("equipamentos", equipamentoService.listarPorCliente(clienteId));
            request.setAttribute("clienteFiltro", clienteService.buscarPorId(clienteId));
        } else {
            request.setAttribute("equipamentos", equipamentoService.listarTodos());
        }
        request.setAttribute("clientes", clienteService.listarTodos());
        return "/WEB-INF/views/equipamento-listar.jsp";
    }
}
