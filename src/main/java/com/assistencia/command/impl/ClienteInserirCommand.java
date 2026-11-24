package com.assistencia.command.impl;

import com.assistencia.command.*;
import com.assistencia.dados.Campos;
import com.assistencia.model.cliente.Cliente;
import com.assistencia.service.*;
import javax.servlet.http.*;

/** [Command - Aula 07] Cadastra um cliente. */
public class ClienteInserirCommand implements ICommand {
    private final ClienteService servico;

    public ClienteInserirCommand(ClienteService servico) {
        this.servico = servico;
    }

    @Override
    public String execute(HttpServletRequest requisicao, HttpServletResponse resposta) throws Exception {
        Campos dados = ParametrosHttp.campos(requisicao);
        Cliente criado = servico.cadastrar(dados);
        Long id = IdDaEntidade.de(criado);
        return Destino.redirecionar("cliente.consultar", id);
    }
}
