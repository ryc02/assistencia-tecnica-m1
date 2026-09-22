package com.assistencia.command.impl;

import com.assistencia.command.ICommand;
import com.assistencia.model.Cliente;
import com.assistencia.service.ClienteService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * [Requisito 8: Padrão Command & Requisito 10: HTTP 303 Redirect]
 * Comando para cadastrar um novo cliente.
 */
public class ClienteInserirCommand implements ICommand {

    private final ClienteService clienteService = new ClienteService();

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String nome = request.getParameter("nome");
        String cpf = request.getParameter("cpf");
        String email = request.getParameter("email");
        String telefone = request.getParameter("telefone");
        String logradouro = request.getParameter("logradouro");
        String numero = request.getParameter("numero");
        String bairro = request.getParameter("bairro");
        String cidade = request.getParameter("cidade");

        Cliente criado = clienteService.cadastrar(nome, cpf, email, telefone, logradouro, numero, bairro, cidade);
        return "redirect:/controle?acao=cliente.consultar&id=" + criado.getId();
    }
}
