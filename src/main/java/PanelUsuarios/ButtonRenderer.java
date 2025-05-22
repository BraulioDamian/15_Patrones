/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PanelUsuarios;

/**
 *
 * @author braul
 */


import Configuraciones.*;
import java.awt.Color;
import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import net.coobird.thumbnailator.Thumbnails;

class ButtonRenderer extends DefaultTableCellRenderer {
    private Icon icon;
    private int pressedRow = -1;
    private int pressedCol = -1;
    private final String iconPath;
    private final int iconWidth;
    private final int iconHeight;
    
  public ButtonRenderer(String iconPath, int width, int height) {
        this.iconPath = iconPath;
        this.iconWidth = width;
        this.iconHeight = height;
        setHorizontalAlignment(JLabel.CENTER);
        initializeIcon();
    }
  
    private void initializeIcon() {
        try {
            // Cargar la imagen desde un recurso y escalarla usando Thumbnailator
            BufferedImage originalImage = Thumbnails.of(getClass().getResourceAsStream(iconPath))
                .size(iconWidth, iconHeight)
                .asBufferedImage();
            this.icon = new ImageIcon(originalImage);
        } catch (IOException e) {
            System.err.println("Error loading or scaling the icon: " + e.getMessage());
            e.printStackTrace();
            this.icon = null; // En caso de error, establecer el ícono a nulo
        }
    }

        
    
    public void setPressed(boolean pressed, int row, int col) {
        if (pressed) {
            pressedRow = row;
            pressedCol = col;
        } else {
            pressedRow = -1;
            pressedCol = -1;
        }
    }


    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        setIcon(icon);

        if (pressedRow == row && pressedCol == column) {
            setBackground(Color.LIGHT_GRAY); // Efecto hover activado
        } else {
            setBackground(Color.WHITE); // Efecto hover desactivado
        }
        return this;
    }
    







}


