<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Atendimento OS #${ordemServico.id} - Assistência Técnica M1</title>
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

    <!-- [Requisito 11] Exibir Ordem de Serviço e Ficha Técnica juntas na página do atendimento -->
    
    <!-- Painel de Controle de Status da Ordem -->
    <div class="card" style="border-left: 6px solid var(--primary);">
        <div style="display: flex; justify-content: space-between; align-items: center; flex-wrap: wrap; gap: 10px;">
            <div>
                <h2 style="margin: 0;"> Ordem de Serviço #${ordemServico.id}</h2>
                <div style="margin-top: 4px;">
                    <strong>Status:</strong> <span class="badge badge-${ordemServico.status.name().toLowerCase()}">${ordemServico.status}</span>
                    &nbsp;|&nbsp; <strong>Prioridade:</strong> ${ordemServico.prioridade}
                    &nbsp;|&nbsp; <strong>Data Abertura:</strong> ${ordemServico.dataAbertura}
                </div>
            </div>

            <!-- Botões de Ação de Transição de Estado -->
            <div class="actions-bar" style="margin: 0;">
                <c:if test="${ordemServico.status == 'ABERTA'}">
                    <form action="${pageContext.request.contextPath}/controle" method="post" style="display: inline;">
                        <input type="hidden" name="acao" value="ordemServico.iniciar">
                        <input type="hidden" name="id" value="${ordemServico.id}">
                        <input type="hidden" name="csrfToken" value="${csrfToken}">
                        <button type="submit" class="btn btn-primary"> Iniciar Atendimento</button>
                    </form>

                    <!-- [Seção 7 / T06] Excluir Ordem Aberta -->
                    <form action="${pageContext.request.contextPath}/controle" method="post" style="display: inline;" onsubmit="return confirm('Excluir esta Ordem e Ficha e retornar o orçamento a PENDENTE?');">
                        <input type="hidden" name="acao" value="ordemServico.excluir">
                        <input type="hidden" name="id" value="${ordemServico.id}">
                        <input type="hidden" name="csrfToken" value="${csrfToken}">
                        <button type="submit" class="btn btn-danger"> Excluir Ordem Aberta</button>
                    </form>
                </c:if>

                <c:if test="${ordemServico.status == 'EM_ANDAMENTO'}">
                    <form action="${pageContext.request.contextPath}/controle" method="post" style="display: inline;">
                        <input type="hidden" name="acao" value="ordemServico.concluir">
                        <input type="hidden" name="id" value="${ordemServico.id}">
                        <input type="hidden" name="csrfToken" value="${csrfToken}">
                        <button type="submit" class="btn btn-success"> Concluir Atendimento</button>
                    </form>
                </c:if>

                <c:if test="${ordemServico.status == 'ABERTA' || ordemServico.status == 'EM_ANDAMENTO'}">
                    <form action="${pageContext.request.contextPath}/controle" method="post" style="display: inline;" onsubmit="return confirm('Cancelar este atendimento?');">
                        <input type="hidden" name="acao" value="ordemServico.cancelar">
                        <input type="hidden" name="id" value="${ordemServico.id}">
                        <input type="hidden" name="csrfToken" value="${csrfToken}">
                        <button type="submit" class="btn btn-warning"> Cancelar Atendimento</button>
                    </form>
                </c:if>
            </div>
        </div>
    </div>

    <!-- Navegação e Resumo do Vínculo -->
    <div class="card" style="background: #f8fafc;">
        <h3 class="card-title"> Resumo do Vínculo</h3>
        <div class="form-grid">
            <div>
                <strong>Orçamento Aprovado:</strong><br>
                <a href="${pageContext.request.contextPath}/controle?acao=orcamento.consultar&id=${ordemServico.orcamento.id}" class="link-vinculo">
                    Orçamento #${ordemServico.orcamento.id} (Valor Total: R$ ${ordemServico.orcamento.valorTotal})
                </a>
            </div>
            <div>
                <strong>Equipamento:</strong><br>
                <a href="${pageContext.request.contextPath}/controle?acao=equipamento.consultar&id=${ordemServico.orcamento.equipamento.id}" class="link-vinculo">
                    ${ordemServico.orcamento.equipamento.tipo} ${ordemServico.orcamento.equipamento.marca} ${ordemServico.orcamento.equipamento.modelo}
                </a>
            </div>
            <div>
                <strong>Cliente:</strong><br>
                <a href="${pageContext.request.contextPath}/controle?acao=cliente.consultar&id=${ordemServico.orcamento.equipamento.cliente.id}" class="link-vinculo">
                    ${ordemServico.orcamento.equipamento.cliente.nome} (${ordemServico.orcamento.equipamento.cliente.telefone})
                </a>
            </div>
        </div>
    </div>

    <!-- Form 1: Edição da Ordem de Serviço -->
    <div class="card">
        <h3 class="card-title"> Dados da Ordem de Serviço</h3>
        <form action="${pageContext.request.contextPath}/controle" method="post">
            <input type="hidden" name="acao" value="ordemServico.atualizar">
            <input type="hidden" name="id" value="${ordemServico.id}">
            <input type="hidden" name="csrfToken" value="${csrfToken}">

            <div class="form-grid">
                <div class="form-group">
                    <label for="responsavel">Técnico Responsável *</label>
                    <input type="text" id="responsavel" name="responsavel" value="${ordemServico.responsavel}" required maxlength="120" ${ordemServico.status == 'CONCLUIDA' || ordemServico.status == 'CANCELADA' ? 'readonly' : ''}>
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
                    <input type="number" id="prazoGarantiaDias" name="prazoGarantiaDias" value="${ordemServico.prazoGarantiaDias}" min="0" ${ordemServico.status == 'CONCLUIDA' || ordemServico.status == 'CANCELADA' ? 'readonly' : ''}>
                </div>
                <div class="form-group full-width">
                    <label for="observacoes">Observações Operacionais</label>
                    <textarea id="observacoes" name="observacoes" rows="2" maxlength="2000" ${ordemServico.status == 'CONCLUIDA' || ordemServico.status == 'CANCELADA' ? 'readonly' : ''}>${ordemServico.observacoes}</textarea>
                </div>
            </div>

            <c:if test="${ordemServico.status == 'ABERTA' || ordemServico.status == 'EM_ANDAMENTO'}">
                <div class="actions-bar">
                    <button type="submit" class="btn btn-primary">✏ Salvar Dados da Ordem</button>
                </div>
            </c:if>
        </form>
    </div>

    <!-- [Requisito 6 / 1:1] Form 2: Ficha Técnica (Exibida na mesma tela) -->
    <div class="card" style="border-top: 4px solid var(--info);">
        <h3 class="card-title"> Ficha Técnica (Inspeção & Recepção - ID #${fichaTecnica.id})</h3>

        <form action="${pageContext.request.contextPath}/controle" method="post">
            <input type="hidden" name="acao" value="fichaTecnica.atualizar">
            <input type="hidden" name="id" value="${fichaTecnica.id}">
            <input type="hidden" name="csrfToken" value="${csrfToken}">

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
                    <input type="text" id="acessoriosEntregues" name="acessoriosEntregues" value="${fichaTecnica.acessoriosEntregues}" maxlength="1000" ${ordemServico.status == 'CONCLUIDA' || ordemServico.status == 'CANCELADA' ? 'readonly' : ''}>
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
                    <textarea id="descricaoAvarias" name="descricaoAvarias" rows="2" maxlength="2000" ${ordemServico.status == 'CONCLUIDA' || ordemServico.status == 'CANCELADA' ? 'readonly' : ''}>${fichaTecnica.descricaoAvarias}</textarea>
                </div>
                <div class="form-group full-width">
                    <label for="testeInicial">Teste Inicial</label>
                    <textarea id="testeInicial" name="testeInicial" rows="2" maxlength="2000" ${ordemServico.status == 'CONCLUIDA' || ordemServico.status == 'CANCELADA' ? 'readonly' : ''}>${fichaTecnica.testeInicial}</textarea>
                </div>
                <div class="form-group full-width">
                    <label for="observacoesRecebimento">Observações no Recebimento</label>
                    <textarea id="observacoesRecebimento" name="observacoesRecebimento" rows="2" maxlength="2000" ${ordemServico.status == 'CONCLUIDA' || ordemServico.status == 'CANCELADA' ? 'readonly' : ''}>${fichaTecnica.observacoesRecebimento}</textarea>
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
