package com.assistencia.command.impl;

import com.assistencia.command.*;
import com.assistencia.service.ClienteService;
import javax.servlet.http.*;

/** [Command - Aula 07] Exclui um cliente sem equipamentos. */
public class ClienteExcluirCommand implements ICommand {
    private final ClienteService servico;

    public ClienteExcluirCommand(ClienteService servico) {
        this.servico = servico;
    }

    @Override
    public String execute(HttpServletRequest requisicao, HttpServletResponse resposta) throws Exception {
        Long id = ParametrosHttp.idObrigatorio(requisicao, "id", "cliente");
        servico.excluir(id);
        return Destino.redirecionar("cliente.listar");
    }
}
