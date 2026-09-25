<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Ficha Técnica #${fichaTecnica.id} - Assistência Técnica M1</title>
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
        <h2 class="card-title"> Detalhes da Ficha Técnica #${fichaTecnica.id}</h2>

        <form action="${pageContext.request.contextPath}/controle" method="post">
            <input type="hidden" name="acao" value="fichaTecnica.atualizar">
            <input type="hidden" name="id" value="${fichaTecnica.id}">
            <input type="hidden" name="csrfToken" value="${csrfToken}">

            <div class="form-grid">
                <div class="form-group">
                    <label for="estadoConservacao">Estado de Conservação *</label>
                    <select id="estadoConservacao" name="estadoConservacao" required>
                        <option value="BOM" ${fichaTecnica.estadoConservacao == 'BOM' ? 'selected' : ''}>BOM</option>
                        <option value="REGULAR" ${fichaTecnica.estadoConservacao == 'REGULAR' ? 'selected' : ''}>REGULAR</option>
                        <option value="RUIM" ${fichaTecnica.estadoConservacao == 'RUIM' ? 'selected' : ''}>RUIM</option>
                        <option value="NAO_AVALIADO" ${fichaTecnica.estadoConservacao == 'NAO_AVALIADO' ? 'selected' : ''}>NÃO AVALIADO</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="acessoriosEntregues">Acessórios Entregues</label>
                    <input type="text" id="acessoriosEntregues" name="acessoriosEntregues" value="${fichaTecnica.acessoriosEntregues}" maxlength="500">
                </div>
                <div class="form-group">
                    <label for="ligaNormalmente">Liga Normalmente?</label>
                    <select id="ligaNormalmente" name="ligaNormalmente">
                        <option value="true" ${fichaTecnica.ligaNormalmente == true ? 'selected' : ''}>Sim</option>
                        <option value="false" ${fichaTecnica.ligaNormalmente == false ? 'selected' : ''}>Não</option>
                        <option value="" ${fichaTecnica.ligaNormalmente == null ? 'selected' : ''}>Não verificado</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="possuiAvarias">Possui Avarias Visíveis?</label>
                    <select id="possuiAvarias" name="possuiAvarias">
                        <option value="true" ${fichaTecnica.possuiAvarias == true ? 'selected' : ''}>Sim</option>
                        <option value="false" ${fichaTecnica.possuiAvarias == false ? 'selected' : ''}>Não</option>
                        <option value="" ${fichaTecnica.possuiAvarias == null ? 'selected' : ''}>Não verificado</option>
                    </select>
                </div>
                <div class="form-group full-width">
                    <label for="descricaoAvarias">Descrição das Avarias (se houver)</label>
                    <textarea id="descricaoAvarias" name="descricaoAvarias" rows="2" maxlength="1000">${fichaTecnica.descricaoAvarias}</textarea>
                </div>
                <div class="form-group full-width">
                    <label for="testeInicial">Teste Inicial</label>
                    <textarea id="testeInicial" name="testeInicial" rows="2" maxlength="2000">${fichaTecnica.testeInicial}</textarea>
                </div>
                <div class="form-group full-width">
                    <label for="laudoTecnicoFinal">Laudo Técnico Final (Obrigatório para Concluir OS)</label>
                    <textarea id="laudoTecnicoFinal" name="laudoTecnicoFinal" rows="3" maxlength="4000">${fichaTecnica.laudoTecnicoFinal}</textarea>
                </div>
            </div>

            <div class="actions-bar">
                <button type="submit" class="btn btn-primary"> Salvar Ficha Técnica</button>
            </div>
        </form>
    </div>

</div>

</body>
</html>
