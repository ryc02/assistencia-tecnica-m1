package com.assistencia.command.impl;

import com.assistencia.command.ICommand;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * [Controle de Acesso por Perfil]
 * Exibido quando um usuário tenta acessar uma ação sem permissão suficiente.
 */
public class AcessoNegadoCommand implements ICommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return "/WEB-INF/views/acesso-negado.jsp";
    }
}
