package com.assistencia.builder;

import com.assistencia.dados.Campos;
import com.assistencia.model.orcamento.Orcamento;
import com.assistencia.model.ordem.*;
import java.time.LocalDateTime;

/** [Builder - Aula 06] Constrói OrdemServico validando responsável, garantia e previsão de conclusão. */
public class OrdemServicoBuilder extends BuilderDeEntidade<OrdemServicoBuilder, OrdemServico> {
    public OrdemServicoBuilder orcamento(Orcamento orcamento) {
        return com("orcamento", orcamento);
    }

    public OrdemServicoBuilder status(StatusOrdem status) {
        return com("status", status);
    }

    public OrdemServicoBuilder responsavel(String responsavel) {
        return com("responsavel", responsavel);
    }

    public OrdemServicoBuilder prioridade(Prioridade prioridade) {
        return com("prioridade", prioridade);
    }

    public OrdemServicoBuilder dataAbertura(LocalDateTime dataAbertura) {
        return com("dataAbertura", dataAbertura);
    }

    public OrdemServicoBuilder dataConclusao(LocalDateTime dataConclusao) {
        return com("dataConclusao", dataConclusao);
    }

    @Override
    protected OrdemServicoBuilder proprio() {
        return this;
    }

    @Override
    protected OrdemServico construir(Campos campos) {
        return OrdemServico.de(campos);
    }
}
