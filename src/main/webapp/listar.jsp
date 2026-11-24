<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Jugadores - Monster Days</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body>
    <header><h1>Monster Days · Jugadores</h1></header>
    <main>
        <c:if test="${param.mensaje == 'guardado'}"><p class="mensaje">Jugador guardado correctamente.</p></c:if>
        <c:if test="${param.mensaje == 'eliminado'}"><p class="mensaje">Jugador eliminado correctamente.</p></c:if>

        <p>
            <a class="boton" href="${pageContext.request.contextPath}/jugadores?accion=nuevo">Nuevo jugador</a>
            <a class="boton" href="${pageContext.request.contextPath}/index.jsp">Inicio</a>
        </p>

        <table>
            <tr>
                <th>ID</th><th>Usuario</th><th>Correo</th><th>Nivel máximo</th><th>Registro</th><th>Acciones</th>
            </tr>
            <c:forEach var="j" items="${jugadores}">
                <tr>
                    <td>${j.idJugador}</td>
                    <td><c:out value="${j.nombreUsuario}"/></td>
                    <td><c:out value="${j.correo}"/></td>
                    <td>${j.nivelMaximo}</td>
                    <td>${j.fechaRegistro}</td>
                    <td>
                        <a class="boton" href="${pageContext.request.contextPath}/jugadores?accion=editar&id=${j.idJugador}">Editar</a>
                        <form class="en-linea" method="post" action="${pageContext.request.contextPath}/jugadores"
                              onsubmit="return confirm('¿Eliminar este jugador?');">
                            <input type="hidden" name="accion" value="eliminar">
                            <input type="hidden" name="idJugador" value="${j.idJugador}">
                            <button type="submit" class="peligro">Eliminar</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty jugadores}">
                <tr><td colspan="6">No hay jugadores registrados.</td></tr>
            </c:if>
        </table>
    </main>
    <jsp:include page="pie.jsp" />
</body>
</html>
