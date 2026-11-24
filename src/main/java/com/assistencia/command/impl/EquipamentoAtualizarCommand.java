package com.assistencia.command.impl;

import com.assistencia.command.*;
import com.assistencia.dados.Campos;
import com.assistencia.service.EquipamentoService;
import javax.servlet.http.*;

/** [Command - Aula 07] Atualiza os dados de um equipamento. */
public class EquipamentoAtualizarCommand implements ICommand {
    private final EquipamentoService servico;

    public EquipamentoAtualizarCommand(EquipamentoService servico) {
        this.servico = servico;
    }

    @Override
    public String execute(HttpServletRequest requisicao, HttpServletResponse resposta) throws Exception {
        Long id = ParametrosHttp.idObrigatorio(requisicao, "id", "equipamento");
        Campos dados = ParametrosHttp.campos(requisicao);
        servico.atualizar(id, dados);
        return Destino.redirecionar("equipamento.consultar", id);
    }
}
