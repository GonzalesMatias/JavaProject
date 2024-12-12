<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Proveedores</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <link rel="stylesheet" href="css/Proveedores.css">
    </head>
    <style>
        body{
            font-family: 'Source Sans Pro', sans-serif;
            background-image: url(img/proveedores.webp);
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
        h2 {
            display: inline-block; /* Ajusta el tamaño del fondo al texto */
            background-color: #ffc107; /* Color de fondo */
            padding: 5px 10px; /* Espaciado opcional para darle margen interno */
            border-radius: 10px; /* Bordes redondeados opcionales */
            color: #000; /* Color del texto */
        }
        .mt-3{
            border-radius: 10px; /* Bordes redondeados */
            background-color: bisque; /* Color de fondo */
            padding: 20px; /* Espaciado interno */
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2); /* Sombra para un efecto más elegante */
        }
        .logo {
            border-radius: 10px;
            position: absolute;
            top: 10px;
            left: 10px;
            width: 250px;
            height: auto;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
            font-size: 16px;
            text-align: left;
        }

        table thead {
            background-color: #000000;
            color: white;
            text-transform: uppercase;
            font-weight: bold;
        }

        table th, table td {
            padding: 12px 15px;
            border: 1px solid #ddd;
        }

        table tbody tr {
            background-color: #f9f9f9;
            transition: background-color 0.3s;
        }

        table tbody tr:nth-child(even) {
            background-color: #f1f1f1; /* Alterna el color de las filas */
        }

        table tbody tr:hover {
            background-color: bisque; /* Color de fondo al pasar el mouse */
        }

        .table-container {
            overflow-x: auto; /* Permite desplazamiento horizontal en pantallas pequeñas */
        }

    </style>

    <body>
        <img src="pics/logo empresa.png" alt="Logo" class="logo">
        <button class="btn-home" onclick="window.location.href = 'home.jsp'">Regresar a Home</button>
        <div class="container">
            <h1 class="mt-5">Gestión de Proveedores</h1>
            <form action="ProveedoresServlet" method="post" class="mt-3">
                <input type="hidden" name="action" value="add">
                <div class="form-group">
                    <label for="nombre">Nombre</label>
                    <input type="text" class="form-control" id="nombre" name="nombre" required>
                </div>
                <div class="form-group">
                    <label for="contacto">Contacto</label>
                    <input type="text" class="form-control" id="contacto" name="contacto">
                </div>
                <div class="form-group">
                    <label for="telefono">Teléfono</label>
                    <input type="text" class="form-control" id="telefono" name="telefono">
                </div>
                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="email" class="form-control" id="email" name="email">
                </div>
                <button type="submit" class="btn btn-primary">Agregar Proveedor</button>
            </form>
            <h2 class="mt-5">Lista de Proveedores</h2>
            <table class="table table-bordered mt-3">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nombre</th>
                        <th>Contacto</th>
                        <th>Teléfono</th>
                        <th>Email</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="proveedor" items="${proveedores}">
                        <tr>
                            <td>${proveedor.idProveedor}</td>
                            <td>${proveedor.nombre}</td>
                            <td>${proveedor.contacto}</td>
                            <td>${proveedor.telefono}</td>
                            <td>${proveedor.email}</td>
                            <td>
                                <button type="button" class="btn btn-warning" data-toggle="modal" data-target="#editModal"
                                        data-id="${proveedor.idProveedor}"
                                        data-nombre="${proveedor.nombre}"
                                        data-contacto="${proveedor.contacto}"
                                        data-telefono="${proveedor.telefono}"
                                        data-email="${proveedor.email}">
                                    Editar
                                </button>
                                <form action="ProveedoresServlet" method="post" style="display:inline;">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="idProveedor" value="${proveedor.idProveedor}">
                                    <button type="submit" class="btn btn-danger">Eliminar</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <!-- Modal para editar proveedor -->
        <div class="modal fade" id="editModal" tabindex="-1" aria-labelledby="editModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="editModalLabel">Editar Proveedor</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">×</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form id="editForm" action="ProveedoresServlet" method="post">
                            <input type="hidden" name="action" value="edit">
                            <input type="hidden" id="editIdProveedor" name="idProveedor">
                            <div class="form-group">
                                <label for="editNombre">Nombre</label>
                                <input type="text" class="form-control" id="editNombre" name="nombre" required>
                            </div>
                            <div class="form-group">
                                <label for="editContacto">Contacto</label>
                                <input type="text" class="form-control" id="editContacto" name="contacto">
                            </div>
                            <div class="form-group">
                                <label for="editTelefono">Teléfono</label>
                                <input type="text" class="form-control" id="editTelefono" name="telefono">
                            </div>
                            <div class="form-group">
                                <label for="editEmail">Email</label>
                                <input type="email" class="form-control" id="editEmail" name="email">
                            </div>
                            <button type="submit" class="btn btn-primary">Guardar cambios</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <script>
            $('#editModal').on('show.bs.modal', function (event) {
                var button = $(event.relatedTarget); // Botón que abrió el modal
                var id = button.data('id');
                var nombre = button.data('nombre');
                var contacto = button.data('contacto');
                var telefono = button.data('telefono');
                var email = button.data('email');

                var modal = $(this);
                modal.find('#editIdProveedor').val(id);
                modal.find('#editNombre').val(nombre);
                modal.find('#editContacto').val(contacto);
                modal.find('#editTelefono').val(telefono);
                modal.find('#editEmail').val(email);
            });
        </script>
    </body>
</html>