package com.assistencia.command.impl;

import com.assistencia.command.ICommand;
import com.assistencia.service.OrcamentoService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

/**
 * [Requisito 8: Padrão Command & RF03]
 * Comando para atualizar orçamento existente.
 */
public class OrcamentoAtualizarCommand implements ICommand {

    private final OrcamentoService orcamentoService = new OrcamentoService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long id = Long.parseLong(request.getParameter("id"));
        Long equipamentoId = Long.parseLong(request.getParameter("equipamentoId"));
        String descricaoProblema = request.getParameter("descricaoProblema");
        String diagnostico = request.getParameter("diagnostico");
        BigDecimal valorPecas = parseBigDecimal(request.getParameter("valorPecas"));
        BigDecimal valorMaoDeObra = parseBigDecimal(request.getParameter("valorMaoDeObra"));
        BigDecimal percentualDesconto = parseBigDecimal(request.getParameter("percentualDesconto"));

        orcamentoService.atualizar(id, equipamentoId, descricaoProblema, diagnostico, valorPecas, valorMaoDeObra, percentualDesconto);
        return "redirect:/controle?acao=orcamento.consultar&id=" + id;
    }

    private BigDecimal parseBigDecimal(String val) {
        if (val == null || val.trim().isEmpty()) return BigDecimal.ZERO;
        return new BigDecimal(val.trim().replace(",", "."));
    }
}
