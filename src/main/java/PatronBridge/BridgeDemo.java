package PatronBridge;

/**
 * Clase demostrativa para el uso del patrón Bridge en el sistema de notificaciones.
 */
public class BridgeDemo {

    /**
     * Método principal para probar el patrón Bridge.
     * 
     * @param args Argumentos de la línea de comandos
     */
    public static void main(String[] args) {
        // Obtener el gestor de notificaciones
        NotificacionManager manager = NotificacionManager.getInstance();
        
        // Imprimir información sobre los implementadores y notificaciones disponibles
        System.out.println("=== PATRÓN BRIDGE - SISTEMA DE NOTIFICACIONES ===");
        System.out.println("Implementadores disponibles:");
        for (String impl : manager.getImplementadoresDisponibles()) {
            System.out.println("- " + impl);
        }
        
        System.out.println("\nTipos de notificaciones disponibles:");
        for (String notif : manager.getNotificacionesDisponibles()) {
            System.out.println("- " + notif);
        }
        
        // Probar diferentes combinaciones de notificaciones e implementadores
        System.out.println("\n=== EJEMPLOS DE NOTIFICACIONES ===");
        
        // Notificación de sistema por email
        System.out.println("\n1. Notificación de sistema por email:");
        manager.cambiarImplementador("sistema", "email");
        NotificacionSistema notifSistema = manager.getNotificacionSistema();
        notifSistema.notificarMantenimiento("usuario@ejemplo.com", 
                "2023-06-30 22:00", "2 horas");
        
        // Notificación de inventario por SMS
        System.out.println("\n2. Notificación de inventario por SMS:");
        manager.cambiarImplementador("inventario", "sms");
        NotificacionInventario notifInventario = manager.getNotificacionInventario();
        notifInventario.notificarStockBajo("5551234567", 101, "Leche", 5, 10);
        
        // Notificación de venta por alerta interna
        System.out.println("\n3. Notificación de venta por alerta interna:");
        manager.cambiarImplementador("venta", "alerta");
        NotificacionVenta notifVenta = manager.getNotificacionVenta();
        notifVenta.notificarVentaSignificativa("admin", 5001, 1500.0, 25, "Juan Pérez");
        
        // Cambiar implementador en tiempo de ejecución
        System.out.println("\n4. Cambiar implementador en tiempo de ejecución:");
        System.out.println("Antes: " + notifVenta.getTipoNotificacion());
        manager.cambiarImplementador("venta", "email");
        System.out.println("Después: " + notifVenta.getTipoNotificacion());
        notifVenta.enviarTicketElectronico("cliente@ejemplo.com", 5002, 
                "Producto A - $50.00 x 2 = $100.00\nProducto B - $75.50 x 1 = $75.50\n" +
                "Total: $175.50", "2023-06-15 17:45");
    }
}