package com.assistencia.command.impl;

import com.assistencia.command.*;
import com.assistencia.dados.Registros;
import com.assistencia.model.cliente.Cliente;
import com.assistencia.service.ConsultaCliente;
import java.util.*;
import javax.servlet.http.*;

/** [Command - Aula 07] Lista os clientes. */
public class ClienteListarCommand implements ICommand {
    private final ConsultaCliente consulta;

    public ClienteListarCommand(ConsultaCliente consulta) {
        this.consulta = consulta;
    }

    @Override
    public String execute(HttpServletRequest requisicao, HttpServletResponse resposta) throws Exception {
        Registros<Cliente> clientes = consulta.listarTodos();
        List<Map<String, Object>> visao = clientes.exportar();
        requisicao.setAttribute("clientes", visao);
        return Destino.view("cliente-listar");
    }
}
