package com.assistencia.api.recurso;

import com.assistencia.service.AprovacaoService;

/** DELETE /api/ordens-servico/{id}: exclui ordem ABERTA, remove a ficha e devolve o orçamento a PENDENTE. */
public class ExclusaoDeOrdemRecurso implements RecursoExcluivel {
    private final AprovacaoService aprovacao;

    public ExclusaoDeOrdemRecurso(AprovacaoService aprovacao) {
        this.aprovacao = aprovacao;
    }

    @Override
    public void excluir(Long id) throws Exception {
        aprovacao.excluirOrdemAberta(id);
    }
}
