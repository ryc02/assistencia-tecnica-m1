package com.assistencia.command.impl;

import com.assistencia.command.*;
import com.assistencia.exception.ValidationException;
import com.assistencia.infra.SessaoUsuario;
import com.assistencia.model.usuario.Usuario;
import com.assistencia.service.AutenticacaoService;
import javax.servlet.http.*;

/** [Command - Aula 07] Autentica o usuário e guarda-o na sessão. */
public class EfetuarLoginCommand implements ICommand {
    private final AutenticacaoService autenticacao;

    public EfetuarLoginCommand(AutenticacaoService autenticacao) {
        this.autenticacao = autenticacao;
    }

    @Override
    public String execute(HttpServletRequest requisicao, HttpServletResponse resposta) throws Exception {
        String email = requisicao.getParameter("email");
        String senha = requisicao.getParameter("senha");
        try {
            Usuario usuario = autenticacao.efetuarLogin(email, senha);
            SessaoUsuario.registrar(requisicao, usuario);
            return Destino.redirecionar("cliente.listar");
        } catch (ValidationException excecao) {
            String mensagem = excecao.getMessage();
            requisicao.setAttribute("erroLogin", mensagem);
            requisicao.setAttribute("emailInformado", email);
            return Destino.view("login");
        }
    }
}
