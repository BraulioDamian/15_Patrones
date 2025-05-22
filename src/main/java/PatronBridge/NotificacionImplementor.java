package PatronBridge;

/**
 * Interfaz implementador para el patrón Bridge.
 * Define cómo se envían las notificaciones concretas.
 */
public interface NotificacionImplementor {
    
    /**
     * Envía un mensaje al destinatario especificado.
     * 
     * @param destinatario Identificador del destinatario (email, número telefónico, etc.)
     * @param asunto Asunto del mensaje
     * @param contenido Contenido del mensaje
     * @return true si se envió correctamente, false en caso contrario
     */
    boolean enviarMensaje(String destinatario, String asunto, String contenido);
    
    /**
     * Verifica si el implementador está configurado y listo para enviar mensajes.
     * 
     * @return true si está listo, false en caso contrario
     */
    boolean estaListo();
    
    /**
     * Obtiene el nombre del tipo de notificación (Email, SMS, etc.).
     * 
     * @return Nombre del tipo de notificación
     */
    String getTipoNotificacion();
}