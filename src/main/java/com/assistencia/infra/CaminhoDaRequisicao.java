package com.assistencia.infra;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

/** Caminho pedido, sem o contexto da aplicação (ex.: /api/clientes/1 ou /css/styles.css). */
final class CaminhoDaRequisicao {
    private static final String API = "/api/";
    private static final int TAMANHO_API = API.length();
    private static final List<String> ESTATICOS = List.of("/css/", "/js/", "/img/");

    private final String caminho;

    private CaminhoDaRequisicao(String caminho) {
        this.caminho = caminho;
    }

    static CaminhoDaRequisicao de(HttpServletRequest requisicao) {
        String uri = requisicao.getRequestURI();
        String contexto = requisicao.getContextPath();
        int inicio = contexto.length();
        return new CaminhoDaRequisicao(uri.substring(inicio));
    }

    /** startsWith (e não contains): "/qualquer/css/" não pode pular a autenticação. */
    boolean estatico() {
        return ESTATICOS.stream()
                .anyMatch(caminho::startsWith);
    }

    boolean daApi() {
        return caminho.startsWith(API);
    }

    String recursoDaApi() {
        String semPrefixo = caminho.substring(TAMANHO_API);
        return semPrefixo.split("/")[0];
    }
}
