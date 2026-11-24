package com.assistencia.factory;

import com.assistencia.api.recurso.*;
import static com.assistencia.factory.FabricaDeAtendimentos.*;
import static com.assistencia.factory.FabricaDeOrcamentos.*;
import static com.assistencia.factory.RecursoFactory.*;

/** Registra os recursos REST de orçamentos, ordens de serviço e fichas técnicas. */
final class RecursosDeAtendimento {
    private RecursosDeAtendimento() { }

    static void registrar() {
        OrcamentoRecurso orcamentos = new OrcamentoRecurso(orcamentoService(), consultaOrcamento());
        AcoesDeOrcamentoRecurso acoesDeOrcamento = new AcoesDeOrcamentoRecurso(gestaoOrcamento(), aprovacaoService());
        CONSULTAVEIS.registrar("orcamentos", orcamentos);
        CRIAVEIS.registrar("orcamentos", orcamentos);
        ATUALIZAVEIS.registrar("orcamentos", orcamentos);
        EXCLUIVEIS.registrar("orcamentos", acoesDeOrcamento);
        COM_ACOES.registrar("orcamentos", acoesDeOrcamento);
        OrdemServicoRecurso ordens = new OrdemServicoRecurso(ordemServicoService(), consultaAtendimento());
        CONSULTAVEIS.registrar("ordens-servico", ordens);
        ATUALIZAVEIS.registrar("ordens-servico", ordens);
        COM_ACOES.registrar("ordens-servico", new AcoesDeOrdemServicoRecurso(ordemServicoService()));
        EXCLUIVEIS.registrar("ordens-servico", new ExclusaoDeOrdemRecurso(aprovacaoService()));
        FichaTecnicaRecurso fichas = new FichaTecnicaRecurso(fichaTecnicaService(), consultaAtendimento());
        CONSULTAVEIS.registrar("fichas-tecnicas", fichas);
        ATUALIZAVEIS.registrar("fichas-tecnicas", fichas);
    }
}
