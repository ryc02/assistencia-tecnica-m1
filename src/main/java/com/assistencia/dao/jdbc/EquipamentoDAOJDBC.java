package com.assistencia.dao.jdbc;

import com.assistencia.dao.ClienteDAO;
import com.assistencia.dao.EquipamentoDAO;
import com.assistencia.model.Cliente;
import com.assistencia.model.Equipamento;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * [Requisito 8: Padrão DAO JDBC & Requisito 11: Segurança]
 * Implementação JDBC de EquipamentoDAO.
 */
public class EquipamentoDAOJDBC implements EquipamentoDAO {

    private final ClienteDAO clienteDAO = new ClienteDAOJDBC();

    @Override
    public Equipamento inserir(Connection conn, Equipamento eq) throws Exception {
        String sql = "INSERT INTO equipamento (cliente_id, tipo, marca, modelo, numero_serie, cor, voltagem, descricao, data_cadastro) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setLong(1, eq.getCliente().getId());
            stmt.setString(2, eq.getTipo());
            stmt.setString(3, eq.getMarca());
            stmt.setString(4, eq.getModelo());
            stmt.setString(5, eq.getNumeroSerie());
            stmt.setString(6, eq.getCor());
            stmt.setString(7, eq.getVoltagem());
            stmt.setString(8, eq.getDescricao());
            stmt.setTimestamp(9, Timestamp.valueOf(eq.getDataCadastro() != null ? eq.getDataCadastro() : LocalDateTime.now()));

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    eq.setId(rs.getLong(1));
                }
            }
        }
        return eq;
    }

    @Override
    public void atualizar(Connection conn, Equipamento eq) throws Exception {
        String sql = "UPDATE equipamento SET cliente_id = ?, tipo = ?, marca = ?, modelo = ?, numero_serie = ?, cor = ?, voltagem = ?, descricao = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, eq.getCliente().getId());
            stmt.setString(2, eq.getTipo());
            stmt.setString(3, eq.getMarca());
            stmt.setString(4, eq.getModelo());
            stmt.setString(5, eq.getNumeroSerie());
            stmt.setString(6, eq.getCor());
            stmt.setString(7, eq.getVoltagem());
            stmt.setString(8, eq.getDescricao());
            stmt.setLong(9, eq.getId());

            stmt.executeUpdate();
        }
    }

    @Override
    public void excluir(Connection conn, Long id) throws Exception {
        String sql = "DELETE FROM equipamento WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public Equipamento buscarPorId(Connection conn, Long id) throws Exception {
        String sql = "SELECT * FROM equipamento WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRow(conn, rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Equipamento> listarTodos(Connection conn) throws Exception {
        List<Equipamento> lista = new ArrayList<>();
        String sql = "SELECT * FROM equipamento ORDER BY id DESC";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(mapRow(conn, rs));
            }
        }
        return lista;
    }

    @Override
    public List<Equipamento> listarPorCliente(Connection conn, Long clienteId) throws Exception {
        List<Equipamento> lista = new ArrayList<>();
        String sql = "SELECT * FROM equipamento WHERE cliente_id = ? ORDER BY id DESC";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, clienteId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapRow(conn, rs));
                }
            }
        }
        return lista;
    }

    private Equipamento mapRow(Connection conn, ResultSet rs) throws Exception {
        Equipamento eq = new Equipamento();
        eq.setId(rs.getLong("id"));
        Long clienteId = rs.getLong("cliente_id");
        Cliente c = clienteDAO.buscarPorId(conn, clienteId);
        eq.setCliente(c);
        eq.setTipo(rs.getString("tipo"));
        eq.setMarca(rs.getString("marca"));
        eq.setModelo(rs.getString("modelo"));
        eq.setNumeroSerie(rs.getString("numero_serie"));
        eq.setCor(rs.getString("cor"));
        eq.setVoltagem(rs.getString("voltagem"));
        eq.setDescricao(rs.getString("descricao"));
        Timestamp ts = rs.getTimestamp("data_cadastro");
        if (ts != null) {
            eq.setDataCadastro(ts.toLocalDateTime());
        }
        return eq;
    }
}
