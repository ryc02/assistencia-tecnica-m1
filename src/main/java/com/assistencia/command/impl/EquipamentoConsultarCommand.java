package com.assistencia.command.impl;

import com.assistencia.command.*;
import com.assistencia.dados.Registros;
import com.assistencia.model.cliente.Cliente;
import com.assistencia.service.*;
import java.util.*;
import javax.servlet.http.*;

/** [Command - Aula 07] Exibe o equipamento, seus orçamentos e a lista de clientes. */
public class EquipamentoConsultarCommand implements ICommand {
    private final TelasDeOrcamento telas;
    private final ConsultaCliente clientes;

    public EquipamentoConsultarCommand(TelasDeOrcamento telas, ConsultaCliente clientes) {
        this.telas = telas;
        this.clientes = clientes;
    }

    @Override
    public String execute(HttpServletRequest requisicao, HttpServletResponse resposta) throws Exception {
        Long id = ParametrosHttp.id(requisicao, "id");
        if (id == null) {
            return Destino.redirecionar("equipamento.listar");
        }
        Tela tela = telas.detalhesDoEquipamento(id);
        tela.publicar(requisicao::setAttribute);
        Registros<Cliente> todos = clientes.listarTodos();
        List<Map<String, Object>> visao = todos.exportar();
        requisicao.setAttribute("clientes", visao);
        return Destino.view("equipamento-detalhes");
    }
}
