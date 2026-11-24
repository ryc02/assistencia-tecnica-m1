package com.assistencia.model.ordem;

import com.assistencia.dados.Campos;
import com.assistencia.model.EntidadeComId;
import com.assistencia.model.valor.Identificador;

/** [Entidade] Ordem de serviço gerada pela aprovação do orçamento (Orçamento 1:1 Ordem 1:1 Ficha Técnica). */
public final class OrdemServico extends EntidadeComId {
    private final Atendimento atendimento;

    private OrdemServico(Identificador identificador, Atendimento atendimento) {
        super(identificador);
        this.atendimento = atendimento;
    }

    public static OrdemServico de(Campos dados) {
        Identificador identificador = Identificador.de(dados);
        return new OrdemServico(identificador, Atendimento.de(dados));
    }

    /** Altera responsável, prioridade, previsão, observações e garantia (só ABERTA ou EM_ANDAMENTO). */
    public void reprogramar(Campos dados) {
        atendimento.reprogramar(dados);
    }

    /** Iniciar exige ABERTA, concluir exige EM_ANDAMENTO e cancelar não aceita estados finais. */
    public void mudar(MudancaDeOrdem mudanca) {
        atendimento.mudar(mudanca);
    }

    public void exigir(ExigenciaDaOrdem exigencia) {
        atendimento.exigir(exigencia);
    }

    @Override
    protected void exportarDados(Campos destino) {
        atendimento.exportar(destino);
    }
}
