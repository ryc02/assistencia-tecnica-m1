package com.assistencia.dao;

import com.assistencia.model.Usuario;
import java.util.List;

public interface UsuarioDAO {
    Usuario buscarPorEmailSenha(String email, String senha);
    List<Usuario> listarTodos();
    void inserir(Usuario usuario);
    void excluir(Long id);
}
