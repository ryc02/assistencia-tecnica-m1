package com.assistencia.dao.jdbc;

import com.assistencia.dao.FichaTecnicaDAO;
import com.assistencia.dao.OrdemServicoDAO;
import com.assistencia.model.EstadoConservacao;
import com.assistencia.model.FichaTecnica;
import com.assistencia.model.OrdemServico;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * [Requisito 8: Padrão DAO JDBC & Requisito 6: Multiplicidade 1:1]
 * Implementação JDBC de FichaTecnicaDAO.
 */
public class FichaTecnicaDAOJDBC implements FichaTecnicaDAO {

    private final OrdemServicoDAO ordemServicoDAO = new OrdemServicoDAOJDBC();

    @Override
    public FichaTecnica inserir(Connection conn, FichaTecnica ft) throws Exception {
        String sql = "INSERT INTO ficha_tecnica (ordem_servico_id, estado_conservacao, acessorios_entregues, liga_normalmente, possui_avarias, descricao_avarias, teste_inicial, observacoes_recebimento, data_registro) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setLong(1, ft.getOrdemServico().getId());
            stmt.setString(2, ft.getEstadoConservacao().name());
            stmt.setString(3, ft.getAcessoriosEntregues());
            stmt.setObject(4, ft.getLigaNormalmente());
            stmt.setObject(5, ft.getPossuiAvarias());
            stmt.setString(6, ft.getDescricaoAvarias());
            stmt.setString(7, ft.getTesteInicial());
            stmt.setString(8, ft.getObservacoesRecebimento());
            stmt.setTimestamp(9, Timestamp.valueOf(ft.getDataRegistro() != null ? ft.getDataRegistro() : LocalDateTime.now()));

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    ft.setId(rs.getLong(1));
                }
            }
        }
        return ft;
    }

    @Override
    public void atualizar(Connection conn, FichaTecnica ft) throws Exception {
        String sql = "UPDATE ficha_tecnica SET estado_conservacao = ?, acessorios_entregues = ?, liga_normalmente = ?, possui_avarias = ?, descricao_avarias = ?, teste_inicial = ?, observacoes_recebimento = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, ft.getEstadoConservacao().name());
            stmt.setString(2, ft.getAcessoriosEntregues());
            stmt.setObject(3, ft.getLigaNormalmente());
            stmt.setObject(4, ft.getPossuiAvarias());
            stmt.setString(5, ft.getDescricaoAvarias());
            stmt.setString(6, ft.getTesteInicial());
            stmt.setString(7, ft.getObservacoesRecebimento());
            stmt.setLong(8, ft.getId());

            stmt.executeUpdate();
        }
    }

    @Override
    public void excluir(Connection conn, Long id) throws Exception {
        String sql = "DELETE FROM ficha_tecnica WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public void excluirPorOrdemServicoId(Connection conn, Long ordemServicoId) throws Exception {
        String sql = "DELETE FROM ficha_tecnica WHERE ordem_servico_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, ordemServicoId);
            stmt.executeUpdate();
        }
    }

    @Override
    public FichaTecnica buscarPorId(Connection conn, Long id) throws Exception {
        String sql = "SELECT * FROM ficha_tecnica WHERE id = ?";
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
    public FichaTecnica buscarPorOrdemServicoId(Connection conn, Long ordemServicoId) throws Exception {
        String sql = "SELECT * FROM ficha_tecnica WHERE ordem_servico_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, ordemServicoId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRow(conn, rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<FichaTecnica> listarTodas(Connection conn) throws Exception {
        List<FichaTecnica> lista = new ArrayList<>();
        String sql = "SELECT * FROM ficha_tecnica ORDER BY id DESC";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(mapRow(conn, rs));
            }
        }
        return lista;
    }

    private FichaTecnica mapRow(Connection conn, ResultSet rs) throws Exception {
        FichaTecnica ft = new FichaTecnica();
        ft.setId(rs.getLong("id"));
        Long osId = rs.getLong("ordem_servico_id");
        OrdemServico os = ordemServicoDAO.buscarPorId(conn, osId);
        ft.setOrdemServico(os);
        ft.setEstadoConservacao(EstadoConservacao.valueOf(rs.getString("estado_conservacao")));
        ft.setAcessoriosEntregues(rs.getString("acessorios_entregues"));
        ft.setLigaNormalmente(rs.getObject("liga_normalmente", Boolean.class));
        ft.setPossuiAvarias(rs.getObject("possui_avarias", Boolean.class));
        ft.setDescricaoAvarias(rs.getString("descricao_avarias"));
        ft.setTesteInicial(rs.getString("teste_inicial"));
        ft.setObservacoesRecebimento(rs.getString("observacoes_recebimento"));
        Timestamp ts = rs.getTimestamp("data_registro");
        if (ts != null) {
            ft.setDataRegistro(ts.toLocalDateTime());
        }
        return ft;
    }
}
