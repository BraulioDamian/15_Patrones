package VentaPayments;

import java.util.List;

import javax.swing.JOptionPane;

import DBObjetos.Producto;
import Venta.EnvioTicket;

public class Efectivo implements MetodoPago {
    @Override
    public boolean validarMonto(double monto, double total) {
        return monto >= total;
    }

    @Override
    public double calcularCambio(double monto, double total) {
        return monto - total;
    }

    @Override
    public boolean enviarTicketPorCorreo(String correo, String pdfPath) {
        if (correo.isEmpty()) {
            int opcion = JOptionPane.showConfirmDialog(null,
                    "No ha ingresado un correo electronico. ¿Desea continuar sin enviar el ticket por correo?",
                    "Correo no ingresado", JOptionPane.YES_NO_OPTION);
            System.out.println(opcion);
            if (opcion == 0) {
                return true;
            }
        } else {
            EnvioTicket.enviarConArchivo(correo, pdfPath);
            JOptionPane.showMessageDialog(null, "Ticket enviado correctamente");
        }
        return false;

    }

    @Override
    public String generarPDF(List<Producto> productos, double totalVenta, double montoRecibido, double cambio) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generarPDF'");
    }

}
