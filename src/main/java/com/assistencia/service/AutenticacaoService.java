package com.assistencia.service;

import com.assistencia.dao.UsuarioDAO;
import com.assistencia.exception.ValidationException;
import com.assistencia.infra.GerenciadorTransacao;
import com.assistencia.model.usuario.Usuario;
import java.sql.Connection;

/** Login: confere e-mail e senha e devolve o usuário sem o hash da senha (pronto para a sessão). */
public class AutenticacaoService {
    private static final String CREDENCIAIS_INVALIDAS = "Credenciais inválidas! Verifique seu e-mail e senha.";

    private final UsuarioDAO usuarios;
    private final GerenciadorTransacao transacao;

    public AutenticacaoService(UsuarioDAO usuarios, GerenciadorTransacao transacao) {
        this.usuarios = usuarios;
        this.transacao = transacao;
    }

    public Usuario efetuarLogin(String email, String senha) throws Exception {
        exigirPreenchido(email, "E-mail é obrigatório.");
        exigirPreenchido(senha, "Senha é obrigatória.");
        Usuario usuario = transacao.executar(conexao -> buscar(conexao, email));
        return usuario.autenticar(senha);
    }

    private Usuario buscar(Connection conexao, String email) throws Exception {
        String emailInformado = email.trim();
        Usuario usuario = usuarios.buscarPorEmail(conexao, emailInformado);
        if (usuario == null) {
            throw new ValidationException(CREDENCIAIS_INVALIDAS);
        }
        return usuario;
    }

    private void exigirPreenchido(String valor, String mensagem) {
        if (valor == null || valor.isBlank()) {
            throw new ValidationException(mensagem);
        }
    }
}
