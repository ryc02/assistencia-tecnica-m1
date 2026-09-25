package com.assistencia.command.impl;

import com.assistencia.command.ICommand;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutCommand implements ICommand {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        if (request.getSession(false) != null) {
            request.getSession().invalidate();
        }
        return "redirect:/controle?acao=login";
    }
}
