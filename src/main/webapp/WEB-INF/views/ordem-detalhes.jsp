<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Atendimento OS #${fn:escapeXml(ordemServico.id)} - Assistência Técnica</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css?v=7">
</head>
<body>

<jsp:include page="/WEB-INF/views/fragments/navbar.jsp">
    <jsp:param name="active" value="ordens"/>
</jsp:include>

<div class="container">

    
    <div class="card" style="border-left: 6px solid hsl(var(--primary));">
        <div style="display: flex; justify-content: space-between; align-items: center; flex-wrap: wrap; gap: 10px;">
            <div>
                <h2 style="margin: 0;"> Ordem de Serviço #${fn:escapeXml(ordemServico.id)}</h2>
                <div style="margin-top: 4px;">
                    <strong>Status:</strong> <span class="badge badge-${ordemServico.status.name().toLowerCase()}">${fn:escapeXml(ordemServico.status)}</span>
                    &nbsp;|&nbsp; <strong>Prioridade:</strong> ${fn:escapeXml(ordemServico.prioridade)}
                    &nbsp;|&nbsp; <strong>Data Abertura:</strong> ${fn:escapeXml(ordemServico.dataAbertura)}
                </div>
            </div>

            <div class="actions-bar" style="margin: 0;">
                <c:if test="${ordemServico.status == 'ABERTA'}">
                    <form action="${pageContext.request.contextPath}/controle" method="post" style="display: inline;">
                        <input type="hidden" name="acao" value="ordemServico.iniciar">
                        <input type="hidden" name="id" value="${fn:escapeXml(ordemServico.id)}">
                        <input type="hidden" name="csrfToken" value="${fn:escapeXml(csrfToken)}">
                        <button type="submit" class="btn btn-primary"> Iniciar Atendimento</button>
                    </form>

                    <form action="${pageContext.request.contextPath}/controle" method="post" style="display: inline;" onsubmit="return confirm('Excluir esta Ordem e Ficha e retornar o orçamento a PENDENTE?');">
                        <input type="hidden" name="acao" value="ordemServico.excluir">
                        <input type="hidden" name="id" value="${fn:escapeXml(ordemServico.id)}">
                        <input type="hidden" name="csrfToken" value="${fn:escapeXml(csrfToken)}">
                        <button type="submit" class="btn btn-danger"> Excluir Ordem Aberta</button>
                    </form>
                </c:if>

                <c:if test="${ordemServico.status == 'EM_ANDAMENTO'}">
                    <form action="${pageContext.request.contextPath}/controle" method="post" style="display: inline;">
                        <input type="hidden" name="acao" value="ordemServico.concluir">
                        <input type="hidden" name="id" value="${fn:escapeXml(ordemServico.id)}">
                        <input type="hidden" name="csrfToken" value="${fn:escapeXml(csrfToken)}">
                        <button type="submit" class="btn btn-success"> Concluir Atendimento</button>
                    </form>
                </c:if>

                <c:if test="${ordemServico.status == 'ABERTA' || ordemServico.status == 'EM_ANDAMENTO'}">
                    <form action="${pageContext.request.contextPath}/controle" method="post" style="display: inline;" onsubmit="return confirm('Cancelar este atendimento?');">
                        <input type="hidden" name="acao" value="ordemServico.cancelar">
                        <input type="hidden" name="id" value="${fn:escapeXml(ordemServico.id)}">
                        <input type="hidden" name="csrfToken" value="${fn:escapeXml(csrfToken)}">
                        <button type="submit" class="btn btn-warning"> Cancelar Atendimento</button>
                    </form>
                </c:if>
            </div>
        </div>
    </div>

    <div class="card" style="background: #f8fafc;">
        <h3 class="card-title"> Resumo do Vínculo</h3>
        <div class="form-grid">
            <div>
                <strong>Orçamento Aprovado:</strong><br>
                <a href="${pageContext.request.contextPath}/controle?acao=orcamento.consultar&id=${fn:escapeXml(ordemServico.orcamento.id)}" class="link-vinculo">
                    Orçamento #${fn:escapeXml(ordemServico.orcamento.id)} (Valor Total: R$ ${fn:escapeXml(ordemServico.orcamento.valorTotal)})
                </a>
            </div>
            <div>
                <strong>Equipamento:</strong><br>
                <a href="${pageContext.request.contextPath}/controle?acao=equipamento.consultar&id=${fn:escapeXml(ordemServico.orcamento.equipamento.id)}" class="link-vinculo">
                    ${fn:escapeXml(ordemServico.orcamento.equipamento.tipo)} ${fn:escapeXml(ordemServico.orcamento.equipamento.marca)} ${fn:escapeXml(ordemServico.orcamento.equipamento.modelo)}
                </a>
            </div>
            <div>
                <strong>Cliente:</strong><br>
                <a href="${pageContext.request.contextPath}/controle?acao=cliente.consultar&id=${fn:escapeXml(ordemServico.orcamento.equipamento.cliente.id)}" class="link-vinculo">
                    ${fn:escapeXml(ordemServico.orcamento.equipamento.cliente.nome)} (${fn:escapeXml(ordemServico.orcamento.equipamento.cliente.telefone)})
                </a>
            </div>
        </div>
    </div>

    <div class="card">
        <h3 class="card-title"> Dados da Ordem de Serviço</h3>
        <form action="${pageContext.request.contextPath}/controle" method="post">
            <input type="hidden" name="acao" value="ordemServico.atualizar">
            <input type="hidden" name="id" value="${fn:escapeXml(ordemServico.id)}">
            <input type="hidden" name="csrfToken" value="${fn:escapeXml(csrfToken)}">

            <div class="form-grid">
                <div class="form-group">
                    <label for="responsavel">Técnico Responsável *</label>
                    <input type="text" id="responsavel" name="responsavel" value="${fn:escapeXml(ordemServico.responsavel)}" required maxlength="120" ${ordemServico.status == 'CONCLUIDA' || ordemServico.status == 'CANCELADA' ? 'readonly' : ''}>
                </div>
                <div class="form-group">
                    <label for="prioridade">Prioridade *</label>
                    <select id="prioridade" name="prioridade" ${ordemServico.status == 'CONCLUIDA' || ordemServico.status == 'CANCELADA' ? 'disabled' : ''}>
                        <option value="BAIXA" ${ordemServico.prioridade == 'BAIXA' ? 'selected' : ''}>BAIXA</option>
                        <option value="NORMAL" ${ordemServico.prioridade == 'NORMAL' ? 'selected' : ''}>NORMAL</option>
                        <option value="ALTA" ${ordemServico.prioridade == 'ALTA' ? 'selected' : ''}>ALTA</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="prazoGarantiaDias">Prazo de Garantia (Dias)</label>
                    <input type="number" id="prazoGarantiaDias" name="prazoGarantiaDias" value="${fn:escapeXml(ordemServico.prazoGarantiaDias)}" min="0" ${ordemServico.status == 'CONCLUIDA' || ordemServico.status == 'CANCELADA' ? 'readonly' : ''}>
                </div>
                <div class="form-group full-width">
                    <label for="observacoes">Observações Operacionais</label>
                    <textarea id="observacoes" name="observacoes" rows="2" maxlength="2000" ${ordemServico.status == 'CONCLUIDA' || ordemServico.status == 'CANCELADA' ? 'readonly' : ''}>${fn:escapeXml(ordemServico.observacoes)}</textarea>
                </div>
            </div>

            <c:if test="${ordemServico.status == 'ABERTA' || ordemServico.status == 'EM_ANDAMENTO'}">
                <div class="actions-bar">
                    <button type="submit" class="btn btn-primary">✏ Salvar Dados da Ordem</button>
                </div>
            </c:if>
        </form>
    </div>

    <div class="card" style="border-top: 4px solid var(--info);">
        <h3 class="card-title"> Ficha Técnica (Inspeção & Recepção - ID #${fn:escapeXml(fichaTecnica.id)})</h3>

        <form action="${pageContext.request.contextPath}/controle" method="post">
            <input type="hidden" name="acao" value="fichaTecnica.atualizar">
            <input type="hidden" name="id" value="${fn:escapeXml(fichaTecnica.id)}">
            <input type="hidden" name="csrfToken" value="${fn:escapeXml(csrfToken)}">

            <div class="form-grid">
                <div class="form-group">
                    <label for="estadoConservacao">Estado de Conservação *</label>
                    <select id="estadoConservacao" name="estadoConservacao" ${ordemServico.status == 'CONCLUIDA' || ordemServico.status == 'CANCELADA' ? 'disabled' : ''}>
                        <option value="NAO_AVALIADO" ${fichaTecnica.estadoConservacao == 'NAO_AVALIADO' ? 'selected' : ''}>NÃO AVALIADO</option>
                        <option value="BOM" ${fichaTecnica.estadoConservacao == 'BOM' ? 'selected' : ''}>BOM</option>
                        <option value="REGULAR" ${fichaTecnica.estadoConservacao == 'REGULAR' ? 'selected' : ''}>REGULAR</option>
                        <option value="RUIM" ${fichaTecnica.estadoConservacao == 'RUIM' ? 'selected' : ''}>RUIM</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="acessoriosEntregues">Acessórios Entregues</label>
                    <input type="text" id="acessoriosEntregues" name="acessoriosEntregues" value="${fn:escapeXml(fichaTecnica.acessoriosEntregues)}" maxlength="1000" ${ordemServico.status == 'CONCLUIDA' || ordemServico.status == 'CANCELADA' ? 'readonly' : ''}>
                </div>
                <div class="form-group">
                    <label for="ligaNormalmente">Liga Normalmente?</label>
                    <select id="ligaNormalmente" name="ligaNormalmente" ${ordemServico.status == 'CONCLUIDA' || ordemServico.status == 'CANCELADA' ? 'disabled' : ''}>
                        <option value="" ${fichaTecnica.ligaNormalmente == null ? 'selected' : ''}>[ Não verificado ]</option>
                        <option value="true" ${fichaTecnica.ligaNormalmente == true ? 'selected' : ''}>Sim</option>
                        <option value="false" ${fichaTecnica.ligaNormalmente == false ? 'selected' : ''}>Não</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="possuiAvarias">Possui Avarias Visíveis?</label>
                    <select id="possuiAvarias" name="possuiAvarias" ${ordemServico.status == 'CONCLUIDA' || ordemServico.status == 'CANCELADA' ? 'disabled' : ''}>
                        <option value="" ${fichaTecnica.possuiAvarias == null ? 'selected' : ''}>[ Não verificado ]</option>
                        <option value="false" ${fichaTecnica.possuiAvarias == false ? 'selected' : ''}>Não</option>
                        <option value="true" ${fichaTecnica.possuiAvarias == true ? 'selected' : ''}>Sim</option>
                    </select>
                </div>
                <div class="form-group full-width">
                    <label for="descricaoAvarias">Descrição das Avarias (Obrigatória se possuiAvarias = Sim)</label>
                    <textarea id="descricaoAvarias" name="descricaoAvarias" rows="2" maxlength="2000" ${ordemServico.status == 'CONCLUIDA' || ordemServico.status == 'CANCELADA' ? 'readonly' : ''}>${fn:escapeXml(fichaTecnica.descricaoAvarias)}</textarea>
                </div>
                <div class="form-group full-width">
                    <label for="testeInicial">Teste Inicial</label>
                    <textarea id="testeInicial" name="testeInicial" rows="2" maxlength="2000" ${ordemServico.status == 'CONCLUIDA' || ordemServico.status == 'CANCELADA' ? 'readonly' : ''}>${fn:escapeXml(fichaTecnica.testeInicial)}</textarea>
                </div>
                <div class="form-group full-width">
                    <label for="observacoesRecebimento">Observações no Recebimento</label>
                    <textarea id="observacoesRecebimento" name="observacoesRecebimento" rows="2" maxlength="2000" ${ordemServico.status == 'CONCLUIDA' || ordemServico.status == 'CANCELADA' ? 'readonly' : ''}>${fn:escapeXml(fichaTecnica.observacoesRecebimento)}</textarea>
                </div>
            </div>

            <c:if test="${ordemServico.status == 'ABERTA' || ordemServico.status == 'EM_ANDAMENTO'}">
                <div class="actions-bar">
                    <button type="submit" class="btn btn-primary">✏ Salvar Dados da Ficha Técnica</button>
                </div>
            </c:if>
        </form>
    </div>

</div>

</body>
</html>
