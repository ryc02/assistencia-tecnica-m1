package com.assistencia.model.ordem;

import com.assistencia.dados.*;
import com.assistencia.model.valor.DataHora;

/** Etapa atual (estado e conclusão) e data de abertura da ordem. */
public final class Andamento implements Exportavel {
    private final EtapaAtual etapa;
    private final DataHora dataAbertura;

    public Andamento(EtapaAtual etapa, DataHora dataAbertura) {
        this.etapa = etapa;
        this.dataAbertura = dataAbertura;
    }

    public static Andamento de(Campos dados) {
        EtapaAtual etapa = EtapaAtual.de(dados);
        DataHora dataAbertura = DataHora.agoraSeVazio(dados, "dataAbertura");
        return new Andamento(etapa, dataAbertura);
    }

    @Override
    public void exportar(Campos destino) {
        etapa.exportar(destino);
        dataAbertura.exportar(destino);
    }

    void validarPrevisao(Planejamento planejamento) {
        planejamento.exigirPrevisaoApos(dataAbertura);
    }

    void mudar(MudancaDeOrdem mudanca) {
        etapa.mudar(mudanca);
    }

    void exigir(ExigenciaDaOrdem exigencia) {
        etapa.exigir(exigencia);
    }
}
