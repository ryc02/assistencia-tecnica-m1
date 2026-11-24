package com.assistencia.model.ficha;

import com.assistencia.dados.*;
import com.assistencia.model.valor.*;

/** Acessórios entregues e observações registradas no recebimento. */
public final class Entrega implements Exportavel {
    private final Texto acessoriosEntregues;
    private final Anotacao registro;

    public Entrega(Texto acessoriosEntregues, Anotacao registro) {
        this.acessoriosEntregues = acessoriosEntregues;
        this.registro = registro;
    }

    public static Entrega de(Campos dados) {
        Texto acessoriosEntregues = Texto.comPadrao(dados, "acessoriosEntregues", 1000, "Acessórios entregues",
                "Nenhum");
        Anotacao registro = registroDe(dados);
        return new Entrega(acessoriosEntregues, registro);
    }

    @Override
    public void exportar(Campos destino) {
        acessoriosEntregues.exportar(destino);
        registro.exportar(destino);
    }

    private static Anotacao registroDe(Campos dados) {
        Texto observacoes = Texto.opcional(dados, "observacoesRecebimento", 2000, "Observações de recebimento");
        return new Anotacao(observacoes, DataHora.agoraSeVazio(dados, "dataRegistro"));
    }
}
