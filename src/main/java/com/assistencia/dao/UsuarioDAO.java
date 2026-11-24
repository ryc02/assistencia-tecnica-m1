package com.assistencia.dao;

import com.assistencia.dados.Registros;
import com.assistencia.model.usuario.Usuario;
import java.sql.Connection;

/** [DAO - Aula 04] Contrato de persistência do Usuário. */
public interface UsuarioDAO {
    Usuario buscarPorEmail(Connection conexao, String email) throws Exception;

    Usuario buscarPorId(Connection conexao, Long id) throws Exception;

    Registros<Usuario> listarTodos(Connection conexao) throws Exception;

    void inserir(Connection conexao, Usuario usuario) throws Exception;

    void excluir(Connection conexao, Long id) throws Exception;
}
