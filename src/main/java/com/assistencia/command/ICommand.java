package com.assistencia.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * [Requisito 8: Padrão Command]
 * Contrato comum para encapsular a execução de ações HTTP.
 */
public interface ICommand {

    /**
     * Executa o comando e retorna o destino (URL de redirecionamento ou caminho da view JSP).
     */
    String execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
