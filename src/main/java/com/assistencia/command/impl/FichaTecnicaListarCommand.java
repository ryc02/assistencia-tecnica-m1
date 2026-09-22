package com.assistencia.command.impl;

import com.assistencia.command.ICommand;
import com.assistencia.model.FichaTecnica;
import com.assistencia.service.FichaTecnicaService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * [Requisito 8: Padrão Command & RF07: Gerenciar Fichas Técnicas]
 * Comando para listar fichas técnicas. A ficha é exibida junto com a ordem
 * na mesma página do atendimento, conforme especificação seção 11.
 * Quando filtrada por ordemServicoId, redireciona para a consulta da ordem correspondente.
 */
public class FichaTecnicaListarCommand implements ICommand {

    private final FichaTecnicaService fichaTecnicaService = new FichaTecnicaService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String osIdStr = request.getParameter("ordemServicoId");
        if (osIdStr != null && !osIdStr.trim().isEmpty()) {
            Long osId = Long.parseLong(osIdStr);
            FichaTecnica ft = fichaTecnicaService.buscarPorOrdemServicoId(osId);
            if (ft != null) {
                return "redirect:/controle?acao=ordemServico.consultar&id=" + osId;
            }
        }
        // Sem filtro: redireciona para listagem de ordens (fichas são exibidas junto com ordens)
        return "redirect:/controle?acao=ordemServico.listar";
    }
}
