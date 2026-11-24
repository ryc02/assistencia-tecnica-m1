package com.assistencia.model.cliente;

import com.assistencia.dados.*;
import com.assistencia.model.valor.*;

/** Nome e CPF do cliente. */
public final class Identidade implements Exportavel {
    private final Texto nome;
    private final Cpf cpf;

    public Identidade(Texto nome, Cpf cpf) {
        this.nome = nome;
        this.cpf = cpf;
    }

    public static Identidade de(Campos dados) {
        Texto nome = Texto.obrigatorio(dados, "nome", 120, "Nome do cliente");
        Cpf cpf = Cpf.de(dados);
        return new Identidade(nome, cpf);
    }

    @Override
    public void exportar(Campos destino) {
        nome.exportar(destino);
        cpf.exportar(destino);
    }
}
