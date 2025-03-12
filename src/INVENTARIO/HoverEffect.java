/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package INVENTARIO;

/**
 *
 * @author braul
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HoverEffect {

    private static boolean menuDesplegado = false; // Variable de estado compartida

    public static void setMenuDesplegado(boolean estado) {
        menuDesplegado = estado;
    }

    public static void applyHoverEffect(JLabel label) {
        final ImageIcon originalIcon = (ImageIcon) label.getIcon();
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!menuDesplegado) {
                    label.setIcon(createScaledIcon(originalIcon, 36, 36));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!menuDesplegado) {
                    label.setIcon(originalIcon);
                }
            }

            private ImageIcon createScaledIcon(ImageIcon icon, int width, int height) {
                Image img = icon.getImage();
                Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                return new ImageIcon(scaledImg);
            }
        });
    }
}
