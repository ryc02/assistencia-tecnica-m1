package com.assistencia.dao;

import com.assistencia.model.FichaTecnica;

import java.sql.Connection;
import java.util.List;

/**
 * [Requisito 8: Padrão DAO]
 * Contrato de persistência para a entidade FichaTecnica.
 */
public interface FichaTecnicaDAO {
    FichaTecnica inserir(Connection conn, FichaTecnica fichaTecnica) throws Exception;
    void atualizar(Connection conn, FichaTecnica fichaTecnica) throws Exception;
    void excluir(Connection conn, Long id) throws Exception;
    void excluirPorOrdemServicoId(Connection conn, Long ordemServicoId) throws Exception;
    FichaTecnica buscarPorId(Connection conn, Long id) throws Exception;
    FichaTecnica buscarPorOrdemServicoId(Connection conn, Long ordemServicoId) throws Exception;
    List<FichaTecnica> listarTodas(Connection conn) throws Exception;
}
