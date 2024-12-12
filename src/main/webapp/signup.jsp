<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Registro - SISTEMA DE GESTION DE INVENTARIOS</title>
        <style>
            body {
                font-family: 'Source Sans Pro', sans-serif;
                background-image: url(img/telas.jpg); /* Cambia esta ruta a la ubicación de tu imagen */
                margin: 0;
                padding: 0;
                color: #000; /* Texto gris claro */
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
            .signup-container {
                background-color: beige;
                padding: 20px;
                border-radius: 5px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                width: 25%;
            }
            .signup-container h1 {
                border-radius: 20px;
                color: white;
                background-color: #000; /* Fondo oscuro */
                margin-bottom: 20px;
                text-align: center;
            }
            .signup-container input[type="email"],
            .signup-container input[type="password"] {
                width: 90%;
                padding: 10px;
                margin: 10px 0;
                border: 1px solid #ccc;
                border-radius: 5px;
            }
            .signup-container button {
                width: 100%;
                padding: 10px;
                background-color: #000; /* Botón negro */
                color: white; /* Letras blancas */
                border: none;
                border-radius: 5px;
                cursor: pointer;
            }
            .signup-container button:hover {
                background-color: #333;
            }
            .back-to-login {
                position: absolute;
                top: 20px;
                right: 20px;
                background-color: #000; /* Botón negro */
                color: white; /* Letras blancas */
                border: none;
                border-radius: 5px;
                padding: 10px;
                cursor: pointer;
                text-decoration: none;
            }
            .back-to-login:hover {
                background-color: #333;
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
        </style>
    </head>
    <body>
        <a href="login.jsp" class="back-to-login">Volver a Login</a>
        <div class="signup-container">
            <div class="login-logo">
                <img src="pics/logo empresa.png" alt="Logo">
            </div>
            <h1>Registro</h1>
            <form action="SignUpServlet" method="post">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" required>
                <label for="password">Password:</label>
                <input type="password" id="password" name="password" required>
                <label for="confirm-password">Confirmar Password:</label>
                <input type="password" id="confirm-password" name="confirm-password" required>
                <button type="submit">Registrarse</button>
            </form>
        </div>
    </body>
    <footer>

            <p>Datos de la Empresa:</p>
            <p>FABRICA DE CONFECCIONES COBRA</p>
            <p>Sector: Textil    Sede: Arequipa    Condición: Activa</p>

        </footer>
</html>