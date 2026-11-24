<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.time.LocalTime" %>
<%-- Declaración JSP: método disponible en toda la página --%>
<%!
    private String saludoSegunHora(int hora) {
        if (hora < 12) return "Buenos días";
        if (hora < 18) return "Buenas tardes";
        return "Buenas noches";
    }
%>
<%-- Scriptlet JSP: código Java dentro de la página --%>
<%
    String saludo = saludoSegunHora(LocalTime.now().getHour());
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Monster Days</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body>
    <header><h1>Monster Days</h1></header>
    <main>
        <%-- Expresión JSP --%>
        <h2><%= saludo %>, bienvenido al panel de jugadores</h2>
        <p>Gestiona los jugadores del videojuego tower defense.</p>
        <a class="boton" href="${pageContext.request.contextPath}/jugadores">Ver jugadores</a>
        <a class="boton" href="${pageContext.request.contextPath}/jugadores?accion=nuevo">Registrar jugador</a>
    </main>
    <%-- Acción JSP: incluye otro archivo --%>
    <jsp:include page="pie.jsp" />
</body>
</html>
