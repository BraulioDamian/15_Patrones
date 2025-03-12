/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DBObjetos;

/**
 *
 * @author braul
 */
public class ConfiguracionRol {
    private int rolID;
    private String rol;
    private String contraseñaToken;

    // Constructor
    public ConfiguracionRol(int rolID, String rol, String contraseñaToken) {
        this.rolID = rolID;
        this.rol = rol;
        this.contraseñaToken = contraseñaToken;
    }

    // Getters
    public int getRolID() {
        return rolID;
    }

    public String getRol() {
        return rol;
    }

    public String getContraseñaToken() {
        return contraseñaToken;
    }

    // Setters
    public void setRolID(int rolID) {
        this.rolID = rolID;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public void setContraseñaToken(String contraseñaToken) {
        this.contraseñaToken = contraseñaToken;
    }

    @Override
    public String toString() {
        return "ConfiguracionRol{" +
                "rolID=" + rolID +
                ", rol='" + rol + '\'' +
                ", contraseñaToken='" + contraseñaToken + '\'' +
                '}';
    }
}
