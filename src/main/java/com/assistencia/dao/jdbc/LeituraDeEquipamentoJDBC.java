package com.assistencia.dao.jdbc;

import com.assistencia.dados.Registros;
import com.assistencia.dao.ClienteDAO;
import com.assistencia.dao.EquipamentoDAO;
import com.assistencia.model.equipamento.Equipamento;
import java.sql.Connection;
import static com.assistencia.dao.jdbc.SqlEquipamento.*;

/** [DAO JDBC] Consultas do Equipamento. A gravação fica na subclasse EquipamentoDAOJDBC. */
abstract class LeituraDeEquipamentoJDBC implements EquipamentoDAO {
    private final LeitorDeEntidades<Equipamento> leitor;

    LeituraDeEquipamentoJDBC(ClienteDAO clientes) {
        leitor = new LeitorDeEntidades<>(new MontagemDeEquipamento(clientes));
    }

    @Override
    public Equipamento buscarPorId(Connection conexao, Long id) throws Exception {
        return leitor.um(conexao, POR_ID, id);
    }

    @Override
    public Registros<Equipamento> listarTodos(Connection conexao) throws Exception {
        return leitor.varios(conexao, TODOS);
    }

    @Override
    public Registros<Equipamento> listarPorCliente(Connection conexao, Long clienteId) throws Exception {
        return leitor.varios(conexao, POR_CLIENTE, clienteId);
    }
}
