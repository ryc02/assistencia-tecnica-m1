package com.assistencia.command.impl;

import com.assistencia.command.*;
import com.assistencia.service.*;
import javax.servlet.http.*;

/** [Command - Aula 07] Exibe o orçamento e, se aprovado, a ordem de serviço e a ficha técnica. */
public class OrcamentoConsultarCommand implements ICommand {
    private final TelasDeOrcamento orcamentos;
    private final TelasDeAtendimento atendimentos;

    public OrcamentoConsultarCommand(TelasDeOrcamento orcamentos, TelasDeAtendimento atendimentos) {
        this.orcamentos = orcamentos;
        this.atendimentos = atendimentos;
    }

    @Override
    public String execute(HttpServletRequest requisicao, HttpServletResponse resposta) throws Exception {
        Long id = ParametrosHttp.id(requisicao, "id");
        if (id == null) {
            return Destino.redirecionar("orcamento.listar");
        }
        Tela orcamento = orcamentos.detalhes(id);
        Tela atendimento = atendimentos.doOrcamento(id);
        orcamento.publicar(requisicao::setAttribute);
        atendimento.publicar(requisicao::setAttribute);
        return Destino.view("orcamento-detalhes");
    }
}
