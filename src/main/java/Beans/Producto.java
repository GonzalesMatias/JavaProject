package Beans;

import java.util.Date;

public class Producto {

private int idProducto;
private String nombre;
private String descripcion;
private double precio;
private String categoria;
private int stockMinimo;
private int stockMaximo;
private int stockActual;
private int cantidadTotal;
private Date fechaActualizacion;

// Constructor
public Producto(int idProducto, String nombre, String descripcion, double precio, String categoria, int stockMinimo, int stockMaximo, int cantidadTotal, Date fechaActualizacion) {
this.idProducto = idProducto;
this.nombre = nombre;
this.descripcion = descripcion;
this.precio = precio;
this.categoria = categoria;
this.stockMinimo = stockMinimo;
this.stockMaximo = stockMaximo;
this.stockActual = 0; // Inicializa stockActual si es necesario
this.cantidadTotal = cantidadTotal;
this.fechaActualizacion = fechaActualizacion;
}

// Getters y Setters
public int getIdProducto() {
return idProducto;
}

public void setIdProducto(int idProducto) {
this.idProducto = idProducto;
}

public String getNombre() {
return nombre;
}

public void setNombre(String nombre) {
this.nombre = nombre;
}

public String getDescripcion() {
return descripcion;
}

public void setDescripcion(String descripcion) {
this.descripcion = descripcion;
}

public double getPrecio() {
return precio;
}

public void setPrecio(double precio) {
this.precio = precio;
}

public String getCategoria() {
return categoria;
}

public void setCategoria(String categoria) {
this.categoria = categoria;
}

public int getStockMinimo() {
return stockMinimo;
}

public void setStockMinimo(int stockMinimo) {
this.stockMinimo = stockMinimo;
}

public int getStockMaximo() {
return stockMaximo;
}

public void setStockMaximo(int stockMaximo) {
this.stockMaximo = stockMaximo;
}

public int getStockActual() {
return stockActual;
}

public void setStockActual(int stockActual) {
this.stockActual = stockActual;
}

public int getCantidadTotal() {
return cantidadTotal;
}

public void setCantidadTotal(int cantidadTotal) {
this.cantidadTotal = cantidadTotal;
}

public Date getFechaActualizacion() {
return fechaActualizacion;
}

public void setFechaActualizacion(Date fechaActualizacion) {
this.fechaActualizacion = fechaActualizacion;
}
}