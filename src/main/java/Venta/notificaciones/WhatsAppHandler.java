package Venta.notificaciones;

import Venta.ServicioEnvio;
import Venta.WhatsAppAdapter;

public class WhatsAppHandler extends NotificacionHandler {
    private ServicioEnvio servicioWhatsApp = new WhatsAppAdapter();

    @Override
public void handleRequest(String destinatario, String asunto, String contenido, String archivo) {
    try {
        servicioWhatsApp.enviar(destinatario, asunto, contenido, archivo);
        System.out.println("WhatsApp enviado. Cadena detenida."); // ¡No llamar al siguiente handler!
    } catch (Exception e) {
        System.err.println("Error en WhatsAppHandler: " + e.getMessage());
        super.handleRequest(destinatario, asunto, contenido, archivo); // Solo pasa al siguiente si falla
    }
}
}