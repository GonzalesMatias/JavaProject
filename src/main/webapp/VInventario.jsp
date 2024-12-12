<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html class="responsive">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Gestión de Inventario</title>
        <link rel="stylesheet" href="css/Inventario.css">
    </head>
    <body>
        <button class="btn-limpiar" onclick="limpiarFiltros()">Limpiar Filtros</button>
        <button class="btn-cerrar-sesion" onclick="cerrarSesion()">Cerrar Sesión</button>
        <h1>Gestión de Inventario</h1>
        <button class="btn-home" onclick="regresarHome()">Regresar a Home</button>
        <form action="ObtenerProductoServlet" method="get">
            <input type="hidden" name="action" value="getUpdatedInventory">
            <button type="submit" class="btn-actualizar">Actualizar Inventario</button>
        </form>
        <div class="table-container">
            <table>
                <thead>
                    <tr>
                        <th>ID Producto
                            <input type="text" class="filter-input">
                        </th>
                        <th>Nombre Producto
                            <input type="text" class="filter-input">
                        </th>
                        <th>Cantidad Actual
                            <input type="text" class="filter-input">
                        </th>
                        <th>Fecha Actualización
                            <input type="text" class="filter-input">
                        </th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="inventario" items="${FDato}">
                        <tr>
                            <td>${inventario.idProducto}</td>
                            <td>${inventario.nombreProducto}</td>
                            <td>${inventario.cantidadActual}</td>
                            <td>${inventario.fechaActualizacion}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <c:if test="${not empty errorMessage}">
            <p style="color:red;">${errorMessage}</p>
        </c:if>
        <script>
            document.querySelectorAll('.filter-input').forEach(input => {
                input.addEventListener('input', function () {
                    const index = Array.from(input.parentElement.parentElement.children).indexOf(input.parentElement);
                    const filter = input.value.toLowerCase();
                    const rows = document.querySelectorAll('tbody tr');
                    rows.forEach(row => {
                        const cell = row.children[index];
                        const text = cell.textContent.toLowerCase();
                        row.style.display = text.includes(filter) ? '' : 'none';
                    });
                });
            });

            function limpiarFiltros() {
                document.querySelectorAll('.filter-input').forEach(input => {
                    input.value = '';
                });
                document.querySelectorAll('tbody tr').forEach(row => {
                    row.style.display = '';
                });
            }

            function cerrarSesion() {
                window.location.href = 'login.jsp';
            }

            function regresarHome() {
                window.location.href = 'home.jsp';
            }
        </script>
    </body>
</html>