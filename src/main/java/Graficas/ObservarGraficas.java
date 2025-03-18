/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Graficas;

/**
 *
 * @author braul
 */


import ConexionDB.Conexion_DB;
import Configuraciones.Configuraciones;
import Consultas.CONSULTASDAO;
import DBObjetos.Usuario;
import INVENTARIO.AnimacionPanel;
import INVENTARIO.HoverEffect;
import INVENTARIO.Principal2_0;
import PanelUsuarios.UsuariosPanel;
import Principal.MenuPrincipal;
import Venta.Venta;
import com.toedter.calendar.JDateChooser;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import javax.swing.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import java.sql.SQLException;
import login.SesionManager;
import org.jfree.chart.labels.StandardPieToolTipGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class ObservarGraficas extends JFrame {

    /**
     * Creates new form ObservarGraficas
     */
    private AvisosFrame avisosFrame;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private boolean isCalendarOpen = false; // Controla si el calendario está abierto



    
    public ObservarGraficas() {
        initComponents();
        setLocationRelativeTo(null); // Centramos la ventana en la pantalla


    }
    
    public ObservarGraficas(AvisosFrame avisosFrame) {
        this.avisosFrame = avisosFrame;
        initComponents();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); // Asegúrate de que solo se cierre esta ventana

        // Añadir WindowListener para capturar el evento de cierre
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (avisosFrame != null) {
                    avisosFrame.setVisible(true);
                }
                dispose(); // Cerrar la ventana actual
            }
        });
    }

       
        public void initialize(Date fechaInicio, Date fechaFin) {
        crearGraficas(fechaInicio, fechaFin);
    }
       
           private void crearGraficas(Date fechaInicio, Date fechaFin) {
        try {
            CONSULTASDAO dao = new CONSULTASDAO(Conexion_DB.getConexion());

            Map<String, Integer> ventas = dao.obtenerVentasPorProducto(new java.sql.Date(fechaInicio.getTime()), new java.sql.Date(fechaFin.getTime()));
            JPanel panelGraficoVentas = crearGraficoVentas(ventas);
            PanelMostrarGrafic.removeAll();
            PanelMostrarGrafic.setLayout(new BorderLayout());
            PanelMostrarGrafic.add(panelGraficoVentas, BorderLayout.CENTER);
            PanelMostrarGrafic.revalidate();
            PanelMostrarGrafic.repaint();

            Map<String, Integer> ventasEmpleado = dao.obtenerVentasPorEmpleado(new java.sql.Date(fechaInicio.getTime()), new java.sql.Date(fechaFin.getTime()));
            JPanel panelGraficoEmpleados = crearGraficoVentasEmpleado(ventasEmpleado);
            PanelMostrarUsuarios.removeAll();
            PanelMostrarUsuarios.setLayout(new BorderLayout());
            PanelMostrarUsuarios.add(panelGraficoEmpleados, BorderLayout.CENTER);
            PanelMostrarUsuarios.revalidate();
            PanelMostrarUsuarios.repaint();

            Map<String, Integer> menosVendidos = dao.obtenerProductosMenosVendidos(new java.sql.Date(fechaInicio.getTime()), new java.sql.Date(fechaFin.getTime()));
            JPanel panelGraficoMenosVendidos = crearGraficoProductosMenosVendidos(menosVendidos);
            PanelMostrarMenosVendidos.removeAll();
            PanelMostrarMenosVendidos.setLayout(new BorderLayout());
            PanelMostrarMenosVendidos.add(panelGraficoMenosVendidos, BorderLayout.CENTER);
            PanelMostrarMenosVendidos.revalidate();
            PanelMostrarMenosVendidos.repaint();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al conectar con la base de datos", "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
        }
    }
       
 /*
    private boolean fechaEsAdecuada(Date fechaInicio, Date fechaFinal) {
        return !fechaFinal.before(fechaInicio);
    }

    private void mostrarCalendario(JTextField textField, boolean esFechaInicio) {
        isCalendarOpen = true;
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");
        dateChooser.setMaxSelectableDate(new Date());

        try {
            Date fechaActual = textField.getText().isEmpty() ? new Date() : dateFormat.parse(textField.getText());
            dateChooser.setDate(fechaActual);

            int result = JOptionPane.showConfirmDialog(null, dateChooser, "Seleccione la fecha", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                Date nuevaFecha = dateChooser.getDate();
                textField.setText(dateFormat.format(nuevaFecha));
                if (!esFechaInicio) {
                    Date fechaInicio = dateFormat.parse(txtFechaInicio.getText());
                    if (!fechaEsAdecuada(fechaInicio, nuevaFecha)) {
                        JOptionPane.showMessageDialog(this, "La fecha final no puede ser anterior a la fecha de inicio.", "Error de Fecha", JOptionPane.ERROR_MESSAGE);
                        textField.setText(dateFormat.format(fechaActual)); // Revertir a la fecha original
                    }
                }
            }
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Error al parsear la fecha.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }
        isCalendarOpen = false;
    }*/




private JPanel crearGraficoVentas(Map<String, Integer> ventas) {
    DefaultPieDataset dataset = new DefaultPieDataset();
    for (Map.Entry<String, Integer> entry : ventas.entrySet()) {
        dataset.setValue(entry.getKey(), entry.getValue());
    }
    
    JFreeChart chart = ChartFactory.createPieChart("Ventas por Producto", dataset, true, true, false);
    PiePlot plot = (PiePlot) chart.getPlot();
    plot.setSimpleLabels(true);
    plot.setToolTipGenerator(new StandardPieToolTipGenerator());

    ChartPanel chartPanel = new ChartPanel(chart);
    chartPanel.setMouseWheelEnabled(true);
    chartPanel.setDomainZoomable(true);
    chartPanel.setRangeZoomable(true);
    
    // Aquí ajustas el tamaño del ChartPanel al tamaño del JPanel destino
    chartPanel.setPreferredSize(new Dimension(PanelMostrarGrafic.getWidth(), PanelMostrarGrafic.getHeight()));

    return chartPanel;
}




    private JPanel crearGraficoVentasEmpleado(Map<String, Integer> ventasEmpleado) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (Map.Entry<String, Integer> entry : ventasEmpleado.entrySet()) {
            dataset.addValue(entry.getValue(), "Ventas", entry.getKey());
        }

        JFreeChart barChart = ChartFactory.createBarChart(
            "Ventas por Empleado",
            "Empleado",
            "Ventas ($)",
            dataset,
            PlotOrientation.VERTICAL,
            true, true, false);

        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setMouseWheelEnabled(true);
        chartPanel.setDomainZoomable(true);
        chartPanel.setRangeZoomable(true);

          // Aquí ajustas el tamaño del ChartPanel al tamaño del JPanel destino
        chartPanel.setPreferredSize(new Dimension(PanelMostrarUsuarios.getWidth(), PanelMostrarGrafic.getHeight()));

        return chartPanel;
    }

    private JPanel crearGraficoProductosMenosVendidos(Map<String, Integer> productosMenosVendidos) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (Map.Entry<String, Integer> entry : productosMenosVendidos.entrySet()) {
            dataset.addValue(entry.getValue(), "Ventas", entry.getKey());
        }

        JFreeChart barChart = ChartFactory.createBarChart(
            "Productos Menos Vendidos",
            "Producto",
            "Unidades Vendidas",
            dataset,
            PlotOrientation.HORIZONTAL,
            true, true, false);

        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setMouseWheelEnabled(true);
        chartPanel.setDomainZoomable(true);
        chartPanel.setRangeZoomable(true);

        // Aquí ajustas el tamaño del ChartPanel al tamaño del JPanel destino
        chartPanel.setPreferredSize(new Dimension(PanelMostrarMenosVendidos.getWidth(), PanelMostrarGrafic.getHeight()));

        return chartPanel;
    }

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Panel2 = new JPanel();
        PanelMostrarGrafic = new JPanel();
        PanelMostrarUsuarios = new JPanel();
        PanelMostrarMenosVendidos = new JPanel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Panel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Panel2MouseClicked(evt);
            }
        });

        PanelMostrarGrafic.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));

        GroupLayout PanelMostrarGraficLayout = new GroupLayout(PanelMostrarGrafic);
        PanelMostrarGrafic.setLayout(PanelMostrarGraficLayout);
        PanelMostrarGraficLayout.setHorizontalGroup(
            PanelMostrarGraficLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 382, Short.MAX_VALUE)
        );
        PanelMostrarGraficLayout.setVerticalGroup(
            PanelMostrarGraficLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 427, Short.MAX_VALUE)
        );

        PanelMostrarUsuarios.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));

        GroupLayout PanelMostrarUsuariosLayout = new GroupLayout(PanelMostrarUsuarios);
        PanelMostrarUsuarios.setLayout(PanelMostrarUsuariosLayout);
        PanelMostrarUsuariosLayout.setHorizontalGroup(
            PanelMostrarUsuariosLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 336, Short.MAX_VALUE)
        );
        PanelMostrarUsuariosLayout.setVerticalGroup(
            PanelMostrarUsuariosLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 422, Short.MAX_VALUE)
        );

        PanelMostrarMenosVendidos.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));

        GroupLayout PanelMostrarMenosVendidosLayout = new GroupLayout(PanelMostrarMenosVendidos);
        PanelMostrarMenosVendidos.setLayout(PanelMostrarMenosVendidosLayout);
        PanelMostrarMenosVendidosLayout.setHorizontalGroup(
            PanelMostrarMenosVendidosLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 380, Short.MAX_VALUE)
        );
        PanelMostrarMenosVendidosLayout.setVerticalGroup(
            PanelMostrarMenosVendidosLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 424, Short.MAX_VALUE)
        );

        GroupLayout Panel2Layout = new GroupLayout(Panel2);
        Panel2.setLayout(Panel2Layout);
        Panel2Layout.setHorizontalGroup(
            Panel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, Panel2Layout.createSequentialGroup()
                .addContainerGap(498, Short.MAX_VALUE)
                .addComponent(PanelMostrarUsuarios, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(PanelMostrarMenosVendidos, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
            .addGroup(Panel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(GroupLayout.Alignment.TRAILING, Panel2Layout.createSequentialGroup()
                    .addContainerGap(65, Short.MAX_VALUE)
                    .addComponent(PanelMostrarGrafic, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(801, Short.MAX_VALUE)))
        );
        Panel2Layout.setVerticalGroup(
            Panel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(Panel2Layout.createSequentialGroup()
                .addGap(101, 101, 101)
                .addGroup(Panel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(PanelMostrarUsuarios, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(PanelMostrarMenosVendidos, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(83, Short.MAX_VALUE))
            .addGroup(Panel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(GroupLayout.Alignment.TRAILING, Panel2Layout.createSequentialGroup()
                    .addContainerGap(96, Short.MAX_VALUE)
                    .addComponent(PanelMostrarGrafic, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(85, Short.MAX_VALUE)))
        );

        getContentPane().add(Panel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1250, 610));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Panel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Panel2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_Panel2MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ObservarGraficas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ObservarGraficas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ObservarGraficas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ObservarGraficas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ObservarGraficas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JPanel Panel2;
    private JPanel PanelMostrarGrafic;
    private JPanel PanelMostrarMenosVendidos;
    private JPanel PanelMostrarUsuarios;
    // End of variables declaration//GEN-END:variables
}