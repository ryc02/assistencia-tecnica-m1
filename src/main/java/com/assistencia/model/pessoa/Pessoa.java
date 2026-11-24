package com.assistencia.model.pessoa;

import com.assistencia.model.EntidadeComId;
import com.assistencia.model.valor.Identificador;

/** [Herança / Polimorfismo com classe abstrata] Superclasse de Cliente e Usuario. */
public abstract class Pessoa extends EntidadeComId {
    protected Pessoa(Identificador identificador) {
        super(identificador);
    }
}
