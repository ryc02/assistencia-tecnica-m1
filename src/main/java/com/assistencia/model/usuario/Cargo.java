package com.assistencia.model.usuario;

import com.assistencia.dados.*;
import com.assistencia.exception.ValidationException;

/** Cargo do usuário (Administrador ou Técnico), que define as permissões. */
public final class Cargo implements Exportavel {
    private static final String ADMINISTRADOR = "Administrador";

    private final String nome;

    private Cargo(String nome) {
        this.nome = nome;
    }

    public static Cargo de(Campos dados) {
        String informado = dados.texto("cargo");
        if (informado == null) {
            throw new ValidationException("Cargo do usuário é obrigatório.");
        }
        return new Cargo(informado);
    }

    boolean ehAdministrador() {
        return ADMINISTRADOR.equalsIgnoreCase(nome);
    }

    @Override
    public void exportar(Campos destino) {
        destino.com("cargo", nome);
    }
}
