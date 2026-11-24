package com.assistencia.command.impl;

import com.assistencia.command.*;
import javax.servlet.http.*;

/** [Command - Aula 07] Exibe o formulário de novo usuário. */
public class UsuarioNovoCommand implements ICommand {
    @Override
    public String execute(HttpServletRequest requisicao, HttpServletResponse resposta) {
        return Destino.view("usuario-detalhes");
    }
}
