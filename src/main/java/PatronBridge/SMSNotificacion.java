package PatronBridge;

/**
 * Implementación concreta para enviar notificaciones por SMS.
 */
public class SMSNotificacion implements NotificacionImplementor {
    
    // En un sistema real, aquí tendríamos una conexión a un servicio de SMS
    private boolean servicioActivo;
    
    public SMSNotificacion() {
        // Simulación de configuración del servicio de SMS
        try {
            // En un entorno real, aquí inicializaríamos la API o servicio de SMS
            servicioActivo = true;
        } catch (Exception e) {
            System.err.println("Error al inicializar servicio SMS: " + e.getMessage());
            servicioActivo = false;
        }
    }
    
    @Override
    public boolean enviarMensaje(String destinatario, String asunto, String contenido) {
        if (!estaListo()) {
            System.err.println("El servicio SMS no está activo");
            return false;
        }
        
        if (!isValidPhoneNumber(destinatario)) {
            System.err.println("Número telefónico no válido: " + destinatario);
            return false;
        }
        
        try {
            // En un sistema real, aquí haríamos la llamada a la API de SMS
            System.out.println("SMS enviado a: " + destinatario);
            System.out.println("Contenido: " + contenido);
            return true;
        } catch (Exception e) {
            System.err.println("Error al enviar SMS: " + e.getMessage());
            return false;
        }
    }
    
    @Override
    public boolean estaListo() {
        return servicioActivo;
    }
    
    @Override
    public String getTipoNotificacion() {
        return "SMS";
    }
    
    /**
     * Verifica si un número telefónico tiene un formato válido.
     * 
     * @param telefono Número telefónico a verificar
     * @return true si el número es válido, false en caso contrario
     */
    private boolean isValidPhoneNumber(String telefono) {
        // Validación simple para números telefónicos (10 dígitos)
        String phoneRegex = "^[0-9]{10}$";
        return telefono.matches(phoneRegex);
    }
}