CREATE TABLE IF NOT EXISTS usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    cargo VARCHAR(50) NOT NULL
);

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

-- Cliente 1:N Equipamento
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

-- Equipamento 1:N Orçamento
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
    adicionais VARCHAR(500),
    CONSTRAINT fk_orcamento_equipamento FOREIGN KEY (equipamento_id) REFERENCES equipamento(id)
);

-- Bancos da M1: adiciona a coluna dos adicionais (Decorator)
ALTER TABLE orcamento ADD COLUMN IF NOT EXISTS adicionais VARCHAR(500);

-- Orçamento 1:1 Ordem de Serviço
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

-- Ordem de Serviço 1:1 Ficha Técnica
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

-- Bancos da M1: troca as senhas iniciais em texto puro pelo hash PBKDF2
UPDATE usuario SET senha = '$pbkdf2$10000$w8s5xuIiGtrngOetQAKWiw==$OJOPBoeqLVUvpAFYRwnT8H8vWGqqJZ3fdt3BQmrAtFk=' WHERE email = 'admin@m1.com' AND senha = 'admin123';
UPDATE usuario SET senha = '$pbkdf2$10000$7tFc/Xi1m+f5O5hvNNSUzQ==$80QeXUuSU2NmQJWDn//1dzP8rFnqwAmcG1yQwzu4z7M=' WHERE email = 'tecnico@m1.com' AND senha = '123456';
