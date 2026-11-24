package com.assistencia.service;

import com.assistencia.dados.*;
import com.assistencia.dao.UsuarioDAO;
import com.assistencia.infra.GerenciadorTransacao;
import com.assistencia.model.usuario.Usuario;
import java.sql.Connection;
import static com.assistencia.service.RegrasDeUsuario.*;

/** Cadastro, listagem e exclusão de usuários do sistema. */
public class UsuarioService {
    private final UsuarioDAO usuarios;
    private final GerenciadorTransacao transacao;

    public UsuarioService(UsuarioDAO usuarios, GerenciadorTransacao transacao) {
        this.usuarios = usuarios;
        this.transacao = transacao;
    }

    public void inserir(Campos dados) throws Exception {
        Usuario novo = Usuario.novo(dados);
        String email = dados.texto("email");
        transacao.executar(conexao -> inserir(conexao, novo, email));
    }

    public void excluir(Long id, Usuario logado) throws Exception {
        exigirOutroUsuario(logado, id);
        transacao.executar(conexao -> excluir(conexao, id));
    }

    public Registros<Usuario> listarTodos() throws Exception {
        return transacao.executar(usuarios::listarTodos);
    }

    private Void inserir(Connection conexao, Usuario novo, String email) throws Exception {
        exigirEmailLivre(usuarios, conexao, email);
        usuarios.inserir(conexao, novo);
        return null;
    }

    private Void excluir(Connection conexao, Long id) throws Exception {
        Usuario encontrado = usuarios.buscarPorId(conexao, id);
        Usuario existente = Existencia.exigir(encontrado, "Usuário não encontrado.");
        exigirOutroAdministrador(usuarios, conexao, existente);
        usuarios.excluir(conexao, id);
        return null;
    }
}
