package Venta.notificaciones;

public abstract class NotificacionHandler {
    private NotificacionHandler next;

    public NotificacionHandler setNext(NotificacionHandler next) {
        this.next = next;
        return next;
    }

    public void handleRequest(String destinatario, String asunto, String contenido, String archivo) {
        if (next != null) {
            next.handleRequest(destinatario, asunto, contenido, archivo);
        }
    }
}