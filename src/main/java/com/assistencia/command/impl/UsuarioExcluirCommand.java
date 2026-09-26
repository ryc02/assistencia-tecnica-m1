package com.assistencia.command.impl;

import com.assistencia.command.ICommand;
import com.assistencia.dao.UsuarioDAO;
import com.assistencia.dao.jdbc.UsuarioDAOJDBC;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UsuarioExcluirCommand implements ICommand {
    private final UsuarioDAO usuarioDAO = new UsuarioDAOJDBC();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String idStr = request.getParameter("id");
        if (idStr != null && !idStr.isEmpty()) {
            try {
                usuarioDAO.excluir(Long.parseLong(idStr));
            } catch (Exception e) {
                request.getSession().setAttribute("erro", "Não foi possível excluir o usuário. Ele pode estar referenciado em outra tabela.");
            }
        }
        return "redirect:/controle?acao=usuario.listar";
    }
}
