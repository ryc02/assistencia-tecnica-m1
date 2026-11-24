<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Detalhes do Cliente - Assistência Técnica</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css?v=7">
</head>
<body>

<jsp:include page="/WEB-INF/views/fragments/navbar.jsp">
    <jsp:param name="active" value="clientes"/>
</jsp:include>

<div class="container">

    <div class="card">
        <h2 class="card-title"> Detalhes do Cliente #${fn:escapeXml(cliente.id)}</h2>
        <form action="${pageContext.request.contextPath}/controle" method="post">
            <input type="hidden" name="acao" value="cliente.atualizar">
            <input type="hidden" name="id" value="${fn:escapeXml(cliente.id)}">
            <input type="hidden" name="csrfToken" value="${fn:escapeXml(csrfToken)}">

            <div class="form-grid">
                <div class="form-group">
                    <label for="nome">Nome Completo</label>
                    <input type="text" id="nome" name="nome" value="${fn:escapeXml(cliente.nome)}" required maxlength="120">
                </div>
                <div class="form-group">
                    <label for="cpf">CPF</label>
                    <input type="text" id="cpf" name="cpf" value="${fn:escapeXml(cliente.cpf)}" required maxlength="14">
                </div>
                <div class="form-group">
                    <label for="email">E-mail</label>
                    <input type="email" id="email" name="email" value="${fn:escapeXml(cliente.email)}" maxlength="254">
                </div>
                <div class="form-group">
                    <label for="telefone">Telefone</label>
                    <input type="text" id="telefone" name="telefone" value="${fn:escapeXml(cliente.telefone)}" required maxlength="20">
                </div>
                <div class="form-group">
                    <label for="logradouro">Logradouro</label>
                    <input type="text" id="logradouro" name="logradouro" value="${fn:escapeXml(cliente.logradouro)}" required maxlength="150">
                </div>
                <div class="form-group">
                    <label for="numero">Número</label>
                    <input type="text" id="numero" name="numero" value="${fn:escapeXml(cliente.numero)}" required maxlength="20">
                </div>
                <div class="form-group">
                    <label for="bairro">Bairro</label>
                    <input type="text" id="bairro" name="bairro" value="${fn:escapeXml(cliente.bairro)}" required maxlength="80">
                </div>
                <div class="form-group">
                    <label for="cidade">Cidade</label>
                    <input type="text" id="cidade" name="cidade" value="${fn:escapeXml(cliente.cidade)}" required maxlength="80">
                </div>
            </div>

            <div class="actions-bar">
                <button type="submit" class="btn btn-primary">✏ Atualizar Cliente</button>
                <a href="${pageContext.request.contextPath}/controle?acao=cliente.listar" class="btn btn-secondary">⬅ Voltar</a>
            </div>
        </form>

        <form action="${pageContext.request.contextPath}/controle" method="post" style="margin-top: 10px;" onsubmit="return confirm('Deseja realmente excluir este cliente?');">
            <input type="hidden" name="acao" value="cliente.excluir">
            <input type="hidden" name="id" value="${fn:escapeXml(cliente.id)}">
            <input type="hidden" name="csrfToken" value="${fn:escapeXml(csrfToken)}">
            <button type="submit" class="btn btn-danger"> Excluir Cliente</button>
        </form>
    </div>

    <div class="card">
        <h2 class="card-title">
             Equipamentos deste Cliente
            <a href="${pageContext.request.contextPath}/controle?acao=equipamento.listar&clienteId=${fn:escapeXml(cliente.id)}" class="btn btn-sm btn-primary"> Novo Equipamento</a>
        </h2>
        <div class="table-responsive">
            <table>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Tipo</th>
                    <th>Marca</th>
                    <th>Modelo</th>
                    <th>Série</th>
                    <th>Ações</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="equip" items="${equipamentos}">
                    <tr>
                        <td>${fn:escapeXml(equip.id)}</td>
                        <td>${fn:escapeXml(equip.tipo)}</td>
                        <td>${fn:escapeXml(equip.marca)}</td>
                        <td><strong><a href="${pageContext.request.contextPath}/controle?acao=equipamento.consultar&id=${fn:escapeXml(equip.id)}" class="link-vinculo">${fn:escapeXml(equip.modelo)}</a></strong></td>
                        <td>${equip.numeroSerie != null ? fn:escapeXml(equip.numeroSerie) : 'N/I'}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/controle?acao=equipamento.consultar&id=${fn:escapeXml(equip.id)}" class="btn btn-sm btn-primary"> Detalhes</a>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty equipamentos}">
                    <tr>
                        <td colspan="6" style="text-align: center; color: var(--text-muted);">Nenhum equipamento cadastrado para este cliente.</td>
                    </tr>
                </c:if>
                </tbody>
            </table>
        </div>
    </div>

</div>

</body>
</html>
