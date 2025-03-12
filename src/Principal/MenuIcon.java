/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Principal;

/**
 *
 * @author braul
 */


import INVENTARIO.Principal2_0;
import UniVentanas.GraficaWindow;
import Venta.Venta;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuIcon extends JPanel {
    private final JLabel iconLabel;
    private final Icon normalIcon;
    private final Icon hoverIcon;
    private final JLabel textLabel;

    public MenuIcon(Icon icon, String text) {
        setLayout(new BorderLayout());
        setOpaque(false); // Hace el fondo del panel transparente

        // Ajusta el tamaño inicial del icono
        int width = icon.getIconWidth();
        int height = icon.getIconHeight();
        Image img = ((ImageIcon) icon).getImage();
        Image normalImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        this.normalIcon = new ImageIcon(normalImg);

        // Ajusta el tamaño del icono para el efecto hover
        Image hoverImg = img.getScaledInstance(width + 20, height + 20, Image.SCALE_SMOOTH);
        this.hoverIcon = new ImageIcon(hoverImg);

        iconLabel = new JLabel(normalIcon);
        iconLabel.setHorizontalAlignment(JLabel.CENTER);
        iconLabel.setVerticalAlignment(JLabel.CENTER);

        textLabel = new JLabel(text);
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setVerticalAlignment(JLabel.CENTER);

        // Establecer el fondo semitransparente del panel
        setBackground(new Color(255, 255, 255, 150)); // Fondo semitransparente

        // Aplicar el efecto hover solo al icono y al texto
        MouseAdapter hoverEffect = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                iconLabel.setIcon(hoverIcon);
                textLabel.setFont(textLabel.getFont().deriveFont(Font.BOLD));
                System.out.println("Mouse entered: " + text);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                iconLabel.setIcon(normalIcon);
                textLabel.setFont(textLabel.getFont().deriveFont(Font.PLAIN));
                System.out.println("Mouse exited: " + text);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Mouse clicked: " + text);
                handleIconClick(text);
            }
        };

        iconLabel.addMouseListener(hoverEffect);
        textLabel.addMouseListener(hoverEffect);
        addMouseListener(hoverEffect);

        add(iconLabel, BorderLayout.CENTER);
        add(textLabel, BorderLayout.SOUTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    private void handleIconClick(String label) {
        System.out.println("Handling click for: " + label);
        JFrame window = null;
        switch (label) {
            case "Venta":
                System.out.println("Opening Venta window...");
                window = Venta.getInstance();
                break;
            case "Inventario":
                System.out.println("Opening Inventario window...");
                window = Principal2_0.getInstance();
                break;
            case "Analítica":
                System.out.println("Opening Analítica window...");
                window = GraficaWindow.getInstance();
                break;
            // Add cases for other labels as needed
            default:
                System.out.println("No action defined for: " + label);
                break;
        }
        if (window != null) {
            window.setVisible(true);
        }
    }
}
