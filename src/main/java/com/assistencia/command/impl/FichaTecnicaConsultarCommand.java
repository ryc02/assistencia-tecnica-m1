package com.assistencia.command.impl;

import com.assistencia.command.ICommand;
import com.assistencia.model.FichaTecnica;
import com.assistencia.service.FichaTecnicaService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * [Requisito 8: Padrão Command & RF07]
 * Comando para consultar detalhes de uma Ficha Técnica.
 */
public class FichaTecnicaConsultarCommand implements ICommand {

    private final FichaTecnicaService fichaTecnicaService = new FichaTecnicaService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String idStr = request.getParameter("id");
        if (idStr == null || idStr.trim().isEmpty()) {
            return "redirect:/controle?acao=ordemServico.listar";
        }
        Long id = Long.parseLong(idStr);
        FichaTecnica ft = fichaTecnicaService.buscarPorId(id);
        request.setAttribute("fichaTecnica", ft);
        return "redirect:/controle?acao=ordemServico.consultar&id=" + ft.getOrdemServico().getId();
    }
}
