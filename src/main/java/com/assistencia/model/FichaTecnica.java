package com.assistencia.model;

import java.time.LocalDateTime;

/**
 * [Requisito 5: Entidade FichaTecnica & Requisito 6: Multiplicidade 1:1]
 * Ficha de triagem e inspeção inicial associada exclusivamente a uma Ordem de Serviço.
 * Contém exatamente 10 atributos persistidos/referenciados conforme especificação do projeto.
 */
public class FichaTecnica {

    // [Atributo 1/10] Identificador gerado pelo banco
    private Long id;

    // [Atributo 2/10] Referência obrigatória e exclusiva à OrdemServico (Atende ao Requisito 1:1)
    private OrdemServico ordemServico;

    // [Atributo 3/10] Estado de conservação (NAO_AVALIADO, BOM, REGULAR, RUIM)
    private EstadoConservacao estadoConservacao;

    // [Atributo 4/10] Acessórios entregues junto com o equipamento (até 1000 caracteres)
    private String acessoriosEntregues;

    // [Atributo 5/10] Se o equipamento liga normalmente (Aceita null para "Não verificado")
    private Boolean ligaNormalmente;

    // [Atributo 6/10] Se possui avarias visíveis (Aceita null para "Não verificado")
    private Boolean possuiAvarias;

    // [Atributo 7/10] Descrição detalhada das avarias (obrigatória se possuiAvarias = true)
    private String descricaoAvarias;

    // [Atributo 8/10] Resultado dos testes iniciais
    private String testeInicial;

    // [Atributo 9/10] Observações registradas no momento da recepção
    private String observacoesRecebimento;

    // [Atributo 10/10] Data e hora de registro gerada pelo servidor
    private LocalDateTime dataRegistro;

    public FichaTecnica() {
        this.estadoConservacao = EstadoConservacao.NAO_AVALIADO;
    }

    public FichaTecnica(Long id, OrdemServico ordemServico, EstadoConservacao estadoConservacao,
                        String acessoriosEntregues, Boolean ligaNormalmente, Boolean possuiAvarias,
                        String descricaoAvarias, String testeInicial, String observacoesRecebimento,
                        LocalDateTime dataRegistro) {
        this.id = id;
        this.ordemServico = ordemServico;
        this.estadoConservacao = estadoConservacao;
        this.acessoriosEntregues = acessoriosEntregues;
        this.ligaNormalmente = ligaNormalmente;
        this.possuiAvarias = possuiAvarias;
        this.descricaoAvarias = descricaoAvarias;
        this.testeInicial = testeInicial;
        this.observacoesRecebimento = observacoesRecebimento;
        this.dataRegistro = dataRegistro;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrdemServico getOrdemServico() {
        return ordemServico;
    }

    public void setOrdemServico(OrdemServico ordemServico) {
        this.ordemServico = ordemServico;
    }

    public EstadoConservacao getEstadoConservacao() {
        return estadoConservacao;
    }

    public void setEstadoConservacao(EstadoConservacao estadoConservacao) {
        this.estadoConservacao = estadoConservacao;
    }

    public String getAcessoriosEntregues() {
        return acessoriosEntregues;
    }

    public void setAcessoriosEntregues(String acessoriosEntregues) {
        this.acessoriosEntregues = acessoriosEntregues;
    }

    public Boolean getLigaNormalmente() {
        return ligaNormalmente;
    }

    public void setLigaNormalmente(Boolean ligaNormalmente) {
        this.ligaNormalmente = ligaNormalmente;
    }

    public Boolean getPossuiAvarias() {
        return possuiAvarias;
    }

    public void setPossuiAvarias(Boolean possuiAvarias) {
        this.possuiAvarias = possuiAvarias;
    }

    public String getDescricaoAvarias() {
        return descricaoAvarias;
    }

    public void setDescricaoAvarias(String descricaoAvarias) {
        this.descricaoAvarias = descricaoAvarias;
    }

    public String getTesteInicial() {
        return testeInicial;
    }

    public void setTesteInicial(String testeInicial) {
        this.testeInicial = testeInicial;
    }

    public String getObservacoesRecebimento() {
        return observacoesRecebimento;
    }

    public void setObservacoesRecebimento(String observacoesRecebimento) {
        this.observacoesRecebimento = observacoesRecebimento;
    }

    public LocalDateTime getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(LocalDateTime dataRegistro) {
        this.dataRegistro = dataRegistro;
    }
}
