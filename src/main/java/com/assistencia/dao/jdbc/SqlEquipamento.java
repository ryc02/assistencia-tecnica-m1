package com.assistencia.dao.jdbc;

/** Comandos SQL e colunas usados pelo EquipamentoDAOJDBC. */
final class SqlEquipamento {
    static final String INSERIR = "INSERT INTO equipamento (cliente_id, tipo, marca, modelo, numero_serie, "
            + "cor, voltagem, descricao, data_cadastro) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    static final String ATUALIZAR = "UPDATE equipamento SET cliente_id = ?, tipo = ?, marca = ?, modelo = ?, "
            + "numero_serie = ?, cor = ?, voltagem = ?, descricao = ? WHERE id = ?";
    static final String[] CHAVES_INSERIR = {"clienteId", "tipo", "marca", "modelo", "numeroSerie", "cor",
        "voltagem", "descricao", "dataCadastro"};
    static final String[] CHAVES_ATUALIZAR = {"clienteId", "tipo", "marca", "modelo", "numeroSerie", "cor",
        "voltagem", "descricao", "id"};
    static final String POR_ID = "SELECT * FROM equipamento WHERE id = ?";
    static final String TODOS = "SELECT * FROM equipamento ORDER BY id DESC";
    static final String POR_CLIENTE = "SELECT * FROM equipamento WHERE cliente_id = ? ORDER BY id DESC";
    static final String CONTAR_ORCAMENTOS = "SELECT COUNT(*) AS total FROM orcamento WHERE equipamento_id = ?";

    private SqlEquipamento() { }
}
