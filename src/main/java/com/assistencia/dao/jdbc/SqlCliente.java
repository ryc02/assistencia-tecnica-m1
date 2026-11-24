package com.assistencia.dao.jdbc;

/** Comandos SQL da tabela cliente (sempre executados com PreparedStatement). */
final class SqlCliente {
    static final String INSERIR = "INSERT INTO cliente (nome, cpf, email, telefone, logradouro, numero, "
            + "bairro, cidade, data_cadastro) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    static final String ATUALIZAR = "UPDATE cliente SET nome = ?, cpf = ?, email = ?, telefone = ?, "
            + "logradouro = ?, numero = ?, bairro = ?, cidade = ? WHERE id = ?";
    static final String[] CHAVES_INSERIR = {"nome", "cpf", "email", "telefone", "logradouro", "numero",
        "bairro", "cidade", "dataCadastro"};
    static final String[] CHAVES_ATUALIZAR = {"nome", "cpf", "email", "telefone", "logradouro", "numero",
        "bairro", "cidade", "id"};
    static final String POR_ID = "SELECT * FROM cliente WHERE id = ?";
    static final String POR_CPF = "SELECT * FROM cliente WHERE cpf = ?";
    static final String TODOS = "SELECT * FROM cliente ORDER BY nome ASC";
    static final String CONTAR_EQUIPAMENTOS = "SELECT COUNT(*) AS total FROM equipamento WHERE cliente_id = ?";

    private SqlCliente() { }
}
