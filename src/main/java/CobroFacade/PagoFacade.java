package CobroFacade;

import DBObjetos.Producto;
import DBObjetos.Usuario;
import Venta.EnvioTicket;
import login.SesionManager;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JOptionPane;
import java.awt.Desktop;

public class PagoFacade {
    public boolean validarMetodoDePago(boolean efectivoSeleccionado, boolean tarjetaSeleccionado) {
        if (!efectivoSeleccionado && !tarjetaSeleccionado) {
            System.out.println("Hola soy funcion validar Metodo de Pago dentro del if");
            JOptionPane.showMessageDialog(null, "Por favor seleccione un método de pago");
            return false;
        }
        return true;
    }

    public boolean validarMonto(double pago, double total) {
        if (pago < total) {
            System.out.println("Hola soy funcion validar Monto dentro del if");
            JOptionPane.showMessageDialog(null, "El monto ingresado es menor al total a pagar");
            return false;
        }
        return true;
    }

    public double calcularCambio(double pago, double total) {
        System.out.println("Hola soy funcion calcular Cambio");
        double cambio = pago - total;
        return cambio;
    }

    public String generarPDF(List<Producto> productosSeleccionados, double total, double pago, double cambio) {
        Document document = new Document();
        String fileName = "Ticket_" + System.currentTimeMillis() + ".pdf"; // Nombre del archivo con marca de tiempo
        String filePath = "./tickets/" + fileName; // Guardar en un directorio específico
        LocalDateTime fechaActual = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String fechaHoraFormateada = fechaActual.format(formato);

        // Asegúrate de que el directorio tickets existe o créalo
        new File("./tickets").mkdirs();

        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            Usuario usuario = SesionManager.getInstance().getUsuarioLogueado();

            Font bold = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            Font normal = FontFactory.getFont(FontFactory.HELVETICA, 12);
            Font gigante = FontFactory.getFont(FontFactory.HELVETICA, 16);

            Paragraph Titulo = new Paragraph("ABARROTES DON LUIS", gigante);
            Titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(Titulo);

            document.add(new Paragraph(""));
            document.add(new Paragraph(""));

            document.add(new Paragraph("Ticket de compra", normal));
            document.add(new Paragraph("Cajero: " + usuario.getNombreCompleto()));

            document.add(new Paragraph(""));
            document.add(new Paragraph(""));

            Paragraph fechaParrafo = new Paragraph("Fecha y Hora: " + fechaHoraFormateada, normal);
            fechaParrafo.setAlignment(Element.ALIGN_RIGHT);
            document.add(fechaParrafo);
            document.add(new Paragraph(" "));
            document.add(new Paragraph(""));

            document.add(new Paragraph("RFC: VECJ880326", normal));
            document.add(new Paragraph("Régimen fiscal: 601-Ley General de Personas Morales", normal));
            document.add(new Paragraph(
                    "Emitido en: Heroica Escuela Naval Militar 917, Reforma Centro, 68050 Oaxaca de Juárez, Oax",
                    normal));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(new float[] { 1, 2, 1, 1, 1 });
            table.setWidthPercentage(100);
            String[] headers = { "Código", "Producto", "Unidades", "Precio Uni.", "Importe" };
            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Paragraph(header, bold));
                cell.setBorder(PdfPCell.NO_BORDER);
                table.addCell(cell);
            }

            for (Producto producto : productosSeleccionados) {
                PdfPCell cell = new PdfPCell(new Paragraph(String.valueOf(producto.getProductoID()), normal));
                cell.setBorder(PdfPCell.NO_BORDER);
                table.addCell(cell);
                cell = new PdfPCell(new Paragraph(producto.getNombre(), normal));
                cell.setBorder(PdfPCell.NO_BORDER);
                table.addCell(cell);
                cell = new PdfPCell(new Paragraph(String.valueOf(producto.getCantidad()), normal));
                cell.setBorder(PdfPCell.NO_BORDER);
                table.addCell(cell);
                cell = new PdfPCell(new Paragraph(String.format("$%.2f", producto.getPrecio()), normal));
                cell.setBorder(PdfPCell.NO_BORDER);
                table.addCell(cell);
                cell = new PdfPCell(
                        new Paragraph(String.format("$%.2f", producto.getPrecio() * producto.getCantidad()), normal));
                cell.setBorder(PdfPCell.NO_BORDER);
                table.addCell(cell);
            }

            document.add(table);

            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));

            Paragraph totalParrafo = new Paragraph(String.format("Total: $%.2f", total), bold);
            totalParrafo.setAlignment(Element.ALIGN_RIGHT);
            document.add(totalParrafo);

            Paragraph pagoParrafo = new Paragraph(String.format("Pago en efectivo: $%.2f", pago), normal);
            pagoParrafo.setAlignment(Element.ALIGN_RIGHT);
            document.add(pagoParrafo);

            Paragraph cambioParrafo = new Paragraph(String.format("Cambio: $%.2f", cambio), normal);
            cambioParrafo.setAlignment(Element.ALIGN_RIGHT);
            document.add(cambioParrafo);

            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));

            Paragraph graciasParrafo = new Paragraph("¡GRACIAS POR SU COMPRA!", bold);
            graciasParrafo.setAlignment(Element.ALIGN_CENTER);
            document.add(graciasParrafo);

            document.close();

            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(new File(filePath)); // Opcional: abrir el archivo automáticamente
            }

            return filePath;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al generar el ticket: " + e.getMessage());
            return null;
        }
    }

    public boolean enviarTicketPorCorreo(String correo, String rutaPDF) {
        if (correo.isEmpty()) {
            int opcion = JOptionPane.showConfirmDialog(null,
                    "No ha ingresado un correo electronico. ¿Desea continuar sin enviar el ticket por correo?",
                    "Correo no ingresado", JOptionPane.YES_NO_OPTION);
                    System.out.println(opcion);
            if (opcion == 0) {
                return true;
            }
        } else {
            EnvioTicket.enviarConArchivo(correo, rutaPDF);
            JOptionPane.showMessageDialog(null, "Ticket enviado correctamente");
        }
        return false;
    }
}
