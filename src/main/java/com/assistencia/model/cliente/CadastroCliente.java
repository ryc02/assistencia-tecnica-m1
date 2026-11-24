package com.assistencia.model.cliente;

import com.assistencia.dados.*;

/** Todos os dados cadastrais do cliente (exceto o id). */
public final class CadastroCliente implements Exportavel {
    private final DadosPessoais pessoais;
    private final Residencia residencia;

    public CadastroCliente(DadosPessoais pessoais, Residencia residencia) {
        this.pessoais = pessoais;
        this.residencia = residencia;
    }

    public static CadastroCliente de(Campos dados) {
        DadosPessoais pessoais = DadosPessoais.de(dados);
        Residencia residencia = Residencia.de(dados);
        return new CadastroCliente(pessoais, residencia);
    }

    @Override
    public void exportar(Campos destino) {
        pessoais.exportar(destino);
        residencia.exportar(destino);
    }
}
