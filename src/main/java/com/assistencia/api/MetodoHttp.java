package com.assistencia.api;

import com.assistencia.dados.Campos;
import com.assistencia.exception.OperacaoNaoSuportadaException;

/** Método HTTP da requisição REST, que define a operação executada no recurso. */
final class MetodoHttp {
    private final String nome;

    MetodoHttp(String nome) {
        this.nome = nome;
    }

    boolean eh(String esperado) {
        return nome.equals(esperado);
    }

    boolean alteraDados() {
        return !"GET".equals(nome);
    }

    RespostaRest executar(NomeDoRecurso recurso, AlvoApi alvo, Campos corpo) throws Exception {
        switch (nome) {
            case "GET":
                return alvo.consultar(recurso);
            case "POST":
                return alvo.criar(recurso, corpo);
            case "PUT":
                return alvo.atualizar(recurso, corpo);
            case "DELETE":
                return alvo.excluir(recurso);
            case "PATCH":
                return alvo.executar(recurso, corpo);
            default:
                throw new OperacaoNaoSuportadaException("Método " + nome + " não suportado.");
        }
    }
}
