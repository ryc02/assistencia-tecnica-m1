<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Detalhes do Equipamento - Assistência Técnica</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css?v=7">
</head>
<body>

<jsp:include page="/WEB-INF/views/fragments/navbar.jsp">
    <jsp:param name="active" value="equipamentos"/>
</jsp:include>

<div class="container">

    <div class="card">
        <h2 class="card-title"> Detalhes do Equipamento #${fn:escapeXml(equipamento.id)}</h2>
        <form action="${pageContext.request.contextPath}/controle" method="post">
            <input type="hidden" name="acao" value="equipamento.atualizar">
            <input type="hidden" name="id" value="${fn:escapeXml(equipamento.id)}">
            <input type="hidden" name="csrfToken" value="${fn:escapeXml(csrfToken)}">

            <div class="form-grid">
                <div class="form-group">
                    <label for="clienteId">Cliente Proprietário</label>
                    <select id="clienteId" name="clienteId" required>
                        <c:forEach var="cli" items="${clientes}">
                            <option value="${fn:escapeXml(cli.id)}" ${equipamento.cliente.id == cli.id ? 'selected' : ''}>
                                ${fn:escapeXml(cli.nome)} (CPF: ${fn:escapeXml(cli.cpf)})
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label for="tipo">Tipo de Equipamento</label>
                    <input type="text" id="tipo" name="tipo" value="${fn:escapeXml(equipamento.tipo)}" required maxlength="60">
                </div>
                <div class="form-group">
                    <label for="marca">Marca</label>
                    <input type="text" id="marca" name="marca" value="${fn:escapeXml(equipamento.marca)}" required maxlength="60">
                </div>
                <div class="form-group">
                    <label for="modelo">Modelo</label>
                    <input type="text" id="modelo" name="modelo" value="${fn:escapeXml(equipamento.modelo)}" required maxlength="80">
                </div>
                <div class="form-group">
                    <label for="numeroSerie">Número de Série</label>
                    <input type="text" id="numeroSerie" name="numeroSerie" value="${fn:escapeXml(equipamento.numeroSerie)}" maxlength="100">
                </div>
                <div class="form-group">
                    <label for="cor">Cor</label>
                    <input type="text" id="cor" name="cor" value="${fn:escapeXml(equipamento.cor)}" maxlength="40">
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
                    <textarea id="descricao" name="descricao" rows="2" maxlength="1000">${fn:escapeXml(equipamento.descricao)}</textarea>
                </div>
            </div>

            <div class="actions-bar">
                <button type="submit" class="btn btn-primary">✏ Atualizar Equipamento</button>
                <a href="${pageContext.request.contextPath}/controle?acao=equipamento.listar" class="btn btn-secondary">⬅ Voltar</a>
            </div>
        </form>

        <form action="${pageContext.request.contextPath}/controle" method="post" style="margin-top: 10px;" onsubmit="return confirm('Deseja realmente excluir este equipamento?');">
            <input type="hidden" name="acao" value="equipamento.excluir">
            <input type="hidden" name="id" value="${fn:escapeXml(equipamento.id)}">
            <input type="hidden" name="csrfToken" value="${fn:escapeXml(csrfToken)}">
            <button type="submit" class="btn btn-danger"> Excluir Equipamento</button>
        </form>
    </div>

    <div class="card">
        <h2 class="card-title">
             Orçamentos deste Equipamento
            <a href="${pageContext.request.contextPath}/controle?acao=orcamento.listar&equipamentoId=${fn:escapeXml(equipamento.id)}" class="btn btn-sm btn-primary"> Criar Novo Orçamento</a>
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
                        <td>${fn:escapeXml(orc.id)}</td>
                        <td>${fn:escapeXml(orc.descricaoProblema)}</td>
                        <td><span class="badge badge-${orc.status.name().toLowerCase()}">${fn:escapeXml(orc.status)}</span></td>
                        <td><strong>R$ ${fn:escapeXml(orc.valorTotal)}</strong></td>
                        <td>
                            <a href="${pageContext.request.contextPath}/controle?acao=orcamento.consultar&id=${fn:escapeXml(orc.id)}" class="btn btn-sm btn-primary"> Ver Detalhes</a>
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
