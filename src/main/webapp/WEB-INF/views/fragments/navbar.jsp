<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="raiz" value="${pageContext.request.contextPath}/controle?acao=" />
<nav class="navbar">
    <a href="${raiz}cliente.listar" class="navbar-brand">
        <span class="navbar-logo" aria-hidden="true">
            <svg viewBox="0 0 24 24" width="18" height="18" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M14.7 6.3a1 1 0 0 0 0 1.4l1.6 1.6a1 1 0 0 0 1.4 0l3.77-3.77a6 6 0 0 1-7.94 7.94l-6.91 6.91a2.12 2.12 0 0 1-3-3l6.91-6.91a6 6 0 0 1 7.94-7.94l-3.76 3.76z"/></svg>
        </span>
        Assistência Técnica
    </a>
    <ul class="navbar-nav">
        <li>
            <a href="${raiz}cliente.listar" class="${param.active == 'clientes' ? 'active' : ''}">
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" aria-hidden="true"><path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/><circle cx="9" cy="7" r="4"/><path d="M23 21v-2a4 4 0 0 0-3-3.87"/><path d="M16 3.13a4 4 0 0 1 0 7.75"/></svg>
                Clientes
            </a>
        </li>
        <li>
            <a href="${raiz}equipamento.listar" class="${param.active == 'equipamentos' ? 'active' : ''}">
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" aria-hidden="true"><rect x="2" y="4" width="20" height="12" rx="2"/><path d="M8 20h8"/><path d="M12 16v4"/></svg>
                Equipamentos
            </a>
        </li>
        <li>
            <a href="${raiz}orcamento.listar" class="${param.active == 'orcamentos' ? 'active' : ''}">
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" aria-hidden="true"><path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"/><path d="M14 2v6h6"/><path d="M12 18v-6"/><path d="M9.5 13.5c0-.8.7-1.5 2.5-1.5s2.5.7 2.5 1.5-1 1.2-2.5 1.5-2.5.7-2.5 1.5.7 1.5 2.5 1.5 2.5-.7 2.5-1.5"/></svg>
                Orçamentos
            </a>
        </li>
        <li>
            <a href="${raiz}ordemServico.listar" class="${param.active == 'ordens' ? 'active' : ''}">
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" aria-hidden="true"><path d="M9 11l3 3L22 4"/><path d="M21 12v7a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h11"/></svg>
                Ordens &amp; Fichas
            </a>
        </li>
        <c:if test="${sessionScope.usuarioLogado.cargo == 'Administrador'}">
            <li>
                <a href="${raiz}usuario.listar" class="${param.active == 'usuarios' ? 'active' : ''}">
                    <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" aria-hidden="true"><path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z"/></svg>
                    Usuários
                </a>
            </li>
        </c:if>
    </ul>

    <div class="user-actions">
        <c:choose>
            <c:when test="${not empty sessionScope.usuarioLogado}">
                <span class="user-info">
                    <span class="user-avatar" aria-hidden="true">${fn:escapeXml(fn:substring(sessionScope.usuarioLogado.nome, 0, 1))}</span>
                    <span class="user-text">
                        <strong>${fn:escapeXml(sessionScope.usuarioLogado.nome)}</strong>
                        <small>${fn:escapeXml(sessionScope.usuarioLogado.cargo)}</small>
                    </span>
                </span>
                <a href="${raiz}logout" class="logout-link" title="Sair do sistema">
                    <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" aria-hidden="true"><path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4"/><path d="M16 17l5-5-5-5"/><path d="M21 12H9"/></svg>
                    <span>Sair</span>
                </a>
            </c:when>
            <c:otherwise>
                <a href="${raiz}login" class="logout-link">Entrar</a>
            </c:otherwise>
        </c:choose>
    </div>
</nav>
