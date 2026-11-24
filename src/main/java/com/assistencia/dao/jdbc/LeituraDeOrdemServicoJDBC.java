package com.assistencia.dao.jdbc;

import com.assistencia.dados.Registros;
import com.assistencia.dao.OrcamentoDAO;
import com.assistencia.dao.OrdemServicoDAO;
import com.assistencia.model.ordem.OrdemServico;
import java.sql.Connection;
import static com.assistencia.dao.jdbc.SqlOrdemServico.*;

/** [DAO JDBC] Consultas do OrdemServico. A gravação fica na subclasse OrdemServicoDAOJDBC. */
abstract class LeituraDeOrdemServicoJDBC implements OrdemServicoDAO {
    private final LeitorDeEntidades<OrdemServico> leitor;

    LeituraDeOrdemServicoJDBC(OrcamentoDAO orcamentos) {
        leitor = new LeitorDeEntidades<>(new MontagemDeOrdemServico(orcamentos));
    }

    @Override
    public OrdemServico buscarPorId(Connection conexao, Long id) throws Exception {
        return leitor.um(conexao, POR_ID, id);
    }

    @Override
    public OrdemServico buscarPorIdParaUpdate(Connection conexao, Long id) throws Exception {
        return leitor.um(conexao, POR_ID_PARA_ATUALIZAR, id);
    }

    @Override
    public OrdemServico buscarPorOrcamentoId(Connection conexao, Long orcamentoId) throws Exception {
        return leitor.um(conexao, POR_ORCAMENTO, orcamentoId);
    }

    @Override
    public Registros<OrdemServico> listarTodas(Connection conexao) throws Exception {
        return leitor.varios(conexao, TODAS);
    }
}
