/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DBObjetos;

/**
 *
 * @author braul
 */
public class DetalleVenta {
    private int detalleVentaID;
    private int ventaID;
    private int productoID;
    private int cantidad;
    private double precioUnitario;
    private double precioTotal;

    // Constructor
    public DetalleVenta(int detalleVentaID, int ventaID, int productoID, int cantidad, double precioUnitario, double precioTotal) {
        this.detalleVentaID = detalleVentaID;
        this.ventaID = ventaID;
        this.productoID = productoID;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.precioTotal = precioTotal;
    }

    // Getters
    public int getDetalleVentaID() {
        return detalleVentaID;
    }

    public int getVentaID() {
        return ventaID;
    }

    public int getProductoID() {
        return productoID;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    // Setters
    public void setDetalleVentaID(int detalleVentaID) {
        this.detalleVentaID = detalleVentaID;
    }

    public void setVentaID(int ventaID) {
        this.ventaID = ventaID;
    }

    public void setProductoID(int productoID) {
        this.productoID = productoID;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }

    // toString
    @Override
    public String toString() {
        return "DetalleVenta{" +
                "detalleVentaID=" + detalleVentaID +
                ", ventaID=" + ventaID +
                ", productoID=" + productoID +
                ", cantidad=" + cantidad +
                ", precioUnitario=" + precioUnitario +
                ", precioTotal=" + precioTotal +
                '}';
    }
}
