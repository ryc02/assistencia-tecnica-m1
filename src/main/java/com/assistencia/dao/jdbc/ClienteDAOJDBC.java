package com.assistencia.dao.jdbc;

import com.assistencia.dados.*;
import com.assistencia.model.cliente.Cliente;
import java.sql.Connection;
import static com.assistencia.dao.jdbc.SqlCliente.*;

/** [DAO JDBC] Gravação do Cliente (as consultas são herdadas de LeituraDeClienteJDBC). */
public class ClienteDAOJDBC extends LeituraDeClienteJDBC {
    @Override
    public Cliente inserir(Connection conexao, Cliente cliente) throws Exception {
        Campos dados = cliente.exportar();
        Long id = ComandoSql.inserir(conexao, INSERIR, dados, CHAVES_INSERIR);
        return buscarPorId(conexao, id);
    }

    @Override
    public void atualizar(Connection conexao, Cliente cliente) throws Exception {
        Campos dados = cliente.exportar();
        ComandoSql.alterar(conexao, ATUALIZAR, dados, CHAVES_ATUALIZAR);
    }

    @Override
    public void excluir(Connection conexao, Long id) throws Exception {
        ComandoSql.executar(conexao, "DELETE FROM cliente WHERE id = ?", id);
    }

    @Override
    public Long contarEquipamentos(Connection conexao, Long clienteId) throws Exception {
        Linhas linhas = ComandoSql.consultar(conexao, CONTAR_EQUIPAMENTOS, clienteId);
        return linhas.total();
    }
}
