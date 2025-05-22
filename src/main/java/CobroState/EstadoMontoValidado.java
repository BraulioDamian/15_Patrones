package CobroState;

import javax.swing.JOptionPane;

public class EstadoMontoValidado implements CobroState {
    
    @Override
    public void seleccionarMetodoPago(Venta.Cobro cobro, String metodoPago) {
        // En este estado ya se validó el monto, si cambia el método de pago
        // debemos regresar al estado correspondiente
        if ("Efectivo".equals(metodoPago)) {
            cobro.setEstadoActual(new EstadoEfectivoSeleccionado());
            cobro.setEfectivoSeleccionado(true);
            cobro.setTarjetaSeleccionada(false);
        } else if ("Tarjeta".equals(metodoPago)) {
            cobro.setEstadoActual(new EstadoTarjetaSeleccionada());
            cobro.setEfectivoSeleccionado(false);
            cobro.setTarjetaSeleccionada(true);
        }
        cobro.notifyMetodoPagoSeleccionado(metodoPago);
    }
    
    @Override
    public void ingresarMonto(Venta.Cobro cobro, double monto) {
        // Actualizar el monto si cambia
        if (cobro.isEfectivoSeleccionado()) {
            if (monto < cobro.getPre()) {
                JOptionPane.showMessageDialog(cobro, "El monto ingresado es insuficiente.");
                return;
            }
            
            cobro.setRecibi(String.valueOf(monto));
            double cambio = monto - cobro.getPre();
            cobro.setCambio(cambio);
            cobro.notifyMontoRecibido(monto);
            cobro.notifyCambioCalculado(cambio);
        } else {
            // Para tarjeta, el monto debe ser exacto
            if (monto != cobro.getPre()) {
                JOptionPane.showMessageDialog(cobro, "Para pago con tarjeta, el monto debe ser exactamente igual al total.");
                return;
            }
            cobro.setRecibi(String.valueOf(monto));
            cobro.setCambio(0.0);
            cobro.notifyMontoRecibido(monto);
        }
    }
    
    @Override
    public boolean procesarPago(Venta.Cobro cobro) {
        // Verificar si se ingresó un correo para el envío del ticket
        if (cobro.getCorreo().trim().isEmpty()) {
            JOptionPane.showMessageDialog(cobro, "Por favor, ingrese un correo electrónico para enviar el comprobante.");
            return false;
        }
        
        cobro.setEstadoActual(new EstadoProcesandoPago());
        return cobro.getEstadoActual().procesarPago(cobro);
    }
    
    @Override
    public void cancelarOperacion(Venta.Cobro cobro) {
        cobro.notifyVentaCancelada();
        cobro.dispose();
    }
    
    @Override
    public String getNombreEstado() {
        return "Monto Validado - Listo para procesar";
    }
}