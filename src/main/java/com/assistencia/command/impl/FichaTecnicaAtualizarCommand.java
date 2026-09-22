package com.assistencia.command.impl;

import com.assistencia.command.ICommand;
import com.assistencia.model.EstadoConservacao;
import com.assistencia.model.FichaTecnica;
import com.assistencia.service.FichaTecnicaService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * [Requisito 8: Padrão Command & RF07]
 * Comando para atualizar dados da Ficha Técnica.
 */
public class FichaTecnicaAtualizarCommand implements ICommand {

    private final FichaTecnicaService fichaTecnicaService = new FichaTecnicaService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long id = Long.parseLong(request.getParameter("id"));
        String estadoStr = request.getParameter("estadoConservacao");
        EstadoConservacao estadoConservacao = estadoStr != null ? EstadoConservacao.valueOf(estadoStr) : EstadoConservacao.NAO_AVALIADO;
        String acessorios = request.getParameter("acessoriosEntregues");
        Boolean liga = parseBoolean(request.getParameter("ligaNormalmente"));
        Boolean possuiAvarias = parseBoolean(request.getParameter("possuiAvarias"));
        String descAvarias = request.getParameter("descricaoAvarias");
        String testeInicial = request.getParameter("testeInicial");
        String obsFicha = request.getParameter("observacoesRecebimento");

        fichaTecnicaService.atualizar(id, estadoConservacao, acessorios, liga, possuiAvarias, descAvarias, testeInicial, obsFicha);

        FichaTecnica ft = fichaTecnicaService.buscarPorId(id);
        return "redirect:/controle?acao=ordemServico.consultar&id=" + ft.getOrdemServico().getId();
    }

    private Boolean parseBoolean(String str) {
        if (str == null || str.trim().isEmpty()) return null;
        return Boolean.parseBoolean(str);
    }
}
