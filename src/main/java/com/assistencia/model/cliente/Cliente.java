package com.assistencia.model.cliente;

import com.assistencia.dados.Campos;
import com.assistencia.model.pessoa.Pessoa;
import com.assistencia.model.valor.Identificador;

/**

 * Cliente da assistência técnica: 10 atributos (id, nome, cpf, email, telefone, logradouro, número, bairro,

 * cidade e data de cadastro), agrupados em objetos com no máximo 2 variáveis.

 */
public final class Cliente extends Pessoa {
    private final CadastroCliente cadastro;

    public Cliente(Identificador identificador, CadastroCliente cadastro) {
        super(identificador);
        this.cadastro = cadastro;
    }

    public static Cliente de(Campos dados) {
        Identificador identificador = Identificador.de(dados);
        CadastroCliente cadastro = CadastroCliente.de(dados);
        return new Cliente(identificador, cadastro);
    }

    @Override
    protected void exportarDados(Campos destino) {
        cadastro.exportar(destino);
    }
}
