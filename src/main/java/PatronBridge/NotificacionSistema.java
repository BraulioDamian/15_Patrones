package PatronBridge;

/**
 * Clase concreta de notificación para el patrón Bridge.
 * Se especializa en notificaciones relacionadas con el sistema.
 */
public class NotificacionSistema extends Notificacion {
    
    private String prefijo;
    
    /**
     * Constructor que recibe el implementador concreto.
     * 
     * @param implementor Implementador de notificaciones
     */
    public NotificacionSistema(NotificacionImplementor implementor) {
        super(implementor);
        this.prefijo = "[SISTEMA] ";
    }
    
    /**
     * Establece un prefijo personalizado para los mensajes del sistema.
     * 
     * @param prefijo Prefijo para los mensajes
     */
    public void setPrefijo(String prefijo) {
        this.prefijo = prefijo;
    }
    
    @Override
    public boolean enviar(String destinatario, String mensaje) {
        if (!estaListoParaEnviar()) {
            System.err.println("El sistema de notificaciones no está listo");
            return false;
        }
        
        // Añadir el prefijo y enviar
        String asunto = prefijo + "Notificación del Sistema";
        String contenido = prefijo + mensaje;
        
        return implementor.enviarMensaje(destinatario, asunto, contenido);
    }
    
    /**
     * Envía una notificación de mantenimiento del sistema.
     * 
     * @param destinatario Destinatario de la notificación
     * @param fechaInicio Fecha de inicio del mantenimiento
     * @param duracion Duración estimada del mantenimiento
     * @return true si se envió correctamente, false en caso contrario
     */
    public boolean notificarMantenimiento(String destinatario, String fechaInicio, String duracion) {
        if (!estaListoParaEnviar()) {
            return false;
        }
        
        String asunto = prefijo + "Mantenimiento Programado";
        String contenido = prefijo + "Se realizará un mantenimiento programado del sistema.\n" +
                "Fecha de inicio: " + fechaInicio + "\n" +
                "Duración estimada: " + duracion + "\n" +
                "Por favor, guarde su trabajo y cierre la aplicación antes del mantenimiento.";
        
        return implementor.enviarMensaje(destinatario, asunto, contenido);
    }
    
    /**
     * Envía una notificación de actualización del sistema.
     * 
     * @param destinatario Destinatario de la notificación
     * @param version Nueva versión del sistema
     * @param cambios Cambios principales en la nueva versión
     * @return true si se envió correctamente, false en caso contrario
     */
    public boolean notificarActualizacion(String destinatario, String version, String cambios) {
        if (!estaListoParaEnviar()) {
            return false;
        }
        
        String asunto = prefijo + "Nueva Actualización Disponible";
        String contenido = prefijo + "Una nueva actualización está disponible para el sistema.\n" +
                "Versión: " + version + "\n" +
                "Principales cambios: " + cambios + "\n" +
                "La actualización se instalará automáticamente en el próximo inicio.";
        
        return implementor.enviarMensaje(destinatario, asunto, contenido);
    }
}