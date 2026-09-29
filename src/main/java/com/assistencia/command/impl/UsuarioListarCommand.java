package com.assistencia.command.impl;

import com.assistencia.command.ICommand;
import com.assistencia.service.UsuarioService;
import com.assistencia.model.Usuario;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class UsuarioListarCommand implements ICommand {
    private final UsuarioService usuarioService = new UsuarioService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Usuario> usuarios = usuarioService.listarTodos();
            request.setAttribute("usuarios", usuarios);
        } catch (Exception e) {
            request.setAttribute("erro", "Erro ao listar usuários: " + e.getMessage());
        }
        return "forward:/WEB-INF/views/usuario-listar.jsp";
    }
}
