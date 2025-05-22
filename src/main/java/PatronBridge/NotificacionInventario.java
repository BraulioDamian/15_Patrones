package PatronBridge;

/**
 * Clase concreta de notificación para el patrón Bridge.
 * Se especializa en notificaciones relacionadas con el inventario.
 */
public class NotificacionInventario extends Notificacion {
    
    /**
     * Constructor que recibe el implementador concreto.
     * 
     * @param implementor Implementador de notificaciones
     */
    public NotificacionInventario(NotificacionImplementor implementor) {
        super(implementor);
    }
    
    @Override
    public boolean enviar(String destinatario, String mensaje) {
        if (!estaListoParaEnviar()) {
            System.err.println("El sistema de notificaciones de inventario no está listo");
            return false;
        }
        
        String asunto = "[INVENTARIO] Notificación de Inventario";
        String contenido = "[INVENTARIO] " + mensaje;
        
        return implementor.enviarMensaje(destinatario, asunto, contenido);
    }
    
    /**
     * Envía una notificación de stock bajo.
     * 
     * @param destinatario Destinatario de la notificación
     * @param productoID ID del producto
     * @param nombre Nombre del producto
     * @param stockActual Stock actual
     * @param stockMinimo Stock mínimo
     * @return true si se envió correctamente, false en caso contrario
     */
    public boolean notificarStockBajo(String destinatario, int productoID, String nombre, 
            int stockActual, int stockMinimo) {
        
        if (!estaListoParaEnviar()) {
            return false;
        }
        
        String asunto = "[INVENTARIO] Alerta de Stock Bajo";
        String contenido = "[INVENTARIO] Se ha detectado un nivel bajo de stock para el siguiente producto:\n" +
                "ID Producto: " + productoID + "\n" +
                "Producto: " + nombre + "\n" +
                "Stock Actual: " + stockActual + " unidades\n" +
                "Stock Mínimo: " + stockMinimo + " unidades\n\n" +
                "Por favor, realice un pedido para reponer el inventario.";
        
        return implementor.enviarMensaje(destinatario, asunto, contenido);
    }
    
    /**
     * Envía una notificación de producto próximo a caducar.
     * 
     * @param destinatario Destinatario de la notificación
     * @param productoID ID del producto
     * @param nombre Nombre del producto
     * @param fechaCaducidad Fecha de caducidad
     * @param diasRestantes Días restantes para la caducidad
     * @return true si se envió correctamente, false en caso contrario
     */
    public boolean notificarProximoACaducar(String destinatario, int productoID, String nombre, 
            String fechaCaducidad, int diasRestantes) {
        
        if (!estaListoParaEnviar()) {
            return false;
        }
        
        String asunto = "[INVENTARIO] Alerta de Producto Próximo a Caducar";
        String contenido = "[INVENTARIO] Se ha detectado un producto próximo a caducar:\n" +
                "ID Producto: " + productoID + "\n" +
                "Producto: " + nombre + "\n" +
                "Fecha de Caducidad: " + fechaCaducidad + "\n" +
                "Días Restantes: " + diasRestantes + " días\n\n" +
                "Por favor, considere aplicar descuentos o tomar medidas para evitar pérdidas.";
        
        return implementor.enviarMensaje(destinatario, asunto, contenido);
    }
    
    /**
     * Envía una notificación de nuevo producto en inventario.
     * 
     * @param destinatario Destinatario de la notificación
     * @param productoID ID del producto
     * @param nombre Nombre del producto
     * @param cantidad Cantidad recibida
     * @param fecha Fecha de recepción
     * @return true si se envió correctamente, false en caso contrario
     */
    public boolean notificarNuevoProducto(String destinatario, int productoID, String nombre, 
            int cantidad, String fecha) {
        
        if (!estaListoParaEnviar()) {
            return false;
        }
        
        String asunto = "[INVENTARIO] Nuevo Producto Recibido";
        String contenido = "[INVENTARIO] Se ha registrado un nuevo producto en el inventario:\n" +
                "ID Producto: " + productoID + "\n" +
                "Producto: " + nombre + "\n" +
                "Cantidad Recibida: " + cantidad + " unidades\n" +
                "Fecha de Recepción: " + fecha + "\n\n" +
                "El producto ya está disponible para la venta.";
        
        return implementor.enviarMensaje(destinatario, asunto, contenido);
    }
}