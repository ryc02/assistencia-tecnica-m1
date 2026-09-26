<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Usuários - Assistência Técnica M1</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css?v=6">
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
        <li><a href="${pageContext.request.contextPath}/controle?acao=ordemServico.listar">Ordens & Fichas</a></li>
        <li><a href="${pageContext.request.contextPath}/controle?acao=usuario.listar" class="active">Usuários</a></li>
    </ul>

    <div class="user-actions" style="display: flex; align-items: center; gap: 1rem; font-size: 0.875rem;">
        <span style="color: var(--muted-foreground);">Olá, <strong>${sessionScope.usuarioLogado}</strong></span>
        <a href="${pageContext.request.contextPath}/controle?acao=logout" style="color: hsl(0 84.2% 60.2%); text-decoration: none; font-weight: 500;">Sair</a>
    </div>
</nav>

    <main class="container">
        <div class="page-header">
            <div>
                <h1 class="page-title">Usuários</h1>
                <p class="page-description">Gerencie os usuários que têm acesso ao sistema.</p>
            </div>
            <a href="${pageContext.request.contextPath}/controle?acao=usuario.inserir" class="btn btn-primary">
                + Novo Usuário
            </a>
        </div>

        <c:if test="${not empty sessionScope.erro}">
            <div class="alert alert-error">
                ${sessionScope.erro}
                <c:remove var="erro" scope="session"/>
            </div>
        </c:if>

        <div class="card">
            <div class="table-container">
                <table class="table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Nome</th>
                            <th>E-mail</th>
                            <th>Cargo</th>
                            <th class="text-right">Ações</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="usuario" items="${usuarios}">
                            <tr>
                                <td>#${usuario.id}</td>
                                <td>${usuario.nome}</td>
                                <td>${usuario.email}</td>
                                <td><span class="badge badge-outline">${usuario.cargo}</span></td>
                                <td class="text-right table-actions">
                                    <form action="${pageContext.request.contextPath}/controle" method="post" style="display:inline;" onsubmit="return confirm('Tem certeza que deseja excluir este usuário?');">
                                        <input type="hidden" name="acao" value="usuario.excluir">
                                        <input type="hidden" name="id" value="${usuario.id}">
                                        <input type="hidden" name="csrfToken" value="${csrfToken}">
                                        <button type="submit" class="btn btn-sm btn-destructive">Excluir</button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty usuarios}">
                            <tr>
                                <td colspan="5" class="text-center" style="padding: 2rem;">
                                    <p class="page-description">Nenhum usuário cadastrado.</p>
                                </td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </div>
    </main>
</body>
</html>
