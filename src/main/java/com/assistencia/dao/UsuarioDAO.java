package com.assistencia.dao;

import com.assistencia.model.Usuario;
import java.sql.Connection;
import java.util.List;
public interface UsuarioDAO {
    Usuario buscarPorEmailSenha(Connection conn, String email, String senha) throws Exception;
    List<Usuario> listarTodos(Connection conn) throws Exception;
    void inserir(Connection conn, Usuario usuario) throws Exception;
    void excluir(Connection conn, Long id) throws Exception;
}
