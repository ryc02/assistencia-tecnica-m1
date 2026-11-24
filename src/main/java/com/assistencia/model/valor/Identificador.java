package com.assistencia.model.valor;

import com.assistencia.dados.*;
import java.util.Objects;

/** Identificador gerado pelo banco de dados. */
public final class Identificador implements Exportavel {
    private final Long valor;

    public Identificador(Long valor) {
        this.valor = valor;
    }

    public static Identificador de(Campos dados) {
        return new Identificador(dados.inteiroLongo("id"));
    }

    public boolean igualA(Long outro) {
        return valor != null && Objects.equals(valor, outro);
    }

    @Override
    public void exportar(Campos destino) {
        destino.com("id", valor);
    }
}
