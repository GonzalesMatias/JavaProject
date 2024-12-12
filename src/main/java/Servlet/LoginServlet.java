package Servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import BD.DatabaseConnection;
import org.glassfish.jersey.internal.guava.Preconditions;//Guava

public class LoginServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
//  (GUAVA)
            Preconditions.checkArgument(email != null && !email.isEmpty(), "El email no puede estar vacío");
            Preconditions.checkArgument(password != null && !password.isEmpty(), "La contraseña no puede estar vacía");

            try (Connection connection = DatabaseConnection.getConnection()) {
                String sql = "SELECT * FROM Usuarios WHERE nombre_usuario = ? AND contrasena = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, email);
                statement.setString(2, password);

                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    request.getSession().setAttribute("user", email);
                    response.sendRedirect("home.jsp");
                } else {
                    request.setAttribute("message", "Email o contraseña incorrectos.");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                request.setAttribute("message", "Ocurrió un error: " + e.getMessage());
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (IllegalArgumentException e) {
            request.setAttribute("message", e.getMessage());
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}
