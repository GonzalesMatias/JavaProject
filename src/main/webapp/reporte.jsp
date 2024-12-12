<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Reportes - SISTEMA DE GESTION DE INVENTARIOS</title>
        <link rel="stylesheet" href="css/Reporte.css">

    </head>
    <style>
        body {
            font-family: 'Source Sans Pro', sans-serif;
            background-image: url(img/imagen_reportes.webp);
            background-size: cover; /* Ajusta la imagen para cubrir el área completa */
            background-repeat: no-repeat; /* Evita que la imagen se repita */
            background-position: center; /* Centra la imagen */
            color: #e0e0e0; /* Texto gris claro */
            margin: 0;
            padding: 0;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .logo {
            
            position: absolute;
            top: 10px;
            left: 10px;
            width: 250px;
            height: auto;
            border-radius: 10px;
        }
        .btn-home {
            background-color: #007BFF; /* Azul */
            color: white;
            border: none;
            padding: 10px 20px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 16px;
            margin: 4px 2px;
            cursor: pointer;
            border-radius: 4px;
            position: absolute;
            top: 20px;
            right: 20px;
        }
        .container {
            background-color: rgba(0, 0, 0, 0.8);
            padding: 20px;
            border-radius: 8px; /* Bordes redondeados */
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.5); /* Sombra */
            text-align: center;
            margin-top: 60px; /* Espacio para el botón de regresar */
        }
        h1 {
            color: #ffffff; /* Texto blanco */
        }
        p {
            color: #bbbbbb; /* Texto gris claro */
        }
        .btn-primary {
            background-color: #28a745; /* Verde Excel */
            border: none;
            padding: 10px 20px;
            margin-top: 20px;
            color: #ffffff; /* Texto blanco */
            text-decoration: none;
            display: inline-block;
            border-radius: 4px; /* Bordes redondeados */
        }
        .btn-primary:hover {
            background-color: #218838; /* Verde oscuro */
        }
        .form-group {
            margin-bottom: 20px;
        }
        .form-control {
            background-color: beige;
            color: #000000; /* Texto gris claro */
            border: 1px solid #444; /* Bordes grises oscuros */
            padding: 10px;
            border-radius: 4px; /* Bordes redondeados */
            width: 100%;
        }
    </style>
    <body>
        <img src="pics/logo empresa.png" alt="Logo" class="logo">
        <button class="btn-home" onclick="window.location.href = 'home.jsp'">Regresar a Home</button>
        <div class="container">
            <h1>Generar Reporte</h1>
            <p>Seleccione el tipo de reporte que desea generar y haga clic en el botón de abajo.</p>
            <form action="ReporteServlet" method="post">
                <div class="form-group">
                    <label for="reportType">Tipo de Reporte:</label>
                    <select id="reportType" name="reportType" class="form-control" required>
                        <option value="Stock Productos">Stock Productos</option>
                        <option value="Usuarios">Usuarios</option>
                        <option value="Proveedores">Proveedores</option>
                        <option value="Clientes">Clientes</option>
                        <option value="Compras">Compras</option>
                        <option value="Ventas">Ventas</option>
                    </select>
                </div>
                <button type="submit" class="btn btn-primary">Generar Reporte</button>
            </form>
        </div>
    </body>
</html>