package com.assistencia.command.impl;

import com.assistencia.command.ICommand;
import com.assistencia.service.EquipamentoService;
import com.assistencia.service.OrcamentoService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * [Requisito 8: Padrão Command & Requisito 10: Contrato HTTP]
 * Comando para listar orçamentos (com filtro opcional por equipamentoId).
 */
public class OrcamentoListarCommand implements ICommand {

    private final OrcamentoService orcamentoService = new OrcamentoService();
    private final EquipamentoService equipamentoService = new EquipamentoService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String eqIdStr = request.getParameter("equipamentoId");
        if (eqIdStr != null && !eqIdStr.trim().isEmpty()) {
            Long eqId = Long.parseLong(eqIdStr);
            request.setAttribute("orcamentos", orcamentoService.listarPorEquipamento(eqId));
            request.setAttribute("equipamentoFiltro", equipamentoService.buscarPorId(eqId));
        } else {
            request.setAttribute("orcamentos", orcamentoService.listarTodos());
        }
        request.setAttribute("equipamentos", equipamentoService.listarTodos());
        return "/WEB-INF/views/orcamento-listar.jsp";
    }
}
