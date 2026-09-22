<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Detalhes do Equipamento - Assistência Técnica M1</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>

<nav class="navbar">
    <a href="${pageContext.request.contextPath}/controle?acao=cliente.listar" class="navbar-brand">
        🛠️ Assistência Técnica M1
    </a>
    <ul class="navbar-nav">
        <li><a href="${pageContext.request.contextPath}/controle?acao=cliente.listar">Clientes</a></li>
        <li><a href="${pageContext.request.contextPath}/controle?acao=equipamento.listar" class="active">Equipamentos</a></li>
        <li><a href="${pageContext.request.contextPath}/controle?acao=orcamento.listar">Orçamentos</a></li>
        <li><a href="${pageContext.request.contextPath}/controle?acao=ordemServico.listar">Ordens & Fichas</a></li>
    </ul>
</nav>

<div class="container">

    <!-- Editar Equipamento -->
    <div class="card">
        <h2 class="card-title">💻 Detalhes do Equipamento #${equipamento.id}</h2>
        <form action="${pageContext.request.contextPath}/controle" method="post">
            <input type="hidden" name="acao" value="equipamento.atualizar">
            <input type="hidden" name="id" value="${equipamento.id}">
            <input type="hidden" name="csrfToken" value="${csrfToken}">

            <div class="form-grid">
                <div class="form-group">
                    <label for="clienteId">Cliente Proprietário</label>
                    <select id="clienteId" name="clienteId" required>
                        <c:forEach var="cli" items="${clientes}">
                            <option value="${cli.id}" ${equipamento.cliente.id == cli.id ? 'selected' : ''}>
                                ${cli.nome} (CPF: ${cli.cpf})
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label for="tipo">Tipo de Equipamento</label>
                    <input type="text" id="tipo" name="tipo" value="${equipamento.tipo}" required maxlength="60">
                </div>
                <div class="form-group">
                    <label for="marca">Marca</label>
                    <input type="text" id="marca" name="marca" value="${equipamento.marca}" required maxlength="60">
                </div>
                <div class="form-group">
                    <label for="modelo">Modelo</label>
                    <input type="text" id="modelo" name="modelo" value="${equipamento.modelo}" required maxlength="80">
                </div>
                <div class="form-group">
                    <label for="numeroSerie">Número de Série</label>
                    <input type="text" id="numeroSerie" name="numeroSerie" value="${equipamento.numeroSerie}" maxlength="100">
                </div>
                <div class="form-group">
                    <label for="cor">Cor</label>
                    <input type="text" id="cor" name="cor" value="${equipamento.cor}" maxlength="40">
                </div>
                <div class="form-group">
                    <label for="voltagem">Voltagem</label>
                    <select id="voltagem" name="voltagem" required>
                        <option value="BIVOLT" ${equipamento.voltagem == 'BIVOLT' ? 'selected' : ''}>BIVOLT</option>
                        <option value="110V" ${equipamento.voltagem == '110V' ? 'selected' : ''}>110V</option>
                        <option value="127V" ${equipamento.voltagem == '127V' ? 'selected' : ''}>127V</option>
                        <option value="220V" ${equipamento.voltagem == '220V' ? 'selected' : ''}>220V</option>
                        <option value="NAO_APLICAVEL" ${equipamento.voltagem == 'NAO_APLICAVEL' ? 'selected' : ''}>NÃO APLICÁVEL</option>
                    </select>
                </div>
                <div class="form-group full-width">
                    <label for="descricao">Descrição / Observações</label>
                    <textarea id="descricao" name="descricao" rows="2" maxlength="1000">${equipamento.descricao}</textarea>
                </div>
            </div>

            <div class="actions-bar">
                <button type="submit" class="btn btn-primary">✏️ Atualizar Equipamento</button>
                <a href="${pageContext.request.contextPath}/controle?acao=equipamento.listar" class="btn btn-secondary">⬅️ Voltar</a>
            </div>
        </form>

        <form action="${pageContext.request.contextPath}/controle" method="post" style="margin-top: 10px;" onsubmit="return confirm('Deseja realmente excluir este equipamento?');">
            <input type="hidden" name="acao" value="equipamento.excluir">
            <input type="hidden" name="id" value="${equipamento.id}">
            <input type="hidden" name="csrfToken" value="${csrfToken}">
            <button type="submit" class="btn btn-danger">🗑️ Excluir Equipamento</button>
        </form>
    </div>

    <!-- [RF06] Navegação: Orçamentos do Equipamento (Multiplicidade 1:N) -->
    <div class="card">
        <h2 class="card-title">
            💰 Orçamentos deste Equipamento
            <a href="${pageContext.request.contextPath}/controle?acao=orcamento.listar&equipamentoId=${equipamento.id}" class="btn btn-sm btn-primary">➕ Criar Novo Orçamento</a>
        </h2>
        <div class="table-responsive">
            <table>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Problema Relatado</th>
                    <th>Status</th>
                    <th>Valor Total</th>
                    <th>Ações</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="orc" items="${orcamentos}">
                    <tr>
                        <td>${orc.id}</td>
                        <td>${orc.descricaoProblema}</td>
                        <td><span class="badge badge-${orc.status.name().toLowerCase()}">${orc.status}</span></td>
                        <td><strong>R$ ${orc.valorTotal}</strong></td>
                        <td>
                            <a href="${pageContext.request.contextPath}/controle?acao=orcamento.consultar&id=${orc.id}" class="btn btn-sm btn-primary">🔍 Ver Detalhes</a>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty orcamentos}">
                    <tr>
                        <td colspan="5" style="text-align: center; color: var(--text-muted);">Nenhum orçamento cadastrado para este equipamento.</td>
                    </tr>
                </c:if>
                </tbody>
            </table>
        </div>
    </div>

</div>

</body>
</html>
