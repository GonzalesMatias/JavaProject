package Servlet;

import Dao.DProducto;
import Beans.Producto;
import BD.DatabaseConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Date;
import java.time.LocalDate;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AgregarProductoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
// Obtener parámetros del formulario
            String idProducto = request.getParameter("idProducto");
            String nombre = request.getParameter("nombreProducto");
            String descripcion = request.getParameter("descripcion");
            String precio = request.getParameter("precio");
            String categoria = request.getParameter("categoria");
            String stockMinimo = request.getParameter("stockMinimo");
            String stockMaximo = request.getParameter("stockMaximo");
            String cantidadTotal = request.getParameter("cantidadTotal");

// Validar que todos los campos estén presentes y no estén vacíos
            if (idProducto == null || nombre == null || descripcion == null || precio == null || categoria == null
                    || stockMinimo == null || stockMaximo == null || cantidadTotal == null
                    || idProducto.isEmpty() || nombre.isEmpty() || descripcion.isEmpty() || precio.isEmpty() || categoria.isEmpty()
                    || stockMinimo.isEmpty() || stockMaximo.isEmpty() || cantidadTotal.isEmpty()) {
                throw new IllegalArgumentException("Todos los campos son obligatorios.");
            }

// Generar la fecha de actualización actual
            Date fechaActualizacion = Date.valueOf(LocalDate.now());

// Crear objeto Producto
            Producto producto = new Producto(
                    Integer.parseInt(idProducto),
                    nombre,
                    descripcion,
                    Double.parseDouble(precio),
                    categoria,
                    Integer.parseInt(stockMinimo),
                    Integer.parseInt(stockMaximo),
                    Integer.parseInt(cantidadTotal),
                    fechaActualizacion
            );

// Insertar producto en la base de datos
            try (Connection connection = DatabaseConnection.getConnection()) {
                DProducto dProducto = new DProducto(connection);
                dProducto.insertarProducto(producto);

// Redirigir a ProductoServlet para cargar la lista de productos
                response.sendRedirect("ProductoServlet");
            } catch (SQLException e) {
                e.printStackTrace();
                if (e.getMessage().contains("duplicate key value")) { // Verifica el mensaje de error específico
                    request.setAttribute("errorMessage", "El ID del producto ya existe. Por favor, elige otro.");
                } else {
                    request.setAttribute("errorMessage", "Error al insertar el producto: " + e.getMessage());
                }
                request.getRequestDispatcher("VProductos.jsp").forward(request, response);
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("VProductos.jsp").forward(request, response);
        }
    }
}
