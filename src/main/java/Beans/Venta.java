package Beans;

import java.util.Date;
import java.util.List;

public class Venta {

    private int idVenta;
    private Date fechaVenta;
    private double totalVenta;
    private List<DetalleVenta> detallesVenta;
    private String nombreCliente;
    private int idCliente; // Añadimos esta propiedad

// Constructor vacío
    public Venta() {
    }

// Constructor con parámetros
    public Venta(int idVenta, Date fechaVenta, double totalVenta) {
        this.idVenta = idVenta;
        this.fechaVenta = fechaVenta;
        this.totalVenta = totalVenta;
    }

// Getters y Setters
    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public Date getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public double getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(double totalVenta) {
        this.totalVenta = totalVenta;
    }

    public List<DetalleVenta> getDetallesVenta() {
        return detallesVenta;
    }

    public void setDetallesVenta(List<DetalleVenta> detallesVenta) {
        this.detallesVenta = detallesVenta;
        calcularTotalVenta(); // Recalcular el total de la venta cuando se establecen los detalles
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public void calcularTotalVenta() {
        totalVenta = 0;
        if (detallesVenta != null) {
            for (DetalleVenta detalle : detallesVenta) {
                totalVenta += detalle.getCantidad() * detalle.getPrecioUnitario().doubleValue();
            }
        }
    }
}

