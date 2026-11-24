package com.assistencia.model.ordem;

import java.util.Set;
import static com.assistencia.model.RegrasDeStatus.*;
import static com.assistencia.model.ordem.StatusOrdem.*;

/** Condições exigidas antes de alterar ou excluir uma ordem de serviço (polimorfismo com enum). */
public enum ExigenciaDaOrdem {
    EDITAVEL {
        @Override
        void conferir(StatusOrdem atual) {
            proibir(atual, Set.of(CONCLUIDA, CANCELADA), "Ordens de serviço %s não podem ter seus dados alterados.");
        }
    },
    ABERTA_PARA_EXCLUIR {
        @Override
        void conferir(StatusOrdem atual) {
            exigirIgual(atual, ABERTA, "Ordem de serviço no status %s não pode ser excluída. "
                    + "Somente ordens ABERTAS podem ser removidas.");
        }
    };

    abstract void conferir(StatusOrdem atual);
}
