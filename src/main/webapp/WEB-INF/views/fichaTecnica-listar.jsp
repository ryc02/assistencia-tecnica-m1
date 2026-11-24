<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Fichas Técnicas - Assistência Técnica</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css?v=7">
</head>
<body>

<jsp:include page="/WEB-INF/views/fragments/navbar.jsp">
    <jsp:param name="active" value="ordens"/>
</jsp:include>

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
                        <td><strong>#${fn:escapeXml(ficha.id)}</strong></td>
                        <td>${fn:escapeXml(ficha.estadoConservacao)}</td>
                        <td>${ficha.ligaNormalmente == null ? 'Não verificado' : (ficha.ligaNormalmente ? 'Sim' : 'Não')}</td>
                        <td>${ficha.possuiAvarias == null ? 'Não verificado' : (ficha.possuiAvarias ? 'Sim' : 'Não')}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/controle?acao=fichaTecnica.consultar&id=${fn:escapeXml(ficha.id)}" class="btn btn-sm btn-primary"> Consultar Ficha</a>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty fichas}">
                    <tr>
                        <td colspan="5" style="text-align: center; color: hsl(var(--muted-foreground));">Nenhuma ficha técnica registrada.</td>
                    </tr>
                </c:if>
                </tbody>
            </table>
        </div>
    </div>

</div>

</body>
</html>
