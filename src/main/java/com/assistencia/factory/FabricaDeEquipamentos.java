package com.assistencia.factory;

import com.assistencia.service.*;
import static com.assistencia.factory.Persistencia.*;

/** [Factory] Monta os Services de equipamentos, injetando DAOs e transação (DIP). */
public final class FabricaDeEquipamentos {
    private static final RepositorioEquipamento REPOSITORIO = new RepositorioEquipamento(EQUIPAMENTOS, CLIENTES);
    private static final EquipamentoService SERVICO = new EquipamentoService(REPOSITORIO, TRANSACAO);
    private static final ConsultaEquipamento CONSULTA = new ConsultaEquipamento(REPOSITORIO, TRANSACAO);
    private static final TelasDeEquipamento TELAS = new TelasDeEquipamento(REPOSITORIO, TRANSACAO);

    private FabricaDeEquipamentos() { }

    public static EquipamentoService equipamentoService() {
        return SERVICO;
    }

    public static ConsultaEquipamento consultaEquipamento() {
        return CONSULTA;
    }

    public static TelasDeEquipamento telasDeEquipamento() {
        return TELAS;
    }
}
