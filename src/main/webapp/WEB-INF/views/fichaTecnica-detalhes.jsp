<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Ficha Técnica #${fn:escapeXml(fichaTecnica.id)} - Assistência Técnica</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css?v=7">
</head>
<body>

<jsp:include page="/WEB-INF/views/fragments/navbar.jsp">
    <jsp:param name="active" value="ordens"/>
</jsp:include>

<div class="container">

    <div class="card">
        <h2 class="card-title"> Detalhes da Ficha Técnica #${fn:escapeXml(fichaTecnica.id)}</h2>

        <form action="${pageContext.request.contextPath}/controle" method="post">
            <input type="hidden" name="acao" value="fichaTecnica.atualizar">
            <input type="hidden" name="id" value="${fn:escapeXml(fichaTecnica.id)}">
            <input type="hidden" name="csrfToken" value="${fn:escapeXml(csrfToken)}">

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
                    <input type="text" id="acessoriosEntregues" name="acessoriosEntregues" value="${fn:escapeXml(fichaTecnica.acessoriosEntregues)}" maxlength="500">
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
                    <textarea id="descricaoAvarias" name="descricaoAvarias" rows="2" maxlength="1000">${fn:escapeXml(fichaTecnica.descricaoAvarias)}</textarea>
                </div>
                <div class="form-group full-width">
                    <label for="testeInicial">Teste Inicial</label>
                    <textarea id="testeInicial" name="testeInicial" rows="2" maxlength="2000">${fn:escapeXml(fichaTecnica.testeInicial)}</textarea>
                </div>
                <div class="form-group full-width">
                    <label for="laudoTecnicoFinal">Laudo Técnico Final (Obrigatório para Concluir OS)</label>
                    <textarea id="laudoTecnicoFinal" name="laudoTecnicoFinal" rows="3" maxlength="4000">${fn:escapeXml(fichaTecnica.laudoTecnicoFinal)}</textarea>
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
