<%@ page import="java.util.List" %>
<%@ page import="Beans.Compra" %>
<%@ page import="Beans.DetalleCompra" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Detalles de la Compra</title>
        <style>
            body {
                font-family: 'Source Sans Pro', sans-serif;
                background-color: #f4f6f9; /* Fondo gris claro */
                margin: 0;
                padding: 20px;
                color: #333; /* Texto gris oscuro */
                position: relative;
            }
            h1 {
                text-align: center;
                color: #007BFF; /* Azul */
                margin-bottom: 20px;
            }
            h2 {
                color: #007BFF; /* Azul */
                margin-bottom: 10px;
            }
            p {
                color: #333; /* Texto gris oscuro */
            }
            table {
                width: 100%;
                border-collapse: collapse;
                margin-top: 20px;
                table-layout: auto;
            }
            table, th, td {
                border: 1px solid #ddd; /* Bordes grises claros */
            }
            th, td {
                padding: 12px;
                text-align: left;
                color: #333; /* Texto gris oscuro */
                white-space: nowrap;
            }
            th {
                background-color: #28a745; /* Azul */
                color: white;
                position: relative;
            }
            tr:nth-child(even) {
                background-color: #f9f9f9; /* Fondo gris claro */
            }
            tr:hover {
                background-color: #f1f1f1; /* Fondo gris más claro */
            }
            .btn-print, .btn-back {
                background-color: #007BFF; /* Azul */
                color: white;
                border: none;
                padding: 10px 20px;
                text-align: center;
                text-decoration: none;
                display: inline-block;
                font-size: 16px;
                margin: 20px auto;
                cursor: pointer;
                border-radius: 4px;
            }
            .btn-print {
                display: block;
            }
            .btn-back {
                position: absolute;
                top: 20px;
                right: 20px;
            }
        </style>
        <script>
            function printPage() {
                window.print();
            }
            function goBack() {
                window.location.href = 'VCompra.jsp'; // Redirigir a la página de compras
            }
        </script>
    </head>
    <body>
        <button class="btn-back" onclick="goBack()">Regresar a Compras</button>
        <h1>Detalles de la Compra</h1>
        <%
        // Obtener los atributos de la solicitud
            Compra compra = (Compra) request.getAttribute("compra");
            List<DetalleCompra> detallesCompra = (List<DetalleCompra>) request.getAttribute("detallesCompra");

            if (compra != null && detallesCompra != null) {
        %>
        <h2>Información de la Compra</h2>
        <p>ID Proveedor: <%= compra.getIdProveedor()%></p>
        <p>Fecha de Compra: <%= compra.getFechaCompra()%></p>        
        <p>Total de la Compra: <%= compra.getTotalCompra()%></p>

        <h2>Detalles de la Compra</h2>
        <table>
            <thead>
                <tr>
                    <th>ID Producto</th>
                    <th>Nombre Producto</th>
                    <th>Cantidad</th>
                    <th>Precio Unitario</th>
                </tr>
            </thead>
            <tbody>
                <% for (DetalleCompra detalle : detallesCompra) {%>
                <tr>
                    <td><%= detalle.getIdProducto()%></td>
                    <td><%= detalle.getNombre()%></td>
                    <td><%= detalle.getCantidad()%></td>
                    <td><%= detalle.getPrecioUnitario()%></td>
                </tr>
                <% } %>
            </tbody>
        </table>
        <%
        } else {
        %>
        <p>Error: No se pudieron obtener los detalles de la compra.</p>
        <%
            }
        %>
        <button class="btn-print" onclick="printPage()">Imprimir</button>
    </body>
</html>