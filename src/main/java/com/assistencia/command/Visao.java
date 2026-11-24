package com.assistencia.command;

import com.assistencia.dados.*;
import java.util.Map;

/** Dados de uma entidade prontos para a JSP (mapa somente leitura). */
public final class Visao {
    private Visao() { }

    public static Map<String, Object> de(Entidade entidade) {
        Campos dados = entidade.exportar();
        return dados.comoMapa();
    }
}
