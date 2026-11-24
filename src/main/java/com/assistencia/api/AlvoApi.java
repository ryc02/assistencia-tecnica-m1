package com.assistencia.api;

import com.assistencia.api.recurso.*;
import com.assistencia.dados.Campos;
import static com.assistencia.controller.StatusHttp.*;
import static com.assistencia.factory.RecursoFactory.*;

/** Alvo da URL (id e ação): alteração, exclusão e ações; consulta e criação vêm de ConsultaDoAlvo. */
final class AlvoApi extends ConsultaDoAlvo {
    AlvoApi(Long id, String acao) {
        super(new IdDaRota(id), new AcaoDaRota(acao));
    }

    boolean vazio() {
        return id.vazio();
    }

    RespostaRest atualizar(NomeDoRecurso recurso, Campos corpo) throws Exception {
        exigirItem(false);
        RecursoAtualizavel atualizavel = recurso.suportado(ATUALIZAVEIS);
        id.atualizar(atualizavel, corpo);
        RecursoConsultavel consulta = recurso.suportado(CONSULTAVEIS);
        return new RespostaRest(OK, id.consultar(consulta));
    }

    RespostaRest excluir(NomeDoRecurso recurso) throws Exception {
        exigirItem(false);
        RecursoExcluivel excluivel = recurso.suportado(EXCLUIVEIS);
        id.excluir(excluivel);
        return new RespostaRest(SEM_CONTEUDO, null);
    }

    RespostaRest executar(NomeDoRecurso recurso, Campos corpo) throws Exception {
        exigirItem(true);
        RecursoComAcoes comAcoes = recurso.suportado(COM_ACOES);
        return new RespostaRest(OK, acao.executar(comAcoes, id, corpo));
    }
}
