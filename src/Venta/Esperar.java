/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Venta;

import Configuraciones.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 *
 * @author Luis
 */
public class Esperar {
    
    public void esperarYImprimir(String mensaje, int delay) {
        Timer timer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println(mensaje);
                JOptionPane.showMessageDialog(null, mensaje);
            }
        });
        timer.setRepeats(false); // Para asegurarse de que el mensaje se imprime solo una vez
        timer.start();
    }
    
}
