package com.assistencia.model;

/**
 * [Herança] Usuário do sistema (operador interno).
 * Herda id, nome e email de Pessoa (PDF Prof. Toledo §5).
 * Acrescenta os atributos específicos de acesso: senha e cargo.
 */
public class Usuario extends Pessoa {

    private String senha;
    private String cargo;

    public Usuario() {}

    public Usuario(Long id, String nome, String email, String senha, String cargo) {
        super(id, nome, email);   // delega para Pessoa
        this.senha = senha;
        this.cargo = cargo;
    }

    // getId(), setId(), getNome(), setNome(), getEmail(), setEmail() → herdados de Pessoa

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }
}

