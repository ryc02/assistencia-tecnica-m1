<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Clientes - Assistência Técnica</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css?v=7">
</head>
<body>

<jsp:include page="/WEB-INF/views/fragments/navbar.jsp">
    <jsp:param name="active" value="clientes"/>
</jsp:include>

<div class="container">

    <div class="card">
        <h2 class="card-title"> Cadastrar Novo Cliente</h2>
        <form action="${pageContext.request.contextPath}/controle" method="post">
            <input type="hidden" name="acao" value="cliente.inserir">
            <input type="hidden" name="csrfToken" value="${fn:escapeXml(csrfToken)}">

            <div class="form-grid">
                <div class="form-group">
                    <label for="nome">Nome Completo *</label>
                    <input type="text" id="nome" name="nome" required maxlength="120" placeholder="Ex: João da Silva">
                </div>
                <div class="form-group">
                    <label for="cpf">CPF (11 dígitos) *</label>
                    <input type="text" id="cpf" name="cpf" required maxlength="14" placeholder="000.000.000-00">
                </div>
                <div class="form-group">
                    <label for="email">E-mail</label>
                    <input type="email" id="email" name="email" maxlength="254" placeholder="cliente@email.com">
                </div>
                <div class="form-group">
                    <label for="telefone">Telefone *</label>
                    <input type="text" id="telefone" name="telefone" required maxlength="20" placeholder="(00) 00000-0000">
                </div>
                <div class="form-group">
                    <label for="logradouro">Logradouro *</label>
                    <input type="text" id="logradouro" name="logradouro" required maxlength="150" placeholder="Rua / Av">
                </div>
                <div class="form-group">
                    <label for="numero">Número *</label>
                    <input type="text" id="numero" name="numero" required maxlength="20" placeholder="123">
                </div>
                <div class="form-group">
                    <label for="bairro">Bairro *</label>
                    <input type="text" id="bairro" name="bairro" required maxlength="80" placeholder="Centro">
                </div>
                <div class="form-group">
                    <label for="cidade">Cidade *</label>
                    <input type="text" id="cidade" name="cidade" required maxlength="80" placeholder="Joinville">
                </div>
            </div>
            <div style="margin-top: 16px;">
                <button type="submit" class="btn btn-primary"> Salvar Cliente</button>
            </div>
        </form>
    </div>

    <div class="card">
        <h2 class="card-title"> Clientes Cadastrados</h2>
        <div class="table-responsive">
            <table>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Nome</th>
                    <th>CPF</th>
                    <th>Telefone</th>
                    <th>Cidade</th>
                    <th>Ações</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="c" items="${clientes}">
                    <tr>
                        <td>${fn:escapeXml(c.id)}</td>
                        <td><strong><a href="${pageContext.request.contextPath}/controle?acao=cliente.consultar&id=${fn:escapeXml(c.id)}" class="link-vinculo">${fn:escapeXml(c.nome)}</a></strong></td>
                        <td>${fn:escapeXml(c.cpf)}</td>
                        <td>${fn:escapeXml(c.telefone)}</td>
                        <td>${fn:escapeXml(c.cidade)}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/controle?acao=cliente.consultar&id=${fn:escapeXml(c.id)}" class="btn btn-sm btn-primary"> Detalhes</a>
                            <a href="${pageContext.request.contextPath}/controle?acao=equipamento.listar&clienteId=${fn:escapeXml(c.id)}" class="btn btn-sm btn-secondary"> Equipamentos</a>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty clientes}">
                    <tr>
                        <td colspan="6" style="text-align: center; color: var(--text-muted);">Nenhum cliente cadastrado.</td>
                    </tr>
                </c:if>
                </tbody>
            </table>
        </div>
    </div>

</div>

</body>
</html>
