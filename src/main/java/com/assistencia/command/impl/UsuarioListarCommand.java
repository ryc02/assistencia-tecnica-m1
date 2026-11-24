package com.assistencia.command.impl;

import com.assistencia.command.*;
import com.assistencia.dados.Registros;
import com.assistencia.model.usuario.Usuario;
import com.assistencia.service.UsuarioService;
import java.util.*;
import javax.servlet.http.*;

/** [Command - Aula 07] Lista os usuários (sem senhas). */
public class UsuarioListarCommand implements ICommand {
    private final UsuarioService servico;

    public UsuarioListarCommand(UsuarioService servico) {
        this.servico = servico;
    }

    @Override
    public String execute(HttpServletRequest requisicao, HttpServletResponse resposta) throws Exception {
        Registros<Usuario> usuarios = servico.listarTodos();
        List<Map<String, Object>> visao = usuarios.exportar();
        requisicao.setAttribute("usuarios", visao);
        return Destino.view("usuario-listar");
    }
}
