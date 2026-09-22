package com.assistencia.command.impl;

import com.assistencia.command.ICommand;
import com.assistencia.service.OrdemServicoService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * [Requisito 8: Padrão Command & Seção 6 Transições de Estado]
 * Comando para cancelar a ordem de serviço.
 */
public class OrdemServicoCancelarCommand implements ICommand {

    private final OrdemServicoService ordemServicoService = new OrdemServicoService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long id = Long.parseLong(request.getParameter("id"));
        ordemServicoService.cancelar(id);
        return "redirect:/controle?acao=ordemServico.consultar&id=" + id;
    }
}
