package Servlet;

import Dao.DProducto;
import Beans.Producto;
import BD.DatabaseConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.google.gson.Gson;

public class ObtenerProductoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idProductoStr = request.getParameter("idProducto");
        if (idProductoStr == null || idProductoStr.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "El ID del producto es requerido.");
            return;
        }

        try {
            int idProducto = Integer.parseInt(idProductoStr);
            System.out.println("ID del producto recibido: " + idProducto); // Mensaje de depuración
            try (Connection connection = DatabaseConnection.getConnection()) {
                DProducto dProducto = new DProducto(connection);
                Producto producto = dProducto.obtenerProductoPorId(idProducto);
                if (producto != null) {
                    String json = new Gson().toJson(producto);
                    System.out.println("Producto encontrado: " + json); // Mensaje de depuración
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(json);
                } else {
                    System.out.println("Producto no encontrado"); // Mensaje de depuración
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Producto no encontrado");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al obtener el producto");
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "El ID del producto debe ser un número válido.");
        }
    }
}
