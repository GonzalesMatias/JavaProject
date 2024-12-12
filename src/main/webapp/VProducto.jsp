<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html class="responsive">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lista de Productos</title>
        <!-- Google Font: Source Sans Pro -->
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700&display=fallback">
        <!-- Font Awesome -->
        <link rel="stylesheet" href="AdminLTE/plugins/fontawesome-free/css/all.min.css">
        <!-- Theme style -->
        <link rel="stylesheet" href="AdminLTE/dist/css/adminlte.min.css">
        <link rel="stylesheet" href="css/Producto.css">
    </head>
    <style>
        body{
            font-family: 'Source Sans Pro', sans-serif;
            background-image: url(img/imagen_inventario.webp);
            background-size: cover; /* Ajusta la imagen para cubrir el área completa */
            background-repeat: no-repeat; /* Evita que la imagen se repita */
            background-position: center; /* Centra la imagen */
            background-attachment: fixed;
            display: flex;
            justify-content: center;
            align-items: center;
            position: relative;
            flex-direction: column;
        }
        h1 {
            display: inline-block; /* Ajusta el tamaño del fondo al texto */
            background-color: #ffc107; /* Color de fondo */
            padding: 5px 10px; /* Espaciado opcional para darle margen interno */
            border-radius: 10px; /* Bordes redondeados opcionales */
            color: #000; /* Color del texto */
        }
        .logo {
            position: absolute;
            top: 10px;
            left: 10px;
            width: 250px;
            height: auto;
            border-radius: 10px;
        }
        .table-container{
            margin: 0 auto;
            width: 90%;
            background-color: rgba(0, 0, 0, 0.7); /* Fondo oscuro */
            
            border-radius: 10px; /* Bordes redondeados */
            padding: 20px;
        }
        
    </style>
    <body>
        <img src="pics/logo empresa.png" alt="Logo" class="logo">
        <h1>Lista de Productos</h1>
        <button class="btn-home" onclick="regresarHome()">Regresar a Home</button>
        <div class="table-container">
            <button class="btn-limpiar" onclick="limpiarFiltros()">Limpiar Filtros</button>
            <div class="button-container">
                <button class="add-button" onclick="openModal()">Agregar Producto</button>
                <button class="edit-button" onclick="editProducto()">Editar Producto</button>
                <button class="delete-button" onclick="eliminarProducto()">Eliminar Producto</button>
            </div>
            <form id="productosForm" action="EliminarProductoServlet" method="POST">
                <table>
                    <thead>
                        <tr>
                            <th>
                                <div class="header-content">
                                    <span>Seleccionar</span>
                                </div>
                            </th>
                            <th>
                                <div class="header-content">
                                    <span>Código</span>
                                    <input type="text" class="filter-input">
                                </div>
                            </th>
                            <th>
                                <div class="header-content">
                                    <span>Nombre</span>
                                    <input type="text" class="filter-input">
                                </div>
                            </th>
                            <th>
                                <div class="header-content">
                                    <span>Descripción</span>
                                    <input type="text" class="filter-input">
                                </div>
                            </th>
                            <th>
                                <div class="header-content">
                                    <span>Precio</span>
                                    <input type="text" class="filter-input">
                                </div>
                            </th>
                            <th>
                                <div class="header-content">
                                    <span>Categoría</span>
                                    <input type="text" class="filter-input">
                                </div>
                            </th>
                            <th>
                                <div class="header-content">
                                    <span>Stock Mín.</span>
                                    <input type="text" class="filter-input">
                                </div>
                            </th>
                            <th>
                                <div class="header-content">
                                    <span>Stock Máx.</span>
                                    <input type="text" class="filter-input">
                                </div>
                            </th>
                            <th>
                                <div class="header-content">
                                    <span>Cantidad Total</span>
                                    <input type="text" class="filter-input">
                                </div>
                            </th>
                            <th>
                                <div class="header-content">
                                    <span>Fecha Actualización</span>
                                    <input type="text" class="filter-input">
                                </div>
                            </th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="producto" items="${FDato}">
                            <tr>
                                <td><input type="checkbox" name="idProducto" value="${producto.idProducto}"></td>
                                <td>${producto.idProducto}</td>
                                <td>${producto.nombre}</td>
                                <td>${producto.descripcion}</td>
                                <td>${producto.precio}</td>
                                <td>${producto.categoria}</td>
                                <td>${producto.stockMinimo}</td>
                                <td>${producto.stockMaximo}</td>
                                <td>${producto.cantidadTotal}</td>
                                <td>${producto.fechaActualizacion}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </form>
        </div>
        <!-- Modal para agregar producto -->
        <div id="myModal" class="modal">
            <div class="modal-content">
                <span class="close" onclick="closeModal()">×</span>
                <h2>Agregar Producto</h2>
                <div class="form-container">
                    <form action="AgregarProductoServlet" method="POST">
                        <label for="idProducto">ID Producto:</label>
                        <input type="text" id="idProducto" name="idProducto"><br>

                        <label for="categoria">Categoría:</label>
                        <div style="display: flex; align-items: center;">
                            <select id="categoria" name="categoria">
                                <option value="Ropa">Ropa</option>
                                <option value="Accesorio">Accesorio</option>
                            </select>
                            <button type="button" onclick="agregarCategoria()" style="margin-left: 10px;">Agregar Categoría</button>
                        </div><br>
                        <label for="nombreProducto">Nombre Producto:</label>
                        <input type="text" id="nombreProducto" name="nombreProducto"><br>

                        <label for="precio">Precio:</label>
                        <input type="text" id="precio" name="precio"><br>

                        <label for="descripcion">Descripción:</label>
                        <input type="text" id="descripcion" name="descripcion"><br>

                        <label for="stockMinimo">Stock Mínimo:</label>
                        <input type="text" id="stockMinimo" name="stockMinimo"><br>

                        <label for="stockMaximo">Stock Máximo:</label>
                        <input type="text" id="stockMaximo" name="stockMaximo"><br>

                        <label for="cantidadTotal">Cantidad Total:</label>
                        <input type="text" id="cantidadTotal" name="cantidadTotal"><br>

                        <label for="fechaActualizacion">Fecha Actualización:</label>
                        <input type="date" id="fechaActualizacion" name="fechaActualizacion"><br>

                        <input type="submit" value="Agregar Producto" style="background-color: #28a745; color: white; border: none; padding: 10px 20px; text-align: center; text-decoration: none; display: inline-block; font-size: 16px; margin-top: 20px; cursor: pointer; border-radius: 4px;">
                    </form>
                </div>
            </div>
        </div>

        <!-- Modal para editar producto -->
        <div id="editModal" class="modal">
            <div class="modal-content">
                <span class="close" onclick="closeEditModal()">×</span>
                <h2>Editar Producto</h2>
                <div class="form-container">
                    <form action="EditarProductoServlet" method="POST">
                        <input type="hidden" id="editIdProducto" name="idProducto">
                        <label for="editNombreProducto">Nombre Producto:</label>
                        <input type="text" id="editNombreProducto" name="nombreProducto"><br>

                        <label for="editPrecio">Precio:</label>
                        <input type="text" id="editPrecio" name="precio"><br>

                        <label for="editDescripcion">Descripción:</label>
                        <input type="text" id="editDescripcion" name="descripcion"><br>

                        <label for="editCategoria">Categoría:</label>
                        <input type="text" id="editCategoria" name="categoria"><br>

                        <label for="editStockMinimo">Stock Mínimo:</label>
                        <input type="text" id="editStockMinimo" name="stockMinimo"><br>

                        <label for="editStockMaximo">Stock Máximo:</label>
                        <input type="text" id="editStockMaximo" name="stockMaximo"><br>

                        <label for="editCantidadTotal">Cantidad Total:</label>
                        <input type="text" id="editCantidadTotal" name="cantidadTotal"><br>

                        <label for="editFechaActualizacion">Fecha Actualización:</label>
                        <input type="date" id="editFechaActualizacion" name="fechaActualizacion"><br>
                        <input type="submit" value="Guardar Cambios" style="background-color: #ffc107; color: white; border: none; padding: 10px 20px; text-align: center; text-decoration: none; display: inline-block; font-size: 16px; margin-top: 20px; cursor: pointer; border-radius: 4px;">
                    </form>
                </div>
            </div>
        </div>

        <!-- Modal para mensajes de error -->
        <div id="errorModal" class="modal">
            <div class="modal-content">
                <span class="close" onclick="closeErrorModal()">×</span>
                <h2>Error</h2>
                <p id="errorMessage"></p>
            </div>
        </div>
        <script>
        // Funciones para abrir y cerrar el modal de agregar producto
            function openModal() {
                document.getElementById("myModal").style.display = "block";
            }

            function closeModal() {
                document.getElementById("myModal").style.display = "none";
            }

        // Funciones para abrir y cerrar el modal de edición
            function openEditModal() {
                document.getElementById("editModal").style.display = "block";
            }

            function closeEditModal() {
                document.getElementById("editModal").style.display = "none";
            }

        // Función para abrir el modal de error
            function openErrorModal(message) {
                document.getElementById("errorMessage").textContent = message;
                document.getElementById("errorModal").style.display = "block";
            }

        // Función para cerrar el modal de error
            function closeErrorModal() {
                document.getElementById("errorModal").style.display = "none";
            }

        // Mostrar el modal de error si hay un mensaje de error
            <% if (request.getAttribute("errorMessage") != null) {%>
            openErrorModal("<%= request.getAttribute("errorMessage")%>");
            <% }%>

        // Cerrar los modales cuando el usuario hace clic fuera del contenido del modal
            window.onclick = function (event) {
                if (event.target == document.getElementById("myModal")) {
                    closeModal();
                }
                if (event.target == document.getElementById("editModal")) {
                    closeEditModal();
                }
                if (event.target == document.getElementById("errorModal")) {
                    closeErrorModal();
                }
            }

        // Función para agregar una nueva categoría
            function agregarCategoria() {
                let nuevaCategoria = prompt("Ingrese el nombre de la nueva categoría:");
                if (nuevaCategoria) {
                    let select = document.getElementById("categoria");
                    let option = document.createElement("option");
                    option.value = nuevaCategoria.toLowerCase();
                    option.text = nuevaCategoria;
                    select.add(option);
                }
            }

        // Función para limpiar filtros
            function limpiarFiltros() {
                document.querySelectorAll('.filter-input').forEach(input => {
                    input.value = '';
                });
                document.querySelectorAll('tbody tr').forEach(row => {
                    row.style.display = '';
                });
            }

        // Función para regresar a la página de inicio
            function regresarHome() {
                window.location.href = 'home.jsp'; // Redirigir a la página de inicio
            }

        // Función para eliminar productos seleccionados
            function eliminarProducto() {
                let form = document.getElementById("productosForm");
                let checkboxes = form.querySelectorAll('input[name="idProducto"]:checked');
                if (checkboxes.length > 0) {
                    if (confirm("¿Está seguro de que desea eliminar los productos seleccionados?")) {
                        form.submit();
                    }
                } else {
                    alert("Por favor, seleccione al menos un producto para eliminar.");
                }
            }

        // Función para editar el producto seleccionado
            function editProducto() {
                let form = document.getElementById("productosForm");
                let checkboxes = form.querySelectorAll('input[name="idProducto"]:checked');
                if (checkboxes.length === 1) {
                    let selectedProduct = checkboxes[0].closest('tr');
                    document.getElementById("editIdProducto").value = selectedProduct.cells[1].textContent;
                    document.getElementById("editNombreProducto").value = selectedProduct.cells[2].textContent;
                    document.getElementById("editDescripcion").value = selectedProduct.cells[3].textContent;
                    document.getElementById("editPrecio").value = selectedProduct.cells[4].textContent;
                    document.getElementById("editCategoria").value = selectedProduct.cells[5].textContent;
                    document.getElementById("editStockMinimo").value = selectedProduct.cells[6].textContent;
                    document.getElementById("editStockMaximo").value = selectedProduct.cells[7].textContent;
                    document.getElementById("editCantidadTotal").value = selectedProduct.cells[8].textContent;
                    document.getElementById("editFechaActualizacion").value = selectedProduct.cells[9].textContent;
                    openEditModal();
                } else {
                    alert("Por favor, seleccione un solo producto para editar.");
                }
            }

        // Filtros de búsqueda en la tabla
            document.querySelectorAll('.filter-input').forEach(input => {
                input.addEventListener('input', function () {
                    const th = input.closest('th');
                    const index = Array.from(th.parentElement.children).indexOf(th);
                    const filter = input.value.toLowerCase();
                    const rows = document.querySelectorAll('tbody tr');
                    rows.forEach(row => {
                        const cell = row.children[index];
                        const text = cell.textContent.toLowerCase();
                        row.style.display = text.includes(filter) ? '' : 'none';
                    });
                });
            });
        </script>
    </body>
</html>