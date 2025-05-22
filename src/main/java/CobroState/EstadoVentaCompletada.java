package CobroState;


import javax.swing.JOptionPane;

public class EstadoVentaCompletada implements CobroState {
    
    @Override
    public void seleccionarMetodoPago(Venta.Cobro cobro, String metodoPago) {
        // No se puede cambiar el método de pago en una venta completada
        JOptionPane.showMessageDialog(cobro, "La venta ya ha sido completada.");
    }
    
    @Override
    public void ingresarMonto(Venta.Cobro cobro, double monto) {
        // No se puede cambiar el monto en una venta completada
        JOptionPane.showMessageDialog(cobro, "La venta ya ha sido completada.");
    }
    
    @Override
    public boolean procesarPago(Venta.Cobro cobro) {
        // No se puede procesar de nuevo una venta completada
        JOptionPane.showMessageDialog(cobro, "La venta ya ha sido procesada correctamente.");
        return true;
    }
    
    @Override
    public void cancelarOperacion(Venta.Cobro cobro) {
        // No se puede cancelar una venta completada
        JOptionPane.showMessageDialog(cobro, "La venta ya ha sido completada, no se puede cancelar.");
    }
    
    @Override
    public String getNombreEstado() {
        return "Venta Completada";
    }
}