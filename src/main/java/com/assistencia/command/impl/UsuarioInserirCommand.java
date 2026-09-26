package com.assistencia.command.impl;

import com.assistencia.command.ICommand;
import com.assistencia.dao.UsuarioDAO;
import com.assistencia.dao.jdbc.UsuarioDAOJDBC;
import com.assistencia.model.Usuario;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UsuarioInserirCommand implements ICommand {
    private final UsuarioDAO usuarioDAO = new UsuarioDAOJDBC();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        // Se for GET, apenas abre a tela vazia
        if ("GET".equalsIgnoreCase(request.getMethod())) {
            return "forward:/WEB-INF/views/usuario-detalhes.jsp";
        }
        
        // Se for POST, processa a inclusao
        try {
            Usuario usuario = new Usuario();
            usuario.setNome(request.getParameter("nome"));
            usuario.setEmail(request.getParameter("email"));
            usuario.setSenha(request.getParameter("senha"));
            usuario.setCargo(request.getParameter("cargo"));

            usuarioDAO.inserir(usuario);
            return "redirect:/controle?acao=usuario.listar";
            
        } catch (Exception e) {
            request.setAttribute("erro", "Erro ao cadastrar usuário: " + e.getMessage());
            return "forward:/WEB-INF/views/usuario-detalhes.jsp";
        }
    }
}
