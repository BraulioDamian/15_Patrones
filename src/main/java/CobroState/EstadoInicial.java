package CobroState;
import javax.swing.JOptionPane;

public class EstadoInicial implements CobroState {
    
    @Override
    public void seleccionarMetodoPago(Venta.Cobro cobro, String metodoPago) {
        if ("Efectivo".equals(metodoPago)) {
            cobro.setEstadoActual(new EstadoEfectivoSeleccionado());
            cobro.setEfectivoSeleccionado(true);
            cobro.setTarjetaSeleccionada(false);
        } else if ("Tarjeta".equals(metodoPago)) {
            cobro.setEstadoActual(new EstadoTarjetaSeleccionada());
            cobro.setEfectivoSeleccionado(false);
            cobro.setTarjetaSeleccionada(true);
        }
        
        // Notificar el cambio de método de pago a los observadores
        cobro.notifyMetodoPagoSeleccionado(metodoPago);
    }
    
    @Override
    public void ingresarMonto(Venta.Cobro cobro, double monto) {
        JOptionPane.showMessageDialog(cobro, "Por favor, seleccione primero un método de pago.");
    }
    
    @Override
    public boolean procesarPago(Venta.Cobro cobro) {
        JOptionPane.showMessageDialog(cobro, "Por favor, seleccione un método de pago y ingrese un monto válido antes de procesar el pago.");
        return false;
    }
    
    @Override
    public void cancelarOperacion(Venta.Cobro cobro) {
        cobro.notifyVentaCancelada();
        cobro.dispose();
    }
    
    @Override
    public String getNombreEstado() {
        return "Estado Inicial - Esperando selección de método de pago";
    }
}