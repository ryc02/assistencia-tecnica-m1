package com.assistencia.command.impl;

import com.assistencia.command.*;
import javax.servlet.http.*;

/** [Command - Aula 07] Exibe a tela de acesso negado (perfil sem permissão). */
public class AcessoNegadoCommand implements ICommand {
    @Override
    public String execute(HttpServletRequest requisicao, HttpServletResponse resposta) {
        return Destino.view("acesso-negado");
    }
}
