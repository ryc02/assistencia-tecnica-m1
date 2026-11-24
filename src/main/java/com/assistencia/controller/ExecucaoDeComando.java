package com.assistencia.controller;

import com.assistencia.command.ICommand;
import java.io.IOException;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import static com.assistencia.controller.StatusHttp.REQUISICAO_INVALIDA;
import static java.util.logging.Level.SEVERE;

/** Executa o Command e transforma as exceções de negócio na página de erro com o status correto. */
final class ExecucaoDeComando {
    private static final Logger REGISTRO = Logger.getLogger("assistencia.controle");

    private ExecucaoDeComando() { }

    static void executar(ICommand comando, HttpServletRequest requisicao, HttpServletResponse resposta)
            throws ServletException, IOException {
        if (comando == null) {
            PaginaDeErro.exibir(requisicao, resposta, REQUISICAO_INVALIDA, "400 Requisição Inválida",
                    "Ação não reconhecida pelo sistema.");
            return;
        }
        try {
            String destino = comando.execute(requisicao, resposta);
            Navegacao.seguir(destino, requisicao, resposta);
        } catch (Exception excecao) {
            tratar(excecao, requisicao, resposta);
        }
    }

    private static void tratar(Exception excecao, HttpServletRequest requisicao, HttpServletResponse resposta)
            throws ServletException, IOException {
        StatusHttp status = StatusHttp.de(excecao);
        if (status == StatusHttp.ERRO_INTERNO) {
            REGISTRO.log(SEVERE, "Erro ao executar ação", excecao);
            PaginaDeErro.exibir(requisicao, resposta, status, "Erro Interno no Servidor",
                    "Ocorreu uma falha ao processar sua solicitação. Tente novamente ou contate o suporte.");
            return;
        }
        PaginaDeErro.exibir(requisicao, resposta, status, "Não foi possível concluir a operação",
                excecao.getMessage());
    }
}
