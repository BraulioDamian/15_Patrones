package CobroState;

import javax.swing.JOptionPane;

public class EstadoTarjetaSeleccionada implements CobroState {
    
    @Override
    public void seleccionarMetodoPago(Venta.Cobro cobro, String metodoPago) {
        if ("Efectivo".equals(metodoPago)) {
            cobro.setEstadoActual(new EstadoEfectivoSeleccionado());
            cobro.setEfectivoSeleccionado(true);
            cobro.setTarjetaSeleccionada(false);
            cobro.notifyMetodoPagoSeleccionado(metodoPago);
        }
        // Si ya está en tarjeta, no hacemos nada
    }
    
    @Override
    public void ingresarMonto(Venta.Cobro cobro, double monto) {
        // En caso de tarjeta, el monto exacto debe ser el total
        if (monto != cobro.getPre()) {
            JOptionPane.showMessageDialog(cobro, "Para pago con tarjeta, el monto debe ser exactamente igual al total.");
            return;
        }
        
        cobro.setRecibi(String.valueOf(monto));
        cobro.notifyMontoRecibido(monto);
        cobro.setCambio(0.0); // No hay cambio en pago con tarjeta
        cobro.setEstadoActual(new EstadoMontoValidado());
    }
    
    @Override
    public boolean procesarPago(Venta.Cobro cobro) {
        // Para tarjeta, no necesitamos validar el monto recibido, se cobra el total exacto
        cobro.setRecibi(String.valueOf(cobro.getPre()));
        cobro.setCambio(0.0);
        
        // Verificar si se ingresó un correo para el envío del ticket
        if (cobro.getCorreo().trim().isEmpty()) {
            JOptionPane.showMessageDialog(cobro, "Por favor, ingrese un correo electrónico para enviar el comprobante.");
            return false;
        }
        
        // Transición al estado de procesamiento de pago
        cobro.setEstadoActual(new EstadoProcesandoPago());
        return true;
    }
    
    @Override
    public void cancelarOperacion(Venta.Cobro cobro) {
        cobro.notifyVentaCancelada();
        cobro.dispose();
    }
    
    @Override
    public String getNombreEstado() {
        return "Tarjeta Seleccionada - Esperando confirmación";
    }
}