package com.assistencia.dao;

import com.assistencia.dados.Registros;
import com.assistencia.model.ficha.FichaTecnica;
import java.sql.Connection;

/** [DAO - Aula 04] Contrato de persistência da Ficha Técnica. */
public interface FichaTecnicaDAO {
    FichaTecnica inserir(Connection conexao, FichaTecnica fichaTecnica) throws Exception;

    void atualizar(Connection conexao, FichaTecnica fichaTecnica) throws Exception;

    void excluirPorOrdemServicoId(Connection conexao, Long ordemServicoId) throws Exception;

    FichaTecnica buscarPorId(Connection conexao, Long id) throws Exception;

    FichaTecnica buscarPorOrdemServicoId(Connection conexao, Long ordemServicoId) throws Exception;

    Registros<FichaTecnica> listarTodas(Connection conexao) throws Exception;
}
