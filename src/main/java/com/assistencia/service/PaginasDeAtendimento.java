package com.assistencia.service;

import com.assistencia.dados.Registros;
import com.assistencia.model.ficha.FichaTecnica;
import com.assistencia.model.ordem.OrdemServico;
import java.sql.Connection;

/** Conteúdo das telas de ordens de serviço, montado dentro da transação aberta por TelasDeAtendimento. */
final class PaginasDeAtendimento {
    private PaginasDeAtendimento() { }

    static Tela paginaDeListagem(LeituraDeAtendimento leitura, Connection conexao, Long orcamentoId)
            throws Exception {
        Tela tela = new Tela();
        if (orcamentoId == null) {
            Registros<OrdemServico> todas = leitura.listarOrdens(conexao);
            return tela.comRegistros("ordens", todas);
        }
        OrdemServico ordem = leitura.ordemDoOrcamento(conexao, orcamentoId);
        Registros<OrdemServico> doOrcamento = Registros.unicoOuVazio(ordem);
        return tela.comRegistros("ordens", doOrcamento);
    }

    static Tela paginaDaOrdem(LeituraDeAtendimento leitura, Connection conexao, Long id) throws Exception {
        OrdemServico ordem = leitura.ordem(conexao, id);
        return comFicha(leitura, conexao, ordem, id);
    }

    static Tela paginaDoOrcamento(LeituraDeAtendimento leitura, Connection conexao, Long orcamentoId)
            throws Exception {
        OrdemServico ordem = leitura.ordemDoOrcamento(conexao, orcamentoId);
        if (ordem == null) {
            return new Tela();
        }
        return comFicha(leitura, conexao, ordem, IdDaEntidade.de(ordem));
    }

    private static Tela comFicha(LeituraDeAtendimento leitura, Connection conexao, OrdemServico ordem, Long ordemId)
            throws Exception {
        FichaTecnica ficha = leitura.fichaDaOrdem(conexao, ordemId);
        Tela tela = new Tela();
        tela.comEntidade("ordemServico", ordem);
        return tela.comEntidade("fichaTecnica", ficha);
    }
}
