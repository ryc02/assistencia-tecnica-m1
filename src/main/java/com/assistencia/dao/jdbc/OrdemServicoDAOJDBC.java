package com.assistencia.dao.jdbc;

import com.assistencia.dados.Campos;
import com.assistencia.dao.OrcamentoDAO;
import com.assistencia.model.ordem.OrdemServico;
import java.sql.Connection;
import static com.assistencia.dao.jdbc.SqlOrdemServico.*;

/** [DAO JDBC] Gravação do OrdemServico (as consultas são herdadas de LeituraDeOrdemServicoJDBC). */
public class OrdemServicoDAOJDBC extends LeituraDeOrdemServicoJDBC {
    public OrdemServicoDAOJDBC(OrcamentoDAO orcamentoDAO) {
        super(orcamentoDAO);
    }

    @Override
    public OrdemServico inserir(Connection conexao, OrdemServico ordemServico) throws Exception {
        Campos dados = ordemServico.exportar();
        Long id = ComandoSql.inserir(conexao, INSERIR, dados, CHAVES_INSERIR);
        return buscarPorId(conexao, id);
    }

    @Override
    public void atualizar(Connection conexao, OrdemServico ordemServico) throws Exception {
        Campos dados = ordemServico.exportar();
        ComandoSql.alterar(conexao, ATUALIZAR, dados, CHAVES_ATUALIZAR);
    }

    @Override
    public void excluir(Connection conexao, Long id) throws Exception {
        ComandoSql.executar(conexao, "DELETE FROM ordem_servico WHERE id = ?", id);
    }
}
