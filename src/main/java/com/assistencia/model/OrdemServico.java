package com.assistencia.model;

import java.time.LocalDateTime;

/**
 * [Requisito 5: Entidade OrdemServico]
 * Representa a Ordem de Serviço autorizada pela aprovação do orçamento.
 * Contém exatamente 10 atributos persistidos/referenciados conforme especificação do projeto.
 */
public class OrdemServico {

    // [Atributo 1/10] Identificador gerado pelo banco
    private Long id;

    // [Atributo 2/10] Referência obrigatória e única ao Orçamento aprovado (Relacionamento 1:1 com Orçamento)
    private Orcamento orcamento;

    // [Atributo 3/10] Nome do técnico responsável (até 120 caracteres)
    private String responsavel;

    // [Atributo 4/10] Prioridade de execução (BAIXA, NORMAL, ALTA)
    private Prioridade prioridade;

    // [Atributo 5/10] Estado do atendimento (ABERTA, EM_ANDAMENTO, CONCLUIDA, CANCELADA)
    private StatusOrdem status;

    // [Atributo 6/10] Data e hora de abertura gerada pelo servidor
    private LocalDateTime dataAbertura;

    // [Atributo 7/10] Previsão de conclusão do serviço
    private LocalDateTime previsaoConclusao;

    // [Atributo 8/10] Data e hora de conclusão (preenchida ao concluir)
    private LocalDateTime dataConclusao;

    // [Atributo 9/10] Observações operacionais do serviço
    private String observacoes;

    // [Atributo 10/10] Prazo de garantia em dias
    private Integer prazoGarantiaDias;

    public OrdemServico() {
        this.status = StatusOrdem.ABERTA;
        this.prioridade = Prioridade.NORMAL;
        this.prazoGarantiaDias = 90;
    }

    public OrdemServico(Long id, Orcamento orcamento, String responsavel, Prioridade prioridade,
                        StatusOrdem status, LocalDateTime dataAbertura, LocalDateTime previsaoConclusao,
                        LocalDateTime dataConclusao, String observacoes, Integer prazoGarantiaDias) {
        this.id = id;
        this.orcamento = orcamento;
        this.responsavel = responsavel;
        this.prioridade = prioridade;
        this.status = status;
        this.dataAbertura = dataAbertura;
        this.previsaoConclusao = previsaoConclusao;
        this.dataConclusao = dataConclusao;
        this.observacoes = observacoes;
        this.prazoGarantiaDias = prazoGarantiaDias;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Orcamento getOrcamento() {
        return orcamento;
    }

    public void setOrcamento(Orcamento orcamento) {
        this.orcamento = orcamento;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }

    public StatusOrdem getStatus() {
        return status;
    }

    public void setStatus(StatusOrdem status) {
        this.status = status;
    }

    public LocalDateTime getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(LocalDateTime dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public LocalDateTime getPrevisaoConclusao() {
        return previsaoConclusao;
    }

    public void setPrevisaoConclusao(LocalDateTime previsaoConclusao) {
        this.previsaoConclusao = previsaoConclusao;
    }

    public LocalDateTime getDataConclusao() {
        return dataConclusao;
    }

    public void setDataConclusao(LocalDateTime dataConclusao) {
        this.dataConclusao = dataConclusao;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Integer getPrazoGarantiaDias() {
        return prazoGarantiaDias;
    }

    public void setPrazoGarantiaDias(Integer prazoGarantiaDias) {
        this.prazoGarantiaDias = prazoGarantiaDias;
    }
}
