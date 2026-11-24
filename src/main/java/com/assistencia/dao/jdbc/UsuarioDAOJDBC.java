package com.assistencia.dao.jdbc;

import com.assistencia.dados.*;
import com.assistencia.dao.UsuarioDAO;
import com.assistencia.model.usuario.Usuario;
import java.sql.Connection;

/** [DAO JDBC] Persistência do Usuário. A listagem nunca lê a coluna de senha. */
public class UsuarioDAOJDBC implements UsuarioDAO {
    private static final String INSERIR = "INSERT INTO usuario (nome, email, senha, cargo) VALUES (?, ?, ?, ?)";

    @Override
    public Usuario buscarPorEmail(Connection conexao, String email) throws Exception {
        Linhas linhas = ComandoSql.consultar(conexao, "SELECT * FROM usuario WHERE email = ?", email);
        return linhas.primeira(Usuario::de);
    }

    @Override
    public Usuario buscarPorId(Connection conexao, Long id) throws Exception {
        Linhas linhas = ComandoSql.consultar(conexao, "SELECT * FROM usuario WHERE id = ?", id);
        return linhas.primeira(Usuario::de);
    }

    @Override
    public Registros<Usuario> listarTodos(Connection conexao) throws Exception {
        String sql = "SELECT id, nome, email, cargo FROM usuario ORDER BY nome";
        Linhas linhas = ComandoSql.consultar(conexao, sql);
        return linhas.converter(Usuario::de);
    }

    @Override
    public void inserir(Connection conexao, Usuario usuario) throws Exception {
        Campos dados = usuario.exportarComSenha();
        ComandoSql.inserir(conexao, INSERIR, dados, "nome", "email", "senha", "cargo");
    }

    @Override
    public void excluir(Connection conexao, Long id) throws Exception {
        ComandoSql.executar(conexao, "DELETE FROM usuario WHERE id = ?", id);
    }
}
