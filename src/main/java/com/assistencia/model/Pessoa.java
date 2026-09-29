package com.assistencia.model;

/**
 * [Requisito POO: Herança]
 * Superclasse abstrata que representa qualquer pessoa no sistema.
 * Encapsula os atributos comuns entre Cliente e Usuario,
 * eliminando duplicação de código (princípio DRY).
 *
 * Diagrama UML: Herança (seta sólida) → Pessoa
 *   Cliente ──────────────► Pessoa
 *   Usuario ──────────────► Pessoa
 */
public abstract class Pessoa {

    protected Long id;
    protected String nome;
    protected String email;

    public Pessoa() {}

    public Pessoa(Long id, String nome, String email) {
        this.id    = id;
        this.nome  = nome;
        this.email = email;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    /**
     * Representação textual padronizada para JSP e logs.
     */
    @Override
    public String toString() {
        return nome + " <" + email + ">";
    }
}
