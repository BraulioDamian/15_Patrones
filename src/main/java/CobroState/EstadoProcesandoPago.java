package CobroState;

import javax.swing.JOptionPane;

import VentaPayments.Efectivo;
import VentaPayments.MetodoPago;

public class EstadoProcesandoPago implements CobroState {
    
    @Override
    public void seleccionarMetodoPago(Venta.Cobro cobro, String metodoPago) {
        // No se permite cambiar el método de pago durante el procesamiento
        JOptionPane.showMessageDialog(cobro, "No se puede cambiar el método de pago mientras se procesa la transacción.");
    }
    
    @Override
    public void ingresarMonto(Venta.Cobro cobro, double monto) {
        // No se permite cambiar el monto durante el procesamiento
        JOptionPane.showMessageDialog(cobro, "No se puede cambiar el monto mientras se procesa la transacción.");
    }
    
    @Override
    public boolean procesarPago(Venta.Cobro cobro) {
        try {
            double montoRecibido = Double.parseDouble(cobro.getRecibi());
            double totalVenta = cobro.getPre();
            double cambio = cobro.getCambio();
            
            // Obtener el método de pago adecuado
            MetodoPago metodoPago = cobro.isEfectivoSeleccionado() 
                ? new Efectivo() 
                : new VentaPayments.Tarjeta();
            
            // Generar PDF con el ticket
            String pdfPath = metodoPago.generarPDF(cobro.getProductos(), totalVenta, montoRecibido, cambio);
            
            if (pdfPath != null) {
                // Enviar el ticket por correo
                boolean enviado = metodoPago.enviarTicketPorCorreo(cobro.getCorreo(), pdfPath);
                
                if (enviado) {
                    cobro.notifyVentaCompleta(totalVenta, montoRecibido, cambio);
                    cobro.setEstadoActual(new EstadoVentaCompletada());
                    // Notificar al listener si existe
                    if (cobro.getVentaListener() != null) {
                        cobro.getVentaListener().onVentaCompleta();
                    }
                    cobro.dispose();
                    return true;
                } else {
                    JOptionPane.showMessageDialog(cobro, "Error al enviar el ticket por correo.");
                    // Volver al estado anterior
                    cobro.setEstadoActual(new EstadoMontoValidado());
                    return false;
                }
            } else {
                JOptionPane.showMessageDialog(cobro, "Error al generar el ticket de venta.");
                // Volver al estado anterior
                cobro.setEstadoActual(new EstadoMontoValidado());
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(cobro, "Error al procesar el pago: " + e.getMessage());
            // Volver al estado anterior
            cobro.setEstadoActual(new EstadoMontoValidado());
            return false;
        }
    }
    
    @Override
    public void cancelarOperacion(Venta.Cobro cobro) {
        // Preguntar si realmente desea cancelar durante el procesamiento
        int respuesta = JOptionPane.showConfirmDialog(cobro, 
            "¿Está seguro de cancelar el proceso de pago en curso?", 
            "Confirmar cancelación", 
            JOptionPane.YES_NO_OPTION);
        
        if (respuesta == JOptionPane.YES_OPTION) {
            cobro.notifyVentaCancelada();
            cobro.dispose();
        }
    }
    
    @Override
    public String getNombreEstado() {
        return "Procesando Pago";
    }
}