package Servlet;

import Dao.DProducto;
import Beans.Producto;
import BD.DatabaseConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Date;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class EditarProductoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
// Obtener parámetros del formulario
            int idProducto = Integer.parseInt(request.getParameter("idProducto"));
            String nombre = request.getParameter("nombreProducto");
            String descripcion = request.getParameter("descripcion");
            double precio = Double.parseDouble(request.getParameter("precio"));
            String categoria = request.getParameter("categoria");
            int stockMinimo = Integer.parseInt(request.getParameter("stockMinimo"));
            int stockMaximo = Integer.parseInt(request.getParameter("stockMaximo"));
            int cantidadTotal = Integer.parseInt(request.getParameter("cantidadTotal"));
            Date fechaActualizacion = Date.valueOf(request.getParameter("fechaActualizacion"));

// Crear objeto Producto
            Producto producto = new Producto(idProducto, nombre, descripcion, precio, categoria, stockMinimo, stockMaximo, cantidadTotal, fechaActualizacion);

// Actualizar producto en la base de datos
            try (Connection connection = DatabaseConnection.getConnection()) {
                DProducto dProducto = new DProducto(connection);
                dProducto.actualizarProducto(producto);

// Redirigir a ProductoServlet para cargar la lista de productos
                response.sendRedirect("ProductoServlet");
            } catch (SQLException e) {
                e.printStackTrace();
                request.setAttribute("errorMessage", "Error al actualizar el producto: " + e.getMessage());
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
}
