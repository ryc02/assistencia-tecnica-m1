package com.assistencia.command.impl;

import com.assistencia.command.*;
import com.assistencia.dados.Campos;
import com.assistencia.service.ClienteService;
import javax.servlet.http.*;

/** [Command - Aula 07] Atualiza os dados de um cliente. */
public class ClienteAtualizarCommand implements ICommand {
    private final ClienteService servico;

    public ClienteAtualizarCommand(ClienteService servico) {
        this.servico = servico;
    }

    @Override
    public String execute(HttpServletRequest requisicao, HttpServletResponse resposta) throws Exception {
        Long id = ParametrosHttp.idObrigatorio(requisicao, "id", "cliente");
        Campos dados = ParametrosHttp.campos(requisicao);
        servico.atualizar(id, dados);
        return Destino.redirecionar("cliente.consultar", id);
    }
}
