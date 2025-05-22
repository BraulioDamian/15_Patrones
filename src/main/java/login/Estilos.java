/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package login;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;

/**
 *
 * @author Luis
 */
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
