package com.assistencia.factory;

import com.assistencia.command.impl.*;
import static com.assistencia.factory.FabricaDeClientes.*;

/** Registra os Commands de autenticação, usuários e acesso negado. */
final class ComandosDeAcesso {
    private ComandosDeAcesso() { }

    static void registrar(RegistroDeComandos registro) {
        registro.registrar("login", new LoginCommand());
        registro.registrar("efetuarLogin", new EfetuarLoginCommand(autenticacaoService()));
        registro.registrar("logout", new LogoutCommand());
        registro.registrar("acesso.negado", new AcessoNegadoCommand());
        registro.registrar("usuario.listar", new UsuarioListarCommand(usuarioService()));
        registro.registrar("usuario.novo", new UsuarioNovoCommand());
        registro.registrar("usuario.inserir", new UsuarioInserirCommand(usuarioService()));
        registro.registrar("usuario.excluir", new UsuarioExcluirCommand(usuarioService()));
    }
}
