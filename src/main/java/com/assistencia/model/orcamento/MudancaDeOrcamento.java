package com.assistencia.model.orcamento;

import java.util.Set;
import static com.assistencia.model.RegrasDeStatus.*;
import static com.assistencia.model.orcamento.StatusOrcamento.*;

/** Mudanças de estado do orçamento: cada constante sabe validar e aplicar a sua (polimorfismo, sem if/else). */
public enum MudancaDeOrcamento {
    APROVAR {
        @Override
        StatusOrcamento aplicar(StatusOrcamento atual) {
            proibir(atual, Set.of(RECUSADO), "Orçamento foi %s e não pode ser aprovado.");
            return APROVADO;
        }

        @Override
        void conferir(Relato relato) {
            relato.exigirDiagnostico();
        }
    },
    RECUSAR {
        @Override
        StatusOrcamento aplicar(StatusOrcamento atual) {
            exigirIgual(atual, PENDENTE, "Apenas orçamentos PENDENTES podem ser recusados (status atual: %s).");
            return RECUSADO;
        }
    },
    VOLTAR_PARA_PENDENTE {
        @Override
        StatusOrcamento aplicar(StatusOrcamento atual) {
            return PENDENTE;
        }
    };

    abstract StatusOrcamento aplicar(StatusOrcamento atual);

    /** Conferência extra feita antes da mudança (só a aprovação exige diagnóstico). */
    void conferir(Relato relato) {
    }
}
