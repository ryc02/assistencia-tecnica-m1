package com.assistencia.builder;

import com.assistencia.dados.Campos;
import com.assistencia.model.ficha.*;
import com.assistencia.model.ordem.OrdemServico;
import java.time.LocalDateTime;

/** [Builder - Aula 06] Constrói FichaTecnica vinculada à Ordem de Serviço, validando a descrição de avarias. */
public class FichaTecnicaBuilder extends BuilderDeEntidade<FichaTecnicaBuilder, FichaTecnica> {
    public FichaTecnicaBuilder ordemServico(OrdemServico ordemServico) {
        return com("ordemServico", ordemServico);
    }

    public FichaTecnicaBuilder dataRegistro(LocalDateTime dataRegistro) {
        return com("dataRegistro", dataRegistro);
    }

    public FichaTecnicaBuilder estadoConservacao(EstadoConservacao estadoConservacao) {
        return com("estadoConservacao", estadoConservacao);
    }

    public FichaTecnicaBuilder possuiAvarias(Boolean possuiAvarias) {
        return com("possuiAvarias", possuiAvarias);
    }

    public FichaTecnicaBuilder descricaoAvarias(String descricaoAvarias) {
        return com("descricaoAvarias", descricaoAvarias);
    }

    @Override
    protected FichaTecnicaBuilder proprio() {
        return this;
    }

    @Override
    protected FichaTecnica construir(Campos campos) {
        return FichaTecnica.de(campos);
    }
}
