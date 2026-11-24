package com.assistencia.dao.jdbc;

import com.assistencia.dados.*;
import com.assistencia.dao.ClienteDAO;
import com.assistencia.model.equipamento.Equipamento;
import java.sql.Connection;
import static com.assistencia.dao.jdbc.SqlEquipamento.*;

/** [DAO JDBC] Gravação do Equipamento (as consultas são herdadas de LeituraDeEquipamentoJDBC). */
public class EquipamentoDAOJDBC extends LeituraDeEquipamentoJDBC {
    public EquipamentoDAOJDBC(ClienteDAO clienteDAO) {
        super(clienteDAO);
    }

    @Override
    public Equipamento inserir(Connection conexao, Equipamento equipamento) throws Exception {
        Campos dados = equipamento.exportar();
        Long id = ComandoSql.inserir(conexao, INSERIR, dados, CHAVES_INSERIR);
        return buscarPorId(conexao, id);
    }

    @Override
    public void atualizar(Connection conexao, Equipamento equipamento) throws Exception {
        Campos dados = equipamento.exportar();
        ComandoSql.alterar(conexao, ATUALIZAR, dados, CHAVES_ATUALIZAR);
    }

    @Override
    public void excluir(Connection conexao, Long id) throws Exception {
        ComandoSql.executar(conexao, "DELETE FROM equipamento WHERE id = ?", id);
    }

    @Override
    public Long contarOrcamentos(Connection conexao, Long equipamentoId) throws Exception {
        Linhas linhas = ComandoSql.consultar(conexao, CONTAR_ORCAMENTOS, equipamentoId);
        return linhas.total();
    }
}
