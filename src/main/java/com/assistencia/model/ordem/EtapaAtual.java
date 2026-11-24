package com.assistencia.model.ordem;

import com.assistencia.dados.*;
import com.assistencia.model.valor.DataHora;
import static com.assistencia.model.ordem.StatusOrdem.ABERTA;

/** Estado atual da ordem de serviço e a data em que foi concluída. */
public final class EtapaAtual implements Exportavel {
    private StatusOrdem status;
    private DataHora dataConclusao;

    private EtapaAtual(StatusOrdem status, DataHora dataConclusao) {
        this.status = status;
        this.dataConclusao = dataConclusao;
    }

    public static EtapaAtual de(Campos dados) {
        StatusOrdem status = dados.opcaoOuPadrao("status", ABERTA);
        return new EtapaAtual(status, DataHora.de(dados, "dataConclusao"));
    }

    void mudar(MudancaDeOrdem mudanca) {
        status = mudanca.aplicar(status);
        dataConclusao = mudanca.conclusao(dataConclusao);
    }

    void exigir(ExigenciaDaOrdem exigencia) {
        exigencia.conferir(status);
    }

    @Override
    public void exportar(Campos destino) {
        destino.com("status", status);
        dataConclusao.exportar(destino);
    }
}
