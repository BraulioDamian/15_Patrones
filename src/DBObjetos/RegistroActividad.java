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

public class RegistroActividad {
    private int registroID;
    private int usuarioID;
    private String acción;
    private LocalDateTime fechaAcción;
    private String descripción;

    // Constructor
    public RegistroActividad(int registroID, int usuarioID, String acción, LocalDateTime fechaAcción, String descripción) {
        this.registroID = registroID;
        this.usuarioID = usuarioID;
        this.acción = acción;
        this.fechaAcción = fechaAcción;
        this.descripción = descripción;
    }

    // Getters
    public int getRegistroID() {
        return registroID;
    }

    public int getUsuarioID() {
        return usuarioID;
    }

    public String getAcción() {
        return acción;
    }

    public LocalDateTime getFechaAcción() {
        return fechaAcción;
    }

    public String getDescripción() {
        return descripción;
    }

    // Setters
    public void setRegistroID(int registroID) {
        this.registroID = registroID;
    }

    public void setUsuarioID(int usuarioID) {
        this.usuarioID = usuarioID;
    }

    public void setAcción(String acción) {
        this.acción = acción;
    }

    public void setFechaAcción(LocalDateTime fechaAcción) {
        this.fechaAcción = fechaAcción;
    }

    public void setDescripción(String descripción) {
        this.descripción = descripción;
    }

    // toString
    @Override
    public String toString() {
        return "RegistroActividad{" +
                "registroID=" + registroID +
                ", usuarioID=" + usuarioID +
                ", acción='" + acción + '\'' +
                ", fechaAcción=" + fechaAcción +
                ", descripción='" + descripción + '\'' +
                '}';
    }
}

