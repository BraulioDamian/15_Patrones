/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Principal;

import INVENTARIO.Principal2_0;
import UniVentanas.GraficaWindow;
import Venta.Venta;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author braul
 */




import javax.swing.*;
import java.awt.*;

public class Menu1 extends JFrame {
    public Menu1() {
        setTitle("Menu con Efecto Hover");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        GradientPanel panel = new GradientPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(30, 30, 30, 30);
        gbc.fill = GridBagConstraints.BOTH;

        String[] iconNames = {
            "Venta128px.png", "Menu32px.png", "Usuarios128px.png",
            "Inventario128px.png", "Graficas128px.png", "Configuraciones128px.png"
        };

        String[] labels = {
            "Venta", "Menú", "Usuario",
            "Inventario", "Analítica", "Configuración"
        };

        int[][] positions = {
            {0, 0}, {1, 0}, {2, 0},
            {0, 1}, {1, 1}, {2, 1}
        };

        for (int i = 0; i < iconNames.length; i++) {
            ImageIcon icon = new ImageIcon(getClass().getResource("/Icons/" + iconNames[i]));
            MenuIcon menuIcon = new MenuIcon(icon, labels[i]);
            menuIcon.setPreferredSize(new Dimension(200, 200));
            menuIcon.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Añadir un borde para verificar la visibilidad

            gbc.gridx = positions[i][0];
            gbc.gridy = positions[i][1];
            gbc.gridwidth = 1;
            gbc.gridheight = 1;
            gbc.weightx = 1.0;
            gbc.weighty = 1.0;
            panel.add(menuIcon, gbc);
        }

        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Menu1 menu = new Menu1();
            menu.setVisible(true);
        });
    }
}
