-- ============================================================================
-- [Requisito: Banco de Dados & Integridade] Schema SQL para a Assistência Técnica
-- Define 5 tabelas com restrições NOT NULL, UNIQUE e Foreign Keys exigidas
-- ============================================================================

CREATE TABLE IF NOT EXISTS cliente (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(120) NOT NULL,
    cpf VARCHAR(11) NOT NULL UNIQUE,
    email VARCHAR(254),
    telefone VARCHAR(20) NOT NULL,
    logradouro VARCHAR(150) NOT NULL,
    numero VARCHAR(20) NOT NULL,
    bairro VARCHAR(80) NOT NULL,
    cidade VARCHAR(80) NOT NULL,
    data_cadastro TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- [Requisito 6] Associação Cliente (1) -> Equipamento (0..N)
CREATE TABLE IF NOT EXISTS equipamento (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cliente_id BIGINT NOT NULL,
    tipo VARCHAR(60) NOT NULL,
    marca VARCHAR(60) NOT NULL,
    modelo VARCHAR(80) NOT NULL,
    numero_serie VARCHAR(100),
    cor VARCHAR(40),
    voltagem VARCHAR(20) NOT NULL,
    descricao VARCHAR(1000),
    data_cadastro TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_equipamento_cliente FOREIGN KEY (cliente_id) REFERENCES cliente(id)
);

-- [Requisito 6] Associação Equipamento (1) -> Orcamento (0..N)
CREATE TABLE IF NOT EXISTS orcamento (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    equipamento_id BIGINT NOT NULL,
    descricao_problema VARCHAR(2000) NOT NULL,
    diagnostico VARCHAR(2000),
    valor_pecas DECIMAL(12,2) NOT NULL DEFAULT 0.00,
    valor_mao_de_obra DECIMAL(12,2) NOT NULL DEFAULT 0.00,
    percentual_desconto DECIMAL(5,2) NOT NULL DEFAULT 0.00,
    valor_total DECIMAL(12,2) NOT NULL DEFAULT 0.00,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDENTE',
    data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_orcamento_equipamento FOREIGN KEY (equipamento_id) REFERENCES equipamento(id)
);

-- [Requisito 6] Associação Orcamento (1) -> OrdemServico (0..1) (orcamento_id UNIQUE e NOT NULL)
CREATE TABLE IF NOT EXISTS ordem_servico (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    orcamento_id BIGINT NOT NULL UNIQUE,
    responsavel VARCHAR(120) NOT NULL,
    prioridade VARCHAR(20) NOT NULL DEFAULT 'NORMAL',
    status VARCHAR(20) NOT NULL DEFAULT 'ABERTA',
    data_abertura TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    previsao_conclusao TIMESTAMP,
    data_conclusao TIMESTAMP,
    observacoes VARCHAR(2000),
    prazo_garantia_dias INT DEFAULT 90,
    CONSTRAINT fk_ordem_orcamento FOREIGN KEY (orcamento_id) REFERENCES orcamento(id)
);

-- [Requisito 6 / 1:1] Associação OrdemServico (1) <-> FichaTecnica (1) (ordem_servico_id UNIQUE e NOT NULL)
CREATE TABLE IF NOT EXISTS ficha_tecnica (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ordem_servico_id BIGINT NOT NULL UNIQUE,
    estado_conservacao VARCHAR(30) NOT NULL DEFAULT 'NAO_AVALIADO',
    acessorios_entregues VARCHAR(1000),
    liga_normalmente BOOLEAN,
    possui_avarias BOOLEAN,
    descricao_avarias VARCHAR(2000),
    teste_inicial VARCHAR(2000),
    observacoes_recebimento VARCHAR(2000),
    data_registro TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_ficha_ordem FOREIGN KEY (ordem_servico_id) REFERENCES ordem_servico(id) ON DELETE CASCADE
);
