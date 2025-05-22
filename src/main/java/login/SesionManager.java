/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package login;

import Configuraciones.Configuraciones;
import DBObjetos.Usuario;
import Graficas.AvisosFrame;
import INVENTARIO.Principal2_0;
import PanelUsuarios.UsuariosPanel;
import Principal.MenuPrincipal;
import Venta.Venta;

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

    // Modificado para incluir el reinicio de las instancias Singleton
    public void logout() {
        this.usuarioLogueado = null;
        resetSingletons();  // Llamada al nuevo método que reinicia los Singletons
    }

    // Método que resetea todas las instancias Singleton
    private void resetSingletons() {
        MenuPrincipal.resetInstance();
        Venta.resetInstance();
        UsuariosPanel.resetInstance();
        AvisosFrame.resetInstance();
        Principal2_0.resetInstance();
        Configuraciones.resetInstance();
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
