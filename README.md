# Sistema de Assistência Técnica — Avaliação M1

Aplicação web para gerenciamento de uma assistência técnica fictícia,
desenvolvida como projeto M1 da disciplina de Padrões de Projeto / Engenharia de Software.

## Pré-requisitos

| Requisito          | Versão mínima  | Observação                                       |
|--------------------|----------------|--------------------------------------------------|
| **Java (JDK)**     | 17             | Compilação e execução                            |
| **Maven**          | 3.9+           | Incluído via Maven Wrapper (`mvnw.cmd`)          |
| **Navegador web**  | Moderno        | Chrome, Edge ou Firefox                          |

> **Nota:** Não é necessário instalar MySQL ou Tomcat separadamente.
> O projeto utiliza **H2 Database embarcado** e **Tomcat embarcado** para execução local imediata.

## Dependências e versões

| Dependência                  | Versão   | Finalidade                              |
|------------------------------|----------|-----------------------------------------|
| Java (JDK)                   | 17       | Linguagem e plataforma                  |
| Servlet API                  | 4.0.1    | Especificação Servlets                  |
| JSTL                         | 1.2      | Tags JSP                                |
| H2 Database                  | 2.2.224  | Banco embarcado (testes e demonstração)  |
| MySQL Connector/J            | 8.3.0    | Driver MySQL (ambiente do laboratório)   |
| Tomcat Embed Core            | 9.0.86   | Servidor web embarcado                  |
| Tomcat Embed Jasper          | 9.0.86   | Compilação de JSPs                      |
| JUnit Jupiter                | 5.10.2   | Testes automatizados                    |
| Maven Compiler Plugin        | 3.11.0   | Compilação                              |
| Maven War Plugin             | 3.4.0    | Empacotamento WAR                       |
| Exec Maven Plugin            | 3.1.1    | Execução via `mvn exec:java`            |

## Como executar

### 1. Iniciar o servidor

```bash
.\mvnw.cmd compile exec:exec
```

O Tomcat embarcado iniciará na porta **8080**.

### 2. Acessar a aplicação

Abrir no navegador:

```
http://localhost:8080/controle
```

A página inicial redireciona automaticamente para a listagem de clientes.

### 3. Executar os testes automatizados

```bash
.\mvnw.cmd test
```

Executa a suíte completa de 20 testes de integração (T01 a T20) usando o banco H2 em memória.

## Estrutura de pacotes

```
com.assistencia
├── model/          → Entidades: Cliente, Equipamento, Orcamento, OrdemServico, FichaTecnica e enums
├── builder/        → Builders: OrcamentoBuilder, OrdemServicoBuilder, ClienteBuilder, etc.
├── controller/     → FrontControllerServlet (Front Controller)
├── command/        → ICommand (interface) + 28 comandos concretos (Command Pattern)
├── factory/        → CommandFactory (Fábrica Simples)
├── service/        → Regras de negócio, transações, estados e coordenação dos DAOs
├── dao/            → Interfaces DAO
├── dao/jdbc/       → Implementações JDBC com PreparedStatement
├── infra/          → ConnectionFactory, TransactionManager, CsrfUtil
└── exception/      → ValidationException, NotFoundException, ConflictException
```

## Padrões de Projeto implementados

| Padrão            | Classe(s) principal(is)                    |
|-------------------|--------------------------------------------|
| MVC               | FrontControllerServlet + JSPs + model/service/dao |
| DAO               | Interfaces DAO + implementações JDBC       |
| Command           | ICommand + 28 comandos concretos           |
| Builder           | OrcamentoBuilder, OrdemServicoBuilder, etc. |
| Fábrica Simples   | CommandFactory                             |
| Front Controller  | FrontControllerServlet                     |

## Contrato HTTP resumido

Todas as requisições passam pela rota `/controle` com o parâmetro `acao`:

- **GET:** `cliente.listar`, `cliente.consultar`, `equipamento.listar`, `equipamento.consultar`, `orcamento.listar`, `orcamento.consultar`, `ordemServico.listar`, `ordemServico.consultar`, `fichaTecnica.listar`, `fichaTecnica.consultar`
- **POST:** `*.inserir`, `*.atualizar`, `*.excluir`, `orcamento.aprovar`, `orcamento.recusar`, `ordemServico.iniciar`, `ordemServico.concluir`, `ordemServico.cancelar`

## Banco de dados

- **Padrão:** H2 Database embarcado (arquivo `schema.sql` e `dados.sql` em `src/main/resources/`)
- **MySQL:** Para usar MySQL no laboratório, alterar `ConnectionFactory.java` com a URL, usuário e senha do MySQL.
- Credenciais nunca são incluídas no código-fonte em produção.

## Autores

- Integrante 1 (Matrícula: 123456)
- Integrante 2 (Matrícula: 654321)
