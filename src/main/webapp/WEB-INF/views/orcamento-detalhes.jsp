<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Orçamento #${fn:escapeXml(orcamento.id)} - Assistência Técnica</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css?v=7">
</head>
<body>

<jsp:include page="/WEB-INF/views/fragments/navbar.jsp">
    <jsp:param name="active" value="orcamentos"/>
</jsp:include>

<div class="container">

    <div class="card">
        <h2 class="card-title">
             Orçamento #${fn:escapeXml(orcamento.id)}
            <span class="badge badge-${orcamento.status.name().toLowerCase()}">${fn:escapeXml(orcamento.status)}</span>
        </h2>

        <div style="background: #f1f5f9; padding: 12px; border-radius: 6px; margin-bottom: 20px;">
            <strong>Equipamento:</strong>
            <a href="${pageContext.request.contextPath}/controle?acao=equipamento.consultar&id=${fn:escapeXml(orcamento.equipamento.id)}" class="link-vinculo">
                ${fn:escapeXml(orcamento.equipamento.tipo)} ${fn:escapeXml(orcamento.equipamento.marca)} ${fn:escapeXml(orcamento.equipamento.modelo)} (SN: ${fn:escapeXml(orcamento.equipamento.numeroSerie)})
            </a>
            <br>
            <strong>Cliente:</strong>
            <a href="${pageContext.request.contextPath}/controle?acao=cliente.consultar&id=${fn:escapeXml(orcamento.equipamento.cliente.id)}" class="link-vinculo">
                ${fn:escapeXml(orcamento.equipamento.cliente.nome)}
            </a>
        </div>

        <form action="${pageContext.request.contextPath}/controle" method="post">
            <input type="hidden" name="acao" value="orcamento.atualizar">
            <input type="hidden" name="id" value="${fn:escapeXml(orcamento.id)}">
            <input type="hidden" name="csrfToken" value="${fn:escapeXml(csrfToken)}">

            <div class="form-grid">
                <div class="form-group">
                    <label for="equipamentoId">Equipamento</label>
                    <select id="equipamentoId" name="equipamentoId" ${orcamento.status != 'PENDENTE' ? 'disabled' : ''} required>
                        <c:forEach var="equip" items="${equipamentos}">
                            <option value="${fn:escapeXml(equip.id)}" ${orcamento.equipamento.id == equip.id ? 'selected' : ''}>
                                #${fn:escapeXml(equip.id)} - ${fn:escapeXml(equip.tipo)} ${fn:escapeXml(equip.modelo)}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group full-width">
                    <label for="descricaoProblema">Descrição do Problema</label>
                    <textarea id="descricaoProblema" name="descricaoProblema" rows="2" ${orcamento.status != 'PENDENTE' ? 'readonly' : ''} required maxlength="2000">${fn:escapeXml(orcamento.descricaoProblema)}</textarea>
                </div>
                <div class="form-group full-width">
                    <label for="diagnostico">Diagnóstico Técnico (Obrigatório para Aprovar)</label>
                    <textarea id="diagnostico" name="diagnostico" rows="2" ${orcamento.status != 'PENDENTE' ? 'readonly' : ''} maxlength="2000">${fn:escapeXml(orcamento.diagnostico)}</textarea>
                </div>
                <div class="form-group">
                    <label for="valorPecas">Valor de Peças (R$)</label>
                    <input type="number" id="valorPecas" name="valorPecas" step="0.01" min="0" value="${fn:escapeXml(orcamento.valorPecas)}" ${orcamento.status != 'PENDENTE' ? 'readonly' : ''}>
                </div>
                <div class="form-group">
                    <label for="valorMaoDeObra">Valor Mão de Obra (R$)</label>
                    <input type="number" id="valorMaoDeObra" name="valorMaoDeObra" step="0.01" min="0" value="${fn:escapeXml(orcamento.valorMaoDeObra)}" ${orcamento.status != 'PENDENTE' ? 'readonly' : ''}>
                </div>
                <div class="form-group">
                    <label for="percentualDesconto">Desconto (%)</label>
                    <input type="number" id="percentualDesconto" name="percentualDesconto" step="0.01" min="0" max="100" value="${fn:escapeXml(orcamento.percentualDesconto)}" ${orcamento.status != 'PENDENTE' ? 'readonly' : ''}>
                </div>
                <div class="form-group full-width">
                    <label>Adicionais (padrão Decorator)</label>
                    <c:forEach var="tipo" items="${tiposAdicionais}">
                        <label style="display:inline-block; margin-right:14px; font-weight:normal;">
                            <input type="checkbox" name="adicionais" value="${fn:escapeXml(tipo.nome)}"
                                   ${orcamento.adicionais.contains(tipo.nome) ? 'checked' : ''}
                                   ${orcamento.status != 'PENDENTE' ? 'disabled' : ''}>
                            ${fn:escapeXml(tipo.descricao)} (+ R$ ${fn:escapeXml(tipo.valor)})
                        </label>
                    </c:forEach>
                    <p style="margin-top:6px;"><strong>Composição:</strong> ${fn:escapeXml(orcamento.descricaoServico)}</p>
                </div>
                <div class="form-group" style="background: #e0f2fe; padding: 10px; border-radius: 6px;">
                    <label>Valor Total Calculado no Servidor</label>
                    <div style="font-size: 1.4rem; font-weight: bold; color: hsl(var(--primary));">R$ ${fn:escapeXml(orcamento.valorTotal)}</div>
                </div>
            </div>

            <c:if test="${orcamento.status == 'PENDENTE'}">
                <div class="actions-bar">
                    <button type="submit" class="btn btn-primary">✏ Salvar Alterações</button>
                </div>
            </c:if>
        </form>

        <c:if test="${orcamento.status == 'PENDENTE'}">
            <form action="${pageContext.request.contextPath}/controle" method="post" style="margin-top: 10px;">
                <input type="hidden" name="acao" value="orcamento.recusar">
                <input type="hidden" name="id" value="${fn:escapeXml(orcamento.id)}">
                <input type="hidden" name="csrfToken" value="${fn:escapeXml(csrfToken)}">
                <button type="submit" class="btn btn-warning"> Recusar Orçamento</button>
            </form>

            <form action="${pageContext.request.contextPath}/controle" method="post" style="margin-top: 10px;" onsubmit="return confirm('Excluir orçamento?');">
                <input type="hidden" name="acao" value="orcamento.excluir">
                <input type="hidden" name="id" value="${fn:escapeXml(orcamento.id)}">
                <input type="hidden" name="csrfToken" value="${fn:escapeXml(csrfToken)}">
                <button type="submit" class="btn btn-danger"> Excluir Orçamento</button>
            </form>
        </c:if>
    </div>

    <c:if test="${orcamento.status == 'PENDENTE'}">
        <div class="card" style="border: 2px solid var(--success);">
            <h2 class="card-title" style="color: var(--success);">
                 Aprovar Orçamento e Gerar Ordem de Serviço + Ficha Técnica
            </h2>
            <p style="margin-bottom: 16px; color: var(--text-muted);">
                Ao aprovar, o sistema irá alterar o status do orçamento para <strong>APROVADO</strong> e criará automaticamente a <strong>Ordem de Serviço (ABERTA)</strong> e a <strong>Ficha Técnica</strong> correspondente em uma única transação indivisível.
            </p>

            <form action="${pageContext.request.contextPath}/controle" method="post">
                <input type="hidden" name="acao" value="orcamento.aprovar">
                <input type="hidden" name="id" value="${fn:escapeXml(orcamento.id)}">
                <input type="hidden" name="csrfToken" value="${fn:escapeXml(csrfToken)}">

                <h4 style="margin-bottom: 10px; color: hsl(var(--primary));"> Dados da Ordem de Serviço</h4>
                <div class="form-grid">
                    <div class="form-group">
                        <label for="responsavel">Técnico Responsável *</label>
                        <input type="text" id="responsavel" name="responsavel" required maxlength="120" placeholder="Ex: Eng. Roberto Santos" value="Técnico Responsável">
                    </div>
                    <div class="form-group">
                        <label for="prioridade">Prioridade *</label>
                        <select id="prioridade" name="prioridade" required>
                            <option value="NORMAL">NORMAL</option>
                            <option value="BAIXA">BAIXA</option>
                            <option value="ALTA">ALTA</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="previsaoConclusao">Previsão de Conclusão</label>
                        <input type="datetime-local" id="previsaoConclusao" name="previsaoConclusao">
                    </div>
                    <div class="form-group full-width">
                        <label for="observacoes">Observações da Ordem</label>
                        <input type="text" id="observacoes" name="observacoes" placeholder="Observações iniciais...">
                    </div>
                </div>

                <h4 style="margin: 20px 0 10px 0; color: hsl(var(--primary));"> Dados da Ficha Técnica (Recepção)</h4>
                <div class="form-grid">
                    <div class="form-group">
                        <label for="ficha.estadoConservacao">Estado de Conservação *</label>
                        <select id="ficha.estadoConservacao" name="ficha.estadoConservacao" required>
                            <option value="BOM">BOM</option>
                            <option value="REGULAR">REGULAR</option>
                            <option value="RUIM">RUIM</option>
                            <option value="NAO_AVALIADO">NÃO AVALIADO</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="ficha.acessoriosEntregues">Acessórios Entregues</label>
                        <input type="text" id="ficha.acessoriosEntregues" name="ficha.acessoriosEntregues" value="Carregador / Cabo" placeholder="Ex: Fonte, Cabo, Case">
                    </div>
                    <div class="form-group">
                        <label for="ficha.ligaNormalmente">Liga Normalmente?</label>
                        <select id="ficha.ligaNormalmente" name="ficha.ligaNormalmente">
                            <option value="true">Sim</option>
                            <option value="false">Não</option>
                            <option value="">Não verificado</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="ficha.possuiAvarias">Possui Avarias Visíveis?</label>
                        <select id="ficha.possuiAvarias" name="ficha.possuiAvarias">
                            <option value="false">Não</option>
                            <option value="true">Sim</option>
                            <option value="">Não verificado</option>
                        </select>
                    </div>
                    <div class="form-group full-width">
                        <label for="ficha.descricaoAvarias">Descrição das Avarias (se houver)</label>
                        <input type="text" id="ficha.descricaoAvarias" name="ficha.descricaoAvarias" placeholder="Riscos no chassi, tampa trincada...">
                    </div>
                    <div class="form-group full-width">
                        <label for="ficha.testeInicial">Teste Inicial</label>
                        <input type="text" id="ficha.testeInicial" name="ficha.testeInicial" value="Equipamento liga e emite bip padrão" placeholder="Testes realizados na bancada de recepção...">
                    </div>
                </div>

                <div style="margin-top: 20px;">
                    <button type="submit" class="btn btn-success"> Confirmar Aprovação e Gerar Atendimento</button>
                </div>
            </form>
        </div>
    </c:if>

    <c:if test="${orcamento.status == 'APROVADO' && ordemServico != null}">
        <div class="card" style="border: 2px solid hsl(var(--primary));">
            <h2 class="card-title">
                 Atendimento Vinculado (Ordem de Serviço #${fn:escapeXml(ordemServico.id)})
                <a href="${pageContext.request.contextPath}/controle?acao=ordemServico.consultar&id=${fn:escapeXml(ordemServico.id)}" class="btn btn-sm btn-primary"> Ir para Atendimento Completo</a>
            </h2>
            <p><strong>Status da Ordem:</strong> <span class="badge badge-${ordemServico.status.name().toLowerCase()}">${fn:escapeXml(ordemServico.status)}</span></p>
            <p><strong>Técnico Responsável:</strong> ${fn:escapeXml(ordemServico.responsavel)}</p>
            <p><strong>Ficha Técnica ID:</strong> #${fn:escapeXml(fichaTecnica.id)} (Estado: ${fn:escapeXml(fichaTecnica.estadoConservacao)})</p>
        </div>
    </c:if>

</div>

</body>
</html>
