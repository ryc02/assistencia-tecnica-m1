package com.assistencia.dao.jdbc;

import com.assistencia.dados.Campos;
import com.assistencia.dao.OrdemServicoDAO;
import com.assistencia.model.ficha.FichaTecnica;
import java.sql.Connection;
import static com.assistencia.dao.jdbc.SqlFichaTecnica.*;

/** [DAO JDBC] Gravação do FichaTecnica (as consultas são herdadas de LeituraDeFichaTecnicaJDBC). */
public class FichaTecnicaDAOJDBC extends LeituraDeFichaTecnicaJDBC {
    public FichaTecnicaDAOJDBC(OrdemServicoDAO ordemServicoDAO) {
        super(ordemServicoDAO);
    }

    @Override
    public FichaTecnica inserir(Connection conexao, FichaTecnica fichaTecnica) throws Exception {
        Campos dados = fichaTecnica.exportar();
        Long id = ComandoSql.inserir(conexao, INSERIR, dados, CHAVES_INSERIR);
        return buscarPorId(conexao, id);
    }

    @Override
    public void atualizar(Connection conexao, FichaTecnica fichaTecnica) throws Exception {
        Campos dados = fichaTecnica.exportar();
        ComandoSql.alterar(conexao, ATUALIZAR, dados, CHAVES_ATUALIZAR);
    }

    @Override
    public void excluirPorOrdemServicoId(Connection conexao, Long ordemServicoId) throws Exception {
        ComandoSql.executar(conexao, "DELETE FROM ficha_tecnica WHERE ordem_servico_id = ?", ordemServicoId);
    }
}
