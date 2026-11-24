# Relatório de Testes e Vulnerabilidades — Assistência Técnica (M1 + M2)

## Ambiente de execução dos testes

| Item               | Valor                                          |
|--------------------|------------------------------------------------|
| Plataforma         | Java 17                                        |
| Framework de teste | JUnit Jupiter 5 + Mockito 5                    |
| Banco de dados     | H2 (embarcado, em memória, um banco por teste) |
| Comando            | `.\mvnw.cmd test`                              |

## Resultado da execução

```
Tests run: 70, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

**Data da execução:** 15/11/2026

## Testes unitários (Mockito, padrão AAA)

| Classe | Cenários |
|--------|----------|
| `AlteracaoDeClienteTest` | atualizar: o próprio cliente pode manter o mesmo CPF |
| `AlteracaoDeOrcamentoTest` | atualizar: orçamento APROVADO não pode ser alterado |
| `AprovacaoIdempotenteTest` | aprovar: segunda chamada devolve a mesma ordem (idempotente) |
| `AprovacaoServiceTest` | aprovar: gera ordem ABERTA e ficha técnica na mesma transação |
| `AutenticacaoServiceTest` | login: senha correta autentica o usuário; login: senha errada gera ValidationException |
| `CadastroDeUsuarioTest` | inserir: e-mail já cadastrado gera ConflictException |
| `CicloDaOrdemTest` | iniciar: ABERTA passa para EM_ANDAMENTO; concluir: ordem ABERTA não pode ser concluída sem iniciar |
| `ClienteInexistenteTest` | atualizar: cliente inexistente gera NotFoundException |
| `ClienteServiceTest` | cadastrar: CPF novo é salvo somente com os dígitos; cadastrar: CPF já cadastrado gera ConflictException e não insere |
| `ConsultaClienteTest` | buscarPorId: devolve o cliente encontrado; buscarPorId: id inexistente gera NotFoundException e id nulo gera ValidationException |
| `EquipamentoServiceTest` | cadastrar: equipamento vinculado a cliente existente; cadastrar: cliente inexistente gera NotFoundException |
| `EstadoFinalDaOrdemTest` | cancelar: ordem CONCLUIDA já está no estado final; reprogramar: ordem CONCLUIDA não pode ter os dados alterados |
| `ExclusaoDeClienteTest` | excluir: cliente sem equipamentos é excluído; excluir: cliente com equipamento gera ConflictException |
| `ExclusaoDeEquipamentoTest` | atualizar: mantendo o mesmo cliente, os dados são alterados; excluir: equipamento com orçamento gera ConflictException |
| `ExclusaoDeOrcamentoTest` | excluir: orçamento com ordem de serviço gera ConflictException |
| `ExclusaoDeOrdemIniciadaTest` | excluir: ordem EM_ANDAMENTO gera ConflictException |
| `ExclusaoDeOrdemTest` | excluir: ordem ABERTA remove a ficha e a ordem |
| `ExclusaoDeUsuarioTest` | excluir: o usuário conectado não pode excluir a si mesmo; excluir: o único Administrador não pode ser removido |
| `FichaTecnicaServiceTest` | atualizar: grava o laudo da ficha existente; atualizar: ficha inexistente gera NotFoundException |
| `ListagemDeClientesTest` | listarTodos: devolve os clientes do DAO |
| `LoginSemEmailTest` | login: e-mail em branco gera ValidationException |
| `OrcamentoServiceTest` | cadastrar: total = (peças + mão de obra + adicionais) com desconto |
| `RecusaDeOrcamentoTest` | recusar: orçamento PENDENTE passa para RECUSADO |
| `TrocaDeDonoTest` | atualizar: trocar o dono de equipamento com orçamento gera ConflictException |
| `AdicionalDecoratorTest` | serviço base: custo = peças + mão de obra; um decorator: backup acrescenta R$ 50,00 |
| `AdicionalFactoryTest` | decorar: devolve o decorator correspondente ao tipo; deNome: nome de adicional desconhecido gera ValidationException |
| `DecoratorsEmpilhadosTest` | decorators empilhados: todos os adicionais somados; descrição: cada decorator anexa o seu nome na ordem em que foi aplicado |
| `JsonTest` | ler: objeto com texto, número, lógico e lista; ler: corpo que não é objeto gera ValidationException; escrever: escapa aspas e os sinais < e > (proteção contra XSS) |
| `CsrfDoFormularioTest` | T18: POST com sessão ativa e sem token CSRF é bloqueado com 403; T18: POST de sessão expirada volta ao login com aviso |
| `FrontControllerSegurancaTest` | T18: ação de alteração via GET é bloqueada com 405 |
| `CodificacaoDaRequisicaoTest` | filtro define UTF-8 antes de ler os parâmetros (acentos dos formulários) |

## Testes de integração (H2 real, sem mocks)

| Caso | Descrição | Classe |
|------|-----------|--------|
| T01 | Cadastro/consulta de cliente e CPF duplicado | `CadastroDeClienteIntegracaoTest` |
| T02 | Equipamento de cliente inexistente | `EquipamentoIntegracaoTest` |
| T03 | Orçamento aprovado não pode ser alterado | `OrcamentoIntegracaoTest` |
| T04 | Aprovação gera Ordem de Serviço e Ficha Técnica | `AprovacaoIntegracaoTest` |
| T05 | Atualizações preservam vínculos e estados finais | `AtendimentoIntegracaoTest` |
| T06 | Excluir ordem ABERTA restaura o orçamento para PENDENTE | `ExclusaoDeOrdemIntegracaoTest` |
| T07 | Excluir ordem iniciada é bloqueado | `OrdemIniciadaIntegracaoTest` |
| T08 | Dois equipamentos do mesmo cliente (1:N) | `EquipamentoIntegracaoTest` |
| T09 | UNIQUE rejeita segunda ficha para a mesma ordem | `UnicidadeDaFichaIntegracaoTest` |
| T10 | R$ 100 + R$ 50 com 10% de desconto = R$ 135,00 | `OrcamentoIntegracaoTest` |
| T11 | Aprovação idempotente | `AprovacaoIntegracaoTest` |
| T12 | Aprovações concorrentes geram uma só ordem e ficha | `ConcorrenciaIntegracaoTest` |
| T13 | Falha na ficha desfaz toda a aprovação (rollback) | `RollbackNaAprovacaoIntegracaoTest` |
| T14 | Falha no meio da exclusão desfaz tudo (rollback) | `RollbackNaExclusaoIntegracaoTest` |
| T15 | Excluir cliente com equipamento é bloqueado | `ExclusaoDeClienteIntegracaoTest` |
| T16 | ID inexistente gera NotFoundException | `CadastroDeClienteIntegracaoTest` |
| T17 | SQL Injection/XSS gravados como texto comum | `SegurancaIntegracaoTest` |
| T18 | POST sem token CSRF (403), sessão expirada volta ao login e alteração via GET (405) | `CsrfDoFormularioTest`, `FrontControllerSegurancaTest` |
| T19 | Desconto de 100% e rejeição acima de 100% | `DescontoIntegracaoTest` |
| T20 | Nenhuma ordem sem ficha e nenhuma ficha órfã | `IntegridadeIntegracaoTest` |
| T21 | Login válido e inválido | `AutenticacaoIntegracaoTest` |
| T22 | Hash PBKDF2, e-mail único e proteção do único administrador | `AdministradorIntegracaoTest`, `UsuarioIntegracaoTest` |

## Vulnerabilidades tratadas

| Vulnerabilidade | Correção |
|-----------------|----------|
| SQL Injection | Todos os comandos passam por `ComandoSql` com `PreparedStatement` |
| XSS | `fn:escapeXml` nas JSPs e escape de `<` e `>` no JSON |
| CSRF | Token por sessão (formulário `csrfToken` e cabeçalho `X-CSRF-Token`) |
| Senhas em texto puro | Hash PBKDF2 com salt (`CriptografiaSenha`) |
| Fixação de sessão | Novo ID de sessão após o login |
| Acesso indevido | `AuthFilter` com regras de perfil (Administrador/Técnico) |
| Vazamento de detalhes técnicos | Erros 500 com mensagem genérica |
