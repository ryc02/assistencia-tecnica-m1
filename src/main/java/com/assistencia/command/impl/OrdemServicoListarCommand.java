package com.assistencia.command.impl;

import com.assistencia.command.ICommand;
import com.assistencia.model.OrdemServico;
import com.assistencia.service.OrcamentoService;
import com.assistencia.service.OrdemServicoService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * [Requisito 8: Padrão Command & Requisito 10: Filtro orcamentoId]
 * Comando para listar ordens de serviço, com filtro opcional por orcamentoId.
 */
public class OrdemServicoListarCommand implements ICommand {

    private final OrdemServicoService ordemServicoService = new OrdemServicoService();
    private final OrcamentoService orcamentoService = new OrcamentoService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String orcIdStr = request.getParameter("orcamentoId");
        if (orcIdStr != null && !orcIdStr.trim().isEmpty()) {
            Long orcId = Long.parseLong(orcIdStr);
            // buscarPorOrcamentoId retorna um objeto singular; converter para lista para a JSP
            OrdemServico os = ordemServicoService.buscarPorOrcamentoId(orcId);
            List<OrdemServico> lista = (os != null) ? Collections.singletonList(os) : new ArrayList<>();
            request.setAttribute("ordens", lista);
            request.setAttribute("orcamentoFiltro", orcamentoService.buscarPorId(orcId));
        } else {
            request.setAttribute("ordens", ordemServicoService.listarTodas());
        }
        return "/WEB-INF/views/ordem-listar.jsp";
    }
}
