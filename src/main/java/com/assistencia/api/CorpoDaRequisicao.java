package com.assistencia.api;

import com.assistencia.api.json.LeitorJson;
import com.assistencia.dados.Campos;
import java.io.*;
import javax.servlet.http.HttpServletRequest;
import static java.nio.charset.StandardCharsets.UTF_8;

/** Lê o corpo JSON da requisição REST e o transforma em Campos. */
final class CorpoDaRequisicao {
    private CorpoDaRequisicao() { }

    static Campos ler(HttpServletRequest requisicao) throws IOException {
        InputStream entrada = requisicao.getInputStream();
        String json = new String(entrada.readAllBytes(), UTF_8);
        return new Campos(LeitorJson.lerObjeto(json));
    }
}
