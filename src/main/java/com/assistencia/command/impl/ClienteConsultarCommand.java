package com.assistencia.command.impl;

import com.assistencia.command.*;
import com.assistencia.service.*;
import javax.servlet.http.*;

/** [Command - Aula 07] Exibe o cliente e seus equipamentos (relacionamento 1:N). */
public class ClienteConsultarCommand implements ICommand {
    private final TelasDeEquipamento telas;

    public ClienteConsultarCommand(TelasDeEquipamento telas) {
        this.telas = telas;
    }

    @Override
    public String execute(HttpServletRequest requisicao, HttpServletResponse resposta) throws Exception {
        Long id = ParametrosHttp.id(requisicao, "id");
        if (id == null) {
            return Destino.redirecionar("cliente.listar");
        }
        Tela tela = telas.detalhesDoCliente(id);
        tela.publicar(requisicao::setAttribute);
        return Destino.view("cliente-detalhes");
    }
}
