package com.assistencia.service;

import com.assistencia.dados.*;

/** Obtém o id de uma entidade a partir dos dados que ela exporta. */
public final class IdDaEntidade {
    private IdDaEntidade() { }

    public static Long de(Entidade entidade) {
        Campos dados = entidade.exportar();
        return dados.inteiroLongo("id");
    }
}
