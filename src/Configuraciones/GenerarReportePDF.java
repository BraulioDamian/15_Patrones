/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Configuraciones;

/**
 *
 * @author braul
 */


import ConexionDB.Conexion_DB;
import Consultas.CONSULTASDAO;
import DBObjetos.Usuario;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class GenerarReportePDF {

    public void generarReporte(Date fechaInicio, Date fechaFin) throws DocumentException, IOException, SQLException {
        // Crear carpeta "reportes" si no existe
        File carpetaReportes = new File("reportes");
        if (!carpetaReportes.exists()) {
            carpetaReportes.mkdirs();
        }

        // Ruta del archivo PDF
        String rutaArchivo = "reportes/reporte_ventas.pdf";

        // Crear documento PDF
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(rutaArchivo));
        document.open();

        // Añadir título principal
        Font fontTitulo = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
        Paragraph titulo = new Paragraph("Reporte de Ventas", fontTitulo);
        titulo.setAlignment(Element.ALIGN_CENTER);
        document.add(titulo);
        document.add(new Paragraph("\n")); // Espacio

        // Añadir las gráficas y sus descripciones
        agregarGraficasYDescripciones(document, fechaInicio, fechaFin);

        // Obtener los datos de los empleados y sus ventas
        CONSULTASDAO dao = new CONSULTASDAO(Conexion_DB.getConexion());
        List<Usuario> usuarios = dao.obtenerTodosLosUsuarios();

        for (Usuario usuario : usuarios) {
            agregarReporteEmpleado(document, dao, usuario, fechaInicio, fechaFin);
        }

        document.close();
    }

    private void agregarGraficasYDescripciones(Document document, Date fechaInicio, Date fechaFin) throws IOException, DocumentException, SQLException {
        ChartGenerator chartGenerator = new ChartGenerator();
        chartGenerator.initialize(fechaInicio, fechaFin);

        // Ruta de las imágenes generadas
        String rutaVentasPorProducto = "ventas_por_producto.png";
        String rutaVentasPorEmpleado = "ventas_por_empleado.png";
        String rutaProductosMenosVendidos = "productos_menos_vendidos.png";

        // Añadir cada sección en una nueva página
        agregarSeccionConGraficaYDescripcion(document, "Ventas por Producto", rutaVentasPorProducto, fechaInicio, fechaFin);
        document.newPage(); // Salto a nueva página

        agregarSeccionConGraficaYDescripcion(document, "Ventas por Empleado", rutaVentasPorEmpleado, fechaInicio, fechaFin);
        document.newPage(); // Salto a nueva página

        agregarSeccionConGraficaYDescripcion(document, "Productos Menos Vendidos", rutaProductosMenosVendidos, fechaInicio, fechaFin);
        document.newPage(); // Salto a nueva página
    }

    private void agregarSeccionConGraficaYDescripcion(Document document, String titulo, String rutaImagen, Date fechaInicio, Date fechaFin) throws IOException, DocumentException, SQLException {
        Font fontTituloSeccion = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
        Font fontContenido = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);

        // Añadir título de la sección
        Paragraph tituloSeccion = new Paragraph(titulo, fontTituloSeccion);
        tituloSeccion.setAlignment(Element.ALIGN_CENTER);
        document.add(tituloSeccion);
        document.add(new Paragraph("\n")); // Espacio

        // Añadir gráfica
        Image imagen = Image.getInstance(rutaImagen);
        imagen.scaleToFit(500, 400);
        imagen.setAlignment(Element.ALIGN_CENTER);
        document.add(imagen);
        document.add(new Paragraph("\n")); // Espacio

        // Añadir descripción
        CONSULTASDAO dao = new CONSULTASDAO(Conexion_DB.getConexion());

        switch (titulo) {
            case "Ventas por Producto":
                Map<String, Integer> ventasPorProducto = dao.obtenerVentasPorProducto(new java.sql.Date(fechaInicio.getTime()), new java.sql.Date(fechaFin.getTime()));
                for (Map.Entry<String, Integer> entry : ventasPorProducto.entrySet()) {
                    document.add(new Paragraph(entry.getKey() + ": " + entry.getValue(), fontContenido));
                }
                break;
            case "Ventas por Empleado":
                Map<String, Integer> ventasPorEmpleado = dao.obtenerVentasPorEmpleado(new java.sql.Date(fechaInicio.getTime()), new java.sql.Date(fechaFin.getTime()));
                for (Map.Entry<String, Integer> entry : ventasPorEmpleado.entrySet()) {
                    document.add(new Paragraph(entry.getKey() + ": " + entry.getValue(), fontContenido));
                }
                break;
            case "Productos Menos Vendidos":
                Map<String, Integer> productosMenosVendidos = dao.obtenerProductosMenosVendidos(new java.sql.Date(fechaInicio.getTime()), new java.sql.Date(fechaFin.getTime()));
                for (Map.Entry<String, Integer> entry : productosMenosVendidos.entrySet()) {
                    document.add(new Paragraph(entry.getKey() + ": " + entry.getValue(), fontContenido));
                }
                break;
        }

        document.add(new Paragraph("\n")); // Espacio
    }

    private void agregarReporteEmpleado(Document document, CONSULTASDAO dao, Usuario usuario, Date fechaInicio, Date fechaFin) throws DocumentException, SQLException {
        Font fontSeccion = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
        Font fontContenido = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);

        // Añadir fecha
        document.add(new Paragraph("Fecha: " + new java.util.Date(), fontContenido));
        document.add(new Paragraph("\n")); // Espacio

        // Añadir nombre y cargo
        document.add(new Paragraph("Nombre de Empleado: " + usuario.getNombreUsuario(), fontSeccion));
        document.add(new Paragraph("Cargo: " + usuario.getRol(), fontContenido));
        document.add(new Paragraph("\n")); // Espacio

        // Obtener y añadir ventas
        document.add(new Paragraph("Ventas", fontSeccion));
        agregarVentasEmpleado(document, dao, usuario, fechaInicio, fechaFin, fontContenido);

        // Añadir total de productos vendidos
        document.add(new Paragraph("Total de productos vendidos", fontSeccion));
        agregarProductosVendidosEmpleado(document, dao, usuario, fechaInicio, fechaFin, fontContenido);

        // Añadir desempeño
        document.add(new Paragraph("Desempeño", fontSeccion));
        agregarDesempeñoEmpleado(document, dao, usuario, fechaInicio, fechaFin, fontContenido);
    }

    private void agregarVentasEmpleado(Document document, CONSULTASDAO dao, Usuario usuario, Date fechaInicio, Date fechaFin, Font fontContenido) throws SQLException, DocumentException {
        Map<String, Integer> ventasDiarias = dao.obtenerVentasDiariasPorEmpleado(new java.sql.Date(fechaInicio.getTime()));
        Map<String, Integer> ventasSemanales = dao.obtenerVentasSemanalesPorEmpleado(new java.sql.Date(fechaInicio.getTime()), new java.sql.Date(fechaFin.getTime()));
        Map<String, Integer> ventasMensuales = dao.obtenerVentasMensualesPorEmpleado(new java.sql.Date(fechaInicio.getTime()), new java.sql.Date(fechaFin.getTime()));

        document.add(new Paragraph("\tDiarias: " + ventasDiarias.getOrDefault(usuario.getNombreUsuario(), 0), fontContenido));
        document.add(new Paragraph("\tSemanales: " + ventasSemanales.getOrDefault(usuario.getNombreUsuario(), 0), fontContenido));
        document.add(new Paragraph("\tMensuales: " + ventasMensuales.getOrDefault(usuario.getNombreUsuario(), 0), fontContenido));
        document.add(new Paragraph("\tTotal de ventas: " + (ventasDiarias.getOrDefault(usuario.getNombreUsuario(), 0)
                + ventasSemanales.getOrDefault(usuario.getNombreUsuario(), 0)
                + ventasMensuales.getOrDefault(usuario.getNombreUsuario(), 0)), fontContenido));
        document.add(new Paragraph("\n")); // Espacio
    }

    private void agregarProductosVendidosEmpleado(Document document, CONSULTASDAO dao, Usuario usuario, Date fechaInicio, Date fechaFin, Font fontContenido) throws SQLException, DocumentException {
        // Similar a agregarVentasEmpleado, se puede crear un método en CONSULTASDAO para obtener productos vendidos
        Map<String, Integer> productosVendidosDiarios = dao.obtenerProductosVendidosDiariosPorEmpleado(new java.sql.Date(fechaInicio.getTime()));
        Map<String, Integer> productosVendidosSemanales = dao.obtenerProductosVendidosSemanalesPorEmpleado(new java.sql.Date(fechaInicio.getTime()), new java.sql.Date(fechaFin.getTime()));
        Map<String, Integer> productosVendidosMensuales = dao.obtenerProductosVendidosMensualesPorEmpleado(new java.sql.Date(fechaInicio.getTime()), new java.sql.Date(fechaFin.getTime()));

        document.add(new Paragraph("\tDiarios: " + productosVendidosDiarios.getOrDefault(usuario.getNombreUsuario(), 0), fontContenido));
        document.add(new Paragraph("\tSemanales: " + productosVendidosSemanales.getOrDefault(usuario.getNombreUsuario(), 0), fontContenido));
        document.add(new Paragraph("\tMensuales: " + productosVendidosMensuales.getOrDefault(usuario.getNombreUsuario(), 0), fontContenido));
        document.add(new Paragraph("\tTotal de productos vendidos: " + (productosVendidosDiarios.getOrDefault(usuario.getNombreUsuario(), 0)
                + productosVendidosSemanales.getOrDefault(usuario.getNombreUsuario(), 0)
                + productosVendidosMensuales.getOrDefault(usuario.getNombreUsuario(), 0)), fontContenido));
        document.add(new Paragraph("\n")); // Espacio
    }

    private void agregarDesempeñoEmpleado(Document document, CONSULTASDAO dao, Usuario usuario, Date fechaInicio, Date fechaFin, Font fontContenido) throws SQLException, DocumentException {
        // Implementar la lógica para determinar el desempeño del empleado
        Map<String, Integer> ventasDiarias = dao.obtenerVentasDiariasPorEmpleado(new java.sql.Date(fechaInicio.getTime()));

        int totalVentasDiarias = ventasDiarias.getOrDefault(usuario.getNombreUsuario(), 0);
        if (totalVentasDiarias > 1000) {
            document.add(new Paragraph("\tBuen desempeño: Sí", fontContenido));
        } else {
            document.add(new Paragraph("\tBuen desempeño: No", fontContenido));
        }
        document.add(new Paragraph("\n")); // Espacio
    }
}