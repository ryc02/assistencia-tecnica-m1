package com.assistencia.command.impl;

import com.assistencia.command.ICommand;
import com.assistencia.model.OrdemServico;
import com.assistencia.service.FichaTecnicaService;
import com.assistencia.service.OrdemServicoService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * [Requisito 8: Padrão Command & Requisito 11: Exibe Ordem e Ficha Juntas]
 * Comando para consultar detalhes de uma Ordem de Serviço e sua Ficha Técnica associada.
 */
public class OrdemServicoConsultarCommand implements ICommand {

    private final OrdemServicoService ordemServicoService = new OrdemServicoService();
    private final FichaTecnicaService fichaTecnicaService = new FichaTecnicaService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String idStr = request.getParameter("id");
        if (idStr == null || idStr.trim().isEmpty()) {
            return "redirect:/controle?acao=ordemServico.listar";
        }
        Long id = Long.parseLong(idStr);
        OrdemServico os = ordemServicoService.buscarPorId(id);
        request.setAttribute("ordemServico", os);
        // Exibe OrdemServico e FichaTecnica juntas na página de atendimento
        request.setAttribute("fichaTecnica", fichaTecnicaService.buscarPorOrdemServicoId(id));
        return "/WEB-INF/views/ordem-detalhes.jsp";
    }
}
