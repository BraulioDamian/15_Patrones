package Configuraciones;


import javax.swing.*;
import java.awt.*;

public class Estilos {

    public Estilos() {

    }

    public static void addPlaceholderStyle(JTextField textField){
        Font f = textField.getFont();
        f = f.deriveFont(Font.ITALIC);
        textField.setFont(f);
        textField.setForeground(Color.gray);
    }

    public static void removePlaceholderStyle(JTextField textField){
        Font f = textField.getFont();
        f = f.deriveFont(Font.PLAIN|Font.BOLD);
        textField.setFont(f);
        textField.setForeground(Color.BLACK);
    }

}