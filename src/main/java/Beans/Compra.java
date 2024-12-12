package Beans;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public class Compra {

    private int idCompra;
    private int idProveedor;
    
    private Date fechaCompra;
    private List<DetalleCompra> detallesCompra;
    private BigDecimal totalCompra;

// Getters y Setters
    public int getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public List<DetalleCompra> getDetallesCompra() {
        return detallesCompra;
    }

    public void setDetallesCompra(List<DetalleCompra> detallesCompra) {
        this.detallesCompra = detallesCompra;
    }

    public BigDecimal getTotalCompra() {
        return totalCompra;
    }

    public void setTotalCompra(BigDecimal totalCompra) {
        this.totalCompra = totalCompra;
    }

// Método para calcular el total de la compra
    public void calcularTotalCompra() {
        BigDecimal total = BigDecimal.ZERO;
        for (DetalleCompra detalle : detallesCompra) {
            BigDecimal subtotal = detalle.getPrecioUnitario().multiply(new BigDecimal(detalle.getCantidad()));
            total = total.add(subtotal);
        }
        this.totalCompra = total;
    }
}
