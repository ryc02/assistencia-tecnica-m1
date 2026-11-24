<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Equipamentos - Assistência Técnica</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css?v=7">
</head>
<body>

<jsp:include page="/WEB-INF/views/fragments/navbar.jsp">
    <jsp:param name="active" value="equipamentos"/>
</jsp:include>

<div class="container">

    <div class="card">
        <h2 class="card-title"> Cadastrar Novo Equipamento</h2>
        <form action="${pageContext.request.contextPath}/controle" method="post">
            <input type="hidden" name="acao" value="equipamento.inserir">
            <input type="hidden" name="csrfToken" value="${fn:escapeXml(csrfToken)}">

            <div class="form-grid">
                <div class="form-group">
                    <label for="clienteId">Cliente Proprietário *</label>
                    <select id="clienteId" name="clienteId" required>
                        <option value="">-- Selecione o Cliente --</option>
                        <c:forEach var="cli" items="${clientes}">
                            <option value="${fn:escapeXml(cli.id)}" ${clienteFiltro != null && clienteFiltro.id == cli.id ? 'selected' : ''}>
                                ${fn:escapeXml(cli.nome)} (CPF: ${fn:escapeXml(cli.cpf)})
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label for="tipo">Tipo de Equipamento *</label>
                    <input type="text" id="tipo" name="tipo" required maxlength="60" placeholder="Ex: Notebook, Impressora">
                </div>
                <div class="form-group">
                    <label for="marca">Marca *</label>
                    <input type="text" id="marca" name="marca" required maxlength="60" placeholder="Ex: Dell, Epson">
                </div>
                <div class="form-group">
                    <label for="modelo">Modelo *</label>
                    <input type="text" id="modelo" name="modelo" required maxlength="80" placeholder="Ex: Inspirion 15">
                </div>
                <div class="form-group">
                    <label for="numeroSerie">Número de Série</label>
                    <input type="text" id="numeroSerie" name="numeroSerie" maxlength="100" placeholder="SN-12345">
                </div>
                <div class="form-group">
                    <label for="cor">Cor</label>
                    <input type="text" id="cor" name="cor" maxlength="40" placeholder="Preto / Prata">
                </div>
                <div class="form-group">
                    <label for="voltagem">Voltagem *</label>
                    <select id="voltagem" name="voltagem" required>
                        <option value="BIVOLT">BIVOLT</option>
                        <option value="110V">110V</option>
                        <option value="127V">127V</option>
                        <option value="220V">220V</option>
                        <option value="NAO_APLICAVEL">NÃO APLICÁVEL</option>
                    </select>
                </div>
                <div class="form-group full-width">
                    <label for="descricao">Descrição / Observações</label>
                    <textarea id="descricao" name="descricao" rows="2" maxlength="1000" placeholder="Problemas relatados previamente ou estado geral..."></textarea>
                </div>
            </div>
            <div style="margin-top: 16px;">
                <button type="submit" class="btn btn-primary"> Salvar Equipamento</button>
            </div>
        </form>
    </div>

    <div class="card">
        <h2 class="card-title">
             Equipamentos Cadastrados
            <c:if test="${clienteFiltro != null}">
                <span style="font-size: 0.9rem; font-weight: normal;">(Filtrado por Cliente: <strong>${fn:escapeXml(clienteFiltro.nome)}</strong>)</span>
            </c:if>
        </h2>
        <div class="table-responsive">
            <table>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Cliente</th>
                    <th>Tipo</th>
                    <th>Marca/Modelo</th>
                    <th>Voltagem</th>
                    <th>Ações</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="equip" items="${equipamentos}">
                    <tr>
                        <td>${fn:escapeXml(equip.id)}</td>
                        <td><a href="${pageContext.request.contextPath}/controle?acao=cliente.consultar&id=${fn:escapeXml(equip.cliente.id)}" class="link-vinculo">${fn:escapeXml(equip.cliente.nome)}</a></td>
                        <td>${fn:escapeXml(equip.tipo)}</td>
                        <td><strong><a href="${pageContext.request.contextPath}/controle?acao=equipamento.consultar&id=${fn:escapeXml(equip.id)}" class="link-vinculo">${fn:escapeXml(equip.marca)} ${fn:escapeXml(equip.modelo)}</a></strong></td>
                        <td>${fn:escapeXml(equip.voltagem)}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/controle?acao=equipamento.consultar&id=${fn:escapeXml(equip.id)}" class="btn btn-sm btn-primary"> Detalhes</a>
                            <a href="${pageContext.request.contextPath}/controle?acao=orcamento.listar&equipamentoId=${fn:escapeXml(equip.id)}" class="btn btn-sm btn-secondary"> Orçamentos</a>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty equipamentos}">
                    <tr>
                        <td colspan="6" style="text-align: center; color: var(--text-muted);">Nenhum equipamento encontrado.</td>
                    </tr>
                </c:if>
                </tbody>
            </table>
        </div>
    </div>

</div>

</body>
</html>
