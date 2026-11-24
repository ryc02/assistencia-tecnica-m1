package com.assistencia.dao.jdbc;

/** Comandos SQL e colunas usados pelo OrdemServicoDAOJDBC. */
final class SqlOrdemServico {
    static final String INSERIR = "INSERT INTO ordem_servico (orcamento_id, responsavel, prioridade, status, "
            + "data_abertura, previsao_conclusao, data_conclusao, observacoes, prazo_garantia_dias) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    static final String ATUALIZAR = "UPDATE ordem_servico SET responsavel = ?, prioridade = ?, status = ?, "
            + "previsao_conclusao = ?, data_conclusao = ?, observacoes = ?, prazo_garantia_dias = ? WHERE id = ?";
    static final String[] CHAVES_INSERIR = {"orcamentoId", "responsavel", "prioridade", "status", "dataAbertura",
        "previsaoConclusao", "dataConclusao", "observacoes", "prazoGarantiaDias"};
    static final String[] CHAVES_ATUALIZAR = {"responsavel", "prioridade", "status", "previsaoConclusao",
        "dataConclusao", "observacoes", "prazoGarantiaDias", "id"};
    static final String POR_ID = "SELECT * FROM ordem_servico WHERE id = ?";
    static final String POR_ID_PARA_ATUALIZAR = "SELECT * FROM ordem_servico WHERE id = ? FOR UPDATE";
    static final String POR_ORCAMENTO = "SELECT * FROM ordem_servico WHERE orcamento_id = ?";
    static final String TODAS = "SELECT * FROM ordem_servico ORDER BY id DESC";

    private SqlOrdemServico() { }
}
