-- ============================================================================
-- [Requisito: Dados de Demonstração Fictícios] População inicial realista
-- Cenários pré-carregados para o roteiro da apresentação (seção 12):
--   1. Clientes com equipamentos (1:N)
--   2. Orçamentos PENDENTES para aprovar ao vivo
--   3. Orçamento já APROVADO com Ordem e Ficha existentes
--   4. Orçamento RECUSADO para demonstrar estado final
-- ============================================================================

-- === Clientes ===
INSERT INTO cliente (id, nome, cpf, email, telefone, logradouro, numero, bairro, cidade, data_cadastro)
VALUES 
(1, 'Carlos Eduardo Silva', '12345678901', 'carlos.silva@emailficticio.com', '(47) 99888-1122', 'Rua das Flores', '150', 'Centro', 'Joinville', CURRENT_TIMESTAMP),
(2, 'Mariana Oliveira Souza', '98765432100', 'mariana.souza@emailficticio.com', '(47) 98765-4321', 'Avenida Brasil', '2450', 'America', 'Joinville', CURRENT_TIMESTAMP),
(3, 'Roberto Almeida Neto', '11122233344', 'roberto.neto@emailficticio.com', '(47) 99177-5566', 'Rua XV de Novembro', '320', 'Bucarein', 'Joinville', CURRENT_TIMESTAMP);

-- === Equipamentos (demonstra 1:N: Carlos tem 2 equipamentos, Mariana tem 1, Roberto tem 1) ===
INSERT INTO equipamento (id, cliente_id, tipo, marca, modelo, numero_serie, cor, voltagem, descricao, data_cadastro)
VALUES 
(1, 1, 'Notebook', 'Dell', 'Inspiron 15 3000', 'SN-DELL-98213', 'Cinza Prata', 'BIVOLT', 'Notebook esquentando e desligando sozinho apos 10 min de uso', CURRENT_TIMESTAMP),
(2, 1, 'Impressora', 'Epson', 'EcoTank L3250', 'SN-EPS-44129', 'Preta', '110V', 'Impressora nao puxa papel da bandeja principal', CURRENT_TIMESTAMP),
(3, 2, 'Smartphone', 'Samsung', 'Galaxy S21 Ultra', 'SN-SAMS-77812', 'Preto', 'NAO_APLICAVEL', 'Tela trincada e conector de carga frouxo', CURRENT_TIMESTAMP),
(4, 3, 'Desktop', 'Custom', 'Ryzen 5 5600 / RTX 3060', 'SN-CUST-20231', 'Preto', '220V', 'PC travando durante jogos pesados', CURRENT_TIMESTAMP);

-- === Orçamentos ===
-- Orçamento #1: PENDENTE (para aprovar durante a demonstração ao vivo)
INSERT INTO orcamento (id, equipamento_id, descricao_problema, diagnostico, valor_pecas, valor_mao_de_obra, percentual_desconto, valor_total, status, data_criacao)
VALUES 
(1, 1, 'Notebook desligando por superaquecimento', 'Troca de pasta termica e limpeza interna dos coolers', 40.00, 110.00, 10.00, 135.00, 'PENDENTE', CURRENT_TIMESTAMP);

-- Orçamento #2: PENDENTE (para demonstrar cenário de exclusão)
INSERT INTO orcamento (id, equipamento_id, descricao_problema, diagnostico, valor_pecas, valor_mao_de_obra, percentual_desconto, valor_total, status, data_criacao)
VALUES 
(2, 2, 'Impressora nao puxa papel', 'Substituicao do rolete tracionador de papel', 65.00, 85.00, 0.00, 150.00, 'PENDENTE', CURRENT_TIMESTAMP);

-- Orçamento #3: APROVADO (pré-aprovado para demonstrar ordem/ficha existentes)
INSERT INTO orcamento (id, equipamento_id, descricao_problema, diagnostico, valor_pecas, valor_mao_de_obra, percentual_desconto, valor_total, status, data_criacao)
VALUES 
(3, 3, 'Tela trincada e conector frouxo', 'Troca de display AMOLED e conector USB-C', 380.00, 120.00, 5.00, 475.00, 'APROVADO', CURRENT_TIMESTAMP);

-- Orçamento #4: RECUSADO (para demonstrar estado final)
INSERT INTO orcamento (id, equipamento_id, descricao_problema, diagnostico, valor_pecas, valor_mao_de_obra, percentual_desconto, valor_total, status, data_criacao)
VALUES 
(4, 4, 'PC travando durante jogos', 'Placa de video com defeito. Troca necessaria', 1800.00, 150.00, 0.00, 1950.00, 'RECUSADO', CURRENT_TIMESTAMP);

-- === Ordem de Serviço (vinculada ao orçamento #3 já aprovado) ===
INSERT INTO ordem_servico (id, orcamento_id, responsavel, prioridade, status, data_abertura, previsao_conclusao, data_conclusao, observacoes, prazo_garantia_dias)
VALUES 
(1, 3, 'Tecnico Marcos Vieira', 'ALTA', 'EM_ANDAMENTO', CURRENT_TIMESTAMP, NULL, NULL, 'Cliente solicitou prioridade. Display encomendado.', 90);

-- === Ficha Técnica (vinculada à ordem #1, par obrigatório 1:1) ===
INSERT INTO ficha_tecnica (id, ordem_servico_id, estado_conservacao, acessorios_entregues, liga_normalmente, possui_avarias, descricao_avarias, teste_inicial, observacoes_recebimento, data_registro)
VALUES 
(1, 1, 'REGULAR', 'Capa protetora, cabo USB-C original', TRUE, TRUE, 'Tela trincada no canto superior direito, risco leve na traseira', 'Aparelho liga normalmente, touch parcialmente funcional na area da trinca', 'Recebido pelo tecnico Marcos. Cliente ciente do prazo de 5 dias uteis.', CURRENT_TIMESTAMP);
