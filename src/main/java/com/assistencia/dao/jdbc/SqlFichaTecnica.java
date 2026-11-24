package com.assistencia.dao.jdbc;

/** Comandos SQL e colunas usados pelo FichaTecnicaDAOJDBC. */
final class SqlFichaTecnica {
    static final String INSERIR = "INSERT INTO ficha_tecnica (ordem_servico_id, estado_conservacao, "
            + "acessorios_entregues, liga_normalmente, possui_avarias, descricao_avarias, teste_inicial, "
            + "observacoes_recebimento, data_registro) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    static final String ATUALIZAR = "UPDATE ficha_tecnica SET estado_conservacao = ?, acessorios_entregues = ?, "
            + "liga_normalmente = ?, possui_avarias = ?, descricao_avarias = ?, teste_inicial = ?, "
            + "observacoes_recebimento = ? WHERE id = ?";
    static final String[] CHAVES_INSERIR = {"ordemServicoId", "estadoConservacao", "acessoriosEntregues",
        "ligaNormalmente", "possuiAvarias", "descricaoAvarias", "testeInicial", "observacoesRecebimento",
        "dataRegistro"};
    static final String[] CHAVES_ATUALIZAR = {"estadoConservacao", "acessoriosEntregues", "ligaNormalmente",
        "possuiAvarias", "descricaoAvarias", "testeInicial", "observacoesRecebimento", "id"};
    static final String POR_ID = "SELECT * FROM ficha_tecnica WHERE id = ?";
    static final String POR_ORDEM = "SELECT * FROM ficha_tecnica WHERE ordem_servico_id = ?";
    static final String TODAS = "SELECT * FROM ficha_tecnica ORDER BY id DESC";

    private SqlFichaTecnica() { }
}
