package com.assistencia.dao.jdbc;

import com.assistencia.dao.EquipamentoDAO;
import com.assistencia.dao.OrcamentoDAO;
import com.assistencia.model.Equipamento;
import com.assistencia.model.Orcamento;
import com.assistencia.model.StatusOrcamento;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * [Requisito 8: Padrão DAO JDBC & Requisito 7: Bloqueio FOR UPDATE]
 * Implementação JDBC de OrcamentoDAO.
 */
public class OrcamentoDAOJDBC implements OrcamentoDAO {

    private final EquipamentoDAO equipamentoDAO = new EquipamentoDAOJDBC();

    @Override
    public Orcamento inserir(Connection conn, Orcamento orc) throws Exception {
        String sql = "INSERT INTO orcamento (equipamento_id, descricao_problema, diagnostico, valor_pecas, valor_mao_de_obra, percentual_desconto, valor_total, status, data_criacao) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setLong(1, orc.getEquipamento().getId());
            stmt.setString(2, orc.getDescricaoProblema());
            stmt.setString(3, orc.getDiagnostico());
            stmt.setBigDecimal(4, orc.getValorPecas());
            stmt.setBigDecimal(5, orc.getValorMaoDeObra());
            stmt.setBigDecimal(6, orc.getPercentualDesconto());
            stmt.setBigDecimal(7, orc.getValorTotal());
            stmt.setString(8, orc.getStatus().name());
            stmt.setTimestamp(9, Timestamp.valueOf(orc.getDataCriacao() != null ? orc.getDataCriacao() : LocalDateTime.now()));

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    orc.setId(rs.getLong(1));
                }
            }
        }
        return orc;
    }

    @Override
    public void atualizar(Connection conn, Orcamento orc) throws Exception {
        String sql = "UPDATE orcamento SET equipamento_id = ?, descricao_problema = ?, diagnostico = ?, valor_pecas = ?, valor_mao_de_obra = ?, percentual_desconto = ?, valor_total = ?, status = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, orc.getEquipamento().getId());
            stmt.setString(2, orc.getDescricaoProblema());
            stmt.setString(3, orc.getDiagnostico());
            stmt.setBigDecimal(4, orc.getValorPecas());
            stmt.setBigDecimal(5, orc.getValorMaoDeObra());
            stmt.setBigDecimal(6, orc.getPercentualDesconto());
            stmt.setBigDecimal(7, orc.getValorTotal());
            stmt.setString(8, orc.getStatus().name());
            stmt.setLong(9, orc.getId());

            stmt.executeUpdate();
        }
    }

    @Override
    public void excluir(Connection conn, Long id) throws Exception {
        String sql = "DELETE FROM orcamento WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public Orcamento buscarPorId(Connection conn, Long id) throws Exception {
        String sql = "SELECT * FROM orcamento WHERE id = ?";
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
    public Orcamento buscarPorIdParaUpdate(Connection conn, Long id) throws Exception {
        // [Requisito 7] Bloqueia a linha do orçamento para a transação de aprovação
        String sql = "SELECT * FROM orcamento WHERE id = ? FOR UPDATE";
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
    public List<Orcamento> listarTodos(Connection conn) throws Exception {
        List<Orcamento> lista = new ArrayList<>();
        String sql = "SELECT * FROM orcamento ORDER BY id DESC";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(mapRow(conn, rs));
            }
        }
        return lista;
    }

    @Override
    public List<Orcamento> listarPorEquipamento(Connection conn, Long equipamentoId) throws Exception {
        List<Orcamento> lista = new ArrayList<>();
        String sql = "SELECT * FROM orcamento WHERE equipamento_id = ? ORDER BY id DESC";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, equipamentoId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapRow(conn, rs));
                }
            }
        }
        return lista;
    }

    private Orcamento mapRow(Connection conn, ResultSet rs) throws Exception {
        Orcamento orc = new Orcamento();
        orc.setId(rs.getLong("id"));
        Long equipamentoId = rs.getLong("equipamento_id");
        Equipamento eq = equipamentoDAO.buscarPorId(conn, equipamentoId);
        orc.setEquipamento(eq);
        orc.setDescricaoProblema(rs.getString("descricao_problema"));
        orc.setDiagnostico(rs.getString("diagnostico"));
        orc.setValorPecas(rs.getBigDecimal("valor_pecas"));
        orc.setValorMaoDeObra(rs.getBigDecimal("valor_mao_de_obra"));
        orc.setPercentualDesconto(rs.getBigDecimal("percentual_desconto"));
        orc.setValorTotal(rs.getBigDecimal("valor_total"));
        orc.setStatus(StatusOrcamento.valueOf(rs.getString("status")));
        Timestamp ts = rs.getTimestamp("data_criacao");
        if (ts != null) {
            orc.setDataCriacao(ts.toLocalDateTime());
        }
        return orc;
    }
}
