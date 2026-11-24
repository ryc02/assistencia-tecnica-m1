package com.assistencia.controller;

import com.assistencia.exception.*;
import javax.servlet.http.*;

/** [Calistenia - primitivos encapsulados] Códigos de status HTTP usados pela aplicação. */
public enum StatusHttp {
    OK(200), CRIADO(201), SEM_CONTEUDO(204), SEE_OTHER(303), REQUISICAO_INVALIDA(400), NAO_AUTENTICADO(401),
    PROIBIDO(403), NAO_ENCONTRADO(404), METODO_NAO_PERMITIDO(405), CONFLITO(409), ERRO_INTERNO(500);

    private final int codigo;

    StatusHttp(int codigo) {
        this.codigo = codigo;
    }

    /** Status correspondente a cada exceção de negócio; qualquer outra é erro interno. */
    public static StatusHttp de(Exception excecao) {
        if (excecao instanceof ValidationException) {
            return REQUISICAO_INVALIDA;
        }
        if (excecao instanceof NotFoundException) {
            return NAO_ENCONTRADO;
        }
        if (excecao instanceof ConflictException) {
            return CONFLITO;
        }
        if (excecao instanceof AcessoNegadoException) {
            return PROIBIDO;
        }
        if (excecao instanceof OperacaoNaoSuportadaException) {
            return METODO_NAO_PERMITIDO;
        }
        return ERRO_INTERNO;
    }

    public void aplicarEm(HttpServletResponse resposta) {
        resposta.setStatus(codigo);
    }

    public void registrarEm(HttpServletRequest requisicao) {
        requisicao.setAttribute("erroCodigo", codigo);
    }

    public Integer comoNumero() {
        return codigo;
    }
}
