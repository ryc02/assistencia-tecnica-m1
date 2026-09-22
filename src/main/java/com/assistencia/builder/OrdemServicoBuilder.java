package com.assistencia.builder;

import com.assistencia.exception.ValidationException;
import com.assistencia.model.Orcamento;
import com.assistencia.model.OrdemServico;
import com.assistencia.model.Prioridade;
import com.assistencia.model.StatusOrdem;

import java.time.LocalDateTime;

/**
 * [Requisito 8: Padrão Builder]
 * OrdemServicoBuilder: Constrói OrdemServico validando regras de prazo e responsável.
 */
public class OrdemServicoBuilder {

    private Long id;
    private Orcamento orcamento;
    private String responsavel;
    private Prioridade prioridade = Prioridade.NORMAL;
    private StatusOrdem status = StatusOrdem.ABERTA;
    private LocalDateTime dataAbertura;
    private LocalDateTime previsaoConclusao;
    private LocalDateTime dataConclusao;
    private String observacoes;
    private Integer prazoGarantiaDias = 90;

    public OrdemServicoBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public OrdemServicoBuilder orcamento(Orcamento orcamento) {
        this.orcamento = orcamento;
        return this;
    }

    public OrdemServicoBuilder responsavel(String responsavel) {
        this.responsavel = responsavel;
        return this;
    }

    public OrdemServicoBuilder prioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
        return this;
    }

    public OrdemServicoBuilder status(StatusOrdem status) {
        this.status = status;
        return this;
    }

    public OrdemServicoBuilder dataAbertura(LocalDateTime dataAbertura) {
        this.dataAbertura = dataAbertura;
        return this;
    }

    public OrdemServicoBuilder previsaoConclusao(LocalDateTime previsaoConclusao) {
        this.previsaoConclusao = previsaoConclusao;
        return this;
    }

    public OrdemServicoBuilder dataConclusao(LocalDateTime dataConclusao) {
        this.dataConclusao = dataConclusao;
        return this;
    }

    public OrdemServicoBuilder observacoes(String observacoes) {
        this.observacoes = observacoes;
        return this;
    }

    public OrdemServicoBuilder prazoGarantiaDias(Integer prazoGarantiaDias) {
        this.prazoGarantiaDias = prazoGarantiaDias;
        return this;
    }

    public OrdemServico build() {
        if (orcamento == null) {
            throw new ValidationException("Orçamento é obrigatório para gerar a ordem de serviço.");
        }
        if (responsavel == null || responsavel.trim().isEmpty()) {
            throw new ValidationException("Responsável técnico é obrigatório.");
        }
        if (responsavel.length() > 120) {
            throw new ValidationException("Nome do responsável não pode exceder 120 caracteres.");
        }
        if (prazoGarantiaDias != null && prazoGarantiaDias < 0) {
            throw new ValidationException("Prazo de garantia não pode ser negativo.");
        }

        LocalDateTime abertura = dataAbertura != null ? dataAbertura : LocalDateTime.now();
        if (previsaoConclusao != null && previsaoConclusao.isBefore(abertura)) {
            throw new ValidationException("Previsão de conclusão não pode ser anterior à data de abertura.");
        }
        if (status == StatusOrdem.CONCLUIDA && dataConclusao == null) {
            dataConclusao = LocalDateTime.now();
        }

        OrdemServico os = new OrdemServico();
        os.setId(id);
        os.setOrcamento(orcamento);
        os.setResponsavel(responsavel.trim());
        os.setPrioridade(prioridade != null ? prioridade : Prioridade.NORMAL);
        os.setStatus(status != null ? status : StatusOrdem.ABERTA);
        os.setDataAbertura(abertura);
        os.setPrevisaoConclusao(previsaoConclusao);
        os.setDataConclusao(dataConclusao);
        os.setObservacoes(observacoes != null ? observacoes.trim() : null);
        os.setPrazoGarantiaDias(prazoGarantiaDias != null ? prazoGarantiaDias : 90);

        return os;
    }
}
