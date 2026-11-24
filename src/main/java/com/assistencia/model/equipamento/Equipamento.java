package com.assistencia.model.equipamento;

import com.assistencia.dados.*;
import com.assistencia.model.valor.Identificador;

/**

 * Equipamento de um cliente: 10 atributos (id, cliente, tipo, marca, modelo, número de série, cor, voltagem,

 * descrição e data de cadastro), agrupados em objetos com no máximo 2 variáveis.

 */
public final class Equipamento implements Entidade {
    private final Identificador identificador;
    private final Aparelho aparelho;

    private Equipamento(Identificador identificador, Aparelho aparelho) {
        this.identificador = identificador;
        this.aparelho = aparelho;
    }

    public static Equipamento de(Campos dados) {
        Identificador identificador = Identificador.de(dados);
        return new Equipamento(identificador, Aparelho.de(dados));
    }

    public boolean pertenceAoCliente(Long clienteId) {
        return aparelho.pertenceA(clienteId);
    }

    @Override
    public boolean possuiId(Long id) {
        return identificador.igualA(id);
    }

    @Override
    public Campos exportar() {
        Campos destino = new Campos();
        identificador.exportar(destino);
        aparelho.exportar(destino);
        return destino;
    }
}
