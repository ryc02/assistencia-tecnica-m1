package com.assistencia.service;

import com.assistencia.dao.EquipamentoDAO;
import com.assistencia.model.equipamento.Equipamento;
import java.sql.Connection;

/** Busca um equipamento que precisa existir (id obrigatório e 404 quando não encontrado). */
final class EquipamentoExistente {
    private EquipamentoExistente() { }

    static Equipamento buscar(EquipamentoDAO equipamentos, Connection conexao, Long id) throws Exception {
        Existencia.exigirId(id, "equipamento");
        Equipamento equipamento = equipamentos.buscarPorId(conexao, id);
        return Existencia.exigir(equipamento, "Equipamento não encontrado com ID: " + id);
    }
}
