package com.assistencia.model;

import java.time.LocalDateTime;

/**
 * [Requisito 5: Entidade Cliente — Herança]
 * Representa um cliente da assistência técnica.
 * Herda de Pessoa os atributos id, nome e email (padrão Herança — PDF Prof. Toledo §5).
 */
public class Cliente extends Pessoa {

    // id, nome e email são herdados de Pessoa

    // [Atributo 3/10] CPF fictício (11 dígitos, único)
    private String cpf;

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

    public Cliente() {}

    public Cliente(Long id, String nome, String cpf, String email, String telefone,
                   String logradouro, String numero, String bairro, String cidade, LocalDateTime dataCadastro) {
        super(id, nome, email);   // delega id, nome e email para Pessoa
        this.cpf = cpf;
        this.telefone = telefone;
        this.logradouro = logradouro;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.dataCadastro = dataCadastro;
    }

    // getId(), setId(), getNome(), setNome(), getEmail(), setEmail() → herdados de Pessoa

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

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
