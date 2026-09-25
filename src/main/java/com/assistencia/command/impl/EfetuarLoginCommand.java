package com.assistencia.command.impl;

import com.assistencia.command.ICommand;
import com.assistencia.exception.ValidationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EfetuarLoginCommand implements ICommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        // Credencial fixa para demonstração
        if ("admin@m1.com".equals(email) && "admin123".equals(senha)) {
            request.getSession(true).setAttribute("usuarioLogado", "Administrador");
            return "redirect:/controle?acao=cliente.listar";
        } else {
            throw new ValidationException("Credenciais inválidas! Tente admin@m1.com e admin123");
        }
    }
}
