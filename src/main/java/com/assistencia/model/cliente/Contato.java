package com.assistencia.model.cliente;

import com.assistencia.dados.*;
import com.assistencia.model.valor.*;

/** Meios de contato do cliente: e-mail (opcional) e telefone. */
public final class Contato implements Exportavel {
    private final Email email;
    private final Texto telefone;

    public Contato(Email email, Texto telefone) {
        this.email = email;
        this.telefone = telefone;
    }

    public static Contato de(Campos dados) {
        Email email = Email.opcional(dados);
        Texto telefone = Texto.obrigatorio(dados, "telefone", 20, "Telefone");
        return new Contato(email, telefone);
    }

    @Override
    public void exportar(Campos destino) {
        email.exportar(destino);
        telefone.exportar(destino);
    }
}
