<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${erroTitulo != null ? fn:escapeXml(erroTitulo) : 'Erro'} - Assistência Técnica</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css?v=7">
</head>
<body>

<jsp:include page="/WEB-INF/views/fragments/navbar.jsp" />

<div class="container">
    <div class="card card-erro">
        <div class="erro-icone" aria-hidden="true">
            <svg viewBox="0 0 24 24" width="28" height="28" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M10.29 3.86 1.82 18a2 2 0 0 0 1.71 3h16.94a2 2 0 0 0 1.71-3L13.71 3.86a2 2 0 0 0-3.42 0z"/><line x1="12" y1="9" x2="12" y2="13"/><line x1="12" y1="17" x2="12.01" y2="17"/></svg>
        </div>
        <div>
            <p class="erro-codigo">Código ${fn:escapeXml(erroCodigo)}</p>
            <h2 class="erro-titulo">${fn:escapeXml(erroTitulo)}</h2>
            <p class="erro-mensagem">${fn:escapeXml(erroMensagem)}</p>
            <div class="actions-bar">
                <a href="javascript:history.back()" class="btn btn-secondary">Voltar à página anterior</a>
                <a href="${pageContext.request.contextPath}/controle?acao=cliente.listar" class="btn btn-primary">Ir para o início</a>
            </div>
        </div>
    </div>
</div>

</body>
</html>
