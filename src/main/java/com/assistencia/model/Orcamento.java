package com.assistencia.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

/**
 * [Requisito 5: Entidade Orcamento]
 * Representa um orçamento de serviços e peças para um equipamento.
 * Contém exatamente 10 atributos persistidos/referenciados conforme especificação.
 */
public class Orcamento {

    // [Atributo 1/10] Identificador gerado pelo banco
    private Long id;

    // [Atributo 2/10] Referência obrigatória ao equipamento (Relacionamento 1:N)
    private Equipamento equipamento;

    // [Atributo 3/10] Descrição do problema relatado pelo cliente (até 2000 caracteres)
    private String descricaoProblema;

    // [Atributo 4/10] Diagnóstico técnico (obrigatório para aprovação)
    private String diagnostico;

    // [Atributo 5/10] Valor estimado de peças (não negativo, 2 casas decimais)
    private BigDecimal valorPecas;

    // [Atributo 6/10] Valor estimado de mão de obra (não negativo, 2 casas decimais)
    private BigDecimal valorMaoDeObra;

    // [Atributo 7/10] Percentual de desconto (entre 0 e 100)
    private BigDecimal percentualDesconto;

    // [Atributo 8/10] Valor total calculado pelo servidor
    private BigDecimal valorTotal;

    // [Atributo 9/10] Estado atual do orçamento (PENDENTE, APROVADO, RECUSADO)
    private StatusOrcamento status;

    // [Atributo 10/10] Data e hora de criação gerada pelo servidor
    private LocalDateTime dataCriacao;

    public Orcamento() {
        this.valorPecas = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        this.valorMaoDeObra = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        this.percentualDesconto = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        this.valorTotal = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        this.status = StatusOrcamento.PENDENTE;
    }

    public Orcamento(Long id, Equipamento equipamento, String descricaoProblema, String diagnostico,
                     BigDecimal valorPecas, BigDecimal valorMaoDeObra, BigDecimal percentualDesconto,
                     BigDecimal valorTotal, StatusOrcamento status, LocalDateTime dataCriacao) {
        this.id = id;
        this.equipamento = equipamento;
        this.descricaoProblema = descricaoProblema;
        this.diagnostico = diagnostico;
        this.valorPecas = valorPecas != null ? valorPecas.setScale(2, RoundingMode.HALF_UP) : BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        this.valorMaoDeObra = valorMaoDeObra != null ? valorMaoDeObra.setScale(2, RoundingMode.HALF_UP) : BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        this.percentualDesconto = percentualDesconto != null ? percentualDesconto.setScale(2, RoundingMode.HALF_UP) : BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        this.status = status;
        this.dataCriacao = dataCriacao;
        this.valorTotal = calcularTotal();
    }

    /**
     * [Requisito 7: Regra de Cálculo]
     * subtotal = valorPecas + valorMaoDeObra
     * valorTotal = subtotal * (1 - percentualDesconto / 100)
     * Arredondamento HALF_UP com 2 casas decimais.
     */
    public BigDecimal calcularTotal() {
        BigDecimal pecas = valorPecas != null ? valorPecas : BigDecimal.ZERO;
        BigDecimal maoObra = valorMaoDeObra != null ? valorMaoDeObra : BigDecimal.ZERO;
        BigDecimal desconto = percentualDesconto != null ? percentualDesconto : BigDecimal.ZERO;

        BigDecimal subtotal = pecas.add(maoObra);
        BigDecimal fatorDesconto = BigDecimal.ONE.subtract(desconto.divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP));
        return subtotal.multiply(fatorDesconto).setScale(2, RoundingMode.HALF_UP);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Equipamento getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(Equipamento equipamento) {
        this.equipamento = equipamento;
    }

    public String getDescricaoProblema() {
        return descricaoProblema;
    }

    public void setDescricaoProblema(String descricaoProblema) {
        this.descricaoProblema = descricaoProblema;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public BigDecimal getValorPecas() {
        return valorPecas;
    }

    public void setValorPecas(BigDecimal valorPecas) {
        this.valorPecas = valorPecas;
    }

    public BigDecimal getValorMaoDeObra() {
        return valorMaoDeObra;
    }

    public void setValorMaoDeObra(BigDecimal valorMaoDeObra) {
        this.valorMaoDeObra = valorMaoDeObra;
    }

    public BigDecimal getPercentualDesconto() {
        return percentualDesconto;
    }

    public void setPercentualDesconto(BigDecimal percentualDesconto) {
        this.percentualDesconto = percentualDesconto;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public StatusOrcamento getStatus() {
        return status;
    }

    public void setStatus(StatusOrcamento status) {
        this.status = status;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
