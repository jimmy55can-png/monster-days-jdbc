<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="esNuevo" value="${empty jugador or jugador.idJugador == 0}" />
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>${esNuevo ? 'Registrar' : 'Editar'} jugador - Monster Days</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/estilos.css">
</head>
<body>
    <header><h1>Monster Days · ${esNuevo ? 'Registrar' : 'Editar'} jugador</h1></header>
    <main>
        <c:if test="${not empty error}"><p class="error"><c:out value="${error}"/></p></c:if>

        <form method="post" action="${pageContext.request.contextPath}/jugadores">
            <input type="hidden" name="accion" value="guardar">
            <input type="hidden" name="idJugador" value="${esNuevo ? '' : jugador.idJugador}">

            <label for="nombreUsuario">Nombre de usuario</label>
            <input type="text" id="nombreUsuario" name="nombreUsuario" maxlength="30" required
                   value="<c:out value='${jugador.nombreUsuario}'/>">

            <label for="correo">Correo</label>
            <input type="email" id="correo" name="correo" maxlength="100" required
                   value="<c:out value='${jugador.correo}'/>">

            <c:if test="${esNuevo}">
                <label for="clave">Clave</label>
                <input type="password" id="clave" name="clave" required>
            </c:if>

            <label for="nivelMaximo">Nivel máximo (1 a 4)</label>
            <input type="number" id="nivelMaximo" name="nivelMaximo" min="1" max="4" required
                   value="${empty jugador ? 1 : jugador.nivelMaximo}">

            <p>
                <button type="submit">Guardar</button>
                <a class="boton" href="${pageContext.request.contextPath}/jugadores">Cancelar</a>
            </p>
        </form>
    </main>
    <jsp:include page="pie.jsp" />
</body>
</html>
