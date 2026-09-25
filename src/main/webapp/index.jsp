<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // Redireciona diretamente para a tela de login
    response.sendRedirect(request.getContextPath() + "/controle?acao=login");
%>
