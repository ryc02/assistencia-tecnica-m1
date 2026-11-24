package com.assistencia.service;

import com.assistencia.dados.Campos;
import com.assistencia.infra.GerenciadorTransacao;
import com.assistencia.model.orcamento.Orcamento;
import java.sql.Connection;
import static com.assistencia.service.OrcamentoMontado.*;

/** [RF03] Cadastro e alteração de orçamentos (com adicionais - Decorator). */
public class OrcamentoService {
    private final RepositorioOrcamento repositorio;
    private final GerenciadorTransacao transacao;

    public OrcamentoService(RepositorioOrcamento repositorio, GerenciadorTransacao transacao) {
        this.repositorio = repositorio;
        this.transacao = transacao;
    }

    public Orcamento cadastrar(Campos dados) throws Exception {
        return transacao.executar(conexao -> inserir(conexao, dados));
    }

    public void atualizar(Long id, Campos dados) throws Exception {
        transacao.executar(conexao -> atualizar(conexao, id, dados));
    }

    private Orcamento inserir(Connection conexao, Campos dados) throws Exception {
        Orcamento orcamento = novo(repositorio, conexao, dados);
        return repositorio.inserir(conexao, orcamento);
    }

    private Void atualizar(Connection conexao, Long id, Campos dados) throws Exception {
        Orcamento orcamento = atualizado(repositorio, conexao, id, dados);
        repositorio.atualizar(conexao, orcamento);
        return null;
    }
}
