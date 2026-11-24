package com.assistencia.command.impl;

import com.assistencia.command.*;
import com.assistencia.exception.*;
import com.assistencia.infra.SessaoUsuario;
import com.assistencia.model.usuario.Usuario;
import com.assistencia.service.UsuarioService;
import javax.servlet.http.*;

/** [Command - Aula 07] Exclui um usuário (nunca o próprio nem o único Administrador). */
public class UsuarioExcluirCommand implements ICommand {
    private final UsuarioService servico;

    public UsuarioExcluirCommand(UsuarioService servico) {
        this.servico = servico;
    }

    @Override
    public String execute(HttpServletRequest requisicao, HttpServletResponse resposta) throws Exception {
        try {
            Long id = ParametrosHttp.idObrigatorio(requisicao, "id", "usuário");
            Usuario logado = SessaoUsuario.logado(requisicao);
            servico.excluir(id, logado);
        } catch (ValidationException | ConflictException | NotFoundException excecao) {
            HttpSession sessao = requisicao.getSession();
            String mensagem = excecao.getMessage();
            sessao.setAttribute("erro", mensagem);
        }
        return Destino.redirecionar("usuario.listar");
    }
}
