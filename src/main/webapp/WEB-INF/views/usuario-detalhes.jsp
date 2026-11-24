<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Novo Usuário - Assistência Técnica</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css?v=7">
</head>
<body>
<jsp:include page="/WEB-INF/views/fragments/navbar.jsp">
    <jsp:param name="active" value="usuarios"/>
</jsp:include>

    <main class="container">
        <div class="page-header">
            <div>
                <h1 class="page-title">Novo Usuário</h1>
                <p class="page-description">Preencha os dados abaixo para cadastrar um novo acesso ao sistema.</p>
            </div>
            <a href="${pageContext.request.contextPath}/controle?acao=usuario.listar" class="btn btn-outline">Voltar</a>
        </div>

        <c:if test="${not empty erro}">
            <div class="alert alert-error">${fn:escapeXml(erro)}</div>
        </c:if>

        <div class="card" style="max-width: 600px;">
            <form action="${pageContext.request.contextPath}/controle" method="post">
                <input type="hidden" name="acao" value="usuario.inserir">
                <input type="hidden" name="csrfToken" value="${fn:escapeXml(csrfToken)}">
                
                <div class="form-group">
                    <label for="nome">Nome Completo</label>
                    <input type="text" id="nome" name="nome" class="form-control" required placeholder="Ex: João da Silva">
                </div>

                <div class="form-group">
                    <label for="email">E-mail Corporativo</label>
                    <input type="email" id="email" name="email" class="form-control" required placeholder="joao@m1.com">
                </div>

                <div class="form-group">
                    <label for="senha">Senha de Acesso</label>
                    <input type="password" id="senha" name="senha" class="form-control" required placeholder="••••••••">
                </div>

                <div class="form-group">
                    <label for="cargo">Cargo / Perfil</label>
                    <select id="cargo" name="cargo" class="form-control" required>
                        <option value="">Selecione...</option>
                        <option value="Administrador">Administrador</option>
                        <option value="Técnico">Técnico</option>
                        <option value="Atendimento">Atendimento</option>
                    </select>
                </div>

                <div style="margin-top: 2rem;">
                    <button type="submit" class="btn btn-primary">Salvar Usuário</button>
                    <a href="${pageContext.request.contextPath}/controle?acao=usuario.listar" class="btn btn-outline">Cancelar</a>
                </div>
            </form>
        </div>
    </main>
</body>
</html>
