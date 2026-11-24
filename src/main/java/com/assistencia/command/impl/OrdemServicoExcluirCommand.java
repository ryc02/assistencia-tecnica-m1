package com.assistencia.command.impl;

import com.assistencia.command.*;
import com.assistencia.service.AprovacaoService;
import javax.servlet.http.*;

/** [Command - Aula 07] Exclui ordem ABERTA, remove a ficha e devolve o orçamento para PENDENTE. */
public class OrdemServicoExcluirCommand implements ICommand {
    private final AprovacaoService aprovacao;

    public OrdemServicoExcluirCommand(AprovacaoService aprovacao) {
        this.aprovacao = aprovacao;
    }

    @Override
    public String execute(HttpServletRequest requisicao, HttpServletResponse resposta) throws Exception {
        Long id = ParametrosHttp.idObrigatorio(requisicao, "id", "ordem de serviço");
        aprovacao.excluirOrdemAberta(id);
        return Destino.redirecionar("ordemServico.listar");
    }
}
