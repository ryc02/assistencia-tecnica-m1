package com.assistencia.infra;

import java.util.Set;

/** Operações exclusivas do Administrador (Técnicos recebem acesso negado) e ações públicas. */
final class RegrasDeAcesso {
    private static final Set<String> TELAS_DO_ADMINISTRADOR = Set.of("usuario.listar", "usuario.novo",
            "usuario.inserir", "usuario.excluir", "cliente.inserir", "cliente.atualizar", "cliente.excluir",
            "equipamento.excluir");
    private static final Set<String> API_DO_ADMINISTRADOR = Set.of("POST clientes", "PUT clientes",
            "DELETE clientes", "DELETE equipamentos");
    private static final Set<String> TELAS_PUBLICAS = Set.of("login", "efetuarLogin");
    private static final Set<String> API_PUBLICA = Set.of("login", "csrf");

    private RegrasDeAcesso() { }

    static boolean telaPublica(String acao) {
        return TELAS_PUBLICAS.contains(acao);
    }

    static boolean telaDoAdministrador(String acao) {
        return TELAS_DO_ADMINISTRADOR.contains(acao);
    }

    static boolean apiPublica(String recurso) {
        return API_PUBLICA.contains(recurso);
    }

    static boolean apiDoAdministrador(String metodo, String recurso) {
        return API_DO_ADMINISTRADOR.contains(metodo + " " + recurso);
    }
}
