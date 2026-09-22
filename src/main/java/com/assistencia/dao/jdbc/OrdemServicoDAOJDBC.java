package com.assistencia.dao.jdbc;

import com.assistencia.dao.OrcamentoDAO;
import com.assistencia.dao.OrdemServicoDAO;
import com.assistencia.model.Orcamento;
import com.assistencia.model.OrdemServico;
import com.assistencia.model.Prioridade;
import com.assistencia.model.StatusOrdem;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * [Requisito 8: Padrão DAO JDBC & Requisito 6: NOT NULL UNIQUE em orcamento_id]
 * Implementação JDBC de OrdemServicoDAO.
 */
public class OrdemServicoDAOJDBC implements OrdemServicoDAO {

    private final OrcamentoDAO orcamentoDAO = new OrcamentoDAOJDBC();

    @Override
    public OrdemServico inserir(Connection conn, OrdemServico os) throws Exception {
        String sql = "INSERT INTO ordem_servico (orcamento_id, responsavel, prioridade, status, data_abertura, previsao_conclusao, data_conclusao, observacoes, prazo_garantia_dias) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setLong(1, os.getOrcamento().getId());
            stmt.setString(2, os.getResponsavel());
            stmt.setString(3, os.getPrioridade().name());
            stmt.setString(4, os.getStatus().name());
            stmt.setTimestamp(5, Timestamp.valueOf(os.getDataAbertura() != null ? os.getDataAbertura() : LocalDateTime.now()));
            stmt.setTimestamp(6, os.getPrevisaoConclusao() != null ? Timestamp.valueOf(os.getPrevisaoConclusao()) : null);
            stmt.setTimestamp(7, os.getDataConclusao() != null ? Timestamp.valueOf(os.getDataConclusao()) : null);
            stmt.setString(8, os.getObservacoes());
            stmt.setObject(9, os.getPrazoGarantiaDias());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    os.setId(rs.getLong(1));
                }
            }
        }
        return os;
    }

    @Override
    public void atualizar(Connection conn, OrdemServico os) throws Exception {
        String sql = "UPDATE ordem_servico SET responsavel = ?, prioridade = ?, status = ?, previsao_conclusao = ?, data_conclusao = ?, observacoes = ?, prazo_garantia_dias = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, os.getResponsavel());
            stmt.setString(2, os.getPrioridade().name());
            stmt.setString(3, os.getStatus().name());
            stmt.setTimestamp(4, os.getPrevisaoConclusao() != null ? Timestamp.valueOf(os.getPrevisaoConclusao()) : null);
            stmt.setTimestamp(5, os.getDataConclusao() != null ? Timestamp.valueOf(os.getDataConclusao()) : null);
            stmt.setString(6, os.getObservacoes());
            stmt.setObject(7, os.getPrazoGarantiaDias());
            stmt.setLong(8, os.getId());

            stmt.executeUpdate();
        }
    }

    @Override
    public void excluir(Connection conn, Long id) throws Exception {
        String sql = "DELETE FROM ordem_servico WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public OrdemServico buscarPorId(Connection conn, Long id) throws Exception {
        String sql = "SELECT * FROM ordem_servico WHERE id = ?";
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
    public OrdemServico buscarPorOrcamentoId(Connection conn, Long orcamentoId) throws Exception {
        String sql = "SELECT * FROM ordem_servico WHERE orcamento_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, orcamentoId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRow(conn, rs);
                }
            }
        }
        return null;
    }

    @Override
    public OrdemServico buscarPorIdParaUpdate(Connection conn, Long id) throws Exception {
        String sql = "SELECT * FROM ordem_servico WHERE id = ? FOR UPDATE";
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
    public List<OrdemServico> listarTodas(Connection conn) throws Exception {
        List<OrdemServico> lista = new ArrayList<>();
        String sql = "SELECT * FROM ordem_servico ORDER BY id DESC";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(mapRow(conn, rs));
            }
        }
        return lista;
    }

    @Override
    public List<OrdemServico> listarPorOrcamento(Connection conn, Long orcamentoId) throws Exception {
        List<OrdemServico> lista = new ArrayList<>();
        String sql = "SELECT * FROM ordem_servico WHERE orcamento_id = ? ORDER BY id DESC";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, orcamentoId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapRow(conn, rs));
                }
            }
        }
        return lista;
    }

    private OrdemServico mapRow(Connection conn, ResultSet rs) throws Exception {
        OrdemServico os = new OrdemServico();
        os.setId(rs.getLong("id"));
        Long orcamentoId = rs.getLong("orcamento_id");
        Orcamento orc = orcamentoDAO.buscarPorId(conn, orcamentoId);
        os.setOrcamento(orc);
        os.setResponsavel(rs.getString("responsavel"));
        os.setPrioridade(Prioridade.valueOf(rs.getString("prioridade")));
        os.setStatus(StatusOrdem.valueOf(rs.getString("status")));
        Timestamp tsAbertura = rs.getTimestamp("data_abertura");
        if (tsAbertura != null) os.setDataAbertura(tsAbertura.toLocalDateTime());
        Timestamp tsPrevisao = rs.getTimestamp("previsao_conclusao");
        if (tsPrevisao != null) os.setPrevisaoConclusao(tsPrevisao.toLocalDateTime());
        Timestamp tsConclusao = rs.getTimestamp("data_conclusao");
        if (tsConclusao != null) os.setDataConclusao(tsConclusao.toLocalDateTime());
        os.setObservacoes(rs.getString("observacoes"));
        os.setPrazoGarantiaDias(rs.getObject("prazo_garantia_dias", Integer.class));
        return os;
    }
}
