package VentaObserver;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UICobroObserver implements CobroObserver {
    private JTextArea logArea;
    private JFrame logFrame;
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

    public UICobroObserver() {
        logFrame = new JFrame("Observador de Eventos de Cobro");
        logArea = new JTextArea(20, 50);
        logArea.setEditable(false);

        logFrame.setLayout(new BorderLayout());
        logFrame.add(new JScrollPane(logArea), BorderLayout.CENTER);
        logFrame.setSize(600, 400);
        logFrame.setLocationRelativeTo(null);
        logFrame.setVisible(true);

        logEvent("Observador de Eventos de Cobro iniciado.");
    }

    private void logEvent(String message) {
        String timestamp = timeFormat.format(new Date());
        SwingUtilities.invokeLater(() -> {
            logArea.append("[" + timestamp + "] " + message + "\n");
            logArea.setCaretPosition(logArea.getDocument().getLength());
        });
    }

    @Override
    public void onMetodoPagoSeleccionado(String metodoPago) {
        logEvent("Método de pago seleccionado: " + metodoPago);
    }

    @Override
    public void onMontoRecibido(double monto) {
        logEvent("Monto recibido: $" + String.format("%.2f", monto));
    }

    @Override
    public void onCambioCalculado(double cambio) {
        logEvent("Cambio calculado: $" + String.format("%.2f", cambio));
    }

    @Override
    public void onVentaCompleta(double total, double recibido, double cambio) {
        logEvent("VENTA COMPLETADA");
        logEvent("  Total: $" + String.format("%.2f", total));
        logEvent("  Recibido: $" + String.format("%.2f", recibido));
        logEvent("  Cambio: $" + String.format("%.2f", cambio));
    }

    @Override
    public void onVentaCancelada() {
        logEvent("VENTA CANCELADA");
    }
}