package com.assistencia.command.impl;

import com.assistencia.command.ICommand;
import com.assistencia.dao.UsuarioDAO;
import com.assistencia.dao.jdbc.UsuarioDAOJDBC;
import com.assistencia.exception.ValidationException;
import com.assistencia.model.Usuario;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EfetuarLoginCommand implements ICommand {
    private final UsuarioDAO usuarioDAO = new UsuarioDAOJDBC();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        Usuario usuario = usuarioDAO.buscarPorEmailSenha(email, senha);

        if (usuario != null) {
            // Guardamos o objeto do usuário na sessão para ter o nome dele e o cargo nas telas
            request.getSession(true).setAttribute("usuarioLogado", usuario.getNome());
            request.getSession().setAttribute("usuarioCargo", usuario.getCargo());
            return "redirect:/controle?acao=cliente.listar";
        } else {
            throw new ValidationException("Credenciais inválidas! Tente novamente.");
        }
    }
}
