package VentaObserver;

import java.util.logging.Logger;
import java.util.logging.Level;

public class CobroLogObserver implements CobroObserver {
    private static final Logger LOGGER = Logger.getLogger(CobroLogObserver.class.getName());
    
    @Override
    public void onMetodoPagoSeleccionado(String metodoPago) {
        LOGGER.log(Level.INFO, "Método de pago seleccionado: {0}", metodoPago);
    }
    
    @Override
    public void onMontoRecibido(double monto) {
        LOGGER.log(Level.INFO, "Monto recibido: ${0}", String.format("%.2f", monto));
    }
    
    @Override
    public void onCambioCalculado(double cambio) {
        LOGGER.log(Level.INFO, "Cambio calculado: ${0}", String.format("%.2f", cambio));
    }
    
    @Override
    public void onVentaCompleta(double total, double recibido, double cambio) {
        LOGGER.log(Level.INFO, "Venta completada - Total: ${0}, Recibido: ${1}, Cambio: ${2}", 
                new Object[]{String.format("%.2f", total), String.format("%.2f", recibido), String.format("%.2f", cambio)});
    }
    
    @Override
    public void onVentaCancelada() {
        LOGGER.log(Level.INFO, "Venta cancelada");
    }
}