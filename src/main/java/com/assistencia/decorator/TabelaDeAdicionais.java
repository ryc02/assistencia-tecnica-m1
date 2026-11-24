package com.assistencia.decorator;

import com.assistencia.dados.Campos;
import java.util.*;
import java.util.stream.Stream;
import static java.util.stream.Collectors.toList;

/** Lista de adicionais disponíveis, exportada para as telas (caixas de marcar). */
public final class TabelaDeAdicionais {
    private TabelaDeAdicionais() { }

    public static List<Map<String, Object>> exportar() {
        TipoAdicional[] tipos = TipoAdicional.values();
        return Stream.of(tipos)
                .map(TabelaDeAdicionais::exportarTipo)
                .collect(toList());
    }

    private static Map<String, Object> exportarTipo(TipoAdicional tipo) {
        Campos destino = new Campos();
        tipo.exportar(destino);
        return destino.comoMapa();
    }
}
