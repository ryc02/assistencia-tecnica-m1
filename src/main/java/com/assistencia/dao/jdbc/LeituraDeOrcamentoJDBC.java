package com.assistencia.dao.jdbc;

import com.assistencia.dados.Registros;
import com.assistencia.dao.EquipamentoDAO;
import com.assistencia.dao.OrcamentoDAO;
import com.assistencia.model.orcamento.Orcamento;
import java.sql.Connection;
import static com.assistencia.dao.jdbc.SqlOrcamento.*;

/** [DAO JDBC] Consultas do Orcamento. A gravação fica na subclasse OrcamentoDAOJDBC. */
abstract class LeituraDeOrcamentoJDBC implements OrcamentoDAO {
    private final LeitorDeEntidades<Orcamento> leitor;

    LeituraDeOrcamentoJDBC(EquipamentoDAO equipamentos) {
        leitor = new LeitorDeEntidades<>(new MontagemDeOrcamento(equipamentos));
    }

    @Override
    public Orcamento buscarPorId(Connection conexao, Long id) throws Exception {
        return leitor.um(conexao, POR_ID, id);
    }

    @Override
    public Orcamento buscarPorIdParaUpdate(Connection conexao, Long id) throws Exception {
        return leitor.um(conexao, POR_ID_PARA_ATUALIZAR, id);
    }

    @Override
    public Registros<Orcamento> listarTodos(Connection conexao) throws Exception {
        return leitor.varios(conexao, TODOS);
    }

    @Override
    public Registros<Orcamento> listarPorEquipamento(Connection conexao, Long equipamentoId) throws Exception {
        return leitor.varios(conexao, POR_EQUIPAMENTO, equipamentoId);
    }
}
