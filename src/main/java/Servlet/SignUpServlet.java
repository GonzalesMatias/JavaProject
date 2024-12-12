package Servlet;

import BD.DatabaseConnection;
import Beans.User;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/SignUpServlet")
public class SignUpServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

// Crear una instancia de User con los datos del formulario
        User newUser = new User(email, password);

// Lógica para guardar el usuario en la base de datos
        try (Connection connection = DatabaseConnection.getConnection()) {
// Verificar si el correo electrónico ya existe
            String checkSql = "SELECT COUNT(*) FROM Usuarios WHERE nombre_usuario = ?";
            PreparedStatement checkStatement = connection.prepareStatement(checkSql);
            checkStatement.setString(1, newUser.getEmail());
            ResultSet resultSet = checkStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);

            if (count > 0) {
                request.setAttribute("message", "El correo electrónico ya está registrado.");
                request.setAttribute("messageType", "error");
            } else {
// Crear la sentencia SQL para insertar un nuevo usuario
                String sql = "INSERT INTO Usuarios (nombre_usuario, contrasena, rol) VALUES (?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, newUser.getEmail());
                statement.setString(2, newUser.getPassword());
                statement.setString(3, "usuario"); // Puedes ajustar el rol según sea necesario

// Ejecutar la sentencia
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    request.setAttribute("message", "¡Un nuevo usuario fue agregado exitosamente!");
                    request.setAttribute("messageType", "success");
                } else {
                    request.setAttribute("message", "Error al agregar el usuario.");
                    request.setAttribute("messageType", "error");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "Ocurrió un error: " + e.getMessage());
            request.setAttribute("messageType", "error");
        }

// Redirige de vuelta a la página de login después del registro
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
}
