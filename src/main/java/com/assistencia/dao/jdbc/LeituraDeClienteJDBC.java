package com.assistencia.dao.jdbc;

import com.assistencia.dados.Registros;
import com.assistencia.dao.ClienteDAO;
import com.assistencia.model.cliente.Cliente;
import java.sql.Connection;
import static com.assistencia.dao.jdbc.SqlCliente.*;

/** [DAO JDBC] Consultas do Cliente. A gravação fica na subclasse ClienteDAOJDBC. */
abstract class LeituraDeClienteJDBC implements ClienteDAO {
    private final LeitorDeEntidades<Cliente> leitor = new LeitorDeEntidades<>(new MontagemDeCliente());


    @Override
    public Cliente buscarPorId(Connection conexao, Long id) throws Exception {
        return leitor.um(conexao, POR_ID, id);
    }

    @Override
    public Cliente buscarPorCpf(Connection conexao, String cpf) throws Exception {
        return leitor.um(conexao, POR_CPF, cpf);
    }

    @Override
    public Registros<Cliente> listarTodos(Connection conexao) throws Exception {
        return leitor.varios(conexao, TODOS);
    }
}
