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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AnimacionPanel {
    private final Timer timer;
    private final int delay = 10; // retraso del temporizador en milisegundos
    private final int stepSize = 10; // cantidad de pixeles que se moverá el componente en cada paso
    private JComponent firstComponent;
    private JComponent secondComponent;
    private int endPositionFirst;
    private int endPositionSecond;
    private boolean toRight;

    public AnimacionPanel() {
        // Inicializar el Timer pero no comenzar todavía
        timer = new Timer(delay, null);
        timer.addActionListener(new AnimationListener());
    }

    public void animar(JComponent firstComponent, JComponent secondComponent,
                        int endPositionFirst, int endPositionSecond, boolean toRight) {
        // Establecer los componentes y posiciones finales para la animación
        this.firstComponent = firstComponent;
        this.secondComponent = secondComponent;
        this.endPositionFirst = endPositionFirst;
        this.endPositionSecond = endPositionSecond;
        this.toRight = toRight;

        // Reiniciar y empezar el Timer
        timer.restart();
    }

    private class AnimationListener implements ActionListener {
        @Override
    public void actionPerformed(ActionEvent e) {
        int step = toRight ? stepSize : -stepSize;
        
        // Mover el primer componente si no es null
        if (firstComponent != null) {
            int newFirstPosition = firstComponent.getX() + step;
            if ((toRight && newFirstPosition > endPositionFirst) || (!toRight && newFirstPosition < endPositionFirst)) {
                newFirstPosition = endPositionFirst;
            }
            firstComponent.setLocation(newFirstPosition, firstComponent.getY());
        }
        
        // Mover el segundo componente si no es null
        if (secondComponent != null) {
            int newSecondPosition = secondComponent.getX() + step;
            if ((toRight && newSecondPosition > endPositionSecond) || (!toRight && newSecondPosition < endPositionSecond)) {
                newSecondPosition = endPositionSecond;
            }
            secondComponent.setLocation(newSecondPosition, secondComponent.getY());
        }

        // Detener el timer si ambos componentes son null o han alcanzado su posición final
        if ((firstComponent == null || firstComponent.getX() == endPositionFirst) &&
            (secondComponent == null || secondComponent.getX() == endPositionSecond)) {
            timer.stop();
        }
    }

        private int ActualizarPosicion(int current, int end, boolean toRight) {
            if (toRight) {
                current += stepSize;
                if (current > end) {
                    return end;
                }
            } else {
                current -= stepSize;
                if (current < end) {
                    return end;
                }
            }
            return current;
        }
    }

 
}

