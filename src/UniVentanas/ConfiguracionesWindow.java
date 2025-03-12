/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UniVentanas;

/**
 *
 * @author braul
 */
public class ConfiguracionesWindow extends BaseWindow {
    private static ConfiguracionesWindow instance;

    private ConfiguracionesWindow() {
        super("Configuraciones");
        // Configura la ventana Configuraciones aquí
        initComponents();
    }

    public static ConfiguracionesWindow getInstance() {
        if (instance == null) {
            instance = new ConfiguracionesWindow();
        }
        return instance;
    }

    private void initComponents() {
        // Código de inicialización generado por NetBeans para los componentes de la ventana
    }
}