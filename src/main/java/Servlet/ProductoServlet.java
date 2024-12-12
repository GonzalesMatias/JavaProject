package Servlet;

import Dao.DProducto;
import Beans.Producto;
import BD.DatabaseConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class ProductoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
// Obtener filtros de la solicitud
        String filterCodigo = request.getParameter("filterCodigo");
        String filterNombre = request.getParameter("filterNombre");
        String filterDescripcion = request.getParameter("filterDescripcion");
        String filterPrecio = request.getParameter("filterPrecio");
        String filterCategoria = request.getParameter("filterCategoria");
        String filterStockMinimo = request.getParameter("filterStockMinimo");
        String filterStockMaximo = request.getParameter("filterStockMaximo");

        List<Producto> productos = null;

        try (Connection connection = DatabaseConnection.getConnection()) {
            DProducto dao = new DProducto(connection);
            productos = dao.getFilteredList(filterCodigo, filterNombre, filterDescripcion, filterPrecio, filterCategoria, filterStockMinimo, filterStockMaximo);
            if (productos == null) {
                productos = new ArrayList<>(); // Inicializa la lista si es null
            }
            System.out.println("Productos cargados: " + productos.size());
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error al obtener la lista de productos: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error al obtener la lista de productos: " + e.getMessage());
        }

// Establecer la lista de productos como atributo de la solicitud
        request.setAttribute("FDato", productos);
        System.out.println("Redirigiendo a VProducto.jsp con " + productos.size() + " productos.");
// Redirigir a VProducto.jsp
        request.getRequestDispatcher("VProducto.jsp").forward(request, response);
    }
}
