package Servlet;

import BD.DatabaseConnection;
import Beans.Compra;
import Beans.DetalleCompra;
import Beans.Producto;
import Beans.Proveedores;
import Dao.DCompra;
import Dao.DProducto;
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

public class CompraServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

// Método doGet para redirigir a la página de registro de compras
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (Connection connection = DatabaseConnection.getConnection()) {
            DProducto dProducto = new DProducto(connection);
            List<Producto> productos = dProducto.obtenerTodosLosProductos();
            request.setAttribute("productos", productos);

// Obtener proveedores
            obtenerProveedores(request, response);

            request.getRequestDispatcher("VCompra.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }

// Método para obtener a los proveedores
    protected void obtenerProveedores(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Proveedores> proveedores = new ArrayList<>();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT id_proveedor, nombre FROM Proveedores";
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Proveedores proveedor = new Proveedores();
                proveedor.setIdProveedor(rs.getInt("id_proveedor"));
                proveedor.setNombre(rs.getString("nombre"));
                proveedores.add(proveedor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
// Depuración: Verificar los proveedores obtenidos
        for (Proveedores proveedor : proveedores) {
            System.out.println("Proveedor: " + proveedor.getIdProveedor() + ", Nombre: " + proveedor.getNombre());
        }
        request.setAttribute("proveedores", proveedores);
    }

// Método doPost para manejar la lógica de la compra
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String idProveedor = request.getParameter("idProveedor");
            String fechaCompra = request.getParameter("fechaCompra");

            if (idProveedor == null || fechaCompra == null || idProveedor.isEmpty() || fechaCompra.isEmpty()) {
                throw new IllegalArgumentException("Proveedor y fecha de compra son obligatorios.");
            }

            Compra compras = new Compra();
            compras.setIdProveedor(Integer.parseInt(idProveedor));
            compras.setFechaCompra(Date.valueOf(fechaCompra));

            List<DetalleCompra> detallesCompra = new ArrayList<>();
            String[] idProductos = request.getParameterValues("idProducto");
            String[] cantidades = request.getParameterValues("cantidad");
            String[] preciosUnitarios = request.getParameterValues("precioUnitario");

            if (idProductos == null || cantidades == null || preciosUnitarios == null) {
                throw new IllegalArgumentException("Detalles de compra son obligatorios.");
            }

            for (int i = 0; i < idProductos.length; i++) {
                DetalleCompra detalle = new DetalleCompra();
                detalle.setIdProducto(Integer.parseInt(idProductos[i]));
                detalle.setCantidad(Integer.parseInt(cantidades[i]));
                detalle.setPrecioUnitario(new BigDecimal(preciosUnitarios[i]));
                detallesCompra.add(detalle);
            }

            compras.setDetallesCompra(detallesCompra);
            compras.calcularTotalCompra();

            try (Connection connection = DatabaseConnection.getConnection()) {
                DCompra dCompra = new DCompra(connection);
                dCompra.insertarCompraConDetalles(compras);
                DProducto dProducto = new DProducto(connection);

                for (DetalleCompra detalle : detallesCompra) {
                    dProducto.agregarCantidadProducto(detalle.getIdProducto(), detalle.getCantidad());
                }

// Obtener los detalles de la compra con el nombre del producto
                List<DetalleCompra> detallesCompraConNombre = dCompra.obtenerDetallesCompraConNombre(compras.getIdCompra());

// Depuración: Verificar los valores antes de pasar a la JSP
                System.out.println("Compra: " + compras);
                for (DetalleCompra detalle : detallesCompraConNombre) {
                    System.out.println("Detalle: " + detalle.getIdDetalleCompra() + ", Nombre Producto: " + detalle.getNombre());
                }

                request.setAttribute("compra", compras);
                request.setAttribute("detallesCompra", detallesCompraConNombre);
                request.getRequestDispatcher("VDetalleCompra.jsp").forward(request, response);
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
