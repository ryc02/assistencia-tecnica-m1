package com.assistencia.service;

import com.assistencia.dados.Campos;
import com.assistencia.dao.*;
import com.assistencia.model.ficha.FichaTecnica;
import com.assistencia.model.ordem.OrdemServico;
import java.sql.Connection;

/** Procura a ordem de serviço de um orçamento e só a considera completa se a ficha técnica existir. */
final class OrdemCompleta {
    private OrdemCompleta() { }

    static OrdemServico buscar(OrdemServicoDAO ordens, FichaTecnicaDAO fichas, Connection conexao, Long orcamentoId)
            throws Exception {
        OrdemServico ordem = ordens.buscarPorOrcamentoId(conexao, orcamentoId);
        if (ordem == null) {
            return null;
        }
        Campos dados = ordem.exportar();
        Long ordemId = dados.inteiroLongo("id");
        FichaTecnica ficha = fichas.buscarPorOrdemServicoId(conexao, ordemId);
        if (ficha == null) {
            return null;
        }
        return ordem;
    }
}
