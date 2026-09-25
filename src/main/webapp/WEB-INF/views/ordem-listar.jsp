<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Ordens de Serviço & Fichas - Assistência Técnica M1</title>
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
        <h2 class="card-title">
             Ordens de Serviço Cadastradas
            <c:if test="${orcamentoFiltro != null}">
                <span style="font-size: 0.9rem; font-weight: normal;">(Filtrado por Orçamento #${orcamentoFiltro.id})</span>
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
                        <td><strong>#${os.id}</strong></td>
                        <td>
                            <a href="${pageContext.request.contextPath}/controle?acao=orcamento.consultar&id=${os.orcamento.id}" class="link-vinculo">
                                Orçamento #${os.orcamento.id} (R$ ${os.orcamento.valorTotal})
                            </a>
                            <br><small style="color: var(--text-muted);">${os.orcamento.equipamento.tipo} ${os.orcamento.equipamento.modelo}</small>
                        </td>
                        <td>${os.responsavel}</td>
                        <td><span class="badge" style="background: #f1f5f9; color: #334155;">${os.prioridade}</span></td>
                        <td><span class="badge badge-${os.status.name().toLowerCase()}">${os.status}</span></td>
                        <td>
                            <a href="${pageContext.request.contextPath}/controle?acao=ordemServico.consultar&id=${os.id}" class="btn btn-sm btn-primary"> Ordem & Ficha Juntas</a>
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
