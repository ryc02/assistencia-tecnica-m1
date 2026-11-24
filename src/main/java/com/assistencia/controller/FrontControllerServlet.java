package com.assistencia.controller;

import com.assistencia.command.ICommand;
import com.assistencia.factory.CommandFactory;
import com.assistencia.infra.TokenCsrf;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import static com.assistencia.controller.StatusHttp.*;

/** [MVC - Controller / Front Controller] Ponto único de entrada das telas (/controle). */
@WebServlet("/controle")
public class FrontControllerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest requisicao, HttpServletResponse resposta)
            throws ServletException, IOException {
        processar(requisicao, resposta);
    }

    @Override
    protected void doPost(HttpServletRequest requisicao, HttpServletResponse resposta)
            throws ServletException, IOException {
        if (!TokenCsrf.validoNoFormulario(requisicao)) {
            RecusaDeFormulario.responder(requisicao, resposta);
            return;
        }
        processar(requisicao, resposta);
    }

    private void processar(HttpServletRequest requisicao, HttpServletResponse resposta)
            throws ServletException, IOException {
        String acao = PreparacaoDaTela.preparar(requisicao, resposta);
        if (PreparacaoDaTela.alteracaoViaGet(requisicao, acao)) {
            PaginaDeErro.exibir(requisicao, resposta, METODO_NAO_PERMITIDO, "405 Método Não Permitido",
                    "Operações de alteração de dados não são permitidas via HTTP GET.");
            return;
        }
        ICommand comando = CommandFactory.criar(acao);
        ExecucaoDeComando.executar(comando, requisicao, resposta);
    }
}
