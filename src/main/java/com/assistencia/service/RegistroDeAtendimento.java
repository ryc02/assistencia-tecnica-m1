package com.assistencia.service;

import com.assistencia.dados.Campos;
import com.assistencia.dao.*;
import com.assistencia.model.orcamento.Orcamento;
import com.assistencia.model.ordem.OrdemServico;
import java.sql.Connection;
import static com.assistencia.service.AtendimentoMontado.*;

/** Grava e remove o par Ordem de Serviço + Ficha Técnica (relacionamento 1:1), sempre juntos. */
public class RegistroDeAtendimento {
    private final OrdemServicoDAO ordens;
    private final FichaTecnicaDAO fichas;

    public RegistroDeAtendimento(OrdemServicoDAO ordens, FichaTecnicaDAO fichas) {
        this.ordens = ordens;
        this.fichas = fichas;
    }

    OrdemServico registrar(Connection conexao, Orcamento orcamento, Campos dadosOrdem, Campos dadosFicha)
            throws Exception {
        OrdemServico ordem = novaOrdem(dadosOrdem, orcamento);
        OrdemServico gravada = ordens.inserir(conexao, ordem);
        fichas.inserir(conexao, novaFicha(dadosFicha, gravada));
        return gravada;
    }

    OrdemServico ordemParaAtualizar(Connection conexao, Long ordemId) throws Exception {
        return OrdemExistente.paraAtualizar(ordens, conexao, ordemId);
    }

    void remover(Connection conexao, Long ordemId) throws Exception {
        fichas.excluirPorOrdemServicoId(conexao, ordemId);
        ordens.excluir(conexao, ordemId);
    }

    /** Ordem já gerada para o orçamento, somente se a ficha também existir. */
    OrdemServico ordemCompleta(Connection conexao, Long orcamentoId) throws Exception {
        return OrdemCompleta.buscar(ordens, fichas, conexao, orcamentoId);
    }
}
