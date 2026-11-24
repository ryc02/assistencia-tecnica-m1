package com.assistencia.builder;

import com.assistencia.dados.Campos;
import com.assistencia.model.equipamento.Equipamento;
import com.assistencia.model.orcamento.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/** [Builder - Aula 06] Constrói Orcamento validando valores, desconto e adicionais (Decorator). */
public class OrcamentoBuilder extends BuilderDeEntidade<OrcamentoBuilder, Orcamento> {
    public OrcamentoBuilder equipamento(Equipamento equipamento) {
        return com("equipamento", equipamento);
    }

    public OrcamentoBuilder status(StatusOrcamento status) {
        return com("status", status);
    }

    public OrcamentoBuilder dataCriacao(LocalDateTime dataCriacao) {
        return com("dataCriacao", dataCriacao);
    }

    public OrcamentoBuilder descricaoProblema(String descricaoProblema) {
        return com("descricaoProblema", descricaoProblema);
    }

    public OrcamentoBuilder valorPecas(BigDecimal valorPecas) {
        return com("valorPecas", valorPecas);
    }

    public OrcamentoBuilder valorMaoDeObra(BigDecimal valorMaoDeObra) {
        return com("valorMaoDeObra", valorMaoDeObra);
    }

    public OrcamentoBuilder adicionais(List<String> adicionais) {
        return com("adicionais", adicionais);
    }

    @Override
    protected OrcamentoBuilder proprio() {
        return this;
    }

    @Override
    protected Orcamento construir(Campos campos) {
        return Orcamento.de(campos);
    }
}
