package CobroState;

import javax.swing.JOptionPane;

import VentaPayments.Efectivo;

public class EstadoEfectivoSeleccionado implements CobroState {
    
    @Override
    public void seleccionarMetodoPago(Venta.Cobro cobro, String metodoPago) {
        if ("Tarjeta".equals(metodoPago)) {
            cobro.setEstadoActual(new EstadoTarjetaSeleccionada());
            cobro.setEfectivoSeleccionado(false);
            cobro.setTarjetaSeleccionada(true);
            cobro.notifyMetodoPagoSeleccionado(metodoPago);
        }
        // Si ya está en efectivo, no hacemos nada
    }
    
    @Override
    public void ingresarMonto(Venta.Cobro cobro, double monto) {
        cobro.setRecibi(String.valueOf(monto));
        cobro.notifyMontoRecibido(monto);
        
        // Verificar si el monto es suficiente
        if (monto >= cobro.getPre()) {
            double cambio = monto - cobro.getPre();
            cobro.setCambio(cambio);
            cobro.notifyCambioCalculado(cambio);
            cobro.setEstadoActual(new EstadoMontoValidado());
        } else {
            JOptionPane.showMessageDialog(cobro, "El monto ingresado es insuficiente.");
        }
    }
    
    @Override
    public boolean procesarPago(Venta.Cobro cobro) {
        String textoRecibi = cobro.getRecibi().trim();
        if (textoRecibi.isEmpty() || !textoRecibi.matches("\\d+(\\.\\d+)?")) {
            JOptionPane.showMessageDialog(cobro, "Por favor, ingrese un monto válido antes de procesar el pago.");
            return false;
        }
        
        double pago = Double.parseDouble(textoRecibi);
        if (pago < cobro.getPre()) {
            JOptionPane.showMessageDialog(cobro, "El monto ingresado es insuficiente para completar la compra.");
            return false;
        }
        
        // Transición al estado de procesamiento de pago
        cobro.setEstadoActual(new EstadoMontoValidado());
        return true;
    }
    
    @Override
    public void cancelarOperacion(Venta.Cobro cobro) {
        cobro.notifyVentaCancelada();
        cobro.dispose();
    }
    
    @Override
    public String getNombreEstado() {
        return "Efectivo Seleccionado - Esperando ingreso de monto";
    }
}