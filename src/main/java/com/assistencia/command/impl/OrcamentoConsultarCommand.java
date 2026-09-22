package com.assistencia.command.impl;

import com.assistencia.command.ICommand;
import com.assistencia.model.Orcamento;
import com.assistencia.model.OrdemServico;
import com.assistencia.service.EquipamentoService;
import com.assistencia.service.FichaTecnicaService;
import com.assistencia.service.OrcamentoService;
import com.assistencia.service.OrdemServicoService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * [Requisito 8: Padrão Command & RF06]
 * Comando para consultar detalhes de um orçamento e exibir Ordem de Serviço e Ficha Técnica vinculadas.
 */
public class OrcamentoConsultarCommand implements ICommand {

    private final OrcamentoService orcamentoService = new OrcamentoService();
    private final OrdemServicoService ordemServicoService = new OrdemServicoService();
    private final FichaTecnicaService fichaTecnicaService = new FichaTecnicaService();
    private final EquipamentoService equipamentoService = new EquipamentoService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String idStr = request.getParameter("id");
        if (idStr == null || idStr.trim().isEmpty()) {
            return "redirect:/controle?acao=orcamento.listar";
        }
        Long id = Long.parseLong(idStr);
        Orcamento orc = orcamentoService.buscarPorId(id);
        request.setAttribute("orcamento", orc);

        // [RF06] Consulta a OrdemServico e FichaTecnica associadas
        OrdemServico os = ordemServicoService.buscarPorOrcamentoId(id);
        if (os != null) {
            request.setAttribute("ordemServico", os);
            request.setAttribute("fichaTecnica", fichaTecnicaService.buscarPorOrdemServicoId(os.getId()));
        }
        request.setAttribute("equipamentos", equipamentoService.listarTodos());
        return "/WEB-INF/views/orcamento-detalhes.jsp";
    }
}
