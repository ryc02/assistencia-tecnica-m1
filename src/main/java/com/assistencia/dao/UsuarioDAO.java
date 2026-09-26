package com.assistencia.dao;

import com.assistencia.model.Usuario;

public interface UsuarioDAO {
    Usuario buscarPorEmailSenha(String email, String senha);
}
