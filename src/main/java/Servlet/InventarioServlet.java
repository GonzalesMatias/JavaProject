package Servlet;

import Dao.DInventario;
import Beans.Inventario;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class InventarioServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "list":
                listarInventario(request, response);
                break;
            case "add":
                agregarInventario(request, response);
                break;
            case "update":
                actualizarInventario(request, response);
                break;
            case "delete":
                eliminarInventario(request, response);
                break;
            case "getUpdatedInventory":
                obtenerInventarioActualizado(request, response);
                break;
            default:
                listarInventario(request, response);
                break;
        }
    }

    private void listarInventario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DInventario dao = new DInventario();
        List<Inventario> inventarios = null;

        try {
            inventarios = dao.obtenerInventarioConNombreProducto();
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error al obtener la lista de inventarios: " + e.getMessage());
        }

        request.setAttribute("FDato", inventarios);
        request.getRequestDispatcher("VInventario.jsp").forward(request, response);
    }

    private void agregarInventario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idProducto = Integer.parseInt(request.getParameter("idProducto"));
        int cantidadActual = Integer.parseInt(request.getParameter("cantidadActual"));
        java.util.Date fechaActualizacion = new java.util.Date();

        String nombreProducto = obtenerNombreProducto(idProducto); // Obtener el nombre del producto
        Inventario inventario = new Inventario(0, idProducto, nombreProducto, cantidadActual, fechaActualizacion);
        DInventario dao = new DInventario();

        try {
            dao.agregarInventario(inventario);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error al agregar inventario: " + e.getMessage());
        }

        response.sendRedirect("InventarioServlet?action=list");
    }

    private void actualizarInventario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idInventario = Integer.parseInt(request.getParameter("idInventario"));
        int idProducto = Integer.parseInt(request.getParameter("idProducto"));
        int cantidadActual = Integer.parseInt(request.getParameter("cantidadActual"));
        java.util.Date fechaActualizacion = new java.util.Date();

        String nombreProducto = obtenerNombreProducto(idProducto); // Obtener el nombre del producto
        Inventario inventario = new Inventario(idInventario, idProducto, nombreProducto, cantidadActual, fechaActualizacion);
        DInventario dao = new DInventario();

        try {
            dao.actualizarInventario(inventario);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error al actualizar inventario: " + e.getMessage());
        }

        response.sendRedirect("InventarioServlet?action=list");
    }

    private void eliminarInventario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idInventario = Integer.parseInt(request.getParameter("idInventario"));
        DInventario dao = new DInventario();

        try {
            dao.eliminarInventario(idInventario);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error al eliminar inventario: " + e.getMessage());
        }

        response.sendRedirect("InventarioServlet?action=list");
    }

    private String obtenerNombreProducto(int idProducto) {
        DInventario dao = new DInventario();
        try {
            return dao.obtenerNombreProductoPorId(idProducto);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void obtenerInventarioActualizado(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        listarInventario(request, response);
    }
}
