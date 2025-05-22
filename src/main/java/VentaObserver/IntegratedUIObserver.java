package VentaObserver;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IntegratedUIObserver implements CobroObserver {
    private JTextArea logArea;
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    
    public IntegratedUIObserver(JTextArea logArea) {
        this.logArea = logArea;
        logEvent("Monitor de eventos iniciado");
    }
    
    private void logEvent(String message) {
        String timestamp = timeFormat.format(new Date());
        SwingUtilities.invokeLater(() -> {
            logArea.append("[" + timestamp + "] " + message + "\n");
            logArea.setCaretPosition(logArea.getDocument().getLength());
        });
    }
    
    // Implementar los métodos del observador igual que en UICobroObserver
    @Override
    public void onMetodoPagoSeleccionado(String metodoPago) {
        logEvent("Método de pago: " + metodoPago);
    }
    
    @Override
    public void onMontoRecibido(double monto) {
        logEvent("Monto: $" + String.format("%.2f", monto));
    }
    
    @Override
    public void onCambioCalculado(double cambio) {
        logEvent("Cambio: $" + String.format("%.2f", cambio));
    }
    
    @Override
    public void onVentaCompleta(double total, double recibido, double cambio) {
        logEvent("✓ Venta completada");
    }
    
    @Override
    public void onVentaCancelada() {
        logEvent("✗ Venta cancelada");
    }
}
