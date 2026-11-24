package com.assistencia.dao.jdbc;

import com.assistencia.dados.*;
import com.assistencia.dao.EquipamentoDAO;
import com.assistencia.model.orcamento.Orcamento;
import java.sql.Connection;
import static com.assistencia.dao.jdbc.SqlOrcamento.*;

/** [DAO JDBC] Gravação do Orcamento (as consultas são herdadas de LeituraDeOrcamentoJDBC). */
public class OrcamentoDAOJDBC extends LeituraDeOrcamentoJDBC {
    public OrcamentoDAOJDBC(EquipamentoDAO equipamentoDAO) {
        super(equipamentoDAO);
    }

    @Override
    public Orcamento inserir(Connection conexao, Orcamento orcamento) throws Exception {
        Campos dados = orcamento.exportar();
        Long id = ComandoSql.inserir(conexao, INSERIR, dados, CHAVES_INSERIR);
        return buscarPorId(conexao, id);
    }

    @Override
    public void atualizar(Connection conexao, Orcamento orcamento) throws Exception {
        Campos dados = orcamento.exportar();
        ComandoSql.alterar(conexao, ATUALIZAR, dados, CHAVES_ATUALIZAR);
    }

    @Override
    public void excluir(Connection conexao, Long id) throws Exception {
        ComandoSql.executar(conexao, "DELETE FROM orcamento WHERE id = ?", id);
    }

    @Override
    public boolean possuiOrdemDeServico(Connection conexao, Long id) throws Exception {
        Linhas linhas = ComandoSql.consultar(conexao, CONTAR_ORDENS, id);
        return linhas.total() > 0;
    }
}
