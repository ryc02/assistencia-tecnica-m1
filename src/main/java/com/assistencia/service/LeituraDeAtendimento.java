package com.assistencia.service;

import com.assistencia.dados.Registros;
import com.assistencia.dao.*;
import com.assistencia.model.ficha.FichaTecnica;
import com.assistencia.model.ordem.OrdemServico;
import java.sql.Connection;

/** Leituras de Ordens de Serviço e Fichas Técnicas usadas pelas consultas e telas. */
public class LeituraDeAtendimento {
    private final OrdemServicoDAO ordens;
    private final FichaTecnicaDAO fichas;

    public LeituraDeAtendimento(OrdemServicoDAO ordens, FichaTecnicaDAO fichas) {
        this.ordens = ordens;
        this.fichas = fichas;
    }

    OrdemServico ordem(Connection conexao, Long id) throws Exception {
        return OrdemExistente.buscar(ordens, conexao, id);
    }

    OrdemServico ordemDoOrcamento(Connection conexao, Long orcamentoId) throws Exception {
        Existencia.exigirId(orcamentoId, "orçamento");
        return ordens.buscarPorOrcamentoId(conexao, orcamentoId);
    }

    FichaTecnica ficha(Connection conexao, Long id) throws Exception {
        return FichaExistente.buscar(fichas, conexao, id);
    }

    FichaTecnica fichaDaOrdem(Connection conexao, Long ordemId) throws Exception {
        Existencia.exigirId(ordemId, "ordem de serviço");
        return fichas.buscarPorOrdemServicoId(conexao, ordemId);
    }

    Registros<OrdemServico> listarOrdens(Connection conexao) throws Exception {
        return ordens.listarTodas(conexao);
    }

    Registros<FichaTecnica> listarFichas(Connection conexao) throws Exception {
        return fichas.listarTodas(conexao);
    }
}
