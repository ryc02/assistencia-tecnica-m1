package com.assistencia.api;

import com.assistencia.api.json.EscritorJson;
import com.assistencia.controller.StatusHttp;
import java.io.*;
import java.util.Map;
import javax.servlet.http.*;
import static com.assistencia.controller.StatusHttp.CRIADO;

/** Resultado de uma operação REST (status HTTP + corpo), que sabe se enviar como JSON. */
public final class RespostaRest {
    private final StatusHttp status;
    private final Object corpo;

    public RespostaRest(StatusHttp status, Object corpo) {
        this.status = status;
        this.corpo = corpo;
    }

    public static RespostaRest erro(StatusHttp status, String mensagem) {
        Integer codigo = status.comoNumero();
        return new RespostaRest(status, Map.of("status", codigo, "erro", mensagem));
    }

    public void enviar(HttpServletRequest requisicao, HttpServletResponse resposta) throws IOException {
        status.aplicarEm(resposta);
        if (corpo == null) {
            return;
        }
        if (status == CRIADO) {
            informarLocalizacao(requisicao, resposta);
        }
        resposta.setContentType("application/json;charset=UTF-8");
        PrintWriter saida = resposta.getWriter();
        String json = EscritorJson.escrever(corpo);
        saida.write(json);
    }

    private void informarLocalizacao(HttpServletRequest requisicao, HttpServletResponse resposta) {
        Object id = ((Map<?, ?>) corpo).get("id");
        String endereco = requisicao.getRequestURI();
        resposta.setHeader("Location", endereco + "/" + id);
    }
}
