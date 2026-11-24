package com.assistencia.model.usuario;

import com.assistencia.dados.*;

/** Perfil (nome e cargo) e credenciais de acesso do usuário. */
public final class Conta implements Exportavel {
    private final PerfilUsuario perfil;
    private final Credenciais credenciais;

    private Conta(PerfilUsuario perfil, Credenciais credenciais) {
        this.perfil = perfil;
        this.credenciais = credenciais;
    }

    static Conta armazenada(Campos dados) {
        PerfilUsuario perfil = PerfilUsuario.de(dados);
        return new Conta(perfil, Credenciais.armazenadas(dados));
    }

    static Conta nova(Campos dados) {
        PerfilUsuario perfil = PerfilUsuario.de(dados);
        return new Conta(perfil, Credenciais.novas(dados));
    }

    boolean ehAdministrador() {
        return perfil.ehAdministrador();
    }

    void exigirSenha(String senhaDigitada) {
        credenciais.exigirSenha(senhaDigitada);
    }

    @Override
    public void exportar(Campos destino) {
        perfil.exportar(destino);
        credenciais.exportar(destino);
    }

    void exportarComSenha(Campos destino) {
        perfil.exportar(destino);
        credenciais.exportarComSenha(destino);
    }
}
