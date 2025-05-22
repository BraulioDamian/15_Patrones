package Venta.notificaciones;

import Venta.ServicioEnvio;
import login.MandarCorreosAdapter;

public class CorreoHandler extends NotificacionHandler {
    private ServicioEnvio servicioCorreo = new MandarCorreosAdapter();

    @Override
public void handleRequest(String destinatario, String asunto, String contenido, String archivo) {
    try {
        servicioCorreo.enviar(destinatario, asunto, contenido, archivo);
        System.out.println("Correo enviado. Cadena detenida."); // ¡No llamar al siguiente handler!
    } catch (Exception e) {
        System.err.println("Error en CorreoHandler: " + e.getMessage());
        super.handleRequest(destinatario, asunto, contenido, archivo); // Solo pasa al siguiente si falla
    }
}
}