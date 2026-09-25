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
 * O coração da nossa arquitetura MVC.
 * Toda requisição web passa por aqui primeiro. Ele valida a segurança (como o CSRF)
 * e depois usa o CommandFactory pra descobrir quem realmente deve processar o pedido.
 */
@WebServlet("/controle")
public class FrontControllerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Proteção contra ataques CSRF: não aceitamos POST sem um token válido gerado pelo servidor.
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

        // Bloqueia tentativas de usar GET para operações perigosas (como inserir ou deletar).
        if (CommandFactory.isPostAction(acao) && "GET".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED); // HTTP 405
            request.setAttribute("erroCodigo", 405);
            request.setAttribute("erroTitulo", "405 Método Não Permitido");
            request.setAttribute("erroMensagem", "Operações de alteração de dados não são permitidas via HTTP GET.");
            request.getRequestDispatcher("/WEB-INF/views/erro.jsp").forward(request, response);
            return;
        }

        // Pede pra fábrica instanciar o comando correto com base no parâmetro "acao"
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
            // Dispara a lógica de negócio encapsulada no comando
            String result = command.execute(request, response);

            if (result != null && result.startsWith("redirect:")) {
                String redirectUrl = request.getContextPath() + result.substring("redirect:".length());
                // Aplica o padrão Post/Redirect/Get para evitar duplo submit ao dar F5 na página
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
