package Dao;

import Beans.Compra;
import Beans.DetalleCompra;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DCompra {

    private Connection connection;

    public DCompra(Connection connection) {
        this.connection = connection;
    }

    public void insertarCompraConDetalles(Compra compra) throws SQLException {
        String insertCompraSQL = "INSERT INTO Compras (id_proveedor, fecha_compra, total_compra) VALUES (?, ?, ?)";
        String insertDetalleSQL = "INSERT INTO Detalle_Compras (id_compra, id_producto, cantidad, precio_unitario) VALUES (?, ?, ?, ?)";

        try (PreparedStatement psCompra = connection.prepareStatement(insertCompraSQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
            connection.setAutoCommit(false); // Iniciar transacción

// Insertar compra
            psCompra.setInt(1, compra.getIdProveedor());
            psCompra.setDate(2, compra.getFechaCompra());
            psCompra.setBigDecimal(3, compra.getTotalCompra());
            psCompra.executeUpdate();

// Obtener el ID generado de la compra
            try (ResultSet generatedKeys = psCompra.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    compra.setIdCompra(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Error al obtener el ID de la compra.");
                }
            }

// Insertar detalles de la compra
            try (PreparedStatement psDetalle = connection.prepareStatement(insertDetalleSQL)) {
                for (DetalleCompra detalle : compra.getDetallesCompra()) {
                    psDetalle.setInt(1, compra.getIdCompra());
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

    public List<DetalleCompra> obtenerDetallesCompraConNombre(int idCompra) throws SQLException {
        String sql = "SELECT dc.id_detalle_compra, dc.id_compra, dc.id_producto, p.nombre AS nombre_producto, dc.cantidad, dc.precio_unitario "
                + "FROM Detalle_Compras dc "
                + "JOIN Productos p ON dc.id_producto = p.id_producto "
                + "WHERE dc.id_compra = ?";

        List<DetalleCompra> detallesCompra = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idCompra);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    DetalleCompra detalle = new DetalleCompra();
                    detalle.setIdDetalleCompra(resultSet.getInt("id_detalle_compra"));
                    detalle.setIdCompra(resultSet.getInt("id_compra"));
                    detalle.setIdProducto(resultSet.getInt("id_producto"));
                    detalle.setNombre(resultSet.getString("nombre_producto"));
                    detalle.setCantidad(resultSet.getInt("cantidad"));
                    detalle.setPrecioUnitario(resultSet.getBigDecimal("precio_unitario"));
                    detallesCompra.add(detalle);

// Línea de depuración
                    System.out.println("Detalle: " + detalle.getIdDetalleCompra() + ", Nombre Producto: " + detalle.getNombre());
                }
            }
        }
        return detallesCompra;
    }
}
