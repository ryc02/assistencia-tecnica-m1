package com.assistencia.service;

import com.assistencia.dados.Registros;
import com.assistencia.decorator.TabelaDeAdicionais;
import com.assistencia.model.equipamento.Equipamento;
import com.assistencia.model.orcamento.Orcamento;
import java.sql.Connection;
import java.util.*;

/** Conteúdo das telas de orçamento, montado dentro da transação aberta por TelasDeOrcamento. */
final class PaginasDeOrcamento {
    private PaginasDeOrcamento() { }

    static Tela paginaDeListagem(RepositorioOrcamento repositorio, Connection conexao, Long equipamentoId)
            throws Exception {
        Tela tela = comEquipamentosEAdicionais(repositorio, conexao);
        if (equipamentoId == null) {
            Registros<Orcamento> todos = repositorio.listarTodos(conexao);
            return tela.comRegistros("orcamentos", todos);
        }
        Equipamento filtro = repositorio.equipamento(conexao, equipamentoId);
        tela.comEntidade("equipamentoFiltro", filtro);
        Registros<Orcamento> doEquipamento = repositorio.listarPorEquipamento(conexao, equipamentoId);
        return tela.comRegistros("orcamentos", doEquipamento);
    }

    static Tela paginaDoOrcamento(RepositorioOrcamento repositorio, Connection conexao, Long id) throws Exception {
        Orcamento orcamento = repositorio.existente(conexao, id);
        Tela tela = comEquipamentosEAdicionais(repositorio, conexao);
        return tela.comEntidade("orcamento", orcamento);
    }

    static Tela paginaDoEquipamento(RepositorioOrcamento repositorio, Connection conexao, Long equipamentoId)
            throws Exception {
        Equipamento equipamento = repositorio.equipamento(conexao, equipamentoId);
        Registros<Orcamento> orcamentos = repositorio.listarPorEquipamento(conexao, equipamentoId);
        Tela tela = new Tela().comEntidade("equipamento", equipamento);
        return tela.comRegistros("orcamentos", orcamentos);
    }

    private static Tela comEquipamentosEAdicionais(RepositorioOrcamento repositorio, Connection conexao)
            throws Exception {
        Tela tela = new Tela();
        Registros<Equipamento> equipamentos = repositorio.listarEquipamentos(conexao);
        tela.comRegistros("equipamentos", equipamentos);
        List<Map<String, Object>> adicionais = TabelaDeAdicionais.exportar();
        return tela.com("tiposAdicionais", adicionais);
    }
}
