package com.assistencia.dao.jdbc;

import com.assistencia.dados.Registros;
import com.assistencia.dao.FichaTecnicaDAO;
import com.assistencia.dao.OrdemServicoDAO;
import com.assistencia.model.ficha.FichaTecnica;
import java.sql.Connection;
import static com.assistencia.dao.jdbc.SqlFichaTecnica.*;

/** [DAO JDBC] Consultas do FichaTecnica. A gravação fica na subclasse FichaTecnicaDAOJDBC. */
abstract class LeituraDeFichaTecnicaJDBC implements FichaTecnicaDAO {
    private final LeitorDeEntidades<FichaTecnica> leitor;

    LeituraDeFichaTecnicaJDBC(OrdemServicoDAO ordemServicos) {
        leitor = new LeitorDeEntidades<>(new MontagemDeFichaTecnica(ordemServicos));
    }

    @Override
    public FichaTecnica buscarPorId(Connection conexao, Long id) throws Exception {
        return leitor.um(conexao, POR_ID, id);
    }

    @Override
    public FichaTecnica buscarPorOrdemServicoId(Connection conexao, Long ordemServicoId) throws Exception {
        return leitor.um(conexao, POR_ORDEM, ordemServicoId);
    }

    @Override
    public Registros<FichaTecnica> listarTodas(Connection conexao) throws Exception {
        return leitor.varios(conexao, TODAS);
    }
}
