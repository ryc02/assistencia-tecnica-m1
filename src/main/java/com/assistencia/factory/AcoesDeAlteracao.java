package com.assistencia.factory;

import java.util.Set;

/** Ações que alteram dados e por isso só podem ser enviadas via HTTP POST (com token CSRF). */
public final class AcoesDeAlteracao {
    private static final Set<String> ACOES = Set.of(
        "cliente.inserir", "cliente.atualizar", "cliente.excluir",
        "equipamento.inserir", "equipamento.atualizar", "equipamento.excluir",
        "orcamento.inserir", "orcamento.atualizar", "orcamento.excluir", "orcamento.aprovar", "orcamento.recusar",
        "ordemServico.inserir", "ordemServico.atualizar", "ordemServico.excluir", "ordemServico.iniciar",
        "ordemServico.concluir", "ordemServico.cancelar",
        "fichaTecnica.atualizar", "efetuarLogin", "usuario.inserir", "usuario.excluir"
    );

    private AcoesDeAlteracao() { }

    public static boolean exigePost(String acao) {
        return ACOES.contains(acao);
    }
}
