/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UniVentanas;

/**
 *
 * @author braul
 */
public class MenuPrincipalWindow extends javax.swing.JFrame {

    private static MenuPrincipalWindow instance;

    private MenuPrincipalWindow() {
      super("MenuPrincipal");

        initComponents();
    }

    public static MenuPrincipalWindow getInstance() {
        if (instance == null) {
            instance = new MenuPrincipalWindow();
        }
        return instance;
    }

    private void initComponents() {
        // Código de inicialización generado por NetBeans para los componentes de la ventana
    }
}

