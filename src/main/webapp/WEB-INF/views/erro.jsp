<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>${erroTitulo != null ? erroTitulo : 'Erro'} - Assistência Técnica</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>

<nav class="navbar">
    <a href="${pageContext.request.contextPath}/controle?acao=cliente.listar" class="navbar-brand">
        🛠️ Assistência Técnica M1
    </a>
</nav>

<div class="container">
    <div class="card" style="border-left: 6px solid var(--danger);">
        <h2 style="color: var(--danger); margin-bottom: 12px;">⚠️ ${erroTitulo} (Código ${erroCodigo})</h2>
        <p style="font-size: 1.1rem; margin-bottom: 20px; color: #334155;">
            ${erroMensagem}
        </p>

        <div style="margin-top: 20px;">
            <a href="javascript:history.back()" class="btn btn-secondary">⬅️ Voltar à página anterior</a>
            <a href="${pageContext.request.contextPath}/controle?acao=cliente.listar" class="btn btn-primary">🏠 Voltar ao Início</a>
        </div>
    </div>
</div>

</body>
</html>
