<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Novo Usuário - Assistência Técnica M1</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css?v=6">
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
        <li><a href="${pageContext.request.contextPath}/controle?acao=ordemServico.listar">Ordens & Fichas</a></li>
        <li><a href="${pageContext.request.contextPath}/controle?acao=usuario.listar" class="active">Usuários</a></li>
    </ul>

    <div class="user-actions" style="display: flex; align-items: center; gap: 1rem; font-size: 0.875rem;">
        <span style="color: var(--muted-foreground);">Olá, <strong>${sessionScope.usuarioLogado}</strong></span>
        <a href="${pageContext.request.contextPath}/controle?acao=logout" style="color: hsl(0 84.2% 60.2%); text-decoration: none; font-weight: 500;">Sair</a>
    </div>
</nav>

    <main class="container">
        <div class="page-header">
            <div>
                <h1 class="page-title">Novo Usuário</h1>
                <p class="page-description">Preencha os dados abaixo para cadastrar um novo acesso ao sistema.</p>
            </div>
            <a href="${pageContext.request.contextPath}/controle?acao=usuario.listar" class="btn btn-outline">Voltar</a>
        </div>

        <c:if test="${not empty erro}">
            <div class="alert alert-error">${erro}</div>
        </c:if>

        <div class="card" style="max-width: 600px;">
            <form action="${pageContext.request.contextPath}/controle" method="post">
                <input type="hidden" name="acao" value="usuario.inserir">
                <input type="hidden" name="csrfToken" value="${csrfToken}">
                
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
