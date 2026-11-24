package com.assistencia.service;

import com.assistencia.dados.Campos;
import com.assistencia.infra.GerenciadorTransacao;
import com.assistencia.model.orcamento.Orcamento;
import com.assistencia.model.ordem.OrdemServico;
import java.sql.Connection;
import static com.assistencia.model.orcamento.MudancaDeOrcamento.*;
import static com.assistencia.model.ordem.ExigenciaDaOrdem.ABERTA_PARA_EXCLUIR;

/** [RF05] Aprovar gera Ordem e Ficha numa transação (FOR UPDATE, idempotente); excluir ordem ABERTA desfaz isso. */
public class AprovacaoService {
    private final RepositorioAprovacao repositorio;
    private final GerenciadorTransacao transacao;

    public AprovacaoService(RepositorioAprovacao repositorio, GerenciadorTransacao transacao) {
        this.repositorio = repositorio;
        this.transacao = transacao;
    }

    public OrdemServico aprovarOrcamento(Long id, Campos dadosOrdem, Campos dadosFicha) throws Exception {
        return transacao.executar(conexao -> aprovar(conexao, id, dadosOrdem, dadosFicha));
    }

    public void excluirOrdemAberta(Long ordemId) throws Exception {
        transacao.executar(conexao -> excluirOrdem(conexao, ordemId));
    }

    private OrdemServico aprovar(Connection conexao, Long id, Campos dadosOrdem, Campos dadosFicha) throws Exception {
        Orcamento orcamento = repositorio.orcamentoParaAtualizar(conexao, id);
        OrdemServico jaGerada = repositorio.ordemCompleta(conexao, id);
        if (orcamento.aprovado() && jaGerada != null) {
            return jaGerada;
        }
        orcamento.mudar(APROVAR);
        repositorio.atualizar(conexao, orcamento);
        return repositorio.registrar(conexao, orcamento, dadosOrdem, dadosFicha);
    }

    private Void excluirOrdem(Connection conexao, Long ordemId) throws Exception {
        OrdemServico ordem = repositorio.ordemParaAtualizar(conexao, ordemId);
        ordem.exigir(ABERTA_PARA_EXCLUIR);
        Orcamento orcamento = repositorio.orcamentoDaOrdem(conexao, ordem);
        repositorio.remover(conexao, ordemId);
        orcamento.mudar(VOLTAR_PARA_PENDENTE);
        repositorio.atualizar(conexao, orcamento);
        return null;
    }
}
