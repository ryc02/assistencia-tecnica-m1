-- ============================================================================
-- [Requisito: Dados Iniciais]
-- O sistema inicia sem dados pré-carregados para clientes/OS.
-- Porém precisamos dos usuários do sistema.
-- ============================================================================

INSERT INTO usuario (nome, email, senha, cargo) VALUES ('Administrador Sistema', 'admin@m1.com', 'admin123', 'Administrador');
INSERT INTO usuario (nome, email, senha, cargo) VALUES ('Técnico Padrão', 'tecnico@m1.com', '123456', 'Técnico');
