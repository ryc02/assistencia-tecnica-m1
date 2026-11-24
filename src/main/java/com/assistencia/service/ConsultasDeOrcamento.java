package com.assistencia.service;

import com.assistencia.dados.*;
import com.assistencia.dao.*;
import com.assistencia.model.equipamento.Equipamento;
import com.assistencia.model.orcamento.Orcamento;
import java.sql.Connection;

/** Consultas dos casos de uso de orçamento; a subclasse RepositorioOrcamento acrescenta a gravação. */
abstract class ConsultasDeOrcamento {
    protected final OrcamentoDAO orcamentos;
    protected final EquipamentoDAO equipamentos;

    ConsultasDeOrcamento(OrcamentoDAO orcamentos, EquipamentoDAO equipamentos) {
        this.orcamentos = orcamentos;
        this.equipamentos = equipamentos;
    }

    Equipamento equipamento(Connection conexao, Long equipamentoId) throws Exception {
        return EquipamentoExistente.buscar(equipamentos, conexao, equipamentoId);
    }

    Equipamento equipamentoInformado(Connection conexao, Campos dados) throws Exception {
        return equipamento(conexao, dados.inteiroLongo("equipamentoId"));
    }

    Orcamento existente(Connection conexao, Long id) throws Exception {
        Existencia.exigirId(id, "orçamento");
        Orcamento orcamento = orcamentos.buscarPorId(conexao, id);
        return Existencia.exigir(orcamento, "Orçamento não encontrado com ID: " + id);
    }

    Registros<Orcamento> listarTodos(Connection conexao) throws Exception {
        return orcamentos.listarTodos(conexao);
    }

    Registros<Orcamento> listarPorEquipamento(Connection conexao, Long equipamentoId) throws Exception {
        return orcamentos.listarPorEquipamento(conexao, equipamentoId);
    }

    Registros<Equipamento> listarEquipamentos(Connection conexao) throws Exception {
        return equipamentos.listarTodos(conexao);
    }
}
