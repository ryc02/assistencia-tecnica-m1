package com.assistencia.command.impl;

import com.assistencia.command.*;
import com.assistencia.dados.Campos;
import com.assistencia.model.orcamento.Orcamento;
import com.assistencia.service.*;
import javax.servlet.http.*;

/** [Command - Aula 07] Cadastra um orçamento (total calculado no servidor com os adicionais). */
public class OrcamentoInserirCommand implements ICommand {
    private final OrcamentoService servico;

    public OrcamentoInserirCommand(OrcamentoService servico) {
        this.servico = servico;
    }

    @Override
    public String execute(HttpServletRequest requisicao, HttpServletResponse resposta) throws Exception {
        Campos dados = ParametrosHttp.campos(requisicao);
        Orcamento criado = servico.cadastrar(dados);
        Long id = IdDaEntidade.de(criado);
        return Destino.redirecionar("orcamento.consultar", id);
    }
}
