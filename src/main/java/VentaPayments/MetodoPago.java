package VentaPayments;

import java.util.List;

import DBObjetos.Producto;

public interface MetodoPago {
    boolean validarMonto(double monto, double total);
    double calcularCambio(double monto, double total);
    boolean enviarTicketPorCorreo(String correo, String pdfPath);
    String generarPDF(List<Producto> productos, double totalVenta, double montoRecibido, double cambio);
}
