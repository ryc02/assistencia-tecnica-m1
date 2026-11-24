package com.assistencia.api.recurso;

import com.assistencia.command.Visao;
import com.assistencia.dados.Campos;
import com.assistencia.exception.NotFoundException;
import com.assistencia.model.ordem.OrdemServico;
import com.assistencia.service.OrdemServicoService;
import java.util.Map;

/** Recurso REST: PATCH /api/ordens-servico/{id}/iniciar | concluir | cancelar (transições de estado). */
public class AcoesDeOrdemServicoRecurso implements RecursoComAcoes {
    private final OrdemServicoService servico;

    public AcoesDeOrdemServicoRecurso(OrdemServicoService servico) {
        this.servico = servico;
    }

    @Override
    public Map<String, Object> executarAcao(Long id, String acao, Campos corpo) throws Exception {
        switch (acao) {
            case "iniciar":
                return visao(servico.iniciar(id));
            case "concluir":
                return visao(servico.concluir(id));
            case "cancelar":
                return visao(servico.cancelar(id));
            default:
                throw new NotFoundException("Ação desconhecida para ordem de serviço: " + acao);
        }
    }

    private Map<String, Object> visao(OrdemServico ordem) {
        return Visao.de(ordem);
    }
}
