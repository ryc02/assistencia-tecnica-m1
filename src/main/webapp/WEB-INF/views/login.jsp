<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Login - Assistência Técnica M1</title>
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
        
        .login-wrapper {
            width: 100%;
            max-width: 400px;
            padding: 0 1rem;
            animation: fadeIn 0.4s ease-out;
        }

        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(10px); }
            to { opacity: 1; transform: translateY(0); }
        }

        .login-card {
            background-color: hsl(var(--card));
            color: hsl(var(--card-foreground));
            border: 1px solid hsl(var(--border));
            border-radius: var(--radius);
            padding: 2rem 1.5rem;
            box-shadow: 0 4px 6px -1px rgb(0 0 0 / 0.05), 0 2px 4px -2px rgb(0 0 0 / 0.05);
        }

        .login-header {
            display: flex;
            flex-direction: column;
            align-items: center;
            text-align: center;
            margin-bottom: 2rem;
        }

        .logo-icon {
            width: 40px;
            height: 40px;
            background-color: hsl(var(--primary));
            color: hsl(var(--primary-foreground));
            border-radius: 8px;
            display: flex;
            align-items: center;
            justify-content: center;
            margin-bottom: 1rem;
        }

        .logo-icon svg {
            width: 24px;
            height: 24px;
        }

        .card-title {
            font-size: 1.25rem;
            font-weight: 600;
            letter-spacing: -0.025em;
            margin: 0 0 0.25rem 0;
            border: none;
            padding: 0;
        }

        .card-subtitle {
            font-size: 0.875rem;
            color: hsl(var(--muted-foreground));
            margin: 0;
        }

        .form-group {
            margin-bottom: 1.25rem;
        }

        .form-group-flex {
            display: flex;
            align-items: center;
            justify-content: space-between;
            margin-bottom: 0.5rem;
        }

        label {
            display: block;
            font-size: 0.875rem;
            font-weight: 500;
            color: hsl(var(--foreground));
        }
        
        .forgot-password {
            font-size: 0.75rem;
            color: hsl(var(--primary));
            text-decoration: none;
            font-weight: 500;
        }

        .forgot-password:hover {
            text-decoration: underline;
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

        input[type="email"]:focus, input[type="password"]:focus {
            outline: none;
            border-color: hsl(var(--ring));
            box-shadow: 0 0 0 1px hsl(var(--ring));
        }

        input::placeholder {
            color: hsl(var(--muted-foreground));
        }

        .btn-primary {
            display: inline-flex;
            align-items: center;
            justify-content: center;
            width: 100%;
            border-radius: calc(var(--radius) - 2px);
            font-size: 0.875rem;
            font-weight: 500;
            height: 2.5rem;
            padding: 0 1rem;
            background-color: hsl(var(--primary));
            color: hsl(var(--primary-foreground));
            border: none;
            cursor: pointer;
            transition: background-color 0.2s;
            margin-top: 1rem;
        }

        .btn-primary:hover {
            background-color: hsl(var(--primary) / 0.9);
        }
    </style>
</head>
<body>
    <div class="login-wrapper">
        <div class="login-card">
            <div class="login-header">
                <div class="logo-icon">
                    <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor">
                        <path stroke-linecap="round" stroke-linejoin="round" d="M10.343 3.94c.09-.542.56-.94 1.11-.94h1.093c.55 0 1.02.398 1.11.94l.149.894c.07.424.384.764.78.93.398.164.855.142 1.205-.108l.737-.527a1.125 1.125 0 011.45.12l.773.774c.39.389.44 1.002.12 1.45l-.527.737c-.25.35-.272.806-.107 1.204.165.397.505.71.93.78l.893.15c.543.09.94.56.94 1.109v1.094c0 .55-.397 1.02-.94 1.11l-.893.149c-.425.07-.765.383-.93.78-.165.398-.143.854.107 1.204l.527.738c.32.447.269 1.06-.12 1.45l-.774.773a1.125 1.125 0 01-1.449.12l-.738-.527c-.35-.25-.806-.272-1.203-.107-.397.165-.71.505-.781.929l-.149.894c-.09.542-.56.94-1.11.94h-1.094c-.55 0-1.019-.398-1.11-.94l-.148-.894c-.071-.424-.384-.764-.781-.93-.398-.164-.854-.142-1.204.108l-.738.527c-.447.32-1.06.269-1.45-.12l-.773-.774a1.125 1.125 0 01-.12-1.45l.527-.737c.25-.35.273-.806.108-1.204-.165-.397-.505-.71-.93-.78l-.894-.15c-.542-.09-.94-.56-.94-1.109v-1.094c0-.55.398-1.02.94-1.11l.894-.149c.424-.07.765-.383.93-.78.165-.398.143-.854-.107-1.204l-.527-.738a1.125 1.125 0 01.12-1.45l.773-.773a1.125 1.125 0 011.45-.12l.737.527c.35.25.807.272 1.204.107.397-.165.71-.505.78-.929l.15-.894z" />
                        <path stroke-linecap="round" stroke-linejoin="round" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z" />
                    </svg>
                </div>
                <h2 class="card-title">Acesso ao Sistema</h2>
                <p class="card-subtitle">Assistência Técnica M1</p>
            </div>
            
            <form action="${pageContext.request.contextPath}/controle" method="post">
                <input type="hidden" name="acao" value="efetuarLogin">
                <input type="hidden" name="csrfToken" value="${csrfToken}">
                
                <div class="form-group">
                    <label for="email">E-mail corporativo</label>
                    <input type="email" id="email" name="email" required placeholder="admin@m1.com" autofocus>
                </div>
                
                <div class="form-group">
                    <div class="form-group-flex">
                        <label for="senha" style="margin-bottom: 0;">Senha de acesso</label>
                        <a href="#" class="forgot-password" onclick="alert('Funcionalidade indisponível. Para recuperar a senha, entre em contato com o suporte em suporte@m1.com'); return false;">Esqueceu a senha?</a>
                    </div>
                    <input type="password" id="senha" name="senha" required placeholder="••••••••">
                </div>
                
                <button type="submit" class="btn-primary">Entrar</button>
            </form>
        </div>
    </div>
</body>
</html>
