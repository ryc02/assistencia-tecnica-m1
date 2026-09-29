package com.assistencia.command.impl;

import com.assistencia.command.ICommand;
import com.assistencia.service.UsuarioService;
import com.assistencia.exception.ValidationException;
import com.assistencia.model.Usuario;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EfetuarLoginCommand implements ICommand {
    private final UsuarioService usuarioService = new UsuarioService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        try {
            Usuario usuario = usuarioService.efetuarLogin(email, senha);
            request.getSession(true).setAttribute("usuarioLogado", usuario.getNome());
            request.getSession().setAttribute("usuarioCargo", usuario.getCargo());
            return "redirect:/controle?acao=cliente.listar";
        } catch (Exception e) {
            throw new ValidationException(e.getMessage());
        }
    }
}
