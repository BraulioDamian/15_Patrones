package PatronBridge;

/**
 * Implementación concreta para mostrar notificaciones como alertas en el sistema.
 */
public class AlertaInternaNotiticacion implements NotificacionImplementor {
    
    @Override
    public boolean enviarMensaje(String destinatario, String asunto, String contenido) {
        try {
            // En un sistema real, aquí registraríamos la alerta en la base de datos
            System.out.println("Alerta interna generada para usuario: " + destinatario);
            System.out.println("Asunto: " + asunto);
            System.out.println("Contenido: " + contenido);
            return true;
        } catch (Exception e) {
            System.err.println("Error al generar alerta interna: " + e.getMessage());
            return false;
        }
    }
    
    @Override
    public boolean estaListo() {
        // Las alertas internas siempre están disponibles
        return true;
    }
    
    @Override
    public String getTipoNotificacion() {
        return "Alerta Interna";
    }
}