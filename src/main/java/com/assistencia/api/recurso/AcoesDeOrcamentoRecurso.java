package com.assistencia.api.recurso;

import com.assistencia.command.Visao;
import com.assistencia.dados.Campos;
import com.assistencia.exception.NotFoundException;
import com.assistencia.model.orcamento.Orcamento;
import com.assistencia.model.ordem.OrdemServico;
import com.assistencia.service.*;
import java.util.Map;

/** DELETE /api/orcamentos/{id} e PATCH /api/orcamentos/{id}/aprovar (gera Ordem e Ficha) ou /recusar. */
public class AcoesDeOrcamentoRecurso implements RecursoExcluivel, RecursoComAcoes {
    private final GestaoOrcamentoService gestao;
    private final AprovacaoService aprovacao;

    public AcoesDeOrcamentoRecurso(GestaoOrcamentoService gestao, AprovacaoService aprovacao) {
        this.gestao = gestao;
        this.aprovacao = aprovacao;
    }

    @Override
    public void excluir(Long id) throws Exception {
        gestao.excluir(id);
    }

    @Override
    public Map<String, Object> executarAcao(Long id, String acao, Campos corpo) throws Exception {
        switch (acao) {
            case "aprovar":
                return aprovar(id, corpo);
            case "recusar":
                return recusar(id);
            default:
                throw new NotFoundException("Ação desconhecida para orçamento: " + acao);
        }
    }

    private Map<String, Object> aprovar(Long id, Campos corpo) throws Exception {
        OrdemServico ordem = aprovacao.aprovarOrcamento(id, corpo, corpo);
        return Visao.de(ordem);
    }

    private Map<String, Object> recusar(Long id) throws Exception {
        Orcamento recusado = gestao.recusar(id);
        return Visao.de(recusado);
    }
}
