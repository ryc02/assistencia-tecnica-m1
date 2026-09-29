<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Acesso Negado - Assistência Técnica M1</title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css?v=6">
    <style>
        body {
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100vh;
            margin: 0;
            background-color: hsl(var(--background));
            font-family: 'Inter', sans-serif;
            color: hsl(var(--foreground));
        }
        .error-wrapper {
            text-align: center;
            padding: 2rem;
            animation: fadeIn 0.4s ease-out;
        }
        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(12px); }
            to   { opacity: 1; transform: translateY(0); }
        }
        .error-code {
            font-size: 6rem;
            font-weight: 700;
            color: hsl(0 84.2% 60.2%);
            line-height: 1;
            margin-bottom: 0.5rem;
        }
        .error-title {
            font-size: 1.5rem;
            font-weight: 600;
            margin-bottom: 0.75rem;
            color: hsl(var(--foreground));
        }
        .error-msg {
            color: hsl(var(--muted-foreground));
            font-size: 0.95rem;
            margin-bottom: 2rem;
            max-width: 380px;
        }
        .badge-cargo {
            display: inline-block;
            background: hsl(0 84.2% 60.2% / 0.12);
            color: hsl(0 84.2% 60.2%);
            border: 1px solid hsl(0 84.2% 60.2% / 0.3);
            border-radius: 999px;
            padding: 0.25rem 0.85rem;
            font-size: 0.8rem;
            font-weight: 600;
            margin-bottom: 1.5rem;
        }
    </style>
</head>
<body>
<div class="error-wrapper">
    <div class="error-code">403</div>
    <h1 class="error-title">Acesso Negado</h1>
    <p class="error-msg">
        Você não tem permissão para acessar esta funcionalidade.<br>
        Esta ação é restrita ao perfil <strong>Administrador</strong>.
    </p>
    <div class="badge-cargo">
        Seu perfil: ${sessionScope.usuarioLogado.cargo}
    </div>
    <br>
    <a href="${pageContext.request.contextPath}/controle?acao=ordemServico.listar" class="btn btn-primary">
        Voltar para Ordens de Serviço
    </a>
</div>
</body>
</html>
