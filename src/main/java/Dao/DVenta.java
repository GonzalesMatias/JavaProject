package Dao;

import Beans.Venta;
import Beans.DetalleVenta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DVenta {

    private Connection connection;

    public DVenta(Connection connection) {
        this.connection = connection;
    }

    public void insertarVentaConDetalles(Venta venta) throws SQLException {
        String insertVentaSQL = "INSERT INTO Ventas (fecha_venta, total_venta) VALUES (?, ?)";
        String insertDetalleSQL = "INSERT INTO Detalle_Ventas (id_venta, id_producto, cantidad, precio_unitario) VALUES (?, ?, ?, ?)";

        try (PreparedStatement psVenta = connection.prepareStatement(insertVentaSQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
            connection.setAutoCommit(false); // Iniciar transacción

// Insertar venta
            psVenta.setTimestamp(1, new java.sql.Timestamp(venta.getFechaVenta().getTime()));
            psVenta.setBigDecimal(2, new java.math.BigDecimal(venta.getTotalVenta()));
            psVenta.executeUpdate();

// Obtener el ID generado de la venta
            try (ResultSet generatedKeys = psVenta.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    venta.setIdVenta(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Error al obtener el ID de la venta.");
                }
            }

// Insertar detalles de la venta
            try (PreparedStatement psDetalle = connection.prepareStatement(insertDetalleSQL)) {
                for (DetalleVenta detalle : venta.getDetallesVenta()) {
                    psDetalle.setInt(1, venta.getIdVenta());
                    psDetalle.setInt(2, detalle.getIdProducto());
                    psDetalle.setInt(3, detalle.getCantidad());
                    psDetalle.setBigDecimal(4, detalle.getPrecioUnitario());
                    psDetalle.addBatch();
                }
                psDetalle.executeBatch();
            }

            connection.commit(); // Confirmar transacción
        } catch (SQLException e) {
            connection.rollback(); // Revertir transacción en caso de error
            throw e;
        } finally {
            connection.setAutoCommit(true); // Restaurar el modo de confirmación automática
        }
    }

    public List<DetalleVenta> obtenerDetallesVentaConNombre(int idVenta) throws SQLException {
        String sql = "SELECT dv.id_detalle_venta, dv.id_venta, dv.id_producto, p.nombre AS nombre_producto, dv.cantidad, dv.precio_unitario "
                + "FROM Detalle_Ventas dv "
                + "JOIN Productos p ON dv.id_producto = p.id_producto "
                + "WHERE dv.id_venta = ?";

        List<DetalleVenta> detallesVenta = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idVenta);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    DetalleVenta detalle = new DetalleVenta();
                    detalle.setIdDetalleVenta(resultSet.getInt("id_detalle_venta"));
                    detalle.setIdVenta(resultSet.getInt("id_venta"));
                    detalle.setIdProducto(resultSet.getInt("id_producto"));
                    detalle.setNombre(resultSet.getString("nombre_producto"));
                    detalle.setCantidad(resultSet.getInt("cantidad"));
                    detalle.setPrecioUnitario(resultSet.getBigDecimal("precio_unitario"));
                    detallesVenta.add(detalle);

// Línea de depuración
                    System.out.println("Detalle: " + detalle.getIdDetalleVenta() + ", Nombre Producto: " + detalle.getNombre());
                }
            }
        }
        return detallesVenta;
    }
}
