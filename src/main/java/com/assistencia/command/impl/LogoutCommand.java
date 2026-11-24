package com.assistencia.command.impl;

import com.assistencia.command.*;
import com.assistencia.infra.SessaoUsuario;
import javax.servlet.http.*;

/** [Command - Aula 07] Encerra a sessão do usuário. */
public class LogoutCommand implements ICommand {
    @Override
    public String execute(HttpServletRequest requisicao, HttpServletResponse resposta) {
        SessaoUsuario.encerrar(requisicao);
        return Destino.redirecionar("login");
    }
}
