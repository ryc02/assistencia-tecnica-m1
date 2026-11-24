package com.assistencia.model.usuario;

import com.assistencia.dados.*;
import com.assistencia.model.valor.Texto;

/** Nome e cargo do usuário do sistema. */
public final class PerfilUsuario implements Exportavel {
    private final Texto nome;
    private final Cargo cargo;

    public PerfilUsuario(Texto nome, Cargo cargo) {
        this.nome = nome;
        this.cargo = cargo;
    }

    public static PerfilUsuario de(Campos dados) {
        Texto nome = Texto.obrigatorio(dados, "nome", 100, "Nome do usuário");
        Cargo cargo = Cargo.de(dados);
        return new PerfilUsuario(nome, cargo);
    }

    @Override
    public void exportar(Campos destino) {
        nome.exportar(destino);
        cargo.exportar(destino);
    }

    boolean ehAdministrador() {
        return cargo.ehAdministrador();
    }
}
