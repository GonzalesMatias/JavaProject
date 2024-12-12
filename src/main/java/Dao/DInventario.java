package Dao;
import BD.DatabaseConnection;
import Beans.Inventario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;

public class DInventario {

    private Connection connection;

    public DInventario(Connection connection) {
        this.connection = connection;
    }

// Constructor sin argumentos
    public DInventario() {
        try {
            this.connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtener la conexión a la base de datos", e);
        }
    }

    public Connection getConnection() {
        return this.connection;
    }

    public List<Inventario> obtenerInventario() throws SQLException {
        List<Inventario> inventarios = new ArrayList<>();
        String query = "SELECT * FROM Inventario";
        try (PreparedStatement ps = connection.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Inventario inventario = new Inventario(
                        rs.getInt("id_inventario"),
                        rs.getInt("id_producto"), rs.getString("nombre"),
                        rs.getInt("cantidad_actual"),
                        rs.getDate("fecha_actualizacion")
                );
                inventarios.add(inventario);
            }
        }
        return inventarios;
    }

    public Inventario obtenerInventarioPorProducto(int idProducto) throws SQLException {
        Inventario inventario = null;
        String query = "SELECT i.id_inventario, i.id_producto, p.nombre, i.cantidad_actual, i.fecha_actualizacion "
                + "FROM Inventario i "
                + "JOIN Productos p ON i.id_producto = p.id_producto "
                + "WHERE i.id_producto = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, idProducto);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    inventario = new Inventario(
                            rs.getInt("id_inventario"),
                            rs.getInt("id_producto"),
                            rs.getString("nombre"),
                            rs.getInt("cantidad_actual"),
                            rs.getDate("fecha_actualizacion")
                    );
                }
            }
        }
        return inventario;
    }

    //Método agregarInventario en DInventario
    public void agregarInventario(Inventario inventario) throws SQLException {
        String query = "INSERT INTO Inventario (id_producto, cantidad_actual, fecha_actualizacion) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, inventario.getIdProducto());
            ps.setInt(2, inventario.getCantidadActual());
            ps.setTimestamp(3, new java.sql.Timestamp(inventario.getFechaActualizacion().getTime()));
            ps.executeUpdate();
        }
    }

    public void actualizarInventario(Inventario inventario) throws SQLException {
        String query = "UPDATE Inventario SET cantidad_actual = ?, fecha_actualizacion = NOW() WHERE id_producto = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, inventario.getCantidadActual());
            ps.setInt(2, inventario.getIdProducto());
            ps.executeUpdate();
        }
    }

    public void eliminarInventario(int idInventario) throws SQLException {
        String query = "DELETE FROM Inventario WHERE id_inventario = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, idInventario);
            ps.executeUpdate();
        }
    }

    public List<Inventario> obtenerInventarioConNombreProducto() throws SQLException {
        List<Inventario> inventarios = new ArrayList<>();
        String query = "SELECT i.id_inventario, i.id_producto, p.nombre, i.cantidad_actual, i.fecha_actualizacion "
                + "FROM Inventario i "
                + "JOIN Productos p ON i.id_producto = p.id_producto";
        try (PreparedStatement ps = connection.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Inventario inventario = new Inventario(
                        rs.getInt("id_inventario"),
                        rs.getInt("id_producto"),
                        rs.getString("nombre"), // Añadimos el nombre del producto
                        rs.getInt("cantidad_actual"),
                        rs.getDate("fecha_actualizacion")
                );
                inventarios.add(inventario);
            }
        }
        return inventarios;
    }

    public String obtenerNombreProductoPorId(int idProducto) throws SQLException {
        String query = "SELECT nombre FROM productos WHERE id_producto = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, idProducto);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("nombre");
                }
            }
        }
        return null;
    }

    public List<Inventario> obtenerInventarioActualizado() throws SQLException {
        List<Inventario> inventarios = new ArrayList<>();
        String query = "SELECT i.id_inventario, i.id_producto, p.nombre AS nombre_producto, i.cantidad_actual, i.fecha_actualizacion "
                + "FROM Inventario i "
                + "JOIN Productos p ON i.id_producto = p.id_producto";
        try (PreparedStatement ps = connection.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Inventario inventario = new Inventario(
                        rs.getInt("id_inventario"),
                        rs.getInt("id_producto"),
                        rs.getString("nombre_producto"),
                        rs.getInt("cantidad_actual"),
                        rs.getDate("fecha_actualizacion")
                );
                inventarios.add(inventario);
            }
        }
        return inventarios;
    }

    public List<Inventario> getFilteredList(String filterIdProducto, String filterNombreProducto, String filterCantidadActual, String filterFechaActualizacion) throws SQLException {
        List<Inventario> inventarios = new ArrayList<>();
        String query = "SELECT i.id_inventario, i.id_producto, p.nombre AS nombre_producto, i.cantidad_actual, i.fecha_actualizacion "
                + "FROM Inventario i "
                + "JOIN Productos p ON i.id_producto = p.id_producto "
                + "WHERE (i.id_producto LIKE ? OR ? IS NULL) "
                + "AND (p.nombre LIKE ? OR ? IS NULL) "
                + "AND (i.cantidad_actual LIKE ? OR ? IS NULL) "
                + "AND (i.fecha_actualizacion LIKE ? OR ? IS NULL)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, filterIdProducto);
            ps.setString(2, filterIdProducto);
            ps.setString(3, filterNombreProducto);
            ps.setString(4, filterNombreProducto);
            ps.setString(5, filterCantidadActual);
            ps.setString(6, filterCantidadActual);
            ps.setString(7, filterFechaActualizacion);
            ps.setString(8, filterFechaActualizacion);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Inventario inventario = new Inventario(
                            rs.getInt("id_inventario"),
                            rs.getInt("id_producto"),
                            rs.getString("nombre_producto"),
                            rs.getInt("cantidad_actual"),
                            rs.getDate("fecha_actualizacion")
                    );
                    inventarios.add(inventario);
                }
            }
        }
        return inventarios;
    }
}
