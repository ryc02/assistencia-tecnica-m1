package com.assistencia.model.ficha;

import com.assistencia.dados.*;
import static com.assistencia.model.ficha.EstadoConservacao.NAO_AVALIADO;

/** Estado de conservação e avarias do equipamento recebido. */
public final class Condicao implements Exportavel {
    private final EstadoConservacao estado;
    private final Avarias avarias;

    public Condicao(EstadoConservacao estado, Avarias avarias) {
        this.estado = estado;
        this.avarias = avarias;
    }

    public static Condicao de(Campos dados) {
        EstadoConservacao estado = dados.opcaoOuPadrao("estadoConservacao", NAO_AVALIADO);
        Avarias avarias = Avarias.de(dados);
        return new Condicao(estado, avarias);
    }

    @Override
    public void exportar(Campos destino) {
        destino.com("estadoConservacao", estado);
        avarias.exportar(destino);
    }
}
