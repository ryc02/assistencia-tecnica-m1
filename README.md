# Sistema de Assistência Técnica — Avaliações M1 + M2

Aplicação web para gerenciamento de uma assistência técnica fictícia (clientes, equipamentos,
orçamentos, ordens de serviço e fichas técnicas), desenvolvida na disciplina **Padrões de Projeto**
(UMC — 5º semestre Engenharia de Software), grupo **G22**.

O projeto da M1 (MVC + DAO + Command + Factory Method + Builder) foi refatorado para a M2 com
**Decorator (sem framework)**, **SOLID** (DIP, LSP/ISP), as **9 regras da Calistenia de Objetos** em
todo o código, **API REST**, **testes unitários automatizados** e correção de vulnerabilidades.

## Pré-requisitos

| Requisito         | Versão mínima | Observação                                   |
|-------------------|---------------|----------------------------------------------|
| **Java (JDK)**    | 17            | Compilação e execução                        |
| **Maven**         | 3.9+          | Já incluído na pasta `tools/maven` e usado pelo `mvnw.cmd` |
| **Navegador web** | Moderno       | Chrome, Edge ou Firefox                      |

> Não é necessário instalar MySQL ou Tomcat: o projeto usa **H2 embarcado** e **Tomcat embarcado**.

## Como executar

```powershell
.\mvnw.cmd compile exec:exec
```

Aguarde a mensagem `Servidor Assistência Técnica rodando em: http://localhost:8080/controle` e deixe o
terminal aberto (`Ctrl + C` encerra o servidor).

- Telas (MVC/JSP): `http://localhost:8080` (abre o login)
- API REST: `http://localhost:8080/api/...`
- Porta ocupada: `$env:PORT=8081; .\mvnw.cmd compile exec:exec`
- No VS Code: **Run and Debug** → configuração **IniciadorAplicacao**

Usuários iniciais (senhas gravadas com hash PBKDF2):

| E-mail          | Senha      | Cargo          |
|-----------------|------------|----------------|
| admin@m1.com    | admin123   | Administrador  |
| tecnico@m1.com  | 123456     | Técnico        |

## Banco de dados

- **H2 embarcado** (modo compatível com MySQL), gravado em `data/assistenciadb`. A pasta `data/` fica
  fora do Git: em um clone novo o banco é criado sozinho na primeira execução (`schema.sql` + `dados.sql`).
- Bancos criados pela versão da M1 são atualizados automaticamente (coluna dos adicionais e senhas em hash).
- Endereço, usuário e senha podem ser trocados pelas variáveis `DB_URL`, `DB_USER` e `DB_PASSWORD`.

Para consultar os dados, execute `abrir_banco.bat` (abre o console web do H2) e informe:

| Campo     | Valor |
|-----------|-------|
| JDBC URL  | `jdbc:h2:file:<pasta do projeto>\data\assistenciadb;MODE=MySQL;AUTO_SERVER=TRUE` |
| User Name | `sa` |
| Password  | (em branco) |

Com `AUTO_SERVER=TRUE` o console e a aplicação podem usar o banco ao mesmo tempo. Os testes de
integração nunca usam esse arquivo: cada teste cria um banco H2 novo em memória.

## Testes automatizados

```bash
.\mvnw.cmd test
```

| Pacote de testes | Tipo | Classes | Testes |
|------------------|------|---------|--------|
| `unitario/` | Unitário (Mockito, AAA) | 24 classes, uma por cenário de Service (ex.: `ClienteServiceTest`, `TrocaDeDonoTest`, `AprovacaoIdempotenteTest`, `CicloDaOrdemTest`, `ExclusaoDeUsuarioTest`) | 34 |
| `decorator/` | Unitário (Decorator) | `AdicionalDecoratorTest`, `DecoratorsEmpilhadosTest`, `AdicionalFactoryTest` | 6 |
| `api/` | Unitário (JSON da API) | `JsonTest` | 3 |
| `controller/` | Unitário (Mockito nos objetos do Servlet) | `CsrfDoFormularioTest` (T18 — CSRF 403 e sessão expirada volta ao login), `FrontControllerSegurancaTest` (T18 — GET 405) | 3 |
| `infra/` | Unitário (Mockito no filtro) | `CodificacaoDaRequisicaoTest` (acentos: UTF-8 antes de ler os parâmetros) | 1 |
| `integracao/` | Integração (H2 em memória, sem mocks) | 18 classes com os cenários T01–T22 (exceto T18) | 23 |
| **Total** | | | **70** |

Os testes unitários seguem o padrão **AAA (Arrange, Act, Assert)**, com um cenário **positivo** e
um **negativo** para cada método dos Services. Os DAOs são simulados com **Mockito** (opção 3 da
aula de testes) e a transação é substituída por `TransacaoFake`, o que só é possível por causa do DIP.

## Padrões de projeto e princípios aplicados

| Padrão / Princípio | Onde está |
|--------------------|-----------|
| **MVC** | `FrontControllerServlet` (Controller) + JSPs (View) + `model`/`service`/`dao` (Model) |
| **DAO** | Interfaces em `dao/` + implementações JDBC em `dao/jdbc/` (sempre `PreparedStatement`) |
| **Command** | `ICommand` + comandos em `command/impl/` (um comando por ação) |
| **Factory Method** | `CommandFactory` (ações → comandos, via `Map`, sem if/else), `RecursoFactory` (API), `AdicionalFactory` (Decorator), `FabricaDeClientes`, `FabricaDeEquipamentos`, `FabricaDeOrcamentos`, `FabricaDeAtendimentos` |
| **Front Controller** | `FrontControllerServlet` (telas) e `ApiServlet` (REST) |
| **Builder** | `BuilderDeEntidade` (base) + `ClienteBuilder`, `EquipamentoBuilder`, `OrcamentoBuilder`, `OrdemServicoBuilder`, `FichaTecnicaBuilder` (interface fluente: `dados(...)` copia os atributos recebidos, `id(...)`, o relacionamento, status e datas; a validação acontece no `build()`) |
| **Decorator** | Pacote `decorator/` — adicionais do orçamento (detalhes abaixo) |
| **DIP** | Services recebem DAOs e `GerenciadorTransacao` **pelo construtor**; DAOs JDBC recebem DAOs relacionados pelo construtor; Commands recebem Services pelo construtor. Só as fábricas (`factory/`) conhecem as classes JDBC |
| **LSP / ISP** | Recursos REST implementam apenas as interfaces que suportam (`RecursoConsultavel`, `RecursoCriavel`, `RecursoAtualizavel`, `RecursoExcluivel`, `RecursoComAcoes`). Ex.: `FichaTecnicaRecurso` não implementa criar/excluir, em vez de lançar `UnsupportedOperationException` |
| **SRP / OCP** | Um Command por ação; novo adicional = nova classe Decorator + 1 linha na `AdicionalFactory`, sem alterar o cálculo |
| **Herança / Polimorfismo** | `EntidadeComId` (abstrata) → `Pessoa` (abstrata) → `Cliente`, `Usuario`; `EntidadeComId` → `Orcamento`, `OrdemServico`. Mudanças de estado por enums com métodos abstratos (`MudancaDeOrcamento`, `MudancaDeOrdem`): cada constante implementa a sua regra, sem if/else |

### Decorator — adicionais do orçamento (como a Cafeteria Café Lindo)

| Papel no padrão       | Classe |
|-----------------------|--------|
| Component             | `ServicoOrcamento` |
| ConcreteComponent     | `ServicoBase` (peças + mão de obra) |
| Decorator             | `AdicionalDecorator` (abstrato) |
| ConcreteDecorator     | `BackupDados` (R$ 50), `LimpezaInterna` (R$ 40), `PeliculaProtecao` (R$ 30), `GarantiaEstendida` (R$ 80), `AtendimentoUrgente` (R$ 60) |

```java
ServicoOrcamento servico = new ServicoBase(pecas, maoDeObra);
servico = new BackupDados(servico);
servico = new LimpezaInterna(servico);
servico.calcularCusto(); // peças + mão de obra + 50 + 40
servico.descrever();     // "Serviço técnico, Backup de dados, Limpeza interna"
```

Regra de cálculo: `valorTotal = custo do serviço decorado × (1 − desconto/100)`, arredondado HALF_UP.

## Relacionamentos

- **Cliente 1:N Equipamento** — `equipamento.cliente_id`
- **Equipamento 1:N Orçamento** — `orcamento.equipamento_id`
- **Orçamento 1:1 Ordem de Serviço** — `ordem_servico.orcamento_id UNIQUE`
- **Ordem de Serviço 1:1 Ficha Técnica** — `ficha_tecnica.ordem_servico_id UNIQUE`

**Processo de negócio automatizado:** aprovar um orçamento cria, numa única transação, a Ordem de
Serviço e a Ficha Técnica e muda o orçamento para APROVADO.

## API REST (endpoints)

Autenticação por sessão: faça `POST /api/login` e envie o `csrfToken` recebido no cabeçalho
`X-CSRF-Token` em todas as requisições que não sejam GET. Corpo e respostas em JSON.

| Método | URL | Descrição | Sucesso |
|--------|-----|-----------|---------|
| POST   | `/api/login` | `{"email","senha"}` → devolve `csrfToken` | 200 |
| POST   | `/api/logout` | Encerra a sessão | 204 |
| GET    | `/api/clientes` | Lista clientes | 200 |
| GET    | `/api/clientes/{id}` | Consulta cliente | 200 |
| POST   | `/api/clientes` | Cadastra cliente (Administrador) | 201 + `Location` |
| PUT    | `/api/clientes/{id}` | Atualiza cliente (Administrador) | 200 |
| DELETE | `/api/clientes/{id}` | Exclui cliente (Administrador) | 204 |
| GET/POST | `/api/equipamentos` | Lista / cadastra (`clienteId` obrigatório) | 200 / 201 |
| GET/PUT/DELETE | `/api/equipamentos/{id}` | Consulta / atualiza / exclui (exclusão: Administrador) | 200 / 200 / 204 |
| GET/POST | `/api/orcamentos` | Lista / cadastra (aceita `"adicionais": ["BACKUP_DADOS", ...]`) | 200 / 201 |
| GET/PUT/DELETE | `/api/orcamentos/{id}` | Consulta / atualiza (só PENDENTE) / exclui | 200 / 200 / 204 |
| PATCH  | `/api/orcamentos/{id}/aprovar` | Aprova e gera Ordem de Serviço + Ficha Técnica | 200 |
| PATCH  | `/api/orcamentos/{id}/recusar` | Recusa o orçamento | 200 |
| GET    | `/api/ordens-servico[/{id}]` | Lista / consulta | 200 |
| PUT    | `/api/ordens-servico/{id}` | Atualiza (ABERTA ou EM_ANDAMENTO) | 200 |
| DELETE | `/api/ordens-servico/{id}` | Exclui ordem ABERTA (orçamento volta a PENDENTE) | 204 |
| PATCH  | `/api/ordens-servico/{id}/iniciar` \| `concluir` \| `cancelar` | Transições de estado | 200 |
| GET    | `/api/fichas-tecnicas[/{id}]` | Lista / consulta | 200 |
| PUT    | `/api/fichas-tecnicas/{id}` | Atualiza a ficha | 200 |

Erros (corpo `{"status": n, "erro": "mensagem"}`): **400** dados inválidos, **401** não autenticado,
**403** CSRF inválido ou perfil sem permissão, **404** não encontrado, **405** operação não suportada
pelo recurso, **409** conflito com regra de negócio, **500** erro interno (sem detalhes técnicos).

Exemplo:

```bash
curl -c c.txt -H "Content-Type: application/json" -X POST http://localhost:8080/api/login \
     -d "{\"email\":\"admin@m1.com\",\"senha\":\"admin123\"}"
curl -b c.txt -H "Content-Type: application/json" -H "X-CSRF-Token: <token>" \
     -X POST http://localhost:8080/api/orcamentos \
     -d "{\"equipamentoId\":1,\"descricaoProblema\":\"Não liga\",\"diagnostico\":\"Fonte\",\"valorPecas\":100,\"valorMaoDeObra\":50,\"adicionais\":[\"BACKUP_DADOS\"]}"
```

## Segurança (vulnerabilidades tratadas)

- **SQL Injection:** todos os DAOs usam `PreparedStatement`.
- **XSS:** toda saída de dados nas JSPs passa por `fn:escapeXml`; o JSON da API escapa `<` e `>`.
- **CSRF:** token por sessão nos formulários (`csrfToken`) e no cabeçalho `X-CSRF-Token` da API. Formulário enviado depois que a sessão expirou (ou o servidor reiniciou) não executa nada e volta ao login com aviso; as telas são enviadas sem cache para o botão "voltar" sempre trazer um token válido.
- **Senhas:** hash PBKDF2 com salt; comparação em texto puro removida; senhas iniciais convertidas automaticamente.
- **Sessão:** novo id de sessão após o login (evita fixação de sessão).
- **Controle de acesso:** `AuthFilter` exige login (telas e API) e restringe operações ao Administrador.
- **Vazamento de informação:** erros 500 não exibem mensagens técnicas ao usuário.
- **Factory sem Reflection sobre entrada do usuário:** apenas ações registradas no `Map` são executadas.

## Estrutura de pacotes

```
com.assistencia
├── IniciadorAplicacao → classe principal (Tomcat embarcado)
├── dados/          → Campos (coleção de dados trocada entre camadas), Registros (lista de entidades), conversores
├── model/          → EntidadeComId (base das entidades) e RegrasDeStatus
├── model/valor/    → Objetos de valor: Chave, Texto, Cpf, Email, Dinheiro, Percentual, DataHora, Identificador, Vinculo...
├── model/pessoa/   → Pessoa (abstrata) → Cliente, Usuario
├── model/cliente, usuario, equipamento, orcamento, ordem, ficha → entidades montadas por composição
├── builder/        → BuilderDeEntidade + builders das entidades
├── decorator/      → Decorator dos adicionais do orçamento + AdicionalFactory
├── controller/     → FrontControllerServlet (telas) e ApiServlet (REST)
├── command/        → ICommand + comandos concretos (command/impl)
├── api/            → Rotas, respostas REST, JSON (api/json) e recursos (api/recurso, interfaces segregadas)
├── factory/        → CommandFactory, RecursoFactory e fábricas de serviços (únicas que conhecem o JDBC)
├── service/        → Regras de negócio (dependem só de abstrações)
├── dao/ + dao/jdbc → Interfaces DAO; JDBC dividido em LeituraDe...JDBC (consultas) + ...DAOJDBC (gravação)
├── infra/          → ConnectionFactory, TransactionManager, AuthFilter, TokenCsrf, SessaoUsuario
├── seguranca/      → CriptografiaSenha (PBKDF2)
└── exception/      → ValidationException, NotFoundException, ConflictException...
```

## Calistenia de Objetos — as 9 regras em todo o projeto

As 9 regras da aula de Calistenia foram aplicadas em **todas** as classes (`src/main` e `src/test`),
inclusive nas entidades:

| # | Regra | Como foi aplicada |
|---|-------|-------------------|
| 1 | Um nível de indentação por método | Laços e condições aninhadas foram extraídos para métodos privados ou classes auxiliares (ex.: `OrdemCompleta`, `RegrasDeUsuario`, `FiltroDeTelas`) |
| 2 | Não use `else` | Cláusulas de guarda com `return`/`throw` antecipado; escolhas por `Map` (`CommandFactory`, `AdicionalFactory`, `StatusHttp`) e por polimorfismo com enum (`MudancaDeOrcamento`, `ExigenciaDoOrcamento`, `MudancaDeOrdem`, `ExigenciaDaOrdem`) |
| 3 | Encapsule primitivos e Strings | Toda classe que guarda um primitivo ou `String` guarda **só** ele: `Cpf`, `Email`, `Dinheiro`, `Percentual`, `Identificador`, `Chave` (nome do campo), `ConteudoDeTexto`, `Instante`, `Resposta`, `Descricao`, `MetodoHttp`, `NomeDoRecurso`, `IdDaRota`, `AcaoDaRota`, `EnderecoDoBanco`. Classes como `Texto` e `DataHora` combinam dois desses objetos (`Chave` + conteúdo) |
| 4 | Coleções de primeira classe | `Campos`, `Registros`, `Adicionais`, `Linhas` e `Registro`: cada classe tem a coleção como único atributo |
| 5 | Um ponto por linha | Variáveis locais e `import static` em vez de cadeias `a.b().c()`; nos testes o `when(...)` e o `.thenReturn(...)` ficam em linhas separadas |
| 6 | Não abrevie | Nomes completos (`requisicao`, `resposta`, `conexao`, `orcamento`); nada de `req`, `resp`, `conn`, `dto` |
| 7 | Classes com menos de 50 linhas | **Todo arquivo `.java` tem menos de 50 linhas contando tudo** (package, imports, comentários e linhas em branco). Classes grandes foram divididas por herança (`LeituraDeOrcamentoJDBC` → `OrcamentoDAOJDBC`, `ConsultasDeOrcamento` → `RepositorioOrcamento`, `BuilderDeEntidade` → builders, `LeituraDeCampos` → `Campos`) ou por classes auxiliares (`PaginasDeOrcamento`, `OrcamentoMontado`, `ColecoesJson`). Os testes têm no máximo dois cenários por classe |
| 8 | No máximo duas variáveis de instância | As entidades são árvores de composição: `Orcamento` = `Identificador` (herdado de `EntidadeComId`) + `ConteudoOrcamento`, que = `Solicitacao` + `Proposta`, etc. Os 10+ atributos exigidos pela especificação continuam existindo, distribuídos nas partes |
| 9 | Sem getters/setters | Os objetos se exportam (`exportar()` devolve `Campos`) e mudam de estado só por comportamento (`mudar(APROVAR)`, `mudar(RECUSAR)`, `mudar(INICIAR)`, `mudar(CONCLUIR)`, `reprogramar(...)`). As JSPs recebem mapas prontos pela classe `Visao` |

## Autores — G22

- Rychard
- Gabriele
