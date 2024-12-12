package Servlet;

import Dao.DProducto;
import BD.DatabaseConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class EliminarProductoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
// Obtener los IDs de los productos seleccionados
            String[] ids = request.getParameterValues("idProducto");
            if (ids != null && ids.length > 0) {
                try (Connection connection = DatabaseConnection.getConnection()) {
                    DProducto dProducto = new DProducto(connection);
                    for (String id : ids) {
                        dProducto.eliminarProducto(Integer.parseInt(id));
                    }
// Redirigir a ProductoServlet para cargar la lista de productos
                    response.sendRedirect("ProductoServlet");
                } catch (SQLException e) {
                    e.printStackTrace();
                    request.setAttribute("errorMessage", "Error al eliminar los productos: " + e.getMessage());
                    request.getRequestDispatcher("error.jsp").forward(request, response);
                }
            } else {
                throw new IllegalArgumentException("Debe seleccionar al menos un producto para eliminar.");
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}
