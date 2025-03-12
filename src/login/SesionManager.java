/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package login;

import DBObjetos.Usuario;

/**
 *
 * @author braul
 */
public class SesionManager {
    
    private static SesionManager instance;
    private Usuario usuarioLogueado;

    private SesionManager() {}

    public static synchronized SesionManager getInstance() {
        if (instance == null) {
            instance = new SesionManager();
        }
        return instance;
    }

    public void login(Usuario usuario) {
        this.usuarioLogueado = usuario;
    }

    public void logout() {
        this.usuarioLogueado = null;
    }

    public Usuario getUsuarioLogueado() {
        return usuarioLogueado;
    }
    
    @Override
    public String toString() {
        if (usuarioLogueado != null) {
            return "SessionManager[usuarioLogueado=" + usuarioLogueado + "]";
        } else {
            return "SessionManager[usuarioLogueado=null]";
        }
    }
}
