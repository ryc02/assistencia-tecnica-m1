package com.assistencia.model;

import java.time.LocalDateTime;

/**
 * [Requisito 5: Entidade Equipamento]
 * Representa um equipamento pertencente a um cliente.
 * Contém exatamente 10 atributos persistidos/referenciados conforme especificação do projeto.
 */
public class Equipamento {

    // [Atributo 1/10] Identificador gerado pelo banco
    private Long id;

    // [Atributo 2/10] Referência obrigatória a cliente existente (Relacionamento 1:N)
    private Cliente cliente;

    // [Atributo 3/10] Tipo de equipamento (ex: Notebook, Impressora, etc.)
    private String tipo;

    // [Atributo 4/10] Marca do equipamento (até 60 caracteres)
    private String marca;

    // [Atributo 5/10] Modelo do equipamento (até 80 caracteres)
    private String modelo;

    // [Atributo 6/10] Número de série (opcional, até 100 caracteres)
    private String numeroSerie;

    // [Atributo 7/10] Cor do equipamento (opcional, até 40 caracteres)
    private String cor;

    // [Atributo 8/10] Voltagem (110V, 127V, 220V, BIVOLT ou NAO_APLICAVEL)
    private String voltagem;

    // [Atributo 9/10] Descrição adicional (opcional, até 1000 caracteres)
    private String descricao;

    // [Atributo 10/10] Data e hora de cadastro gerada pelo servidor
    private LocalDateTime dataCadastro;

    public Equipamento() {
    }

    public Equipamento(Long id, Cliente cliente, String tipo, String marca, String modelo,
                       String numeroSerie, String cor, String voltagem, String descricao, LocalDateTime dataCadastro) {
        this.id = id;
        this.cliente = cliente;
        this.tipo = tipo;
        this.marca = marca;
        this.modelo = modelo;
        this.numeroSerie = numeroSerie;
        this.cor = cor;
        this.voltagem = voltagem;
        this.descricao = descricao;
        this.dataCadastro = dataCadastro;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getVoltagem() {
        return voltagem;
    }

    public void setVoltagem(String voltagem) {
        this.voltagem = voltagem;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
}
