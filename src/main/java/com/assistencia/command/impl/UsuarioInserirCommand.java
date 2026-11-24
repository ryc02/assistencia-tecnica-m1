package com.assistencia.command.impl;

import com.assistencia.command.*;
import com.assistencia.dados.Campos;
import com.assistencia.exception.*;
import com.assistencia.service.UsuarioService;
import javax.servlet.http.*;

/** [Command - Aula 07] Cadastra um usuário (senha gravada com hash). */
public class UsuarioInserirCommand implements ICommand {
    private final UsuarioService servico;

    public UsuarioInserirCommand(UsuarioService servico) {
        this.servico = servico;
    }

    @Override
    public String execute(HttpServletRequest requisicao, HttpServletResponse resposta) throws Exception {
        try {
            Campos dados = ParametrosHttp.campos(requisicao);
            servico.inserir(dados);
            return Destino.redirecionar("usuario.listar");
        } catch (ValidationException | ConflictException excecao) {
            String mensagem = excecao.getMessage();
            requisicao.setAttribute("erro", "Erro ao cadastrar usuário: " + mensagem);
            return Destino.view("usuario-detalhes");
        }
    }
}
