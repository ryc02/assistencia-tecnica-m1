package com.assistencia.model.ordem;

import com.assistencia.model.valor.DataHora;
import java.util.Set;
import static com.assistencia.model.RegrasDeStatus.*;
import static com.assistencia.model.ordem.StatusOrdem.*;

/** Mudanças de estado da ordem de serviço: cada constante valida e aplica a sua (polimorfismo, sem if/else). */
public enum MudancaDeOrdem {
    INICIAR {
        @Override
        StatusOrdem aplicar(StatusOrdem atual) {
            exigirIgual(atual, ABERTA, "Transição inválida: a ordem está %s e só pode ser iniciada se ABERTA.");
            return EM_ANDAMENTO;
        }
    },
    CONCLUIR {
        @Override
        StatusOrdem aplicar(StatusOrdem atual) {
            exigirIgual(atual, EM_ANDAMENTO,
                    "Transição inválida: a ordem está %s e só pode ser concluída se EM_ANDAMENTO.");
            return CONCLUIDA;
        }

        @Override
        DataHora conclusao(DataHora atual) {
            return DataHora.agora("dataConclusao");
        }
    },
    CANCELAR {
        @Override
        StatusOrdem aplicar(StatusOrdem atual) {
            proibir(atual, Set.of(CONCLUIDA, CANCELADA), "Ordem de serviço já está no estado final %s");
            return CANCELADA;
        }
    };

    abstract StatusOrdem aplicar(StatusOrdem atual);

    /** Data de conclusão após a mudança (só a conclusão registra o momento atual). */
    DataHora conclusao(DataHora atual) {
        return atual;
    }
}
