package com.assistencia.builder;

import com.assistencia.exception.ValidationException;
import com.assistencia.model.Cliente;

import java.time.LocalDateTime;

/**
 * [Requisito 8: Padrão Builder]
 * ClienteBuilder: Constrói objetos Cliente com validação de CPF e campos obrigatórios.
 */
public class ClienteBuilder {

    private Long id;
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private String logradouro;
    private String numero;
    private String bairro;
    private String cidade;
    private LocalDateTime dataCadastro;

    public ClienteBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public ClienteBuilder nome(String nome) {
        this.nome = nome;
        return this;
    }

    public ClienteBuilder cpf(String cpf) {
        this.cpf = cpf;
        return this;
    }

    public ClienteBuilder email(String email) {
        this.email = email;
        return this;
    }

    public ClienteBuilder telefone(String telefone) {
        this.telefone = telefone;
        return this;
    }

    public ClienteBuilder logradouro(String logradouro) {
        this.logradouro = logradouro;
        return this;
    }

    public ClienteBuilder numero(String numero) {
        this.numero = numero;
        return this;
    }

    public ClienteBuilder bairro(String bairro) {
        this.bairro = bairro;
        return this;
    }

    public ClienteBuilder cidade(String cidade) {
        this.cidade = cidade;
        return this;
    }

    public ClienteBuilder dataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
        return this;
    }

    public Cliente build() {
        if (nome == null || nome.trim().isEmpty()) {
            throw new ValidationException("Nome do cliente é obrigatório.");
        }
        if (nome.length() > 120) {
            throw new ValidationException("Nome não pode exceder 120 caracteres.");
        }
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new ValidationException("CPF é obrigatório.");
        }
        String cleanCpf = cpf.replaceAll("\\D", "");
        if (cleanCpf.length() != 11) {
            throw new ValidationException("CPF deve conter exatamente 11 dígitos numéricos.");
        }
        if (telefone == null || telefone.trim().isEmpty()) {
            throw new ValidationException("Telefone é obrigatório.");
        }
        if (logradouro == null || logradouro.trim().isEmpty() ||
            numero == null || numero.trim().isEmpty() ||
            bairro == null || bairro.trim().isEmpty() ||
            cidade == null || cidade.trim().isEmpty()) {
            throw new ValidationException("Endereço completo (logradouro, número, bairro e cidade) é obrigatório.");
        }

        Cliente cliente = new Cliente();
        cliente.setId(id);
        cliente.setNome(nome.trim());
        cliente.setCpf(cleanCpf);
        cliente.setEmail(email != null ? email.trim() : null);
        cliente.setTelefone(telefone.trim());
        cliente.setLogradouro(logradouro.trim());
        cliente.setNumero(numero.trim());
        cliente.setBairro(bairro.trim());
        cliente.setCidade(cidade.trim());
        cliente.setDataCadastro(dataCadastro != null ? dataCadastro : LocalDateTime.now());

        return cliente;
    }
}
