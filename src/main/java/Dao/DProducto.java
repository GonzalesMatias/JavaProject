package Dao;

import BD.DatabaseConnection;
import Beans.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

public class DProducto {

    private Connection connection;

// Constructor
    public DProducto(Connection connection) {
        this.connection = connection;
    }

// Obtener Producto por ID
    public Producto obtenerProductoPorId(int idProducto) throws SQLException {
        String query = "SELECT * FROM productos WHERE id_producto = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, idProducto);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Producto(
                            rs.getInt("id_producto"),
                            rs.getString("nombre"),
                            rs.getString("descripcion"),
                            rs.getDouble("precio"),
                            rs.getString("categoria"),
                            rs.getInt("stock_minimo"),
                            rs.getInt("stock_maximo"),
                            rs.getInt("cantidad_total"),
                            rs.getDate("fecha_actualizacion")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error al obtener el producto por ID", e);
        }
        return null;
    }

// Actualizar Producto
    public void actualizarProducto(Producto producto) throws SQLException {
        String query = "UPDATE productos SET nombre = ?, descripcion = ?, precio = ?, categoria = ?, stock_minimo = ?, stock_maximo = ?, cantidad_total = ?, fecha_actualizacion = ? WHERE id_producto = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, producto.getNombre());
            ps.setString(2, producto.getDescripcion());
            ps.setDouble(3, producto.getPrecio());
            ps.setString(4, producto.getCategoria());
            ps.setInt(5, producto.getStockMinimo());
            ps.setInt(6, producto.getStockMaximo());
            ps.setInt(7, producto.getCantidadTotal());
            ps.setDate(8, new Date(producto.getFechaActualizacion().getTime()));
            ps.setInt(9, producto.getIdProducto());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error al actualizar el producto", e);
        }
    }

// Método para agregar cantidad a un producto existente
    public void agregarCantidadProducto(int idProducto, int cantidad) throws SQLException {
        String query = "UPDATE productos SET cantidad_total = cantidad_total + ?, fecha_actualizacion = ? WHERE id_producto = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, cantidad);
            ps.setDate(2, new Date(System.currentTimeMillis()));
            ps.setInt(3, idProducto);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error al agregar cantidad al producto", e);
        }
    }

// Obtener Todos los Productos
    public List<Producto> obtenerTodosLosProductos() throws SQLException {
        List<Producto> productos = new ArrayList<>();
        String query = "SELECT * FROM productos";
        try (PreparedStatement ps = connection.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                productos.add(new Producto(
                        rs.getInt("id_producto"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getDouble("precio"),
                        rs.getString("categoria"),
                        rs.getInt("stock_minimo"),
                        rs.getInt("stock_maximo"),
                        rs.getInt("cantidad_total"),
                        rs.getDate("fecha_actualizacion")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error al obtener todos los productos", e);
        }
        return productos;
    }

// Método para obtener una lista filtrada de productos
    public List<Producto> getFilteredList(String codigo, String nombre, String descripcion, String precio, String categoria, String stockMinimo, String stockMaximo) throws Exception {
        List<Producto> array = new ArrayList<>();
        String query = "SELECT * FROM productos WHERE 1=1";

        if (codigo != null && !codigo.isEmpty()) {
            query += " AND id_producto LIKE ?";
        }
        if (nombre != null && !nombre.isEmpty()) {
            query += " AND nombre LIKE ?";
        }
        if (descripcion != null && !descripcion.isEmpty()) {
            query += " AND descripcion LIKE ?";
        }
        if (precio != null && !precio.isEmpty()) {
            query += " AND precio LIKE ?";
        }
        if (categoria != null && !categoria.isEmpty()) {
            query += " AND categoria LIKE ?";
        }
        if (stockMinimo != null && !stockMinimo.isEmpty()) {
            query += " AND stock_minimo LIKE ?";
        }
        if (stockMaximo != null && !stockMaximo.isEmpty()) {
            query += " AND stock_maximo LIKE ?";
        }

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            int paramIndex = 1;
            if (codigo != null && !codigo.isEmpty()) {
                ps.setString(paramIndex++, "%" + codigo + "%");
            }
            if (nombre != null && !nombre.isEmpty()) {
                ps.setString(paramIndex++, "%" + nombre + "%");
            }
            if (descripcion != null && !descripcion.isEmpty()) {
                ps.setString(paramIndex++, "%" + descripcion + "%");
            }
            if (precio != null && !precio.isEmpty()) {
                ps.setString(paramIndex++, "%" + precio + "%");
            }
            if (categoria != null && !categoria.isEmpty()) {
                ps.setString(paramIndex++, "%" + categoria + "%");
            }
            if (stockMinimo != null && !stockMinimo.isEmpty()) {
                ps.setString(paramIndex++, "%" + stockMinimo + "%");
            }
            if (stockMaximo != null && !stockMaximo.isEmpty()) {
                ps.setString(paramIndex++, "%" + stockMaximo + "%");
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    array.add(new Producto(
                            rs.getInt("id_producto"),
                            rs.getString("nombre"),
                            rs.getString("descripcion"),
                            rs.getDouble("precio"),
                            rs.getString("categoria"),
                            rs.getInt("stock_minimo"),
                            rs.getInt("stock_maximo"),
                            rs.getInt("cantidad_total"),
                            rs.getDate("fecha_actualizacion")
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error al obtener la lista de productos", e);
        }
        return array;
    }

// Método insertar producto
    public void insertarProducto(Producto producto) throws SQLException {
        String query = "INSERT INTO productos (id_producto, nombre, descripcion, precio, categoria, stock_minimo, stock_maximo, cantidad_total, fecha_actualizacion) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, producto.getIdProducto());
            ps.setString(2, producto.getNombre());
            ps.setString(3, producto.getDescripcion());
            ps.setDouble(4, producto.getPrecio());
            ps.setString(5, producto.getCategoria());
            ps.setInt(6, producto.getStockMinimo());
            ps.setInt(7, producto.getStockMaximo());
            ps.setInt(8, producto.getCantidadTotal());
            ps.setDate(9, new Date(producto.getFechaActualizacion().getTime()));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error al insertar el producto", e);
        }
    }

// Método eliminar producto
    public void eliminarProducto(int idProducto) throws SQLException {
        String query = "DELETE FROM productos WHERE id_producto = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, idProducto);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error al eliminar el producto", e);
        }
    }

// Método para reducir la cantidad de un producto
    public void reducirCantidadProducto(int idProducto, int cantidad) throws SQLException {
        String query = "UPDATE productos SET cantidad_total = cantidad_total - ?, fecha_actualizacion = ? WHERE id_producto = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, cantidad);
            ps.setDate(2, new Date(System.currentTimeMillis()));
            ps.setInt(3, idProducto);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error al reducir la cantidad del producto", e);
        }
    }
}
