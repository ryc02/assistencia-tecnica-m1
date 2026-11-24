package com.assistencia.decorator;

import java.util.*;
import java.util.function.UnaryOperator;
import static com.assistencia.decorator.TipoAdicional.*;

/** [Factory - Aula 06] Cria o decorator adequado para cada tipo de adicional. */
public final class AdicionalFactory {
    private static final Map<TipoAdicional, UnaryOperator<ServicoOrcamento>> DECORADORES =
            new EnumMap<>(TipoAdicional.class);

    static {
        DECORADORES.put(BACKUP_DADOS, BackupDados::new);
        DECORADORES.put(LIMPEZA_INTERNA, LimpezaInterna::new);
        DECORADORES.put(PELICULA_PROTECAO, PeliculaProtecao::new);
        DECORADORES.put(GARANTIA_ESTENDIDA, GarantiaEstendida::new);
        DECORADORES.put(ATENDIMENTO_URGENTE, AtendimentoUrgente::new);
    }

    private AdicionalFactory() { }

    public static ServicoOrcamento decorar(ServicoOrcamento servico, TipoAdicional tipo) {
        UnaryOperator<ServicoOrcamento> decorador = DECORADORES.get(tipo);
        return decorador.apply(servico);
    }
}
