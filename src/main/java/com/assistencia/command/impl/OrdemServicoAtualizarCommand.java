package com.assistencia.command.impl;

import com.assistencia.command.ICommand;
import com.assistencia.model.Prioridade;
import com.assistencia.service.OrdemServicoService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

/**
 * [Requisito 8: Padrão Command & RF04]
 * Comando para atualizar dados de uma ordem de serviço.
 */
public class OrdemServicoAtualizarCommand implements ICommand {

    private final OrdemServicoService ordemServicoService = new OrdemServicoService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long id = Long.parseLong(request.getParameter("id"));
        String responsavel = request.getParameter("responsavel");
        String prioridadeStr = request.getParameter("prioridade");
        Prioridade prioridade = prioridadeStr != null ? Prioridade.valueOf(prioridadeStr) : Prioridade.NORMAL;
        String previsaoStr = request.getParameter("previsaoConclusao");
        LocalDateTime previsaoConclusao = (previsaoStr != null && !previsaoStr.trim().isEmpty()) ? LocalDateTime.parse(previsaoStr) : null;
        String observacoes = request.getParameter("observacoes");
        String garantiaStr = request.getParameter("prazoGarantiaDias");
        Integer prazoGarantia = (garantiaStr != null && !garantiaStr.trim().isEmpty()) ? Integer.parseInt(garantiaStr) : 90;

        ordemServicoService.atualizar(id, responsavel, prioridade, previsaoConclusao, observacoes, prazoGarantia);
        return "redirect:/controle?acao=ordemServico.consultar&id=" + id;
    }
}
