package com.assistencia.command;

import javax.servlet.http.*;

/** [Command - Aula 07] Contrato comum das ações da aplicação web. */
public interface ICommand {
    String execute(HttpServletRequest requisicao, HttpServletResponse resposta) throws Exception;
}
