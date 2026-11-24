package com.assistencia.model.ordem;

import com.assistencia.dados.*;
import com.assistencia.model.valor.DataHora;

/** Previsão de conclusão e termos do serviço. */
public final class Compromisso implements Exportavel {
    private final DataHora previsaoConclusao;
    private final Termos termos;

    public Compromisso(DataHora previsaoConclusao, Termos termos) {
        this.previsaoConclusao = previsaoConclusao;
        this.termos = termos;
    }

    public static Compromisso de(Campos dados) {
        DataHora previsaoConclusao = DataHora.de(dados, "previsaoConclusao");
        Termos termos = Termos.de(dados);
        return new Compromisso(previsaoConclusao, termos);
    }

    @Override
    public void exportar(Campos destino) {
        previsaoConclusao.exportar(destino);
        termos.exportar(destino);
    }

    void exigirApos(DataHora abertura) {
        previsaoConclusao.exigirPosteriorA(abertura,
                "Previsão de conclusão não pode ser anterior à data de abertura.");
    }
}
