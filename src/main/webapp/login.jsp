<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>SISTEMA DE GESTION DE INVENTARIOS</title>

        <style>

            body {

                font-family: 'Source Sans Pro', sans-serif;
                background-image: url(img/telas.jpg); /* Cambia esta ruta a la ubicación de tu imagen */
                margin: 0;
                padding: 0;
                color: #e0e0e0; /* Texto gris claro */
                display: flex;
                flex-direction: column;
                justify-content: center;
                align-items: center;
                height: 100vh;
            }
            .login-box {
                width: 400px;
                padding: 20px;
                background-color: #1e1e1e; /* Fondo oscuro */
                border-radius: 10px; /* Bordes redondeados */
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.5); /* Sombra */
                margin-bottom: 20px;
            }

            .login-logo {
                text-align: center;
                margin-bottom: 20px;
            }
            .login-logo img {
                border-radius: 10px; /* Bordes redondeados */
                width: 200px;
                height: auto;
            }
            .card {
                background-color: #1e1e1e; /* Fondo oscuro */
                color: #e0e0e0; /* Texto gris claro */
                border-radius: 8px; /* Bordes redondeados */
            }
            .card-header {
                border-radius: 10px; /* Bordes redondeados */
                background-color: #333; /* Fondo oscuro */
                color: white;
                text-align: center;
                font-size: 24px;
                font-weight: bold;
            }
            .card-body {

                background-color: #1e1e1e; /* Fondo oscuro */
            }
            .input-group-text {

                background-color: #333; /* Fondo oscuro */
                color: #e0e0e0; /* Texto gris claro */
            }
            .form-control {
                margin: 2px;
                border-radius: 10px; /* Bordes redondeados */
                background-color: #333; /* Fondo oscuro */
                color: #e0e0e0; /* Texto gris claro */
                border: 1px solid #444; /* Bordes grises oscuros */
                height: 35px; /* Aumentar la altura */
                width: 395px; /* Aumentar la altura */
                font-size: 14px; /* Aumentar el tamaño de la fuente */
            }
            .form-control::placeholder {
                color: #bbb; /* Placeholder gris claro */
            }
            .btn-primary {
                border-radius: 10px; /* Bordes redondeados */
                background-color: #007BFF; /* Azul */
                border: none;
                width: 100%;
                padding: 10px;
                margin-top: 10px;
            }
            .btn-primary:hover {
                background-color: #0056b3; /* Azul oscuro */
            }
            footer {

                width: 100%; /* Asegura que el footer ocupe todo el ancho */
                background-color: rgba(30, 30, 30, 0.90); /* Fondo oscuro con 50% de opacidad */ 
                color: #e0e0e0; /* Texto gris claro */
                text-align: center; /* Centra el texto */
                padding: 10px 0; /* Ajusta el espacio interno */
                position: fixed; /* Fija el footer en la parte inferior */
                bottom: 0; /* Coloca el footer al final */
                left: 0; /* Alinea el footer al inicio horizontal */
                z-index: 10; /* Asegura que esté por encima de otros elementos si es necesario */
            }
            
            a {
                text-decoration: none;
                color: #007BFF; /* Azul */
            }
            a:hover {
                color: #0056b3; /* Azul oscuro */
            }
        </style>
    </head>
    <body class="hold-transition login-page">
        <div class="login-box">
            <div class="login-logo">
                <img src="pics/logo empresa.png" alt="Logo">
            </div>
            <div class="card card-outline card-primary">
                <div class="card-header">
                    Sistema Inventarios
                </div>
                <div class="card-body">
                    <center><p class="login-box-msg">Iniciar sesión</p></center>
                    <form action="LoginServlet" method="post">
                        <div class="input-group mb-3">
                            <input type="text" id="email" name="email" class="form-control" placeholder="Email" required>
                            <div class="input-group-append">
                                <div class="input-group-text">
                                    <span class="fas fa-envelope"></span>
                                </div>
                            </div>
                        </div>
                        <div class="input-group mb-3">
                            <input type="password" id="password" name="password" class="form-control" placeholder="Password" required>
                            <div class="input-group-append">
                                <div class="input-group-text">
                                    <span class="fas fa-lock"></span>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-8">
                                <div class="icheck-primary">
                                    <input type="checkbox" id="remember">
                                    <label for="remember">
                                        Recordarme
                                    </label>
                                </div>
                            </div>
                            <div class="col-4">
                                <button type="submit" class="btn btn-primary btn-block">Iniciar Sesión</button>
                            </div>
                        </div>
                    </form>
                    <p class="mb-1">
                        <a href="#">Olvidé mi contraseña</a>
                    </p>
                    <p class="mb-0">
                        <a href="signup.jsp" class="text-center">Registrar una nueva cuenta</a>
                    </p>
                    <% String message = (String) request.getAttribute("message");
                        String messageType = (String) request.getAttribute("messageType");
                        if (message != null) {%>
                    <div class="message <%= messageType%>"><%= message%></div>
                    <% }%>
                </div>
            </div>
        </div>
        <!-- Pie de página con datos de la empresa -->
        <footer>

            <p>Datos de la Empresa:</p>
            <p>FABRICA DE CONFECCIONES COBRA</p>
            <p>Sector: Textil    Sede: Arequipa    Condición: Activa</p>

        </footer>

    </body>
</html>