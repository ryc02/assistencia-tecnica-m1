package com.assistencia.model.orcamento;

import com.assistencia.dados.*;
import com.assistencia.model.valor.DataHora;
import static com.assistencia.model.orcamento.StatusOrcamento.*;

/** Estado do orçamento e data de criação. */
public final class Situacao implements Exportavel {
    private StatusOrcamento status;
    private final DataHora dataCriacao;

    private Situacao(StatusOrcamento status, DataHora dataCriacao) {
        this.status = status;
        this.dataCriacao = dataCriacao;
    }

    public static Situacao de(Campos dados) {
        StatusOrcamento status = dados.opcaoOuPadrao("status", PENDENTE);
        return new Situacao(status, DataHora.agoraSeVazio(dados, "dataCriacao"));
    }

    void mudar(MudancaDeOrcamento mudanca) {
        status = mudanca.aplicar(status);
    }

    void exigir(ExigenciaDoOrcamento exigencia) {
        exigencia.conferir(status);
    }

    boolean aprovado() {
        return status == APROVADO;
    }

    @Override
    public void exportar(Campos destino) {
        destino.com("status", status);
        dataCriacao.exportar(destino);
    }
}
