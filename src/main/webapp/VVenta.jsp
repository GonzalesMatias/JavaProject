<%@ page import="java.util.List" %>
<%@ page import="Beans.Producto" %>
<%@page import="Beans.Clientes"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    List<Producto> productos = (List<Producto>) request.getAttribute("productos");
    List<Clientes> clientes = (List<Clientes>) request.getAttribute("clientes");
%>
<!DOCTYPE html>
<html>
    <img src="pics/logo empresa.png" alt="Logo" class="logo">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Registro de Venta</title>
        <!-- AdminLTE CSS -->
        <!-- Google Font: Source Sans Pro -->
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700&display=fallback">
        <!-- Font Awesome -->
        <link rel="stylesheet" href="AdminLTE/plugins/fontawesome-free/css/all.min.css">
        <!-- Theme style -->
        <link rel="stylesheet" href="AdminLTE/dist/css/adminlte.min.css">
        <link rel="stylesheet" href="css/Venta.css">
        <script>
            function actualizarPrecio(select) {
                var idProducto = select.value;
                var precioInput = select.parentElement.querySelector(".precioUnitario");
                var precios = {
            <% if (productos != null) {
                    for (Producto producto : productos) {%>
            <%= producto.getIdProducto()%>: <%= producto.getPrecio()%>,
            <% }
                } %>
                };
                if (idProducto) {
                    precioInput.value = precios[idProducto];
                } else {
                    precioInput.value = "";
                }
            }

            function disableSubmitButton() {
                document.getElementById("submitButton").disabled = true;
            }

            function regresarHome() {
                window.location.href = 'home.jsp'; // Redirigir a la página de inicio
            }
        </script>
    </head>
    <style>
        body{
            font-family: 'Source Sans Pro', sans-serif;
            background-image: url(img/imagen_venta.png);
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
        .logo {
            border-radius: 10px;
            position: absolute;
            top: 10px;
            left: 10px;
            width: 250px;
            height: auto;
        }
        .btn {
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            background-color: blue;
            color: white;
        }
        .card {
            background-color: rgba(0,0,0,0.8); /* Fondo gris oscuro */
            border: 1px solid black;
            border-radius: 15px;
            padding: 20px;
            width: 60%; /* Reducir el ancho al 60% */
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            margin: 0 auto; /* Centrar horizontalmente */
        }
        .card-header {
            background-color: #ffc107;
            padding: 10px;
            border-radius: 10px;
            text-align: center; /* Centrar el texto del encabezado */
        }
        h1 {
            text-align: center;
            color: white; /* Blanco */
            margin-bottom: 20px;
        }
    </style>
    <body>
        <button class="btn-home" onclick="regresarHome()">Regresar a Home</button>
        <div class="card">
            <div class="card-header">
                <h1>Registro de Venta</h1>
            </div>
            <div class="card-body">
                <form action="VentaServlet" method="post" onsubmit="disableSubmitButton()">
                    <div class="form-group">
                        <label for="idCliente">Cliente:</label>
                        <select id="idCliente" name="idCliente" class="form-control" required>
                            <option value="">Seleccione un Cliente</option>
                            <% if (clientes != null) {
                            for (Clientes cliente : clientes) {%>
                            <option value="<%= cliente.getIdCliente()%>"><%= cliente.getNombre()%></option>
                            <% }
    } %>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="fechaVenta">Fecha de Venta:</label>
                        <input type="date" id="fechaVenta" name="fechaVenta" class="form-control" required>
                    </div>
                    <!-- Detalles de Venta -->
                    <h2>Detalles de Venta</h2>
                    <div id="detallesVenta">
                        <div class="detalleVenta">
                            <div class="form-group">
                                <label for="idProducto">ID Producto:</label>
                                <select name="idProducto" class="form-control idProducto" onchange="actualizarPrecio(this)" required>
                                    <option value="">Seleccione un producto</option>
                                    <% if (productos != null) {
                                        for (Producto producto : productos) {%>
                                    <option value="<%= producto.getIdProducto()%>"><%= producto.getNombre()%></option>
                                    <% }
                                    }%>
                                </select><br>
                                <div class="form-group">
                                    <label for="cantidad">Cantidad:</label>
                                    <input type="number" name="cantidad" class="form-control cantidad" required>
                                </div>
                                <div class="form-group">
                                    <label for="precioUnitario">Precio Unitario:</label>
                                    <input type="text" name="precioUnitario" class="form-control precioUnitario" readonly>
                                </div>
                            </div>
                        </div>
                        <input type="submit" id="submitButton" class="btn btn-primary btn-registrar-venta" value="Registrar Venta">
                        </form>
                    </div>
            </div>
    </body>
</html>