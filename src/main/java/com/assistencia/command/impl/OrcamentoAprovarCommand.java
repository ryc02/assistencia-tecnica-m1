package com.assistencia.command.impl;

import com.assistencia.command.ICommand;
import com.assistencia.model.EstadoConservacao;
import com.assistencia.model.OrdemServico;
import com.assistencia.model.Prioridade;
import com.assistencia.service.OrcamentoService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

/**
 * [Requisito 8: Padrão Command & RF05 Aprovar Orçamento]
 * Comando que aciona a aprovação do orçamento e a geração indivisível de Ordem de Serviço e Ficha Técnica.
 */
public class OrcamentoAprovarCommand implements ICommand {

    private final OrcamentoService orcamentoService = new OrcamentoService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.trim().isEmpty()) {
            idParam = request.getParameter("orcamentoId");
        }
        Long orcamentoId = Long.parseLong(idParam);

        String responsavel = request.getParameter("responsavel");
        String prioridadeStr = request.getParameter("prioridade");
        Prioridade prioridade = prioridadeStr != null ? Prioridade.valueOf(prioridadeStr) : Prioridade.NORMAL;
        String previsaoStr = request.getParameter("previsaoConclusao");
        LocalDateTime previsaoConclusao = (previsaoStr != null && !previsaoStr.trim().isEmpty()) ? LocalDateTime.parse(previsaoStr) : null;
        String observacoesOrdem = request.getParameter("observacoes");

        // Parâmetros da Ficha Técnica com prefixo "ficha." conforme contrato HTTP
        String estadoStr = request.getParameter("ficha.estadoConservacao");
        EstadoConservacao estadoConservacao = estadoStr != null ? EstadoConservacao.valueOf(estadoStr) : EstadoConservacao.NAO_AVALIADO;
        String acessorios = request.getParameter("ficha.acessoriosEntregues");
        Boolean liga = parseBoolean(request.getParameter("ficha.ligaNormalmente"));
        Boolean possuiAvarias = parseBoolean(request.getParameter("ficha.possuiAvarias"));
        String descAvarias = request.getParameter("ficha.descricaoAvarias");
        String testeInicial = request.getParameter("ficha.testeInicial");
        String obsFicha = request.getParameter("ficha.observacoesRecebimento");

        OrdemServico os = orcamentoService.aprovarOrcamento(
                orcamentoId, responsavel, prioridade, previsaoConclusao, observacoesOrdem,
                estadoConservacao, acessorios, liga, possuiAvarias, descAvarias, testeInicial, obsFicha
        );

        return "redirect:/controle?acao=ordemServico.consultar&id=" + os.getId();
    }

    private Boolean parseBoolean(String str) {
        if (str == null || str.trim().isEmpty()) return null;
        return Boolean.parseBoolean(str);
    }
}
