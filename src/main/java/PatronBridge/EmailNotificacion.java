package PatronBridge;

import login.MandarCorreos;
import javax.swing.JOptionPane;

/**
 * Implementación concreta para enviar notificaciones por correo electrónico.
 */
public class EmailNotificacion implements NotificacionImplementor {
    
    private MandarCorreos servicioEmail;
    
    public EmailNotificacion() {
        try {
            servicioEmail = new MandarCorreos();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                    "Error al inicializar el servicio de correo: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    @Override
    public boolean enviarMensaje(String destinatario, String asunto, String contenido) {
        try {
            if (!isValidEmail(destinatario)) {
                System.out.println("Dirección de correo no válida: " + destinatario);
                return false;
            }
            
            // Enviar el mensaje con el asunto y contenido proporcionados
            return servicioEmail.enviarCorreo(destinatario, asunto, contenido);
        } catch (Exception e) {
            System.err.println("Error al enviar correo: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean estaListo() {
        return servicioEmail != null;
    }
    
    @Override
    public String getTipoNotificacion() {
        return "Email";
    }
    
    /**
     * Verifica si una dirección de correo es válida.
     * 
     * @param email Dirección de correo a verificar
     * @return true si la dirección es válida, false en caso contrario
     */
    private boolean isValidEmail(String email) {
        // Validación simple de email usando expresión regular
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(emailRegex);
    }
}