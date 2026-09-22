package com.assistencia.builder;

import com.assistencia.exception.ValidationException;
import com.assistencia.model.EstadoConservacao;
import com.assistencia.model.FichaTecnica;
import com.assistencia.model.OrdemServico;

import java.time.LocalDateTime;

/**
 * [Requisito 8: Padrão Builder]
 * FichaTecnicaBuilder: Constrói FichaTecnica associada à OrdemServico com validações.
 */
public class FichaTecnicaBuilder {

    private Long id;
    private OrdemServico ordemServico;
    private EstadoConservacao estadoConservacao = EstadoConservacao.NAO_AVALIADO;
    private String acessoriosEntregues;
    private Boolean ligaNormalmente;
    private Boolean possuiAvarias;
    private String descricaoAvarias;
    private String testeInicial;
    private String observacoesRecebimento;
    private LocalDateTime dataRegistro;

    public FichaTecnicaBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public FichaTecnicaBuilder ordemServico(OrdemServico ordemServico) {
        this.ordemServico = ordemServico;
        return this;
    }

    public FichaTecnicaBuilder estadoConservacao(EstadoConservacao estadoConservacao) {
        this.estadoConservacao = estadoConservacao;
        return this;
    }

    public FichaTecnicaBuilder acessoriosEntregues(String acessoriosEntregues) {
        this.acessoriosEntregues = acessoriosEntregues;
        return this;
    }

    public FichaTecnicaBuilder ligaNormalmente(Boolean ligaNormalmente) {
        this.ligaNormalmente = ligaNormalmente;
        return this;
    }

    public FichaTecnicaBuilder possuiAvarias(Boolean possuiAvarias) {
        this.possuiAvarias = possuiAvarias;
        return this;
    }

    public FichaTecnicaBuilder descricaoAvarias(String descricaoAvarias) {
        this.descricaoAvarias = descricaoAvarias;
        return this;
    }

    public FichaTecnicaBuilder testeInicial(String testeInicial) {
        this.testeInicial = testeInicial;
        return this;
    }

    public FichaTecnicaBuilder observacoesRecebimento(String observacoesRecebimento) {
        this.observacoesRecebimento = observacoesRecebimento;
        return this;
    }

    public FichaTecnicaBuilder dataRegistro(LocalDateTime dataRegistro) {
        this.dataRegistro = dataRegistro;
        return this;
    }

    public FichaTecnica build() {
        if (ordemServico == null) {
            throw new ValidationException("Ordem de serviço é obrigatória para a ficha técnica.");
        }
        if (Boolean.TRUE.equals(possuiAvarias) && (descricaoAvarias == null || descricaoAvarias.trim().isEmpty())) {
            throw new ValidationException("Descrição das avarias é obrigatória quando possuiAvarias = true.");
        }

        FichaTecnica ft = new FichaTecnica();
        ft.setId(id);
        ft.setOrdemServico(ordemServico);
        ft.setEstadoConservacao(estadoConservacao != null ? estadoConservacao : EstadoConservacao.NAO_AVALIADO);
        ft.setAcessoriosEntregues(acessoriosEntregues != null ? acessoriosEntregues.trim() : "Nenhum");
        ft.setLigaNormalmente(ligaNormalmente);
        ft.setPossuiAvarias(possuiAvarias);
        ft.setDescricaoAvarias(descricaoAvarias != null ? descricaoAvarias.trim() : null);
        ft.setTesteInicial(testeInicial != null ? testeInicial.trim() : "Não realizado");
        ft.setObservacoesRecebimento(observacoesRecebimento != null ? observacoesRecebimento.trim() : null);
        ft.setDataRegistro(dataRegistro != null ? dataRegistro : LocalDateTime.now());

        return ft;
    }
}
