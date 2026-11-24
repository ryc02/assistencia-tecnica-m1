package com.assistencia.dao.jdbc;

/** Comandos SQL e colunas usados pelo OrcamentoDAOJDBC. */
final class SqlOrcamento {
    static final String INSERIR = "INSERT INTO orcamento (equipamento_id, descricao_problema, diagnostico, "
            + "valor_pecas, valor_mao_de_obra, percentual_desconto, valor_total, status, data_criacao, adicionais) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    static final String ATUALIZAR = "UPDATE orcamento SET equipamento_id = ?, descricao_problema = ?, "
            + "diagnostico = ?, valor_pecas = ?, valor_mao_de_obra = ?, percentual_desconto = ?, valor_total = ?, "
            + "status = ?, adicionais = ? WHERE id = ?";
    static final String[] CHAVES_INSERIR = {"equipamentoId", "descricaoProblema", "diagnostico", "valorPecas",
        "valorMaoDeObra", "percentualDesconto", "valorTotal", "status", "dataCriacao", "adicionais"};
    static final String[] CHAVES_ATUALIZAR = {"equipamentoId", "descricaoProblema", "diagnostico", "valorPecas",
        "valorMaoDeObra", "percentualDesconto", "valorTotal", "status", "adicionais", "id"};
    static final String POR_ID = "SELECT * FROM orcamento WHERE id = ?";
    static final String POR_ID_PARA_ATUALIZAR = "SELECT * FROM orcamento WHERE id = ? FOR UPDATE";
    static final String TODOS = "SELECT * FROM orcamento ORDER BY id DESC";
    static final String POR_EQUIPAMENTO = "SELECT * FROM orcamento WHERE equipamento_id = ? ORDER BY id DESC";
    static final String CONTAR_ORDENS = "SELECT COUNT(*) AS total FROM ordem_servico WHERE orcamento_id = ?";

    private SqlOrcamento() { }
}
