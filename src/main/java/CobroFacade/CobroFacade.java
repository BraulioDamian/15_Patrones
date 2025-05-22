package CobroFacade;

import Venta.Cobro;
import VentaMemento.CobroMemento;
import VentaMemento.CobroCaretaker;
import VentaMemento.CobroOriginator;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.util.List;
import java.io.FileOutputStream;
import DBObjetos.Producto;



public class CobroFacade {
    private Cobro cobro;
    private CobroOriginator originator;
    private CobroCaretaker caretaker;

    public CobroFacade(){
        this.cobro = new Cobro();
        this.originator = new CobroOriginator(cobro);
        this.caretaker= new CobroCaretaker();
    }



    public void iniciarCobro(){
        cobro.iniciar();
    }


    public void agregarMonto (double monto){
        cobro.agregarMonto(monto);
        guardarEstado();
    }

    public void deshacer(){
        CobroMemento memento = caretaker.deshacer();
        if(memento!=null){
            originator.restore(memento);
        }
    }

    public void rehacer(){
        CobroMemento memento = caretaker.rehacer();
        if(memento!=null){
            originator.restore(memento);
        }
    }

    private void guardarEstado() {
        CobroMemento memento = originator.save();
        caretaker.addMemento(memento);
    }

    public String generarPDF(List<Producto> productos, double total, double pago, double cambio) throws DocumentException, java.io.IOException {
        Document document = new Document();
        String ruta = "ticket.pdf";
        PdfWriter.getInstance(document, new FileOutputStream(ruta));
        document.open();
        document.add(new Paragraph("Ticket de compra"));
        document.add(new Paragraph("Productos:"));
        for (Producto producto : productos) {
            document.add(new Paragraph(producto.getNombre() + " - " + producto.getPrecio()));
        }
        document.add(new Paragraph("Total: " + total));
        document.add(new Paragraph("Pago: " + pago));
        document.add(new Paragraph("Cambio: " + cambio));
        document.close();
        return ruta;
    }

}
