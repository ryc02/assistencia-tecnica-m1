<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Orçamentos - Assistência Técnica M1</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>

<nav class="navbar">
    <a href="${pageContext.request.contextPath}/controle?acao=cliente.listar" class="navbar-brand">
        🛠️ Assistência Técnica M1
    </a>
    <ul class="navbar-nav">
        <li><a href="${pageContext.request.contextPath}/controle?acao=cliente.listar">Clientes</a></li>
        <li><a href="${pageContext.request.contextPath}/controle?acao=equipamento.listar">Equipamentos</a></li>
        <li><a href="${pageContext.request.contextPath}/controle?acao=orcamento.listar" class="active">Orçamentos</a></li>
        <li><a href="${pageContext.request.contextPath}/controle?acao=ordemServico.listar">Ordens & Fichas</a></li>
    </ul>
</nav>

<div class="container">

    <!-- [RF03] Form de Cadastro de Orçamento -->
    <div class="card">
        <h2 class="card-title">➕ Criar Novo Orçamento</h2>
        <form action="${pageContext.request.contextPath}/controle" method="post">
            <input type="hidden" name="acao" value="orcamento.inserir">
            <input type="hidden" name="csrfToken" value="${csrfToken}">

            <div class="form-grid">
                <div class="form-group">
                    <label for="equipamentoId">Equipamento *</label>
                    <select id="equipamentoId" name="equipamentoId" required>
                        <option value="">-- Selecione o Equipamento --</option>
                        <c:forEach var="eq" items="${equipamentos}">
                            <option value="${eq.id}" ${equipamentoFiltro != null && equipamentoFiltro.id == eq.id ? 'selected' : ''}>
                                #${eq.id} - ${eq.tipo} ${eq.marca} ${eq.modelo} (${eq.cliente.nome})
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group full-width">
                    <label for="descricaoProblema">Descrição do Problema *</label>
                    <textarea id="descricaoProblema" name="descricaoProblema" rows="2" required maxlength="2000" placeholder="Relato do cliente sobre o defeito..."></textarea>
                </div>
                <div class="form-group full-width">
                    <label for="diagnostico">Diagnóstico Técnico</label>
                    <textarea id="diagnostico" name="diagnostico" rows="2" maxlength="2000" placeholder="Análise do técnico (obrigatório para aprovação)..."></textarea>
                </div>
                <div class="form-group">
                    <label for="valorPecas">Valor de Peças (R$)</label>
                    <input type="number" id="valorPecas" name="valorPecas" step="0.01" min="0" value="0.00">
                </div>
                <div class="form-group">
                    <label for="valorMaoDeObra">Valor Mão de Obra (R$)</label>
                    <input type="number" id="valorMaoDeObra" name="valorMaoDeObra" step="0.01" min="0" value="0.00">
                </div>
                <div class="form-group">
                    <label for="percentualDesconto">Desconto (%)</label>
                    <input type="number" id="percentualDesconto" name="percentualDesconto" step="0.01" min="0" max="100" value="0.00">
                </div>
            </div>
            <div style="margin-top: 16px;">
                <button type="submit" class="btn btn-primary">💾 Criar Orçamento (Calcula Total no Servidor)</button>
            </div>
        </form>
    </div>

    <!-- Listagem de Orçamentos -->
    <div class="card">
        <h2 class="card-title">
            💰 Orçamentos Cadastrados
            <c:if test="${equipamentoFiltro != null}">
                <span style="font-size: 0.9rem; font-weight: normal;">(Filtrado por Equipamento #${equipamentoFiltro.id} - ${equipamentoFiltro.modelo})</span>
            </c:if>
        </h2>
        <div class="table-responsive">
            <table>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Equipamento / Cliente</th>
                    <th>Problema</th>
                    <th>Status</th>
                    <th>Valor Total</th>
                    <th>Ações</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="orc" items="${orcamentos}">
                    <tr>
                        <td>${orc.id}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/controle?acao=equipamento.consultar&id=${orc.equipamento.id}" class="link-vinculo">
                                ${orc.equipamento.tipo} ${orc.equipamento.modelo}
                            </a>
                            <br><small style="color: var(--text-muted);">${orc.equipamento.cliente.nome}</small>
                        </td>
                        <td>${orc.descricaoProblema}</td>
                        <td><span class="badge badge-${orc.status.name().toLowerCase()}">${orc.status}</span></td>
                        <td><strong>R$ ${orc.valorTotal}</strong></td>
                        <td>
                            <a href="${pageContext.request.contextPath}/controle?acao=orcamento.consultar&id=${orc.id}" class="btn btn-sm btn-primary">🔍 Visualizar / Aprovar</a>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty orcamentos}">
                    <tr>
                        <td colspan="6" style="text-align: center; color: var(--text-muted);">Nenhum orçamento encontrado.</td>
                    </tr>
                </c:if>
                </tbody>
            </table>
        </div>
    </div>

</div>

</body>
</html>
