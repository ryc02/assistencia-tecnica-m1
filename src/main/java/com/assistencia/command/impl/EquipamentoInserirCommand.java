package com.assistencia.command.impl;

import com.assistencia.command.*;
import com.assistencia.dados.Campos;
import com.assistencia.model.equipamento.Equipamento;
import com.assistencia.service.*;
import javax.servlet.http.*;

/** [Command - Aula 07] Cadastra um equipamento para um cliente. */
public class EquipamentoInserirCommand implements ICommand {
    private final EquipamentoService servico;

    public EquipamentoInserirCommand(EquipamentoService servico) {
        this.servico = servico;
    }

    @Override
    public String execute(HttpServletRequest requisicao, HttpServletResponse resposta) throws Exception {
        Campos dados = ParametrosHttp.campos(requisicao);
        Equipamento criado = servico.cadastrar(dados);
        Long id = IdDaEntidade.de(criado);
        return Destino.redirecionar("equipamento.consultar", id);
    }
}
