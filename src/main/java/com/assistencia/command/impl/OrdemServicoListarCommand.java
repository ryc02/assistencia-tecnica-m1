package com.assistencia.command.impl;

import com.assistencia.command.*;
import com.assistencia.model.orcamento.Orcamento;
import com.assistencia.service.*;
import java.util.Map;
import javax.servlet.http.*;

/** [Command - Aula 07] Lista as ordens de serviço (todas ou a de um orçamento). */
public class OrdemServicoListarCommand implements ICommand {
    private final TelasDeAtendimento telas;
    private final ConsultaOrcamento orcamentos;

    public OrdemServicoListarCommand(TelasDeAtendimento telas, ConsultaOrcamento orcamentos) {
        this.telas = telas;
        this.orcamentos = orcamentos;
    }

    @Override
    public String execute(HttpServletRequest requisicao, HttpServletResponse resposta) throws Exception {
        Long orcamentoId = ParametrosHttp.id(requisicao, "orcamentoId");
        Tela tela = telas.listagem(orcamentoId);
        tela.publicar(requisicao::setAttribute);
        if (orcamentoId != null) {
            Orcamento filtro = orcamentos.buscarPorId(orcamentoId);
            Map<String, Object> visao = Visao.de(filtro);
            requisicao.setAttribute("orcamentoFiltro", visao);
        }
        return Destino.view("ordem-listar");
    }
}
