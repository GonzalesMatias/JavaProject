package Dao;

import Beans.Proveedores;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DProveedores {

    private Connection connection;

    public DProveedores(Connection connection) {
        this.connection = connection;
    }

    public List<Proveedores> getAllProveedores() throws SQLException {
        List<Proveedores> proveedores = new ArrayList<>();
        String query = "SELECT * FROM Proveedores";
        try (PreparedStatement statement = connection.prepareStatement(query); ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Proveedores proveedor = new Proveedores();
                proveedor.setIdProveedor(resultSet.getInt("id_proveedor"));
                proveedor.setNombre(resultSet.getString("nombre"));
                proveedor.setContacto(resultSet.getString("contacto"));
                proveedor.setTelefono(resultSet.getString("telefono"));
                proveedor.setEmail(resultSet.getString("email"));
                proveedores.add(proveedor);
            }
        }
        return proveedores;
    }

    public void addProveedor(Proveedores proveedor) throws SQLException {
        String query = "INSERT INTO Proveedores (nombre, contacto, telefono, email) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, proveedor.getNombre());
            statement.setString(2, proveedor.getContacto());
            statement.setString(3, proveedor.getTelefono());
            statement.setString(4, proveedor.getEmail());
            statement.executeUpdate();
        }
    }

    public void updateProveedor(Proveedores proveedor) throws SQLException {
        String query = "UPDATE Proveedores SET nombre = ?, contacto = ?, telefono = ?, email = ? WHERE id_proveedor = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, proveedor.getNombre());
            statement.setString(2, proveedor.getContacto());
            statement.setString(3, proveedor.getTelefono());
            statement.setString(4, proveedor.getEmail());
            statement.setInt(5, proveedor.getIdProveedor());
            statement.executeUpdate();
        }
    }

    public void deleteProveedor(int idProveedor) throws SQLException {
        String query = "DELETE FROM Proveedores WHERE id_proveedor = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idProveedor);
            statement.executeUpdate();
        }
    }
}
