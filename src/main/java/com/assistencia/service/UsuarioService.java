package com.assistencia.service;

import com.assistencia.dao.UsuarioDAO;
import com.assistencia.dao.jdbc.UsuarioDAOJDBC;
import com.assistencia.exception.ValidationException;
import com.assistencia.infra.TransactionManager;
import com.assistencia.model.Usuario;

import java.util.List;

public class UsuarioService {
    
    private final UsuarioDAO usuarioDAO = new UsuarioDAOJDBC();

    public Usuario efetuarLogin(String email, String senha) throws Exception {
        if (email == null || email.trim().isEmpty() || senha == null || senha.trim().isEmpty()) {
            throw new ValidationException("E-mail e senha são obrigatórios.");
        }
        return TransactionManager.executeInTransaction(conn -> {
            Usuario u = usuarioDAO.buscarPorEmailSenha(conn, email, senha);
            if (u == null) {
                throw new ValidationException("Credenciais inválidas! Tente novamente.");
            }
            return u;
        });
    }

    public List<Usuario> listarTodos() throws Exception {
        return TransactionManager.executeInTransaction(usuarioDAO::listarTodos);
    }

    public void inserir(Usuario usuario) throws Exception {
        if (usuario.getNome() == null || usuario.getNome().trim().isEmpty() ||
            usuario.getEmail() == null || usuario.getEmail().trim().isEmpty() ||
            usuario.getSenha() == null || usuario.getSenha().trim().isEmpty() ||
            usuario.getCargo() == null || usuario.getCargo().trim().isEmpty()) {
            throw new ValidationException("Todos os campos do usuário são obrigatórios.");
        }
        
        TransactionManager.executeInTransaction(conn -> {
            usuarioDAO.inserir(conn, usuario);
            return null;
        });
    }

    public void excluir(Long id) throws Exception {
        if (id == null) {
            throw new ValidationException("ID do usuário é obrigatório.");
        }
        TransactionManager.executeInTransaction(conn -> {
            usuarioDAO.excluir(conn, id);
            return null;
        });
    }
}
