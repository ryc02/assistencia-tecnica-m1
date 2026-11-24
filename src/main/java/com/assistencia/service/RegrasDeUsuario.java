package com.assistencia.service;

import com.assistencia.dados.Registros;
import com.assistencia.dao.UsuarioDAO;
import com.assistencia.exception.ConflictException;
import com.assistencia.model.usuario.Usuario;
import java.sql.Connection;

/** Regras de negócio de usuários: e-mail único, não excluir a si mesmo e nunca ficar sem Administrador. */
final class RegrasDeUsuario {
    private RegrasDeUsuario() { }

    static void exigirEmailLivre(UsuarioDAO usuarios, Connection conexao, String email) throws Exception {
        if (usuarios.buscarPorEmail(conexao, email) != null) {
            throw new ConflictException("Já existe um usuário cadastrado com o e-mail: " + email);
        }
    }

    static void exigirOutroUsuario(Usuario logado, Long id) {
        Existencia.exigirId(id, "usuário");
        if (logado != null && logado.possuiId(id)) {
            throw new ConflictException("Você não pode excluir o seu próprio usuário enquanto estiver conectado.");
        }
    }

    static void exigirOutroAdministrador(UsuarioDAO usuarios, Connection conexao, Usuario removido) throws Exception {
        if (!removido.ehAdministrador()) {
            return;
        }
        Registros<Usuario> todos = usuarios.listarTodos(conexao);
        if (todos.contar(Usuario::ehAdministrador) <= 1) {
            throw new ConflictException("Não é possível excluir o único Administrador cadastrado no sistema.");
        }
    }
}
