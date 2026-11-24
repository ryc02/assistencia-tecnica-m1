package com.assistencia.service;

import com.assistencia.dao.*;
import com.assistencia.model.equipamento.Equipamento;
import java.sql.Connection;

/** Reúne os DAOs usados pelos casos de uso de equipamento (Equipamento e o Cliente dono). */
public class RepositorioEquipamento extends ConsultasDeEquipamento {
    public RepositorioEquipamento(EquipamentoDAO equipamentos, ClienteDAO clientes) {
        super(equipamentos, clientes);
    }

    Equipamento inserir(Connection conexao, Equipamento equipamento) throws Exception {
        return equipamentos.inserir(conexao, equipamento);
    }

    void atualizar(Connection conexao, Equipamento equipamento) throws Exception {
        equipamentos.atualizar(conexao, equipamento);
    }

    void excluir(Connection conexao, Long id) throws Exception {
        equipamentos.excluir(conexao, id);
    }
}
