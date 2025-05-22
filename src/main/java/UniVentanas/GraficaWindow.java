/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UniVentanas;

/**
 *
 * @author braul
 */


public class GraficaWindow extends BaseWindow {
    private static GraficaWindow instance;

    private GraficaWindow() {
        super("Grafica Window");
        initComponents();
    }

    public static GraficaWindow getInstance() {
        if (instance == null) {
            instance = new GraficaWindow();
        }
        return instance;
    }

    private void initComponents() {
        // Código de inicialización de componentes, por ejemplo:

    }
}
