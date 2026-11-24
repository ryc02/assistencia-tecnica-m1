package com.assistencia.service;

import com.assistencia.dados.Campos;
import com.assistencia.infra.GerenciadorTransacao;
import com.assistencia.model.cliente.Cliente;
import com.assistencia.model.equipamento.Equipamento;
import java.sql.Connection;
import static com.assistencia.service.EquipamentoMontado.*;

/** [RF02] Cadastro, alteração e exclusão de equipamentos (relacionamento Cliente 1:N Equipamento). */
public class EquipamentoService {
    private final RepositorioEquipamento repositorio;
    private final GerenciadorTransacao transacao;

    public EquipamentoService(RepositorioEquipamento repositorio, GerenciadorTransacao transacao) {
        this.repositorio = repositorio;
        this.transacao = transacao;
    }

    public Equipamento cadastrar(Campos dados) throws Exception {
        return transacao.executar(conexao -> inserir(conexao, dados));
    }

    public void atualizar(Long id, Campos dados) throws Exception {
        transacao.executar(conexao -> atualizar(conexao, id, dados));
    }

    public void excluir(Long id) throws Exception {
        transacao.executar(conexao -> excluir(conexao, id));
    }

    private Equipamento inserir(Connection conexao, Campos dados) throws Exception {
        Long clienteId = dados.inteiroLongo("clienteId");
        Cliente dono = repositorio.dono(conexao, clienteId);
        return repositorio.inserir(conexao, novo(dados, dono));
    }

    private Void atualizar(Connection conexao, Long id, Campos dados) throws Exception {
        repositorio.atualizar(conexao, atualizado(repositorio, conexao, id, dados));
        return null;
    }

    private Void excluir(Connection conexao, Long id) throws Exception {
        TrocaDeDono.exigirSemOrcamentos(repositorio, conexao, id);
        repositorio.excluir(conexao, id);
        return null;
    }
}
