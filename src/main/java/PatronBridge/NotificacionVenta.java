package PatronBridge;

/**
 * Clase concreta de notificación para el patrón Bridge.
 * Se especializa en notificaciones relacionadas con las ventas.
 */
public class NotificacionVenta extends Notificacion {
    
    /**
     * Constructor que recibe el implementador concreto.
     * 
     * @param implementor Implementador de notificaciones
     */
    public NotificacionVenta(NotificacionImplementor implementor) {
        super(implementor);
    }
    
    @Override
    public boolean enviar(String destinatario, String mensaje) {
        if (!estaListoParaEnviar()) {
            System.err.println("El sistema de notificaciones de venta no está listo");
            return false;
        }
        
        String asunto = "[VENTA] Notificación de Venta";
        String contenido = "[VENTA] " + mensaje;
        
        return implementor.enviarMensaje(destinatario, asunto, contenido);
    }
    
    /**
     * Envía una confirmación de venta al cliente.
     * 
     * @param destinatario Destinatario de la notificación
     * @param ventaID ID de la venta
     * @param monto Monto total de la venta
     * @param fecha Fecha de la venta
     * @param metodoPago Método de pago utilizado
     * @return true si se envió correctamente, false en caso contrario
     */
    public boolean enviarConfirmacionVenta(String destinatario, int ventaID, double monto, 
            String fecha, String metodoPago) {
        
        if (!estaListoParaEnviar()) {
            return false;
        }
        
        String asunto = "[VENTA] Confirmación de Compra";
        String contenido = "[VENTA] ¡Gracias por su compra!\n\n" +
                "Detalles de la transacción:\n" +
                "ID de Venta: " + ventaID + "\n" +
                "Fecha: " + fecha + "\n" +
                "Monto Total: $" + String.format("%.2f", monto) + "\n" +
                "Método de Pago: " + metodoPago + "\n\n" +
                "Guarde este comprobante para cualquier reclamación o devolución.";
        
        return implementor.enviarMensaje(destinatario, asunto, contenido);
    }
    
    /**
     * Notifica sobre una venta significativa (alto valor).
     * 
     * @param destinatario Destinatario de la notificación
     * @param ventaID ID de la venta
     * @param monto Monto total de la venta
     * @param itemsVendidos Cantidad de items vendidos
     * @param vendedor Nombre del vendedor
     * @return true si se envió correctamente, false en caso contrario
     */
    public boolean notificarVentaSignificativa(String destinatario, int ventaID, double monto, 
            int itemsVendidos, String vendedor) {
        
        if (!estaListoParaEnviar()) {
            return false;
        }
        
        String asunto = "[VENTA] Alerta de Venta Significativa";
        String contenido = "[VENTA] Se ha registrado una venta de alto valor:\n" +
                "ID de Venta: " + ventaID + "\n" +
                "Monto Total: $" + String.format("%.2f", monto) + "\n" +
                "Items Vendidos: " + itemsVendidos + "\n" +
                "Vendedor: " + vendedor + "\n\n" +
                "Esta venta representa un ingreso significativo para el negocio.";
        
        return implementor.enviarMensaje(destinatario, asunto, contenido);
    }
    
    /**
     * Envía un ticket electrónico de una venta.
     * 
     * @param destinatario Destinatario de la notificación
     * @param ventaID ID de la venta
     * @param detalleVenta Detalle de los productos vendidos
     * @param fecha Fecha de la venta
     * @return true si se envió correctamente, false en caso contrario
     */
    public boolean enviarTicketElectronico(String destinatario, int ventaID, String detalleVenta, 
            String fecha) {
        
        if (!estaListoParaEnviar()) {
            return false;
        }
        
        String asunto = "[VENTA] Ticket Electrónico #" + ventaID;
        String contenido = "[VENTA] TICKET ELECTRÓNICO\n" +
                "===============================\n" +
                "Venta: #" + ventaID + "\n" +
                "Fecha: " + fecha + "\n\n" +
                "DETALLE DE PRODUCTOS:\n" +
                "===============================\n" +
                detalleVenta + "\n\n" +
                "===============================\n" +
                "Gracias por su compra.";
        
        return implementor.enviarMensaje(destinatario, asunto, contenido);
    }
}