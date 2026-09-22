package com.assistencia.command.impl;

import com.assistencia.command.ICommand;
import com.assistencia.service.EquipamentoService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * [Requisito 8: Padrão Command & Requisito 10: HTTP 303 Redirect]
 * Comando para atualizar dados do equipamento.
 */
public class EquipamentoAtualizarCommand implements ICommand {

    private final EquipamentoService equipamentoService = new EquipamentoService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Long id = Long.parseLong(request.getParameter("id"));
        Long clienteId = Long.parseLong(request.getParameter("clienteId"));
        String tipo = request.getParameter("tipo");
        String marca = request.getParameter("marca");
        String modelo = request.getParameter("modelo");
        String numeroSerie = request.getParameter("numeroSerie");
        String cor = request.getParameter("cor");
        String voltagem = request.getParameter("voltagem");
        String descricao = request.getParameter("descricao");

        equipamentoService.atualizar(id, clienteId, tipo, marca, modelo, numeroSerie, cor, voltagem, descricao);
        return "redirect:/controle?acao=equipamento.consultar&id=" + id;
    }
}
