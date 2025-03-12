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

public class Alerta {
    private int alertaID;
    private int productoID;
    private String tipoAlerta;
    private String mensaje;
    private LocalDateTime fechaEmitida;

    // Constructor
    public Alerta(int alertaID, int productoID, String tipoAlerta, String mensaje, LocalDateTime fechaEmitida) {
        this.alertaID = alertaID;
        this.productoID = productoID;
        this.tipoAlerta = tipoAlerta;
        this.mensaje = mensaje;
        this.fechaEmitida = fechaEmitida;
    }

    // Getters
    public int getAlertaID() {
        return alertaID;
    }

    public int getProductoID() {
        return productoID;
    }

    public String getTipoAlerta() {
        return tipoAlerta;
    }

    public String getMensaje() {
        return mensaje;
    }

    public LocalDateTime getFechaEmitida() {
        return fechaEmitida;
    }

    // Setters
    public void setAlertaID(int alertaID) {
        this.alertaID = alertaID;
    }

    public void setProductoID(int productoID) {
        this.productoID = productoID;
    }

    public void setTipoAlerta(String tipoAlerta) {
        this.tipoAlerta = tipoAlerta;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void setFechaEmitida(LocalDateTime fechaEmitida) {
        this.fechaEmitida = fechaEmitida;
    }

    // toString
    @Override
    public String toString() {
        return "Alerta{" +
                "alertaID=" + alertaID +
                ", productoID=" + productoID +
                ", tipoAlerta='" + tipoAlerta + '\'' +
                ", mensaje='" + mensaje + '\'' +
                ", fechaEmitida=" + fechaEmitida +
                '}';
    }
}

