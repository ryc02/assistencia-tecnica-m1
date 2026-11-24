package com.assistencia;

import java.io.File;

/** Porta HTTP (variável PORT, padrão 8080) e pasta com as JSPs e arquivos estáticos. */
final class ConfiguracaoDoServidor {
    private static final Integer PORTA_PADRAO = 8080;

    private ConfiguracaoDoServidor() { }

    static Integer porta() {
        String informada = System.getenv("PORT");
        if (informada == null || informada.isBlank()) {
            return PORTA_PADRAO;
        }
        return Integer.valueOf(informada);
    }

    static String pastaWeb() {
        File pasta = new File("src/main/webapp");
        if (pasta.exists()) {
            return pasta.getAbsolutePath();
        }
        File alternativa = new File("webapp");
        return alternativa.getAbsolutePath();
    }
}
