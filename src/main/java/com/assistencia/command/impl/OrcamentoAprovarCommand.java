package com.assistencia.command.impl;

import com.assistencia.command.*;
import com.assistencia.dados.Campos;
import com.assistencia.model.ordem.OrdemServico;
import com.assistencia.service.*;
import javax.servlet.http.*;

/** [Command - Aula 07] Aprova o orçamento e gera a Ordem de Serviço e a Ficha Técnica. */
public class OrcamentoAprovarCommand implements ICommand {
    private final AprovacaoService aprovacao;

    public OrcamentoAprovarCommand(AprovacaoService aprovacao) {
        this.aprovacao = aprovacao;
    }

    @Override
    public String execute(HttpServletRequest requisicao, HttpServletResponse resposta) throws Exception {
        Long id = ParametrosHttp.idObrigatorio(requisicao, "id", "orçamento");
        Campos dadosOrdem = ParametrosHttp.campos(requisicao);
        Campos dadosFicha = ParametrosHttp.comPrefixo(requisicao, "ficha.");
        OrdemServico ordem = aprovacao.aprovarOrcamento(id, dadosOrdem, dadosFicha);
        Long ordemId = IdDaEntidade.de(ordem);
        return Destino.redirecionar("ordemServico.consultar", ordemId);
    }
}
