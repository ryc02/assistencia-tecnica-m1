package com.assistencia.command.impl;

import com.assistencia.command.*;
import com.assistencia.service.EquipamentoService;
import javax.servlet.http.*;

/** [Command - Aula 07] Exclui um equipamento sem orçamentos. */
public class EquipamentoExcluirCommand implements ICommand {
    private final EquipamentoService servico;

    public EquipamentoExcluirCommand(EquipamentoService servico) {
        this.servico = servico;
    }

    @Override
    public String execute(HttpServletRequest requisicao, HttpServletResponse resposta) throws Exception {
        Long id = ParametrosHttp.idObrigatorio(requisicao, "id", "equipamento");
        servico.excluir(id);
        return Destino.redirecionar("equipamento.listar");
    }
}
