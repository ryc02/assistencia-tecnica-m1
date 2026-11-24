package com.assistencia.model.orcamento;

import java.util.Set;
import static com.assistencia.model.RegrasDeStatus.*;
import static com.assistencia.model.orcamento.StatusOrcamento.*;

/** Condições exigidas antes de alterar ou excluir um orçamento (polimorfismo com enum). */
public enum ExigenciaDoOrcamento {
    PENDENTE_PARA_ALTERAR {
        @Override
        void conferir(StatusOrcamento atual) {
            exigirIgual(atual, PENDENTE, "Não é permitido alterar um orçamento com status %s");
        }
    },
    EXCLUIVEL {
        @Override
        void conferir(StatusOrcamento atual) {
            proibir(atual, Set.of(APROVADO), "Não é possível excluir diretamente um orçamento %s.");
        }
    };

    abstract void conferir(StatusOrcamento atual);
}
