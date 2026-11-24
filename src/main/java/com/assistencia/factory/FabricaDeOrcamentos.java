package com.assistencia.factory;

import com.assistencia.service.*;
import static com.assistencia.factory.Persistencia.*;

/** [Factory] Monta os Services de orçamentos, injetando DAOs e transação (DIP). */
public final class FabricaDeOrcamentos {
    private static final RepositorioOrcamento REPOSITORIO = new RepositorioOrcamento(ORCAMENTOS, EQUIPAMENTOS);
    private static final OrcamentoService SERVICO = new OrcamentoService(REPOSITORIO, TRANSACAO);
    private static final GestaoOrcamentoService GESTAO = new GestaoOrcamentoService(REPOSITORIO, TRANSACAO);
    private static final ConsultaOrcamento CONSULTA = new ConsultaOrcamento(REPOSITORIO, TRANSACAO);
    private static final TelasDeOrcamento TELAS = new TelasDeOrcamento(REPOSITORIO, TRANSACAO);

    private FabricaDeOrcamentos() { }

    public static OrcamentoService orcamentoService() {
        return SERVICO;
    }

    public static GestaoOrcamentoService gestaoOrcamento() {
        return GESTAO;
    }

    public static ConsultaOrcamento consultaOrcamento() {
        return CONSULTA;
    }

    public static TelasDeOrcamento telasDeOrcamento() {
        return TELAS;
    }
}
