<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Home - SISTEMA DE GESTION DE INVENTARIOS</title>
        <!-- Google Font: Source Sans Pro -->
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700&display=fallback">
        <!-- Font Awesome -->
        <link rel="stylesheet" href="AdminLTE/plugins/fontawesome-free/css/all.min.css">
        <!-- Theme style -->
        <link rel="stylesheet" href="AdminLTE/dist/css/adminlte.min.css">
        <link rel="stylesheet" href="css/Home.css">
    </head><!-- comment -->
    <style>
        body {
            font-family: 'Source Sans Pro', sans-serif;
            background-image: url(img/imagenNueva.jpg);
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
        .menu {
            display: flex;
            justify-content: center;
            flex-wrap: wrap;
            margin-top: 20px;
        }

        .menu-item {
            border: 1px solid #444444;
            border-radius: 5px;
            padding: 20px;
            width: 30%;
            margin: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);
            text-align: center;
            cursor: pointer;
            transition: background-color 0.3s, transform 0.3s;
            color: #000; /* Color del texto en estado normal */
            text-decoration: none; /* Quita subrayado si es un enlace */
        }
        
        .menu-item:hover {
            background-color: #fbc02d;
            color: beige; /* Asegura que el texto permanezca negro */
            transform: scale(1.05); /* Efecto de zoom */
        }

        .menu-item:nth-child(1) {
            background-color: #d32f2f;
        }

        .menu-item:nth-child(2) {
            background-color: #388e3c;
        }

        .menu-item:nth-child(3) {
            background-color: darkcyan;
        }

        .menu-item:nth-child(4) {
            background-color: darkorange;
        }

        .menu-item:nth-child(5) {
            background-color: gold;
        }

        .menu-item:nth-child(6) {
            background-color: burlywood;
        }
    </style>
    <body>
        <img src="pics/logo empresa.png" alt="Logo" class="logo">
        <a href="LogoutServlet" class="logout-button">Cerrar sesión</a>
        <div class="welcome-message">
            <h1><ion-icon name="star-outline"></ion-icon> Bienvenido, <%= request.getSession().getAttribute("user")%>!</h1>
        </div>
        <div class="home-container">
            <h2>MENÚ PRINCIPAL</h2>
            <div class="menu">
                <a href="ProductoServlet" class="menu-item"><ion-icon name="analytics-outline"></ion-icon>Gestiones de productos y Seguimiento de inventario</a>
                <a href="CompraServlet" class="menu-item"><ion-icon name="bag-outline"></ion-icon> Registro de compras</a>
                <a href="VentaServlet" class="menu-item"><ion-icon name="reader-outline"></ion-icon> Registro de ventas</a>
                <a href="reporte.jsp" class="menu-item"><ion-icon name="print-outline"></ion-icon> Reportes</a>
                <a href="ProveedoresServlet" class="menu-item"><ion-icon name="people-circle-outline"></ion-icon>Proveedores</a>
                <a href="ClientesServlet" class="menu-item"><ion-icon name="accessibility-outline"></ion-icon>Clientes</a>
            </div>
        </div>
    </body>


    <script type="module" src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.esm.js"></script>
    <script nomodule src="https://unpkg.com/ionicons@7.1.0/dist/ionicons/ionicons.js"></script>
</html>