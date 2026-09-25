<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Fichas Técnicas - Assistência Técnica M1</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css?v=5">
</head>
<body>

<nav class="navbar">
    <a href="${pageContext.request.contextPath}/controle?acao=cliente.listar" class="navbar-brand">
         Assistência Técnica M1
    </a>
    <ul class="navbar-nav">
        <li><a href="${pageContext.request.contextPath}/controle?acao=cliente.listar">Clientes</a></li>
        <li><a href="${pageContext.request.contextPath}/controle?acao=equipamento.listar">Equipamentos</a></li>
        <li><a href="${pageContext.request.contextPath}/controle?acao=orcamento.listar">Orçamentos</a></li>
        <li><a href="${pageContext.request.contextPath}/controle?acao=ordemServico.listar" class="active">Ordens & Fichas</a></li>
    </ul>

    <div class="user-actions" style="display: flex; align-items: center; gap: 1rem; font-size: 0.875rem;">
        <span style="color: var(--muted-foreground);">Olá, <strong>${sessionScope.usuarioLogado}</strong></span>
        <a href="${pageContext.request.contextPath}/controle?acao=logout" style="color: hsl(0 84.2% 60.2%); text-decoration: none; font-weight: 500;">Sair</a>
    </div>
</nav>

<div class="container">

    <div class="card">
        <h2 class="card-title"> Fichas Técnicas</h2>
        <div class="table-responsive">
            <table>
                <thead>
                <tr>
                    <th>ID da Ficha</th>
                    <th>Estado de Conservação</th>
                    <th>Liga Normalmente?</th>
                    <th>Possui Avarias?</th>
                    <th>Ações</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="ficha" items="${fichas}">
                    <tr>
                        <td><strong>#${ficha.id}</strong></td>
                        <td>${ficha.estadoConservacao}</td>
                        <td>${ficha.ligaNormalmente == null ? 'Não verificado' : (ficha.ligaNormalmente ? 'Sim' : 'Não')}</td>
                        <td>${ficha.possuiAvarias == null ? 'Não verificado' : (ficha.possuiAvarias ? 'Sim' : 'Não')}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/controle?acao=fichaTecnica.consultar&id=${ficha.id}" class="btn btn-sm btn-primary"> Consultar Ficha</a>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty fichas}">
                    <tr>
                        <td colspan="5" style="text-align: center; color: var(--muted-foreground);">Nenhuma ficha técnica registrada.</td>
                    </tr>
                </c:if>
                </tbody>
            </table>
        </div>
    </div>

</div>

</body>
</html>
