package com.assistencia;

import com.assistencia.controller.*;
import com.assistencia.infra.AuthFilter;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.*;

/** Registra no Tomcat embarcado os dois Front Controllers (telas e API) e o filtro de autenticação. */
final class RegistroWeb {
    private static final String FILTRO = "AuthFilter";

    private RegistroWeb() { }

    static void registrar(Context contexto) {
        Tomcat.addServlet(contexto, "FrontControllerServlet", new FrontControllerServlet());
        contexto.addServletMappingDecoded("/controle", "FrontControllerServlet");
        Tomcat.addServlet(contexto, "ApiServlet", new ApiServlet());
        contexto.addServletMappingDecoded("/api/*", "ApiServlet");
        registrarFiltro(contexto);
    }

    private static void registrarFiltro(Context contexto) {
        Class<AuthFilter> classe = AuthFilter.class;
        String nomeDaClasse = classe.getName();
        FilterDef definicao = new FilterDef();
        definicao.setFilterName(FILTRO);
        definicao.setFilterClass(nomeDaClasse);
        contexto.addFilterDef(definicao);
        FilterMap mapeamento = new FilterMap();
        mapeamento.setFilterName(FILTRO);
        mapeamento.addURLPattern("/*");
        contexto.addFilterMap(mapeamento);
    }
}
