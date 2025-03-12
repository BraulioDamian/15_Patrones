/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UniVentanas;

/**
 *
 * @author braul
 */

public class UsuariosWindow extends BaseWindow {
    private static UsuariosWindow instance;

    private UsuariosWindow() {
        super("Usuarios");
        initComponents();
    }

    public static UsuariosWindow getInstance() {
        if (instance == null) {
            instance = new UsuariosWindow();
        }
        return instance;
    }

    private void initComponents() {
        // Código de inicialización generado por NetBeans para los componentes de la ventana
    }
}
