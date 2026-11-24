package com.assistencia.builder;

import com.assistencia.dados.Campos;
import com.assistencia.model.cliente.Cliente;
import java.time.LocalDateTime;

/** [Builder - Aula 06] Constrói Cliente validando CPF, e-mail, telefone e endereço. */
public class ClienteBuilder extends BuilderDeEntidade<ClienteBuilder, Cliente> {
    public ClienteBuilder dataCadastro(LocalDateTime dataCadastro) {
        return com("dataCadastro", dataCadastro);
    }

    public ClienteBuilder nome(String nome) {
        return com("nome", nome);
    }

    public ClienteBuilder cpf(String cpf) {
        return com("cpf", cpf);
    }

    public ClienteBuilder email(String email) {
        return com("email", email);
    }

    public ClienteBuilder telefone(String telefone) {
        return com("telefone", telefone);
    }

    @Override
    protected ClienteBuilder proprio() {
        return this;
    }

    @Override
    protected Cliente construir(Campos campos) {
        return Cliente.de(campos);
    }
}
