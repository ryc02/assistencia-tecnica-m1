package com.assistencia.service;

import com.assistencia.exception.ConflictException;
import com.assistencia.infra.GerenciadorTransacao;
import com.assistencia.model.orcamento.Orcamento;
import java.sql.Connection;
import static com.assistencia.model.orcamento.ExigenciaDoOrcamento.EXCLUIVEL;
import static com.assistencia.model.orcamento.MudancaDeOrcamento.RECUSAR;

/** [RF04] Recusa e exclusão de orçamentos, aplicando as regras de estado da entidade. */
public class GestaoOrcamentoService {
    private final RepositorioOrcamento repositorio;
    private final GerenciadorTransacao transacao;

    public GestaoOrcamentoService(RepositorioOrcamento repositorio, GerenciadorTransacao transacao) {
        this.repositorio = repositorio;
        this.transacao = transacao;
    }

    public Orcamento recusar(Long id) throws Exception {
        return transacao.executar(conexao -> recusar(conexao, id));
    }

    public void excluir(Long id) throws Exception {
        transacao.executar(conexao -> excluir(conexao, id));
    }

    private Orcamento recusar(Connection conexao, Long id) throws Exception {
        Orcamento orcamento = repositorio.paraAtualizar(conexao, id);
        orcamento.mudar(RECUSAR);
        repositorio.atualizar(conexao, orcamento);
        return orcamento;
    }

    private Void excluir(Connection conexao, Long id) throws Exception {
        Orcamento orcamento = repositorio.existente(conexao, id);
        if (repositorio.possuiOrdemDeServico(conexao, id)) {
            throw new ConflictException("Não é possível excluir o orçamento pois ele possui uma ordem de "
                    + "serviço.");
        }
        orcamento.exigir(EXCLUIVEL);
        repositorio.excluir(conexao, id);
        return null;
    }
}
