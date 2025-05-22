package Venta.notificaciones;

public class NotificacionChain {
    private NotificacionHandler chain;

    public NotificacionChain() {
        buildChain();
    }

    private void buildChain() {
        this.chain = new CorreoHandler();
        chain.setNext(new WhatsAppHandler());
    }

    public void enviarNotificacion(String destinatario, String asunto, String contenido, String archivo) {
        chain.handleRequest(destinatario, asunto, contenido, archivo);
    }
}