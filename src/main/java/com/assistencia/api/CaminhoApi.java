package com.assistencia.api;

import com.assistencia.dados.Campos;
import static com.assistencia.api.PartesDoCaminho.*;

/** Caminho da URL da API: nome do recurso e o alvo (id e ação). */
final class CaminhoApi {
    private final NomeDoRecurso recurso;
    private final AlvoApi alvo;

    private CaminhoApi(NomeDoRecurso recurso, AlvoApi alvo) {
        this.recurso = recurso;
        this.alvo = alvo;
    }

    static CaminhoApi de(String caminho) {
        String[] partes = separar(caminho);
        AlvoApi alvo = new AlvoApi(id(partes), parte(partes, 2));
        return new CaminhoApi(new NomeDoRecurso(partes[0]), alvo);
    }

    boolean eh(String nome) {
        boolean mesmoRecurso = recurso.eh(nome);
        return mesmoRecurso && alvo.vazio();
    }

    RespostaRest executar(MetodoHttp metodo, Campos corpo) throws Exception {
        recurso.exigirExistente();
        return metodo.executar(recurso, alvo, corpo);
    }
}
