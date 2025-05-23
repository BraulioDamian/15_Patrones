/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package login;


import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MandarCorreos {
    /**
     * Envía un correo electrónico con el asunto y contenido especificados.
     * 
     * @param emailDestino Dirección de correo del destinatario
     * @param asunto Asunto del correo
     * @param contenido Contenido del correo (puede incluir formato HTML)
     * @return true si el correo se envió correctamente, false en caso contrario
     */
    public boolean enviarCorreo(String emailDestino, String asunto, String contenido) {
        try {
            // Validar el email
            if (emailDestino == null || emailDestino.trim().isEmpty() || !emailDestino.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                Logger.getLogger(MandarCorreos.class.getName()).log(Level.WARNING, "Dirección de correo no válida: {0}", emailDestino);
                return false;
            }
            
            // Crear un contenido HTML adecuado
            String htmlContent = "<html><body>" + contenido.replace("\n", "<br>") + "</body></html>";
            
            // Enviar el correo
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailFrom));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(emailDestino));
            message.setSubject(asunto);
            message.setContent(htmlContent, "text/html; charset=UTF-8");

            Transport transport = session.getTransport("smtp");
            transport.connect(emailFrom, passwordFrom);
            transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
            transport.close();
            
            Logger.getLogger(MandarCorreos.class.getName()).log(Level.INFO, "Correo enviado con éxito a: {0}", emailDestino);
            return true;
        } catch (Exception ex) {
            Logger.getLogger(MandarCorreos.class.getName()).log(Level.SEVERE, "Error al enviar correo", ex);
            return false;
        }
    }
    private static final String emailFrom = "brauliodamian80@gmail.com";
    private static final String passwordFrom = "xkpy qgkw zqpg akmr";
    private Properties properties;
    private Session session;
    
    public MandarCorreos() {
        properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.port", "587");
        properties.setProperty("mail.smtp.user", emailFrom);
        properties.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
        properties.setProperty("mail.smtp.auth", "true");

        session = Session.getDefaultInstance(properties);
    }
    
    public void enviarCodigoVerificacion(String emailDestino, int codigoVerificacion) {
        String subject = "Código de Verificación";
        String content = "Su código de verificación es: " + codigoVerificacion;
        sendEmail(emailDestino, subject, content);
    }

    public void enviarArchivo(String emailDestino, String subject, String content, String filePath) {
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailFrom));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailDestino));
            message.setSubject(subject);

            // Crear el contenido del mensaje y adjuntar el archivo
            Multipart multipart = new MimeMultipart();

            // Parte de texto
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(content);
            multipart.addBodyPart(messageBodyPart);

            // Parte del archivo
            messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(filePath);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filePath.substring(filePath.lastIndexOf("/") + 1));
            multipart.addBodyPart(messageBodyPart);

            // Set the complete message parts
            message.setContent(multipart);

            // Enviar el mensaje
            Transport transport = session.getTransport("smtp");
            transport.connect(emailFrom, passwordFrom);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();

        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }
    
    
    
    private void sendEmail(String to, String subject, String content) {
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailFrom));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setText(content, "ISO-8859-1", "html");

            Transport transport = session.getTransport("smtp");
            transport.connect(emailFrom, passwordFrom);
            transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
            transport.close();
            
            Logger.getLogger(MandarCorreos.class.getName()).log(Level.INFO, "Correo enviado con éxito a: {0}", to);
        } catch (AddressException ex) {
            Logger.getLogger(MandarCorreos.class.getName()).log(Level.SEVERE, "Error de dirección de correo", ex);
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(MandarCorreos.class.getName()).log(Level.SEVERE, "Error de proveedor no encontrado", ex);
        } catch (MessagingException ex) {
            Logger.getLogger(MandarCorreos.class.getName()).log(Level.SEVERE, "Error al enviar correo", ex);
        }
    }
}
