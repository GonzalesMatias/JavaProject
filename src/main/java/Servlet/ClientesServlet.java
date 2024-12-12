package Servlet;

import Beans.Clientes;
import BD.DatabaseConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//@WebServlet("/ClientesServlet")
public class ClientesServlet extends HttpServlet {

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
            switch (action) {
                case "add":
                    addCliente(request, response, connection);
                    break;
                case "edit":
                    editCliente(request, response, connection);
                    break;
                case "delete":
                    deleteCliente(request, response, connection);
                    break;
                default:
                    listClientes(request, response, connection);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException("Database connection problem", e);
        }
    }

    private void listClientes(HttpServletRequest request, HttpServletResponse response, Connection connection) throws ServletException, IOException {
        try {
            List<Clientes> clientes = new ArrayList<>();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Clientes");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Clientes cliente = new Clientes();
                cliente.setIdCliente(resultSet.getInt("id_cliente"));
                cliente.setNombre(resultSet.getString("nombre"));
                cliente.setContacto(resultSet.getString("contacto"));
                cliente.setTelefono(resultSet.getString("telefono"));
                cliente.setEmail(resultSet.getString("email"));
                clientes.add(cliente);
            }
            request.setAttribute("clientes", clientes);
            request.getRequestDispatcher("clientes.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Error listing clientes", e);
        }
    }

    private void addCliente(HttpServletRequest request, HttpServletResponse response, Connection connection) throws ServletException, IOException {
        Clientes cliente = new Clientes();
        cliente.setNombre(request.getParameter("nombre"));
        cliente.setContacto(request.getParameter("contacto"));
        cliente.setTelefono(request.getParameter("telefono"));
        cliente.setEmail(request.getParameter("email"));

        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Clientes (nombre, contacto, telefono, email) VALUES (?, ?, ?, ?)");
            statement.setString(1, cliente.getNombre());
            statement.setString(2, cliente.getContacto());
            statement.setString(3, cliente.getTelefono());
            statement.setString(4, cliente.getEmail());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ServletException("Error adding cliente", e);
        }

        response.sendRedirect("ClientesServlet");
    }

    private void editCliente(HttpServletRequest request, HttpServletResponse response, Connection connection) throws ServletException, IOException {
        String nombre = request.getParameter("nombre");
        String contacto = request.getParameter("contacto");
        String telefono = request.getParameter("telefono");
        String email = request.getParameter("email");

        if (nombre == null || nombre.isEmpty()) {
            throw new ServletException("El nombre no puede estar vacío");
        }

        Clientes cliente = new Clientes();
        cliente.setIdCliente(Integer.parseInt(request.getParameter("idCliente")));
        cliente.setNombre(nombre);
        cliente.setContacto(contacto);
        cliente.setTelefono(telefono);
        cliente.setEmail(email);

        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE Clientes SET nombre = ?, contacto = ?, telefono = ?, email = ? WHERE id_cliente = ?");
            statement.setString(1, cliente.getNombre());
            statement.setString(2, cliente.getContacto());
            statement.setString(3, cliente.getTelefono());
            statement.setString(4, cliente.getEmail());
            statement.setInt(5, cliente.getIdCliente());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ServletException("Error editing cliente", e);
        }

        response.sendRedirect("ClientesServlet");
    }

    private void deleteCliente(HttpServletRequest request, HttpServletResponse response, Connection connection) throws ServletException, IOException {
        int idCliente = Integer.parseInt(request.getParameter("idCliente"));

        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM Clientes WHERE id_cliente = ?");
            statement.setInt(1, idCliente);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ServletException("Error deleting cliente", e);
        }

        response.sendRedirect("ClientesServlet");
    }
}
