package com.assistencia.controller;

import com.assistencia.api.ExecucaoApi;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

/** [REST API - M2] Front Controller da API REST (/api/*), sem framework. */
@WebServlet("/api/*")
public class ApiServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest requisicao, HttpServletResponse resposta) throws IOException {
        requisicao.setCharacterEncoding("UTF-8");
        ExecucaoApi.executar(requisicao, resposta);
    }
}
