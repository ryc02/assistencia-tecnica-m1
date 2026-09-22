<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Detalhes do Cliente - Assistência Técnica M1</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>

<nav class="navbar">
    <a href="${pageContext.request.contextPath}/controle?acao=cliente.listar" class="navbar-brand">
        🛠️ Assistência Técnica M1
    </a>
    <ul class="navbar-nav">
        <li><a href="${pageContext.request.contextPath}/controle?acao=cliente.listar" class="active">Clientes</a></li>
        <li><a href="${pageContext.request.contextPath}/controle?acao=equipamento.listar">Equipamentos</a></li>
        <li><a href="${pageContext.request.contextPath}/controle?acao=orcamento.listar">Orçamentos</a></li>
        <li><a href="${pageContext.request.contextPath}/controle?acao=ordemServico.listar">Ordens & Fichas</a></li>
    </ul>
</nav>

<div class="container">

    <!-- Editar / Visualizar Cliente -->
    <div class="card">
        <h2 class="card-title">👤 Detalhes do Cliente #${cliente.id}</h2>
        <form action="${pageContext.request.contextPath}/controle" method="post">
            <input type="hidden" name="acao" value="cliente.atualizar">
            <input type="hidden" name="id" value="${cliente.id}">
            <input type="hidden" name="csrfToken" value="${csrfToken}">

            <div class="form-grid">
                <div class="form-group">
                    <label for="nome">Nome Completo</label>
                    <input type="text" id="nome" name="nome" value="${cliente.nome}" required maxlength="120">
                </div>
                <div class="form-group">
                    <label for="cpf">CPF</label>
                    <input type="text" id="cpf" name="cpf" value="${cliente.cpf}" required maxlength="14">
                </div>
                <div class="form-group">
                    <label for="email">E-mail</label>
                    <input type="email" id="email" name="email" value="${cliente.email}" maxlength="254">
                </div>
                <div class="form-group">
                    <label for="telefone">Telefone</label>
                    <input type="text" id="telefone" name="telefone" value="${cliente.telefone}" required maxlength="20">
                </div>
                <div class="form-group">
                    <label for="logradouro">Logradouro</label>
                    <input type="text" id="logradouro" name="logradouro" value="${cliente.logradouro}" required maxlength="150">
                </div>
                <div class="form-group">
                    <label for="numero">Número</label>
                    <input type="text" id="numero" name="numero" value="${cliente.numero}" required maxlength="20">
                </div>
                <div class="form-group">
                    <label for="bairro">Bairro</label>
                    <input type="text" id="bairro" name="bairro" value="${cliente.bairro}" required maxlength="80">
                </div>
                <div class="form-group">
                    <label for="cidade">Cidade</label>
                    <input type="text" id="cidade" name="cidade" value="${cliente.cidade}" required maxlength="80">
                </div>
            </div>

            <div class="actions-bar">
                <button type="submit" class="btn btn-primary">✏️ Atualizar Cliente</button>
                <a href="${pageContext.request.contextPath}/controle?acao=cliente.listar" class="btn btn-secondary">⬅️ Voltar</a>
            </div>
        </form>

        <form action="${pageContext.request.contextPath}/controle" method="post" style="margin-top: 10px;" onsubmit="return confirm('Deseja realmente excluir este cliente?');">
            <input type="hidden" name="acao" value="cliente.excluir">
            <input type="hidden" name="id" value="${cliente.id}">
            <input type="hidden" name="csrfToken" value="${csrfToken}">
            <button type="submit" class="btn btn-danger">🗑️ Excluir Cliente</button>
        </form>
    </div>

    <!-- [RF06] Navegação: Equipamentos do Cliente (Multiplicidade 1:N) -->
    <div class="card">
        <h2 class="card-title">
            💻 Equipamentos deste Cliente
            <a href="${pageContext.request.contextPath}/controle?acao=equipamento.listar&clienteId=${cliente.id}" class="btn btn-sm btn-primary">➕ Novo Equipamento</a>
        </h2>
        <div class="table-responsive">
            <table>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Tipo</th>
                    <th>Marca</th>
                    <th>Modelo</th>
                    <th>Série</th>
                    <th>Ações</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="eq" items="${equipamentos}">
                    <tr>
                        <td>${eq.id}</td>
                        <td>${eq.tipo}</td>
                        <td>${eq.marca}</td>
                        <td><strong><a href="${pageContext.request.contextPath}/controle?acao=equipamento.consultar&id=${eq.id}" class="link-vinculo">${eq.modelo}</a></strong></td>
                        <td>${eq.numeroSerie != null ? eq.numeroSerie : 'N/I'}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/controle?acao=equipamento.consultar&id=${eq.id}" class="btn btn-sm btn-primary">🔍 Detalhes</a>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty equipamentos}">
                    <tr>
                        <td colspan="6" style="text-align: center; color: var(--text-muted);">Nenhum equipamento cadastrado para este cliente.</td>
                    </tr>
                </c:if>
                </tbody>
            </table>
        </div>
    </div>

</div>

</body>
</html>
