package com.assistencia.service;

import com.assistencia.dados.Campos;
import com.assistencia.dao.FichaTecnicaDAO;
import com.assistencia.infra.GerenciadorTransacao;
import com.assistencia.model.ficha.FichaTecnica;
import java.sql.Connection;

/** Atualização da ficha técnica (permitida só enquanto a ordem estiver ABERTA ou EM_ANDAMENTO). */
public class FichaTecnicaService {
    private final FichaTecnicaDAO fichas;
    private final GerenciadorTransacao transacao;

    public FichaTecnicaService(FichaTecnicaDAO fichas, GerenciadorTransacao transacao) {
        this.fichas = fichas;
        this.transacao = transacao;
    }

    public FichaTecnica atualizar(Long id, Campos dados) throws Exception {
        return transacao.executar(conexao -> atualizar(conexao, id, dados));
    }

    private FichaTecnica atualizar(Connection conexao, Long id, Campos dados) throws Exception {
        FichaTecnica ficha = FichaExistente.buscar(fichas, conexao, id);
        Campos atuais = ficha.exportar();
        Campos revisados = new Campos();
        revisados.comTodos(dados);
        Object dataRegistro = atuais.valor("dataRegistro");
        revisados.com("dataRegistro", dataRegistro);
        ficha.atualizar(revisados);
        fichas.atualizar(conexao, ficha);
        return ficha;
    }
}
