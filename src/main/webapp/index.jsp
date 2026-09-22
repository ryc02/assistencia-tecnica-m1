<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // Redireciona para o Front Controller da aplicação
    response.sendRedirect(request.getContextPath() + "/controle?acao=cliente.listar");
%>
