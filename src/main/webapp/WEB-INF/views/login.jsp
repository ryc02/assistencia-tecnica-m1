<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Login - Assistência Técnica M1</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css?v=5">
    <style>
        body {
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100vh;
            margin: 0;
            background-color: hsl(var(--background));
        }
        .login-card {
            width: 100%;
            max-width: 400px;
            margin: 0 1rem;
        }
        input[type="email"], input[type="password"] {
            display: flex;
            width: 100%;
            border-radius: calc(var(--radius) - 2px);
            border: 1px solid hsl(var(--input));
            background-color: transparent;
            padding: 0.5rem 0.75rem;
            font-size: 0.875rem;
            line-height: 1.25rem;
            color: hsl(var(--foreground));
            transition: box-shadow 0.2s, border-color 0.2s;
            font-family: inherit;
        }
        input:focus {
            outline: none;
            border-color: hsl(var(--ring));
            box-shadow: 0 0 0 1px hsl(var(--ring));
        }
    </style>
</head>
<body>
    <div class="card login-card">
        <h2 class="card-title" style="justify-content: center; font-size: 1.5rem; border: none; padding-bottom: 0;">Acesso ao Sistema</h2>
        <form action="${pageContext.request.contextPath}/controle" method="post">
            <input type="hidden" name="acao" value="efetuarLogin">
            <input type="hidden" name="csrfToken" value="${csrfToken}">
            
            <div class="form-group" style="margin-bottom: 1.5rem;">
                <label for="email">E-mail</label>
                <input type="email" id="email" name="email" required placeholder="admin@m1.com" autofocus>
            </div>
            
            <div class="form-group" style="margin-bottom: 2rem;">
                <label for="senha">Senha</label>
                <input type="password" id="senha" name="senha" required placeholder="••••••••">
            </div>
            
            <button type="submit" class="btn btn-primary" style="width: 100%;">Entrar</button>
            
        </form>
    </div>
</body>
</html>
