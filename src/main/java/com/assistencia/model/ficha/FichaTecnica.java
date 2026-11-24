package com.assistencia.model.ficha;

import com.assistencia.dados.*;
import com.assistencia.model.valor.Identificador;

/**

 * Ficha técnica de recepção: id, ordem de serviço, estado de conservação, acessórios, liga normalmente, possui

 * avarias, descrição das avarias, teste inicial, observações e data.

 */
public final class FichaTecnica implements Entidade {
    private final Identificador identificador;
    private final Inspecao inspecao;

    private FichaTecnica(Identificador identificador, Inspecao inspecao) {
        this.identificador = identificador;
        this.inspecao = inspecao;
    }

    public static FichaTecnica de(Campos dados) {
        Identificador identificador = Identificador.de(dados);
        return new FichaTecnica(identificador, Inspecao.de(dados));
    }

    public void atualizar(Campos dados) {
        inspecao.atualizar(dados);
    }

    @Override
    public boolean possuiId(Long id) {
        return identificador.igualA(id);
    }

    @Override
    public Campos exportar() {
        Campos destino = new Campos();
        identificador.exportar(destino);
        inspecao.exportar(destino);
        return destino;
    }
}
