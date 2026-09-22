package com.assistencia.command.impl;

import com.assistencia.command.ICommand;
import com.assistencia.service.ClienteService;
import com.assistencia.service.EquipamentoService;
import com.assistencia.service.OrcamentoService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * [Requisito 8: Padrão Command & RF06]
 * Comando para consultar detalhes do equipamento e exibir os orçamentos do equipamento.
 */
public class EquipamentoConsultarCommand implements ICommand {

    private final EquipamentoService equipamentoService = new EquipamentoService();
    private final OrcamentoService orcamentoService = new OrcamentoService();
    private final ClienteService clienteService = new ClienteService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String idStr = request.getParameter("id");
        if (idStr == null || idStr.trim().isEmpty()) {
            return "redirect:/controle?acao=equipamento.listar";
        }
        Long id = Long.parseLong(idStr);
        request.setAttribute("equipamento", equipamentoService.buscarPorId(id));
        // [RF06] Exibir orçamentos do equipamento
        request.setAttribute("orcamentos", orcamentoService.listarPorEquipamento(id));
        request.setAttribute("clientes", clienteService.listarTodos());
        return "/WEB-INF/views/equipamento-detalhes.jsp";
    }
}
