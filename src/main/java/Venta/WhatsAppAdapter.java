package Venta;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import javax.swing.JOptionPane;

public class WhatsAppAdapter implements ServicioEnvio {
    private static final String ACCOUNT_SID = "AC46d9d1165ed6b5784954a46124f9f273"; // Reemplaza con tu SID
    private static final String AUTH_TOKEN = "a43a86e3853df601f336d935a230fea3"; // Reemplaza con tu token
    private static final String TWILIO_WHATSAPP_NUMBER = "whatsapp:+14155238886";

    // Bloque estático para inicializar Twilio una sola vez
    static {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN); // Inicializa al cargar la clase
    }

    @Override
    public void enviar(String destinatario, String asunto, String contenido, String rutaArchivo) {
        try {
            // Asegúrate de que el número tenga formato: whatsapp:+521234567890
            destinatario="5219516499211";
            PhoneNumber to = new PhoneNumber("whatsapp:+" + destinatario);
            
            Message.creator(
                to,
                new PhoneNumber(TWILIO_WHATSAPP_NUMBER),
                "Ticket: " + contenido
            ).create();
            
            JOptionPane.showMessageDialog(null, "Mensaje enviado a WhatsApp.");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al enviar: " + e.getMessage());
        }
    }
}