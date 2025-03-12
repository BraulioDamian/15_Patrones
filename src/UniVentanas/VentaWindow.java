/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UniVentanas;

/**
 *
 * @author braul
 */
public class VentaWindow extends BaseWindow {
    private static VentaWindow instance;

    private VentaWindow() {
        super("Venta");
        // Configura la ventana Venta aquí
        initComponents();
    }

    public static VentaWindow getInstance() {
        if (instance == null) {
            instance = new VentaWindow();
        }
        return instance;
    }

    private void initComponents() {
        // Código de inicialización generado por NetBeans para los componentes de la ventana
    }
}

