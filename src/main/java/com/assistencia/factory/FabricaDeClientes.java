package com.assistencia.factory;

import com.assistencia.service.*;
import static com.assistencia.factory.Persistencia.*;

/** [Factory] Monta os Services de clientes e usuários, injetando DAOs e transação (DIP). */
public final class FabricaDeClientes {
    private static final ClienteService CLIENTE = new ClienteService(CLIENTES, TRANSACAO);
    private static final ConsultaCliente CONSULTA = new ConsultaCliente(CLIENTES, TRANSACAO);
    private static final UsuarioService USUARIO = new UsuarioService(USUARIOS, TRANSACAO);
    private static final AutenticacaoService AUTENTICACAO = new AutenticacaoService(USUARIOS, TRANSACAO);

    private FabricaDeClientes() { }

    public static ClienteService clienteService() {
        return CLIENTE;
    }

    public static ConsultaCliente consultaCliente() {
        return CONSULTA;
    }

    public static UsuarioService usuarioService() {
        return USUARIO;
    }

    public static AutenticacaoService autenticacaoService() {
        return AUTENTICACAO;
    }
}
