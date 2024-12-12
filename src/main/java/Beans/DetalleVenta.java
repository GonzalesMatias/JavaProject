package Beans;

import java.math.BigDecimal;

public class DetalleVenta {

private int idDetalleVenta;
private int idVenta;
private int idProducto;
private int cantidad;
private BigDecimal precioUnitario;
private String nombre; // Para almacenar el nombre del producto, si es necesario

// Constructor vacío
public DetalleVenta() {
}

// Constructor con parámetros
public DetalleVenta(int idDetalleVenta, int idVenta, int idProducto, int cantidad, BigDecimal precioUnitario) {
this.idDetalleVenta = idDetalleVenta;
this.idVenta = idVenta;
this.idProducto = idProducto;
this.cantidad = cantidad;
this.precioUnitario = precioUnitario;
}

// Getters y Setters
public int getIdDetalleVenta() {
return idDetalleVenta;
}

public void setIdDetalleVenta(int idDetalleVenta) {
this.idDetalleVenta = idDetalleVenta;
}

public int getIdVenta() {
return idVenta;
}

public void setIdVenta(int idVenta) {
this.idVenta = idVenta;
}

public int getIdProducto() {
return idProducto;
}

public void setIdProducto(int idProducto) {
this.idProducto = idProducto;
}

public int getCantidad() {
return cantidad;
}

public void setCantidad(int cantidad) {
this.cantidad = cantidad;
}

public BigDecimal getPrecioUnitario() {
return precioUnitario;
}

public void setPrecioUnitario(BigDecimal precioUnitario) {
this.precioUnitario = precioUnitario;
}

public String getNombre() {
return nombre;
}

public void setNombre(String nombre) {
this.nombre = nombre;
}

@Override
public String toString() {
return "DetalleVenta{" +
"idDetalleVenta=" + idDetalleVenta +
", idVenta=" + idVenta +
", idProducto=" + idProducto +
", cantidad=" + cantidad +
", precioUnitario=" + precioUnitario +
", nombre='" + nombre + '\'' +
'}';
}
}