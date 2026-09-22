package com.assistencia.model;

import java.time.LocalDateTime;

/**
 * [Requisito 5: Entidade Cliente]
 * Representa um cliente da assistência técnica.
 * Contém exatamente 10 atributos persistidos conforme especificação do projeto.
 */
public class Cliente {

    // [Atributo 1/10] Identificador gerado pelo banco
    private Long id;

    // [Atributo 2/10] Nome do cliente (até 120 caracteres)
    private String nome;

    // [Atributo 3/10] CPF fictício (11 dígitos, único)
    private String cpf;

    // [Atributo 4/10] E-mail do cliente (opcional, até 254 caracteres)
    private String email;

    // [Atributo 5/10] Telefone de contato (até 20 caracteres)
    private String telefone;

    // [Atributo 6/10] Logradouro do endereço (até 150 caracteres)
    private String logradouro;

    // [Atributo 7/10] Número do endereço (até 20 caracteres)
    private String numero;

    // [Atributo 8/10] Bairro do endereço (até 80 caracteres)
    private String bairro;

    // [Atributo 9/10] Cidade do endereço (até 80 caracteres)
    private String cidade;

    // [Atributo 10/10] Data e hora de cadastro gerada pelo servidor
    private LocalDateTime dataCadastro;

    public Cliente() {
    }

    public Cliente(Long id, String nome, String cpf, String email, String telefone,
                   String logradouro, String numero, String bairro, String cidade, LocalDateTime dataCadastro) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.logradouro = logradouro;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.dataCadastro = dataCadastro;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
}
