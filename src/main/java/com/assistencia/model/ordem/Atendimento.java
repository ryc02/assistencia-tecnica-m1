package com.assistencia.model.ordem;

import com.assistencia.dados.*;
import static com.assistencia.model.ordem.ExigenciaDaOrdem.EDITAVEL;

/** Execução (orçamento, estado e datas) e planejamento (responsável, prioridade, previsão etc.) da ordem. */
public final class Atendimento implements Exportavel {
    private final Execucao execucao;
    private Planejamento planejamento;

    private Atendimento(Execucao execucao, Planejamento planejamento) {
        this.execucao = execucao;
        this.planejamento = planejamento;
    }

    public static Atendimento de(Campos dados) {
        Execucao execucao = Execucao.de(dados);
        Planejamento planejamento = Planejamento.de(dados);
        execucao.validarPrevisao(planejamento);
        return new Atendimento(execucao, planejamento);
    }

    void reprogramar(Campos dados) {
        execucao.exigir(EDITAVEL);
        Planejamento novo = Planejamento.de(dados);
        execucao.validarPrevisao(novo);
        planejamento = novo;
    }

    @Override
    public void exportar(Campos destino) {
        execucao.exportar(destino);
        planejamento.exportar(destino);
    }

    void mudar(MudancaDeOrdem mudanca) {
        execucao.mudar(mudanca);
    }

    void exigir(ExigenciaDaOrdem exigencia) {
        execucao.exigir(exigencia);
    }
}
