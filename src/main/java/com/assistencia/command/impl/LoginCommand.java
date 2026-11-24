package com.assistencia.command.impl;

import com.assistencia.command.*;
import javax.servlet.http.*;

/** [Command - Aula 07] Exibe a tela de login. */
public class LoginCommand implements ICommand {
    @Override
    public String execute(HttpServletRequest requisicao, HttpServletResponse resposta) {
        return Destino.view("login");
    }
}
