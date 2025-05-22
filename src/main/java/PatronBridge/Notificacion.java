package PatronBridge;

/**
 * Clase abstracta que define la abstracción en el patrón Bridge.
 * Declara la interfaz de alto nivel que los clientes utilizarán.
 */
public abstract class Notificacion {
    
    protected NotificacionImplementor implementor;
    
    /**
     * Constructor que recibe el implementador concreto.
     * 
     * @param implementor Implementador de notificaciones
     */
    public Notificacion(NotificacionImplementor implementor) {
        this.implementor = implementor;
    }
    
    /**
     * Cambia el implementador en tiempo de ejecución.
     * 
     * @param implementor Nuevo implementador de notificaciones
     */
    public void setImplementor(NotificacionImplementor implementor) {
        this.implementor = implementor;
    }
    
    /**
     * Obtiene el implementador actual de la notificación.
     * 
     * @return Implementador de notificaciones
     */
    public NotificacionImplementor getImplementor() {
        return this.implementor;
    }
    
    /**
     * Método abstracto para enviar una notificación.
     * Cada subclase lo implementará según sus necesidades.
     * 
     * @param destinatario Destinatario de la notificación
     * @param mensaje Mensaje a enviar
     * @return true si se envió correctamente, false en caso contrario
     */
    public abstract boolean enviar(String destinatario, String mensaje);
    
    /**
     * Verifica si la notificación está lista para ser enviada.
     * 
     * @return true si está lista, false en caso contrario
     */
    public boolean estaListoParaEnviar() {
        return implementor != null && implementor.estaListo();
    }
    
    /**
     * Obtiene el tipo de notificación.
     * 
     * @return Tipo de notificación
     */
    public String getTipoNotificacion() {
        return implementor != null ? implementor.getTipoNotificacion() : "Desconocido";
    }
}
