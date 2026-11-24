package com.assistencia;

import java.util.logging.Logger;
import org.apache.catalina.*;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;

/** Inicia o Tomcat embarcado (porta 8080 ou a variável PORT) com as telas em /controle e a API em /api. */
public final class IniciadorAplicacao {
    private static final Logger REGISTRO = Logger.getLogger("assistencia");

    private IniciadorAplicacao() { }

    public static void main(String[] argumentos) throws Exception {
        Tomcat servidor = new Tomcat();
        Integer porta = ConfiguracaoDoServidor.porta();
        servidor.setPort(porta);
        Connector conector = servidor.getConnector();
        conector.setProperty("address", "0.0.0.0");
        String pastaWeb = ConfiguracaoDoServidor.pastaWeb();
        Context contexto = servidor.addWebapp("", pastaWeb);
        RegistroWeb.registrar(contexto);
        servidor.start();
        REGISTRO.info("Servidor Assistência Técnica rodando em: http://localhost:" + porta + "/controle");
        Server instancia = servidor.getServer();
        instancia.await();
    }
}
