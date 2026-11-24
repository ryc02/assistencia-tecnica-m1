package com.assistencia.service;

import com.assistencia.dados.Registros;
import com.assistencia.infra.GerenciadorTransacao;
import com.assistencia.model.ficha.FichaTecnica;
import java.sql.Connection;

/** Consultas de ordens de serviço (herdadas de ConsultaDeOrdens) e de fichas técnicas. */
public class ConsultaAtendimento extends ConsultaDeOrdens {
    public ConsultaAtendimento(LeituraDeAtendimento leitura, GerenciadorTransacao transacao) {
        super(leitura, transacao);
    }

    public FichaTecnica ficha(Long id) throws Exception {
        return transacao.executar(conexao -> leituraDaFicha(conexao, id));
    }

    public FichaTecnica fichaDaOrdem(Long ordemId) throws Exception {
        return transacao.executar(conexao -> leituraDaFichaDaOrdem(conexao, ordemId));
    }

    public Registros<FichaTecnica> listarFichas() throws Exception {
        return transacao.executar(leitura::listarFichas);
    }

    private FichaTecnica leituraDaFicha(Connection conexao, Long id) throws Exception {
        return leitura.ficha(conexao, id);
    }

    private FichaTecnica leituraDaFichaDaOrdem(Connection conexao, Long ordemId) throws Exception {
        return leitura.fichaDaOrdem(conexao, ordemId);
    }
}
