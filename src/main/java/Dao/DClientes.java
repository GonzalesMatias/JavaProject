package Dao;
import Beans.Clientes;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DClientes {
    private Connection connection;

    public DClientes(Connection connection) {
        this.connection = connection;
    }

    public List<Clientes> getAllClientes() throws SQLException {
        List<Clientes> clientes = new ArrayList<>();
        String query = "SELECT * FROM Clientes";
        try (PreparedStatement statement = connection.prepareStatement(query); ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Clientes cliente = new Clientes();
                cliente.setIdCliente(resultSet.getInt("id_cliente"));
                cliente.setNombre(resultSet.getString("nombre"));
                cliente.setContacto(resultSet.getString("contacto"));
                cliente.setTelefono(resultSet.getString("telefono"));
                cliente.setEmail(resultSet.getString("email"));
                clientes.add(cliente);
            }
        }
        return clientes;
    }

    public void addCliente(Clientes cliente) throws SQLException {
        String query = "INSERT INTO Clientes (nombre, contacto, telefono, email) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, cliente.getNombre());
            statement.setString(2, cliente.getContacto());
            statement.setString(3, cliente.getTelefono());
            statement.setString(4, cliente.getEmail());
            statement.executeUpdate();
        }
    }

    public void updateCliente(Clientes cliente) throws SQLException {
        String query = "UPDATE Clientes SET nombre = ?, contacto = ?, telefono = ?, email = ? WHERE id_cliente = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, cliente.getNombre());
            statement.setString(2, cliente.getContacto());
            statement.setString(3, cliente.getTelefono());
            statement.setString(4, cliente.getEmail());
            statement.setInt(5, cliente.getIdCliente());
            statement.executeUpdate();
        }
    }

    public void deleteCliente(int idCliente) throws SQLException {
        String query = "DELETE FROM Clientes WHERE id_cliente = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idCliente);
            statement.executeUpdate();
        }
    }
}
