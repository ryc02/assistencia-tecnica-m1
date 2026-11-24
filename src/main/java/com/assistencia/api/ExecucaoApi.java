package com.assistencia.api;

import com.assistencia.controller.StatusHttp;
import java.io.IOException;
import java.util.logging.Logger;
import javax.servlet.http.*;
import static com.assistencia.controller.StatusHttp.ERRO_INTERNO;
import static java.util.logging.Level.SEVERE;

/** Executa a requisição da API e converte exceções em respostas JSON com o status HTTP correto. */
public final class ExecucaoApi {
    private static final Logger REGISTRO = Logger.getLogger("assistencia.api");

    private ExecucaoApi() { }

    public static void executar(HttpServletRequest requisicao, HttpServletResponse resposta) throws IOException {
        try {
            String endereco = requisicao.getPathInfo();
            CaminhoApi caminho = CaminhoApi.de(endereco);
            RotaApi rota = new RotaApi(requisicao.getMethod(), caminho);
            RespostaRest resultado = new ProcessadorApi(requisicao, rota).processar();
            resultado.enviar(requisicao, resposta);
        } catch (Exception excecao) {
            RespostaRest erro = respostaDeErro(excecao);
            erro.enviar(requisicao, resposta);
        }
    }

    private static RespostaRest respostaDeErro(Exception excecao) {
        StatusHttp status = StatusHttp.de(excecao);
        if (status == ERRO_INTERNO) {
            REGISTRO.log(SEVERE, "Erro na API REST", excecao);
            return RespostaRest.erro(status, "Erro interno ao processar a requisição.");
        }
        String mensagem = excecao.getMessage();
        return RespostaRest.erro(status, mensagem);
    }
}
