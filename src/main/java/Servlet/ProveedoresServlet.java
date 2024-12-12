package Servlet;

import Beans.Proveedores;
import Dao.DProveedores;
import BD.DatabaseConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//@WebServlet("/ProveedoresServlet")
public class ProveedoresServlet extends HttpServlet {

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
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        try (Connection connection = DatabaseConnection.getConnection()) {
            DProveedores dao = new DProveedores(connection);

            switch (action) {
                case "add":
                    addProveedor(request, response, dao);
                    break;
                case "edit":
                    editProveedor(request, response, dao);
                    break;
                case "delete":
                    deleteProveedor(request, response, dao);
                    break;
                default:
                    listProveedores(request, response, dao);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException("Database connection problem", e);
        }
    }

    private void listProveedores(HttpServletRequest request, HttpServletResponse response, DProveedores dao) throws ServletException, IOException {
        try {
            List<Proveedores> proveedores = dao.getAllProveedores();
            request.setAttribute("proveedores", proveedores);
            request.getRequestDispatcher("proveedores.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Error listing proveedores", e);
        }
    }

    private void addProveedor(HttpServletRequest request, HttpServletResponse response, DProveedores dao) throws ServletException, IOException {
        Proveedores proveedor = new Proveedores();
        proveedor.setNombre(request.getParameter("nombre"));
        proveedor.setContacto(request.getParameter("contacto"));
        proveedor.setTelefono(request.getParameter("telefono"));
        proveedor.setEmail(request.getParameter("email"));

        try {
            dao.addProveedor(proveedor);
        } catch (SQLException e) {
            throw new ServletException("Error adding proveedor", e);
        }

        response.sendRedirect("ProveedoresServlet");
    }

    private void editProveedor(HttpServletRequest request, HttpServletResponse response, DProveedores dao) throws ServletException, IOException {
        String nombre = request.getParameter("nombre");
        String contacto = request.getParameter("contacto");
        String telefono = request.getParameter("telefono");
        String email = request.getParameter("email");

        if (nombre == null || nombre.isEmpty()) {
            throw new ServletException("El nombre no puede estar vacío");
        }

        Proveedores proveedor = new Proveedores();
        proveedor.setIdProveedor(Integer.parseInt(request.getParameter("idProveedor")));
        proveedor.setNombre(nombre);
        proveedor.setContacto(contacto);
        proveedor.setTelefono(telefono);
        proveedor.setEmail(email);

        try {
            dao.updateProveedor(proveedor);
        } catch (SQLException e) {
            throw new ServletException("Error editing proveedor", e);
        }

        response.sendRedirect("ProveedoresServlet");
    }

    private void deleteProveedor(HttpServletRequest request, HttpServletResponse response, DProveedores dao) throws ServletException, IOException {
        int idProveedor = Integer.parseInt(request.getParameter("idProveedor"));

        try {
            dao.deleteProveedor(idProveedor);
        } catch (SQLException e) {
            throw new ServletException("Error deleting proveedor", e);
        }

        response.sendRedirect("ProveedoresServlet");
    }
}
