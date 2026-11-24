package com.assistencia.service;

import com.assistencia.infra.GerenciadorTransacao;
import static com.assistencia.service.PaginasDeEquipamento.*;

/** Monta os dados das telas de listagem de equipamentos e de detalhes do cliente. */
public class TelasDeEquipamento {
    private final RepositorioEquipamento repositorio;
    private final GerenciadorTransacao transacao;

    public TelasDeEquipamento(RepositorioEquipamento repositorio, GerenciadorTransacao transacao) {
        this.repositorio = repositorio;
        this.transacao = transacao;
    }

    /** Equipamentos (todos ou de um cliente), o cliente filtrado e a lista de clientes. */
    public Tela listagem(Long clienteIdFiltro) throws Exception {
        return transacao.executar(conexao -> paginaDeListagem(repositorio, conexao, clienteIdFiltro));
    }

    /** Cliente e seus equipamentos (relacionamento 1:N). */
    public Tela detalhesDoCliente(Long clienteId) throws Exception {
        return transacao.executar(conexao -> paginaDoCliente(repositorio, conexao, clienteId));
    }
}
