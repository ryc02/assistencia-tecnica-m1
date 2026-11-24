<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Ordens de Serviço & Fichas - Assistência Técnica</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css?v=7">
</head>
<body>

<jsp:include page="/WEB-INF/views/fragments/navbar.jsp">
    <jsp:param name="active" value="ordens"/>
</jsp:include>

<div class="container">

    <div class="card">
        <h2 class="card-title">
             Ordens de Serviço Cadastradas
            <c:if test="${orcamentoFiltro != null}">
                <span style="font-size: 0.9rem; font-weight: normal;">(Filtrado por Orçamento #${fn:escapeXml(orcamentoFiltro.id)})</span>
            </c:if>
        </h2>

        <div class="table-responsive">
            <table>
                <thead>
                <tr>
                    <th>ID OS</th>
                    <th>Orçamento / Equipamento</th>
                    <th>Responsável</th>
                    <th>Prioridade</th>
                    <th>Status OS</th>
                    <th>Ações</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="os" items="${ordens}">
                    <tr>
                        <td><strong>#${fn:escapeXml(os.id)}</strong></td>
                        <td>
                            <a href="${pageContext.request.contextPath}/controle?acao=orcamento.consultar&id=${fn:escapeXml(os.orcamento.id)}" class="link-vinculo">
                                Orçamento #${fn:escapeXml(os.orcamento.id)} (R$ ${fn:escapeXml(os.orcamento.valorTotal)})
                            </a>
                            <br><small style="color: var(--text-muted);">${fn:escapeXml(os.orcamento.equipamento.tipo)} ${fn:escapeXml(os.orcamento.equipamento.modelo)}</small>
                        </td>
                        <td>${fn:escapeXml(os.responsavel)}</td>
                        <td><span class="badge" style="background: #f1f5f9; color: #334155;">${fn:escapeXml(os.prioridade)}</span></td>
                        <td><span class="badge badge-${os.status.name().toLowerCase()}">${fn:escapeXml(os.status)}</span></td>
                        <td>
                            <a href="${pageContext.request.contextPath}/controle?acao=ordemServico.consultar&id=${fn:escapeXml(os.id)}" class="btn btn-sm btn-primary"> Ordem & Ficha Juntas</a>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty ordens}">
                    <tr>
                        <td colspan="6" style="text-align: center; color: var(--text-muted);">Nenhuma ordem de serviço cadastrada. Para gerar uma ordem, aprove um orçamento pendente.</td>
                    </tr>
                </c:if>
                </tbody>
            </table>
        </div>
    </div>

</div>

</body>
</html>
