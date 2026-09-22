package com.assistencia.builder;

import com.assistencia.exception.ValidationException;
import com.assistencia.model.Equipamento;
import com.assistencia.model.Orcamento;
import com.assistencia.model.StatusOrcamento;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

/**
 * [Requisito 8: Padrão Builder]
 * OrcamentoBuilder: Constrói e valida objetos Orcamento garantindo a consistência do domínio.
 */
public class OrcamentoBuilder {

    private Long id;
    private Equipamento equipamento;
    private String descricaoProblema;
    private String diagnostico;
    private BigDecimal valorPecas = BigDecimal.ZERO;
    private BigDecimal valorMaoDeObra = BigDecimal.ZERO;
    private BigDecimal percentualDesconto = BigDecimal.ZERO;
    private StatusOrcamento status = StatusOrcamento.PENDENTE;
    private LocalDateTime dataCriacao;

    public OrcamentoBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public OrcamentoBuilder equipamento(Equipamento equipamento) {
        this.equipamento = equipamento;
        return this;
    }

    public OrcamentoBuilder descricaoProblema(String descricaoProblema) {
        this.descricaoProblema = descricaoProblema;
        return this;
    }

    public OrcamentoBuilder diagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
        return this;
    }

    public OrcamentoBuilder valorPecas(BigDecimal valorPecas) {
        this.valorPecas = valorPecas;
        return this;
    }

    public OrcamentoBuilder valorMaoDeObra(BigDecimal valorMaoDeObra) {
        this.valorMaoDeObra = valorMaoDeObra;
        return this;
    }

    public OrcamentoBuilder percentualDesconto(BigDecimal percentualDesconto) {
        this.percentualDesconto = percentualDesconto;
        return this;
    }

    public OrcamentoBuilder status(StatusOrcamento status) {
        this.status = status;
        return this;
    }

    public OrcamentoBuilder dataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
        return this;
    }

    /**
     * Build com validação completa conforme Seção 5 e Seção 7.
     */
    public Orcamento build() {
        if (equipamento == null) {
            throw new ValidationException("Equipamento é obrigatório para o orçamento.");
        }
        if (descricaoProblema == null || descricaoProblema.trim().isEmpty()) {
            throw new ValidationException("Descrição do problema é obrigatória.");
        }
        if (descricaoProblema.length() > 2000) {
            throw new ValidationException("Descrição do problema não pode exceder 2000 caracteres.");
        }
        if (diagnostico != null && diagnostico.length() > 2000) {
            throw new ValidationException("Diagnóstico não pode exceder 2000 caracteres.");
        }

        if (valorPecas == null || valorPecas.compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException("Valor de peças não pode ser negativo.");
        }
        if (valorMaoDeObra == null || valorMaoDeObra.compareTo(BigDecimal.ZERO) < 0) {
            throw new ValidationException("Valor de mão de obra não pode ser negativo.");
        }
        if (percentualDesconto == null || percentualDesconto.compareTo(BigDecimal.ZERO) < 0 || percentualDesconto.compareTo(new BigDecimal("100")) > 0) {
            throw new ValidationException("Percentual de desconto deve estar entre 0% e 100%.");
        }

        if (status == StatusOrcamento.APROVADO && (diagnostico == null || diagnostico.trim().isEmpty())) {
            throw new ValidationException("Diagnóstico é obrigatório para aprovar um orçamento.");
        }

        Orcamento orcamento = new Orcamento();
        orcamento.setId(id);
        orcamento.setEquipamento(equipamento);
        orcamento.setDescricaoProblema(descricaoProblema.trim());
        orcamento.setDiagnostico(diagnostico != null ? diagnostico.trim() : null);
        orcamento.setValorPecas(valorPecas.setScale(2, RoundingMode.HALF_UP));
        orcamento.setValorMaoDeObra(valorMaoDeObra.setScale(2, RoundingMode.HALF_UP));
        orcamento.setPercentualDesconto(percentualDesconto.setScale(2, RoundingMode.HALF_UP));
        orcamento.setStatus(status != null ? status : StatusOrcamento.PENDENTE);
        orcamento.setDataCriacao(dataCriacao != null ? dataCriacao : LocalDateTime.now());
        orcamento.setValorTotal(orcamento.calcularTotal());

        return orcamento;
    }
}
