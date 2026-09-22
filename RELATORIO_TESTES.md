# Relatório de Testes e Vulnerabilidades — M1 Assistência Técnica

## Ambiente de execução dos testes

| Item               | Valor                                   |
|--------------------|-----------------------------------------|
| Plataforma         | Java 17                                 |
| Framework de teste | JUnit Jupiter 5.10.2                    |
| Banco de dados     | H2 Database 2.2.224 (embarcado, em memória) |
| Tipo de teste      | Integração real (sem mocks de DAO/banco) |
| Classe de testes   | `AssistenciaTecnicaIntegrationTest.java` |

## Resultado da execução

```
Tests run: 20, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

**Data da execução:** 22/09/2026

---

## Detalhamento dos casos de teste

| Caso | Descrição | Status | Observações |
|------|-----------|--------|-------------|
| T01 | CRUD de cliente e rejeição de CPF duplicado | ✅ PASSOU | Cadastro, consulta e `ConflictException` para CPF repetido |
| T02 | CRUD de equipamento e rejeição de cliente inexistente | ✅ PASSOU | Cadastro com vínculo válido; `NotFoundException` para `clienteId` inexistente |
| T03 | CRUD de orçamento, cálculo no servidor e bloqueio de alteração de aprovado | ✅ PASSOU | `valorTotal` calculado como R$ 135,00; `ConflictException` ao tentar alterar orçamento aprovado |
| T04 | Aprovação de orçamento gera par OrdemServico e FichaTecnica | ✅ PASSOU | Par criado em transação única; status ABERTA e dados da ficha verificados |
| T05 | Atualizar ordem e ficha preserva vínculos e estados finais | ✅ PASSOU | Edição em ABERTA funciona; `ConflictException` ao editar ordem CONCLUIDA |
| T06 | Excluir ordem ABERTA remove ficha, ordem e restaura orçamento para PENDENTE | ✅ PASSOU | Exclusão atômica; orçamento volta a PENDENTE |
| T07 | Excluir ordem iniciada (EM_ANDAMENTO) deve ser bloqueado | ✅ PASSOU | `ConflictException` lançada; registros preservados |
| T08 | Dois equipamentos de um cliente (1:N) | ✅ PASSOU | `listarPorCliente()` retorna exatamente 2 equipamentos |
| T09 | Restrição UNIQUE rejeita segunda ficha técnica para mesma ordem | ✅ PASSOU | Inserção direta via SQL viola constraint UNIQUE |
| T10 | Cálculo de aceite: R$ 100 peças + R$ 50 MO - 10% = R$ 135,00 | ✅ PASSOU | Valor total conferido antes e depois da aprovação |
| T11 | Aprovação idempotente retorna a mesma ordem sem duplicar | ✅ PASSOU | Segunda aprovação retorna mesmo `ordemServico.id` |
| T12 | Aprovações concorrentes com 5 threads simultâneas | ✅ PASSOU | Exatamente 1 ordem persistida após execução paralela |
| T13 | Falha ao criar ficha (validação) realiza rollback integral | ✅ PASSOU | `possuiAvarias=true` sem `descricaoAvarias` → `ValidationException`; orçamento permanece PENDENTE, nenhuma ordem criada |
| T14 | Falha no meio da exclusão faz rollback integral | ✅ PASSOU | Exceção simulada após deletar ficha; rollback restaura todos os registros; orçamento permanece APROVADO |
| T15 | Excluir cliente com equipamento é bloqueado | ✅ PASSOU | `ConflictException` impedindo exclusão em cascata |
| T16 | Consulta de ID inexistente retorna 404 | ✅ PASSOU | `NotFoundException` para `clienteId = 99999` |
| T17 | SQL Injection e XSS são tratados com segurança | ✅ PASSOU | Payloads `' OR '1'='1'; DROP TABLE` e `<script>alert()` armazenados como texto literal; tabelas intactas |
| T18 | Proteção CSRF (403) e validação de método HTTP (405) | ✅ PASSOU | GET em `cliente.inserir` → HTTP 405; POST sem token → HTTP 403 |
| T19 | Limites de desconto: 0%, 100% e >100% | ✅ PASSOU | 0% → R$ 100,00; 100% → R$ 0,00; 150% → `ValidationException` |
| T20 | Invariante de integridade: nenhuma ordem sem ficha, nenhuma ficha órfã | ✅ PASSOU | Queries SQL de verificação retornam contagem 0 em ambos os casos |

---

## Análise de vulnerabilidades

### Proteções implementadas

| Vulnerabilidade       | Proteção                                      | Status    |
|-----------------------|-----------------------------------------------|-----------|
| **SQL Injection**     | `PreparedStatement` em todas as queries JDBC   | ✅ Protegido |
| **Cross-Site Scripting (XSS)** | Dados escapados nas JSPs via JSTL `${...}` | ✅ Protegido |
| **CSRF**              | Token aleatório vinculado à sessão, validado em todo POST | ✅ Protegido |
| **Alteração via GET** | `isPostAction()` rejeita GET em operações de mutação (HTTP 405) | ✅ Protegido |
| **Ações desconhecidas** | `CommandFactory` retorna null → HTTP 400     | ✅ Protegido |
| **Exposição de stack traces** | Erro genérico na JSP `erro.jsp`; detalhes apenas nos logs | ✅ Protegido |
| **Conexão transacional** | DAOs recebem conexão do serviço; nunca abrem conexões próprias | ✅ Protegido |
| **try-with-resources** | Statements e ResultSets fechados automaticamente | ✅ Protegido |

### Limitações conhecidas (fora do escopo)

| Item                             | Observação                                                         |
|----------------------------------|--------------------------------------------------------------------|
| Autenticação e autorização       | Não implementadas; CSRF protege sessão, mas não identifica usuário |
| HTTPS                            | Não configurado; execução local apenas                             |
| Rate limiting                    | Não implementado                                                   |
| Validação de e-mail com regex    | Formato validado pelo navegador (`type="email"`), não no servidor  |
| Content-Security-Policy          | Não configurado nos headers HTTP                                   |

Essas limitações estão documentadas como "fora do escopo inicial" na seção 3 do documento de especificação.
