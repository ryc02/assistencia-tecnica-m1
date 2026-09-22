package com.assistencia;

import com.assistencia.controller.FrontControllerServlet;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;

import java.io.File;

/**
 * [Requisito: Execução Local]
 * Classe principal para inicialização do Tomcat embarcado na porta 8080.
 * Permite rodar e testar a aplicação instantaneamente via `mvn exec:java` ou execução direta.
 */
public class AppLauncher {

    public static void main(String[] args) throws Exception {
        int port = 8080;
        String portEnv = System.getenv("PORT");
        if (portEnv != null && !portEnv.isEmpty()) {
            port = Integer.parseInt(portEnv);
        }

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(port);
        tomcat.getConnector();

        String webappDir = new File("src/main/webapp").getAbsolutePath();
        if (!new File(webappDir).exists()) {
            webappDir = new File("webapp").getAbsolutePath();
        }

        Context ctx = tomcat.addWebapp("", webappDir);

        // Registra o Front Controller Servlet
        Tomcat.addServlet(ctx, "FrontControllerServlet", new FrontControllerServlet());
        ctx.addServletMappingDecoded("/controle", "FrontControllerServlet");

        System.out.println("======================================================================");
        System.out.println("🚀 Servidor Assistência Técnica M1 rodando em: http://localhost:" + port + "/controle");
        System.out.println("======================================================================");

        tomcat.start();
        tomcat.getServer().await();
    }
}
