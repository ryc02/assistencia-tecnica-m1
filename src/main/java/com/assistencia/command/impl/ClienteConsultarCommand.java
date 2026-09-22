package com.assistencia.command.impl;

import com.assistencia.command.ICommand;
import com.assistencia.service.ClienteService;
import com.assistencia.service.EquipamentoService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * [Requisito 8: Padrão Command & Requisito 10: Contrato HTTP]
 * Comando para consultar detalhes de um cliente e exibir seus equipamentos vinculados.
 */
public class ClienteConsultarCommand implements ICommand {

    private final ClienteService clienteService = new ClienteService();
    private final EquipamentoService equipamentoService = new EquipamentoService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String idStr = request.getParameter("id");
        if (idStr == null || idStr.trim().isEmpty()) {
            return "redirect:/controle?acao=cliente.listar";
        }
        Long id = Long.parseLong(idStr);
        request.setAttribute("cliente", clienteService.buscarPorId(id));
        // [RF06] Exibir equipamentos do cliente
        request.setAttribute("equipamentos", equipamentoService.listarPorCliente(id));
        return "/WEB-INF/views/cliente-detalhes.jsp";
    }
}
