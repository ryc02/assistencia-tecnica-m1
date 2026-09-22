package com.assistencia.dao.jdbc;

import com.assistencia.dao.ClienteDAO;
import com.assistencia.model.Cliente;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * [Requisito 8: Padrão DAO JDBC & Requisito 11: Segurança]
 * Implementação JDBC de ClienteDAO utilizando PreparedStatement e try-with-resources.
 */
public class ClienteDAOJDBC implements ClienteDAO {

    @Override
    public Cliente inserir(Connection conn, Cliente cliente) throws Exception {
        String sql = "INSERT INTO cliente (nome, cpf, email, telefone, logradouro, numero, bairro, cidade, data_cadastro) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setString(3, cliente.getEmail());
            stmt.setString(4, cliente.getTelefone());
            stmt.setString(5, cliente.getLogradouro());
            stmt.setString(6, cliente.getNumero());
            stmt.setString(7, cliente.getBairro());
            stmt.setString(8, cliente.getCidade());
            stmt.setTimestamp(9, Timestamp.valueOf(cliente.getDataCadastro() != null ? cliente.getDataCadastro() : LocalDateTime.now()));

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    cliente.setId(rs.getLong(1));
                }
            }
        }
        return cliente;
    }

    @Override
    public void atualizar(Connection conn, Cliente cliente) throws Exception {
        String sql = "UPDATE cliente SET nome = ?, cpf = ?, email = ?, telefone = ?, logradouro = ?, numero = ?, bairro = ?, cidade = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setString(3, cliente.getEmail());
            stmt.setString(4, cliente.getTelefone());
            stmt.setString(5, cliente.getLogradouro());
            stmt.setString(6, cliente.getNumero());
            stmt.setString(7, cliente.getBairro());
            stmt.setString(8, cliente.getCidade());
            stmt.setLong(9, cliente.getId());

            stmt.executeUpdate();
        }
    }

    @Override
    public void excluir(Connection conn, Long id) throws Exception {
        String sql = "DELETE FROM cliente WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public Cliente buscarPorId(Connection conn, Long id) throws Exception {
        String sql = "SELECT * FROM cliente WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null;
    }

    @Override
    public Cliente buscarPorCpf(Connection conn, String cpf) throws Exception {
        String sql = "SELECT * FROM cliente WHERE cpf = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Cliente> listarTodos(Connection conn) throws Exception {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM cliente ORDER BY nome ASC";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(mapRow(rs));
            }
        }
        return lista;
    }

    private Cliente mapRow(ResultSet rs) throws SQLException {
        Cliente c = new Cliente();
        c.setId(rs.getLong("id"));
        c.setNome(rs.getString("nome"));
        c.setCpf(rs.getString("cpf"));
        c.setEmail(rs.getString("email"));
        c.setTelefone(rs.getString("telefone"));
        c.setLogradouro(rs.getString("logradouro"));
        c.setNumero(rs.getString("numero"));
        c.setBairro(rs.getString("bairro"));
        c.setCidade(rs.getString("cidade"));
        Timestamp ts = rs.getTimestamp("data_cadastro");
        if (ts != null) {
            c.setDataCadastro(ts.toLocalDateTime());
        }
        return c;
    }
}
