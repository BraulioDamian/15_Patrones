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
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ChartGenerator {

    public void initialize(Date fechaInicio, Date fechaFin) throws IOException, SQLException {
        crearGraficas(fechaInicio, fechaFin);
    }

    private void crearGraficas(Date fechaInicio, Date fechaFin) throws IOException, SQLException {
        generarGraficaVentasPorProducto(fechaInicio, fechaFin, "ventas_por_producto.png");
        generarGraficaVentasPorEmpleado(fechaInicio, fechaFin, "ventas_por_empleado.png");
        generarGraficaProductosMenosVendidos(fechaInicio, fechaFin, "productos_menos_vendidos.png");
    }

    private String generarGraficaVentasPorProducto(Date fechaInicio, Date fechaFin, String rutaArchivo) throws IOException, SQLException {
        Map<String, Integer> ventasPorProducto;
        try {
            CONSULTASDAO dao = new CONSULTASDAO(Conexion_DB.getConexion());
            ventasPorProducto = dao.obtenerVentasPorProducto(new java.sql.Date(fechaInicio.getTime()), new java.sql.Date(fechaFin.getTime()));
        } catch (SQLException e) {
            throw new SQLException("Error al obtener las ventas por producto", e);
        }

        CategoryChart chart = new CategoryChartBuilder()
                .width(800)
                .height(600)
                .title("Ventas por Producto")
                .xAxisTitle("Producto")
                .yAxisTitle("Ventas")
                .build();

        List<String> productos = ventasPorProducto.keySet().stream().collect(Collectors.toList());
        List<Integer> ventas = ventasPorProducto.values().stream().collect(Collectors.toList());

        chart.addSeries("Ventas", productos, ventas);

        Path path = Paths.get(rutaArchivo);
        BitmapEncoder.saveBitmap(chart, path.toString(), BitmapEncoder.BitmapFormat.PNG);

        return rutaArchivo;
    }

    private String generarGraficaVentasPorEmpleado(Date fechaInicio, Date fechaFin, String rutaArchivo) throws IOException, SQLException {
        Map<String, Integer> ventasPorEmpleado;
        try {
            CONSULTASDAO dao = new CONSULTASDAO(Conexion_DB.getConexion());
            ventasPorEmpleado = dao.obtenerVentasPorEmpleado(new java.sql.Date(fechaInicio.getTime()), new java.sql.Date(fechaFin.getTime()));
        } catch (SQLException e) {
            throw new SQLException("Error al obtener las ventas por empleado", e);
        }

        CategoryChart chart = new CategoryChartBuilder()
                .width(800)
                .height(600)
                .title("Ventas por Empleado")
                .xAxisTitle("Empleado")
                .yAxisTitle("Ventas")
                .build();

        List<String> empleados = ventasPorEmpleado.keySet().stream().collect(Collectors.toList());
        List<Integer> ventas = ventasPorEmpleado.values().stream().collect(Collectors.toList());

        chart.addSeries("Ventas", empleados, ventas);

        Path path = Paths.get(rutaArchivo);
        BitmapEncoder.saveBitmap(chart, path.toString(), BitmapEncoder.BitmapFormat.PNG);

        return rutaArchivo;
    }

    private String generarGraficaProductosMenosVendidos(Date fechaInicio, Date fechaFin, String rutaArchivo) throws IOException, SQLException {
        Map<String, Integer> productosMenosVendidos;
        try {
            CONSULTASDAO dao = new CONSULTASDAO(Conexion_DB.getConexion());
            productosMenosVendidos = dao.obtenerProductosMenosVendidos(new java.sql.Date(fechaInicio.getTime()), new java.sql.Date(fechaFin.getTime()));
        } catch (SQLException e) {
            throw new SQLException("Error al obtener los productos menos vendidos", e);
        }

        CategoryChart chart = new CategoryChartBuilder()
                .width(800)
                .height(600)
                .title("Productos Menos Vendidos")
                .xAxisTitle("Producto")
                .yAxisTitle("Ventas")
                .build();

        List<String> productos = productosMenosVendidos.keySet().stream().collect(Collectors.toList());
        List<Integer> ventas = productosMenosVendidos.values().stream().collect(Collectors.toList());

        chart.addSeries("Ventas", productos, ventas);

        Path path = Paths.get(rutaArchivo);
        BitmapEncoder.saveBitmap(chart, path.toString(), BitmapEncoder.BitmapFormat.PNG);

        return rutaArchivo;
    }
}
