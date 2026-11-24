package com.assistencia.api;

import com.assistencia.api.recurso.*;
import com.assistencia.dados.Campos;
import com.assistencia.exception.OperacaoNaoSuportadaException;
import static com.assistencia.controller.StatusHttp.*;
import static com.assistencia.factory.RecursoFactory.*;

/** Alvo da URL (id e ação): consulta e criação; a subclasse AlvoApi trata alteração, exclusão e ações. */
abstract class ConsultaDoAlvo {
    protected final IdDaRota id;
    protected final AcaoDaRota acao;

    ConsultaDoAlvo(IdDaRota id, AcaoDaRota acao) {
        this.id = id;
        this.acao = acao;
    }

    RespostaRest consultar(NomeDoRecurso recurso) throws Exception {
        exigir(acao.vazia());
        RecursoConsultavel consultavel = recurso.suportado(CONSULTAVEIS);
        return new RespostaRest(OK, id.consultar(consultavel));
    }

    RespostaRest criar(NomeDoRecurso recurso, Campos corpo) throws Exception {
        exigir(id.vazio());
        RecursoCriavel criavel = recurso.suportado(CRIAVEIS);
        return new RespostaRest(CRIADO, criavel.criar(corpo));
    }

    /** Exige um id na URL, com ou sem ação conforme a operação. */
    protected void exigirItem(boolean comAcao) {
        boolean semId = id.vazio();
        boolean semAcao = acao.vazia();
        exigir(!semId && semAcao != comAcao);
    }

    protected void exigir(boolean suportada) {
        if (!suportada) {
            throw new OperacaoNaoSuportadaException("Operação não suportada para este recurso.");
        }
    }
}
