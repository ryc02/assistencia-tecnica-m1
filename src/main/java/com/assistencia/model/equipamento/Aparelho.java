package com.assistencia.model.equipamento;

import com.assistencia.dados.*;
import com.assistencia.model.cliente.Cliente;
import com.assistencia.model.valor.Vinculo;

/** Equipamento físico e o cliente dono dele (relacionamento Cliente 1:N Equipamento). */
public final class Aparelho implements Exportavel {
    private final Vinculo<Cliente> dono;
    private final Especificacao especificacao;

    public Aparelho(Vinculo<Cliente> dono, Especificacao especificacao) {
        this.dono = dono;
        this.especificacao = especificacao;
    }

    public static Aparelho de(Campos dados) {
        Vinculo<Cliente> dono = Vinculo.obrigatorio(dados, "cliente",
                "Cliente é obrigatório para cadastrar um equipamento.");
        Especificacao especificacao = Especificacao.de(dados);
        return new Aparelho(dono, especificacao);
    }

    @Override
    public void exportar(Campos destino) {
        dono.exportar(destino);
        especificacao.exportar(destino);
    }

    boolean pertenceA(Long clienteId) {
        return dono.referencia(clienteId);
    }
}
