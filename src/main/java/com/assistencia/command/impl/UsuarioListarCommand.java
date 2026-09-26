package com.assistencia.command.impl;

import com.assistencia.command.ICommand;
import com.assistencia.dao.UsuarioDAO;
import com.assistencia.dao.jdbc.UsuarioDAOJDBC;
import com.assistencia.model.Usuario;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class UsuarioListarCommand implements ICommand {
    private final UsuarioDAO usuarioDAO = new UsuarioDAOJDBC();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        List<Usuario> usuarios = usuarioDAO.listarTodos();
        request.setAttribute("usuarios", usuarios);
        return "forward:/WEB-INF/views/usuario-listar.jsp";
    }
}
