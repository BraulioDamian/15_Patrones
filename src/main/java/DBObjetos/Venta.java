/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DBObjetos;

/**
 *
 * @author braul
 */
import java.time.LocalDateTime;

public class Venta {
    private int ventaID;
    private int usuarioID;
    private LocalDateTime fechaVenta;
    private double precioTotal;

    // Constructor
    public Venta(int ventaID, int usuarioID, LocalDateTime fechaVenta, double precioTotal) {
        this.ventaID = ventaID;
        this.usuarioID = usuarioID;
        this.fechaVenta = fechaVenta;
        this.precioTotal = precioTotal;
    }

    // Getters
    public int getVentaID() {
        return ventaID;
    }

    public int getUsuarioID() {
        return usuarioID;
    }

    public LocalDateTime getFechaVenta() {
        return fechaVenta;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    // Setters
    public void setVentaID(int ventaID) {
        this.ventaID = ventaID;
    }

    public void setUsuarioID(int usuarioID) {
        this.usuarioID = usuarioID;
    }

    public void setFechaVenta(LocalDateTime fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }

    // toString
    @Override
    public String toString() {
        return "Venta{" +
                "ventaID=" + ventaID +
                ", usuarioID=" + usuarioID +
                ", fechaVenta=" + fechaVenta +
                ", precioTotal=" + precioTotal +
                '}';
    }
}

