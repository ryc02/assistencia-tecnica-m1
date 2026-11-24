package com.assistencia.controller;

import com.assistencia.factory.AcoesDeAlteracao;
import com.assistencia.infra.TokenCsrf;
import java.io.IOException;
import javax.servlet.http.*;

/** Prepara a requisição de tela: UTF-8, sem cache (o "voltar" recarrega o token), token CSRF disponível aos formulários e a ação pedida. */
final class PreparacaoDaTela {
    private static final String GET = "GET";

    private PreparacaoDaTela() { }

    static String preparar(HttpServletRequest requisicao, HttpServletResponse resposta) throws IOException {
        requisicao.setCharacterEncoding("UTF-8");
        resposta.setCharacterEncoding("UTF-8");
        resposta.setHeader("Cache-Control", "no-store");
        HttpSession sessao = requisicao.getSession(true);
        String token = TokenCsrf.obter(sessao);
        requisicao.setAttribute("csrfToken", token);
        return AcaoDaRequisicao.de(requisicao);
    }

    /** Alterações de dados só são aceitas via POST (GET responde 405). */
    static boolean alteracaoViaGet(HttpServletRequest requisicao, String acao) {
        String metodo = requisicao.getMethod();
        boolean viaGet = GET.equals(metodo);
        return viaGet && AcoesDeAlteracao.exigePost(acao);
    }
}
