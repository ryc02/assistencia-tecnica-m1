package com.assistencia.api;

import com.assistencia.dados.Campos;
import com.assistencia.exception.AcessoNegadoException;
import com.assistencia.infra.*;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import static com.assistencia.controller.StatusHttp.*;

/**

 * Trata uma requisição da API: login, token CSRF, logout e as operações dos recursos. Toda requisição que

 * altera dados exige o cabeçalho X-CSRF-Token.

 */
final class ProcessadorApi {
    private final HttpServletRequest requisicao;
    private final RotaApi rota;

    ProcessadorApi(HttpServletRequest requisicao, RotaApi rota) {
        this.requisicao = requisicao;
        this.rota = rota;
    }

    RespostaRest processar() throws Exception {
        Campos corpo = CorpoDaRequisicao.ler(requisicao);
        if (rota.eh("POST", "login")) {
            return LoginApi.entrar(requisicao, corpo);
        }
        if (rota.eh("GET", "csrf")) {
            String token = LoginApi.token(requisicao);
            return new RespostaRest(OK, Map.of("csrfToken", token));
        }
        exigirTokenCsrf();
        if (rota.eh("POST", "logout")) {
            SessaoUsuario.encerrar(requisicao);
            return new RespostaRest(SEM_CONTEUDO, null);
        }
        return rota.executar(corpo);
    }

    private void exigirTokenCsrf() {
        boolean tokenValido = TokenCsrf.validoNoCabecalho(requisicao);
        if (rota.alteraDados() && !tokenValido) {
            throw new AcessoNegadoException("Token CSRF ausente ou inválido no cabeçalho X-CSRF-Token.");
        }
    }
}
