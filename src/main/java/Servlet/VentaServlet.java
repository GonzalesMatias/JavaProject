package Servlet;

import BD.DatabaseConnection;
import Beans.Clientes;
import Beans.Venta;
import Beans.DetalleVenta;
import Beans.Producto;
import Dao.DVenta;
import Dao.DProducto;
import Dao.DClientes;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;

public class VentaServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

// Método doGet para redirigir a la página de registro de ventas
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (Connection connection = DatabaseConnection.getConnection()) {
            DProducto dProducto = new DProducto(connection);
            List<Producto> productos = dProducto.obtenerTodosLosProductos();
            request.setAttribute("productos", productos);

// Obtener Clientes
            obtenerClientes(request, response);
            request.getRequestDispatcher("VVenta.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }

// Método para obtener a los clientes:
    protected void obtenerClientes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Clientes> clientes = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            DClientes dClientes = new DClientes(connection);
            clientes = dClientes.getAllClientes();
        } catch (SQLException e) {
            e.printStackTrace();
        }

// Depuración: Verificar los clientes obtenidos
        for (Clientes cliente : clientes) {
            System.out.println("Cliente: " + cliente.getIdCliente() + ", Nombre: " + cliente.getNombre());
        }
        request.setAttribute("clientes", clientes);
    }

// Método doPost para manejar la lógica de la venta
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String fechaVenta = request.getParameter("fechaVenta");

            if (fechaVenta == null || fechaVenta.isEmpty()) {
                throw new IllegalArgumentException("La fecha de venta es obligatoria.");
            }

            Venta venta = new Venta();
            venta.setFechaVenta(Date.valueOf(fechaVenta));

// Obtener el ID del cliente desde el formulario
            String idClienteStr = request.getParameter("idCliente");
            if (idClienteStr == null || idClienteStr.isEmpty()) {
                throw new IllegalArgumentException("El ID del cliente es obligatorio.");
            }
            int idCliente = Integer.parseInt(idClienteStr);
            venta.setIdCliente(idCliente); // Asignar el ID del cliente a la venta

            List<DetalleVenta> detallesVenta = new ArrayList<>();
            String[] idProductos = request.getParameterValues("idProducto");
            String[] cantidades = request.getParameterValues("cantidad");
            String[] preciosUnitarios = request.getParameterValues("precioUnitario");

            if (idProductos == null || cantidades == null || preciosUnitarios == null) {
                throw new IllegalArgumentException("Los detalles de la venta son obligatorios.");
            }

            for (int i = 0; i < idProductos.length; i++) {
                DetalleVenta detalle = new DetalleVenta();
                detalle.setIdProducto(Integer.parseInt(idProductos[i]));
                detalle.setCantidad(Integer.parseInt(cantidades[i]));
                detalle.setPrecioUnitario(new BigDecimal(preciosUnitarios[i]));
                detallesVenta.add(detalle);
            }

            venta.setDetallesVenta(detallesVenta);
            venta.calcularTotalVenta();

            try (Connection connection = DatabaseConnection.getConnection()) {
                DVenta dVenta = new DVenta(connection);
                dVenta.insertarVentaConDetalles(venta);

                DProducto dProducto = new DProducto(connection);

                for (DetalleVenta detalle : detallesVenta) {
                    dProducto.reducirCantidadProducto(detalle.getIdProducto(), detalle.getCantidad());
                }

// Obtener los detalles de la venta con el nombre del producto
                List<DetalleVenta> detallesVentaConNombre = dVenta.obtenerDetallesVentaConNombre(venta.getIdVenta());

// Obtener el nombre del cliente
                String queryCliente = "SELECT c.nombre FROM Clientes c JOIN Ventas v ON c.id_cliente = v.id_cliente WHERE v.id_venta = ?";
                PreparedStatement stmtCliente = connection.prepareStatement(queryCliente);
                stmtCliente.setInt(1, venta.getIdVenta());
                System.out.println("Ejecutando consulta SQL: " + stmtCliente);
                ResultSet rsCliente = stmtCliente.executeQuery();
                if (rsCliente.next()) {
                    venta.setNombreCliente(rsCliente.getString("nombre"));
                } else {
                    System.out.println("No se encontró el cliente para la venta con ID: " + venta.getIdVenta());
                }

// Depuración: Verificar los valores antes de pasar a la JSP
                System.out.println("Venta: " + venta);
                System.out.println("ID del Cliente: " + venta.getIdCliente());
                System.out.println("Nombre del Cliente: " + venta.getNombreCliente());
                for (DetalleVenta detalle : detallesVentaConNombre) {
                    System.out.println("Detalle: " + detalle.getIdDetalleVenta() + ", Nombre Producto: " + detalle.getNombre());
                }

                request.setAttribute("venta", venta);
                request.setAttribute("detallesVenta", detallesVentaConNombre);
                request.getRequestDispatcher("VDetalleVenta.jsp").forward(request, response);
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendRedirect("error.jsp");
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }
}
