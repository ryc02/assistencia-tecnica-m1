package com.assistencia.controller;

import com.assistencia.command.ICommand;
import com.assistencia.exception.ConflictException;
import com.assistencia.exception.NotFoundException;
import com.assistencia.exception.ValidationException;
import com.assistencia.factory.CommandFactory;
import com.assistencia.infra.CsrfUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * [Front Controller]
 * Pense neste Servlet como o "Guarda de Trânsito" do nosso sistema.
 * Ele recebe TODAS as requisições que chegam no endereço /controle.
 * Em vez de termos um arquivo para salvar cliente, outro para listar, etc.,
 * este arquivo centraliza tudo. Ele olha para o parâmetro "acao", confere se 
 * é seguro continuar (valida o Token CSRF) e então repassa o trabalho para a 
 * classe correta (o Command).
 */
@WebServlet("/controle")
public class FrontControllerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // [Requisito 11: Segurança] Valida Token CSRF em todas as requisições POST
        if (!CsrfUtil.isValid(request)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN); // HTTP 403
            request.setAttribute("erroCodigo", 403);
            request.setAttribute("erroTitulo", "403 Acesso Negado (CSRF)");
            request.setAttribute("erroMensagem", "Token CSRF ausente ou inválido. A operação foi bloqueada por motivos de segurança.");
            request.getRequestDispatcher("/WEB-INF/views/erro.jsp").forward(request, response);
            return;
        }
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // Prepara Token CSRF na sessão para renderização das JSPs
        request.setAttribute("csrfToken", CsrfUtil.getToken(request.getSession(true)));

        String acao = request.getParameter("acao");
        if (acao == null || acao.trim().isEmpty()) {
            acao = "cliente.listar";
        }

        // [Requisito 11: Segurança] Verifica se método HTTP GET está sendo usado para operação de alteração
        if (CommandFactory.isPostAction(acao) && "GET".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED); // HTTP 405
            request.setAttribute("erroCodigo", 405);
            request.setAttribute("erroTitulo", "405 Método Não Permitido");
            request.setAttribute("erroMensagem", "Operações de alteração de dados não são permitidas via HTTP GET.");
            request.getRequestDispatcher("/WEB-INF/views/erro.jsp").forward(request, response);
            return;
        }

        // [Padrão Simple Factory] Obtém o comando correspondente à ação
        ICommand command = CommandFactory.createCommand(acao);

        if (command == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // HTTP 400
            request.setAttribute("erroCodigo", 400);
            request.setAttribute("erroTitulo", "400 Requisição Inválida");
            request.setAttribute("erroMensagem", "Ação não reconhecida pelo sistema: " + acao);
            request.getRequestDispatcher("/WEB-INF/views/erro.jsp").forward(request, response);
            return;
        }

        try {
            // [Padrão Command] Executa a ação encapsulada
            String result = command.execute(request, response);

            if (result != null && result.startsWith("redirect:")) {
                String redirectUrl = request.getContextPath() + result.substring("redirect:".length());
                // [Requisito 10: HTTP 303 Redirect após alteração]
                response.setStatus(HttpServletResponse.SC_SEE_OTHER);
                response.setHeader("Location", redirectUrl);
            } else if (result != null) {
                request.getRequestDispatcher(result).forward(request, response);
            }
        } catch (ValidationException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // HTTP 400
            request.setAttribute("erroCodigo", 400);
            request.setAttribute("erroTitulo", "Dados Inválidos");
            request.setAttribute("erroMensagem", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/erro.jsp").forward(request, response);
        } catch (NotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND); // HTTP 404
            request.setAttribute("erroCodigo", 404);
            request.setAttribute("erroTitulo", "Registro Não Encontrado");
            request.setAttribute("erroMensagem", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/erro.jsp").forward(request, response);
        } catch (ConflictException e) {
            response.setStatus(HttpServletResponse.SC_CONFLICT); // HTTP 409
            request.setAttribute("erroCodigo", 409);
            request.setAttribute("erroTitulo", "Conflito de Regras / Estado");
            request.setAttribute("erroMensagem", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/erro.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // HTTP 500
            request.setAttribute("erroCodigo", 500);
            request.setAttribute("erroTitulo", "Erro Interno no Servidor");
            request.setAttribute("erroMensagem", "Ocorreu uma falha ao processar sua solicitação: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/erro.jsp").forward(request, response);
        }
    }
}
