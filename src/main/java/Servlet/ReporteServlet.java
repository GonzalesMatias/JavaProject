package Servlet;

import static BD.DatabaseConnection.getConnection;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.*; // APACHE POI
import org.apache.poi.xssf.usermodel.XSSFWorkbook; // APACHE POI

//@WebServlet("/ReporteServlet")
public class ReporteServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(ReporteServlet.class.getName());

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String reportType = request.getParameter("reportType");

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=" + reportType + ".xlsx");

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet(reportType);

            try (Connection conn = getConnection()) {
                if ("Stock Productos".equals(reportType)) {
                    createStockProductosReport(sheet, conn);
                } else if ("Usuarios".equals(reportType)) {
                    createUsuariosReport(sheet, conn);
                } else if ("Proveedores".equals(reportType)) {
                    createProveedoresReport(sheet, conn);
                } else if ("Clientes".equals(reportType)) {
                    createClientesReport(sheet, conn);
                } else if ("Compras".equals(reportType)) {
                    createComprasReport(sheet, conn);
                } else if ("Ventas".equals(reportType)) {
                    createVentasReport(sheet, conn);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try (OutputStream out = response.getOutputStream()) {
                workbook.write(out);
            }
        }
    }

    private void createStockProductosReport(Sheet sheet, Connection conn) throws SQLException {
        String query = "SELECT id_producto, nombre, descripcion, precio, categoria, stock_minimo, stock_maximo FROM Productos";
        try (PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("ID Producto");
            headerRow.createCell(1).setCellValue("Nombre");
            headerRow.createCell(2).setCellValue("Descripción");
            headerRow.createCell(3).setCellValue("Precio");
            headerRow.createCell(4).setCellValue("Categoría");
            headerRow.createCell(5).setCellValue("Stock Mínimo");
            headerRow.createCell(6).setCellValue("Stock Máximo");

            int rowNum = 1;
            while (rs.next()) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(rs.getInt("id_producto"));
                row.createCell(1).setCellValue(rs.getString("nombre"));
                row.createCell(2).setCellValue(rs.getString("descripcion"));
                row.createCell(3).setCellValue(rs.getDouble("precio"));
                row.createCell(4).setCellValue(rs.getString("categoria"));
                row.createCell(5).setCellValue(rs.getInt("stock_minimo"));
                row.createCell(6).setCellValue(rs.getInt("stock_maximo"));
            }
        }
    }

    private void createUsuariosReport(Sheet sheet, Connection conn) throws SQLException {
        String query = "SELECT id_usuario, nombre_usuario, contrasena, rol FROM Usuarios";
        try (PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("ID Usuario");
            headerRow.createCell(1).setCellValue("Nombre Usuario");
            headerRow.createCell(2).setCellValue("Contraseña");
            headerRow.createCell(3).setCellValue("Rol");

            int rowNum = 1;
            while (rs.next()) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(rs.getInt("id_usuario"));
                row.createCell(1).setCellValue(rs.getString("nombre_usuario"));
                row.createCell(2).setCellValue(rs.getString("contrasena"));
                row.createCell(3).setCellValue(rs.getString("rol"));
            }
        }
    }

    private void createProveedoresReport(Sheet sheet, Connection conn) throws SQLException {
        String query = "SELECT id_proveedor, nombre, contacto, telefono, email FROM Proveedores";
        try (PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("ID Proveedor");
            headerRow.createCell(1).setCellValue("Nombre");
            headerRow.createCell(2).setCellValue("Contacto");
            headerRow.createCell(3).setCellValue("Teléfono");
            headerRow.createCell(4).setCellValue("Email");

            int rowNum = 1;
            while (rs.next()) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(rs.getInt("id_proveedor"));
                row.createCell(1).setCellValue(rs.getString("nombre"));
                row.createCell(2).setCellValue(rs.getString("contacto"));
                row.createCell(3).setCellValue(rs.getString("telefono"));
                row.createCell(4).setCellValue(rs.getString("email"));
            }
        }
    }

    private void createClientesReport(Sheet sheet, Connection conn) throws SQLException {
        String query = "SELECT id_cliente, nombre, contacto, telefono, email FROM Clientes";
        try (PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("ID Cliente");
            headerRow.createCell(1).setCellValue("Nombre");
            headerRow.createCell(2).setCellValue("Contacto");
            headerRow.createCell(3).setCellValue("Teléfono");
            headerRow.createCell(4).setCellValue("Email");

            int rowNum = 1;
            while (rs.next()) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(rs.getInt("id_cliente"));
                row.createCell(1).setCellValue(rs.getString("nombre"));
                row.createCell(2).setCellValue(rs.getString("contacto"));
                row.createCell(3).setCellValue(rs.getString("telefono"));
                row.createCell(4).setCellValue(rs.getString("email"));
            }
        }
    }

    private void createComprasReport(Sheet sheet, Connection conn) throws SQLException {
        String query = "SELECT id_compra, id_proveedor, fecha_compra, total_compra FROM Compras";
        try (PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("ID Compra");
            headerRow.createCell(1).setCellValue("ID Proveedor");
            headerRow.createCell(2).setCellValue("Fecha Compra");
            headerRow.createCell(3).setCellValue("Total Compra");

            int rowNum = 1;
            while (rs.next()) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(rs.getInt("id_compra"));
                row.createCell(1).setCellValue(rs.getInt("id_proveedor"));
                row.createCell(2).setCellValue(rs.getTimestamp("fecha_compra").toString());
                row.createCell(3).setCellValue(rs.getDouble("total_compra"));
            }
        }
    }

    private void createVentasReport(Sheet sheet, Connection conn) throws SQLException {
        String query = "SELECT id_venta, fecha_venta, total_venta FROM Ventas";
        try (PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("ID Venta");
            headerRow.createCell(1).setCellValue("Fecha Venta");
            headerRow.createCell(2).setCellValue("Total Venta");

            int rowNum = 1;
            while (rs.next()) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(rs.getInt("id_venta"));
                row.createCell(1).setCellValue(rs.getTimestamp("fecha_venta").toString());
                row.createCell(2).setCellValue(rs.getDouble("total_venta"));
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}
