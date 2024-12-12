package Beans;

import java.util.Date;

public class Inventario {

    private int idInventario;
    private int idProducto;
    private String nombreProducto; // Añadimos este campo
    private int cantidadActual;
    private Date fechaActualizacion;

// Constructor con nombreProducto
    public Inventario(int idInventario, int idProducto, String nombreProducto, int cantidadActual, Date fechaActualizacion) {
        this.idInventario = idInventario;
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.cantidadActual = cantidadActual;
        this.fechaActualizacion = fechaActualizacion;
    }

// Constructor sin nombreProducto
    public Inventario(int idInventario, int idProducto, int cantidadActual, Date fechaActualizacion) {
        this.idInventario = idInventario;
        this.idProducto = idProducto;
        this.cantidadActual = cantidadActual;
        this.fechaActualizacion = fechaActualizacion;
    }

// Getters y setters
    public int getIdInventario() {
        return idInventario;
    }

    public void setIdInventario(int idInventario) {
        this.idInventario = idInventario;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public int getCantidadActual() {
        return cantidadActual;
    }

    public void setCantidadActual(int cantidadActual) {
        this.cantidadActual = cantidadActual;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }
}
