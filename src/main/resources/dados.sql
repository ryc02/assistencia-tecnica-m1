-- Usuários iniciais (senhas admin123 e 123456 em hash PBKDF2)
INSERT INTO usuario (nome, email, senha, cargo) VALUES ('Administrador Sistema', 'admin@m1.com', '$pbkdf2$10000$w8s5xuIiGtrngOetQAKWiw==$OJOPBoeqLVUvpAFYRwnT8H8vWGqqJZ3fdt3BQmrAtFk=', 'Administrador');
INSERT INTO usuario (nome, email, senha, cargo) VALUES ('Técnico Padrão', 'tecnico@m1.com', '$pbkdf2$10000$7tFc/Xi1m+f5O5hvNNSUzQ==$80QeXUuSU2NmQJWDn//1dzP8rFnqwAmcG1yQwzu4z7M=', 'Técnico');
