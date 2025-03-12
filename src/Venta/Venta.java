package Venta;


import ConexionDB.Conexion_DB;
import Configuraciones.Configuraciones;
import Configuraciones.Estilos;
import Consultas.CONSULTASDAO;
import DBObjetos.Producto;
import DBObjetos.Usuario;
import Graficas.AvisosFrame;
import INVENTARIO.Principal2_0;
import INVENTARIO.AnimacionPanel;
import INVENTARIO.HoverEffect;
import Principal.MenuPrincipal;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import login.SesionManager;
import UniVentanas.*;
import login.*;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */


/**
 *
 * @author braul
 */
public class Venta extends javax.swing.JFrame {

    /**
     * Creates new form Venta
     */

    private AnimacionPanel animador; // Añade esta línea

    private List<Producto> listaProductosConArea;
    private List<Producto> listaFiltrada = new ArrayList<>(); // Lista que refleja los productos filtrados
    
    private double sin;
    private double con;
    private double ivaa;
        
    private Principal2_0 ventanaPrincipal;    
    private static Venta instance;

    private Usuario usuarioLogueado;
    private boolean menuDesplegado = false;

    
    private Venta() {
        initComponents();
        setLocationRelativeTo(null); // Centramos la ventana en la pantalla
       
        initProductosConArea();

        animador = new AnimacionPanel(); // Inicializa el animador
        Estilos.addPlaceholderStyle(Busqueda);        
      
        // Hacer que jPanel6 no sea opaco
        jPanel6.setOpaque(false);
        // Establecer un color de fondo semitransparente (e.g., blanco semitransparente)
        Color semiTransparentColor = new Color(255, 255, 255, 123); // Cambia 123 al valor alpha deseado
        jPanel6.setBackground(semiTransparentColor);
        // Si es necesario, puedes requerir repintar el panel para asegurar que el cambio es visible
        jPanel6.repaint();
        jPanel6.setVisible(false); // Asegúrate de que el JPanel inicialmente esté oculto

        

        Busqueda.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String textoBuscado = Busqueda.getText().trim();
                if (textoBuscado.isEmpty()) {
                    jPanel6.setVisible(false);
                } else {
                    filtrarTablaPorTexto(textoBuscado);
                    ajustarAlturaComponentes();
                    jPanel6.setVisible(true);
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if ((e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_UP) && TablaBusqueda.isVisible()) {
                    TablaBusqueda.requestFocus();
                    cambiarSeleccionEnTabla(e.getKeyCode());
                    e.consume();
                }
            }
        });

        TablaBusqueda.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_DOWN:
                    case KeyEvent.VK_UP:
                        cambiarSeleccionEnTabla(e.getKeyCode());
                        e.consume();
                        break;
                    case KeyEvent.VK_ENTER:
                        if (TablaBusqueda.getSelectedRow() != -1) {
                            agregarProductoACobroYCerrarTabla();
                            e.consume();
                        }
                        break;
                }
            }
        });



        
            // Configura el listener para la tabla TablaCobro
    TablaCobro.getModel().addTableModelListener(new TableModelListener() {
        @Override
        public void tableChanged(TableModelEvent e) {
            // Se asegura de reaccionar solo a cambios relevantes en los datos
            if (e.getType() == TableModelEvent.UPDATE) {
                CalcularTotales();
            }
        }
    });

    TablaCobro.removeColumn(TablaCobro.getColumnModel().getColumn(0));    // Ocultar la columna PRODUCTOID visualmente, pero sigue estando en el modelo
   
    inicializarTablaCobro();
    
    
    cargarProductosConAjusteDePrecio();
    
    agregarEfectoHover();
    
    }
    
    
    private void agregarEfectoHover() {
        HoverEffect.applyHoverEffect(Menu);
        HoverEffect.applyHoverEffect(Inicio);
        HoverEffect.applyHoverEffect(Usuarios);
        HoverEffect.applyHoverEffect(Configuracion);
        HoverEffect.applyHoverEffect(Ventas);
        HoverEffect.applyHoverEffect(Analisis);
        HoverEffect.applyHoverEffect(Inventario);
    }
    
    
        private Venta(Usuario usuario) {
        this.usuarioLogueado = usuario;
        initComponents();
        setLocationRelativeTo(null); // Centramos la ventana en la pantalla

        initProductosConArea();

        animador = new AnimacionPanel(); // Inicializa el animador
        Estilos.addPlaceholderStyle(Busqueda);        
        
        
        
        // Hacer que jPanel6 no sea opaco
        jPanel6.setOpaque(false);
        // Establecer un color de fondo semitransparente (e.g., blanco semitransparente)
        Color semiTransparentColor = new Color(255, 255, 255, 123); // Cambia 123 al valor alpha deseado
        jPanel6.setBackground(semiTransparentColor);
        // Si es necesario, puedes requerir repintar el panel para asegurar que el cambio es visible
        jPanel6.repaint();
        jPanel6.setVisible(false); // Asegúrate de que el JPanel inicialmente esté oculto

        

        Busqueda.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String textoBuscado = Busqueda.getText().trim();
                if (textoBuscado.isEmpty()) {
                    jPanel6.setVisible(false);
                } else {
                    filtrarTablaPorTexto(textoBuscado);
                    ajustarAlturaComponentes();
                    jPanel6.setVisible(true);
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if ((e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_UP) && TablaBusqueda.isVisible()) {
                    TablaBusqueda.requestFocus();
                    cambiarSeleccionEnTabla(e.getKeyCode());
                    e.consume();
                }
            }
        });

        TablaBusqueda.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_DOWN:
                    case KeyEvent.VK_UP:
                        cambiarSeleccionEnTabla(e.getKeyCode());
                        e.consume();
                        break;
                    case KeyEvent.VK_ENTER:
                        if (TablaBusqueda.getSelectedRow() != -1) {
                            agregarProductoACobroYCerrarTabla();
                            e.consume();
                        }
                        break;
                }
            }
        });



        
            // Configura el listener para la tabla TablaCobro
    TablaCobro.getModel().addTableModelListener(new TableModelListener() {
        @Override
        public void tableChanged(TableModelEvent e) {
            // Se asegura de reaccionar solo a cambios relevantes en los datos
            if (e.getType() == TableModelEvent.UPDATE) {
                CalcularTotales();
            }
        }
    });

    TablaCobro.removeColumn(TablaCobro.getColumnModel().getColumn(0));    // Ocultar la columna PRODUCTOID visualmente, pero sigue estando en el modelo
   
    inicializarTablaCobro();
    
    
    cargarProductosConAjusteDePrecio();
    
    configurarVisibilidadComponentes();
    
    agregarEfectoHover() ;
    
    
    }
        
        
        

        
    public static Venta getInstance() {
        if (instance == null) {
            Usuario usuario = SesionManager.getInstance().getUsuarioLogueado();
            instance = new Venta(usuario);
        }
        return instance;
    }

    
     public void initialize(Usuario usuario) {
        this.usuarioLogueado = usuario;
        configurarVisibilidadComponentes();
    }  
   
  
     
         private void configurarVisibilidadComponentes() {
        if (usuarioLogueado.getRol() == Usuario.Rol.EMPLEADO) {
            Usuarios.setVisible(false);
            Configuracion.setVisible(false);
            Analisis.setVisible(false);
        } else if (usuarioLogueado.getRol() == Usuario.Rol.ADMINISTRADOR) {
            Usuarios.setVisible(true);
            Configuracion.setVisible(true);
        }
    }
   

    private void cargarProductosConAjusteDePrecio() {
        try {
            CONSULTASDAO dao = new CONSULTASDAO(Conexion_DB.getConexion());
            Map<Integer, Double> porcentajes = dao.obtenerPorcentajesGanancia();
            listaProductosConArea = dao.obtenerProductosConNombreArea();
            ajustarPrecios(listaProductosConArea, porcentajes);
            actualizarTablaProductos();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar productos: " + e.getMessage(), "Error de Conexión", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void ajustarPrecios(List<Producto> productos, Map<Integer, Double> porcentajes) {
        for (Producto producto : productos) {
            double precioOriginal = producto.getPrecio();
            double porcentaje = porcentajes.getOrDefault(producto.getAreaID(), 0.0);
            double precioAjustado = precioOriginal + (precioOriginal * porcentaje / 100);
            producto.setPrecio(precioAjustado);
        }
    }

    
private void actualizarTablaProductos() {
    DefaultTableModel model = (DefaultTableModel) TablaBusqueda.getModel();
    model.setRowCount(0);
    for (Producto producto : listaProductosConArea) {
        model.addRow(new Object[]{
            producto.getProductoID(),
            producto.getNombre(),
            producto.getMarca(),
            String.format("%.2f", producto.getPrecio()),  // Formato a dos decimales
            producto.getUnidadesDisponibles()
        });
    }
}



    
    
     private void inicializarTablaCobro() {
        DefaultTableModel modelo = (DefaultTableModel) TablaCobro.getModel();
        modelo.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE && e.getColumn() == 2) {  // Asumiendo que la columna 2 es la de unidades
                    int fila = e.getFirstRow();
                    actualizarImporte(fila);
                    CalcularTotales();
                }
            }
        });

    }

private void actualizarImporte(int fila) {
    DefaultTableModel modelo = (DefaultTableModel) TablaCobro.getModel();
    try {
        int unidades = Integer.parseInt(modelo.getValueAt(fila, 2).toString()); // Columna 2 para unidades
        double precioUnitario = Double.parseDouble(modelo.getValueAt(fila, 4).toString()); // Columna 4 para precio unitario, siempre mantiene el valor original con IVA

        double importe = unidades * precioUnitario;  // Calcula el importe basado en las unidades

        // Almacenar el importe directamente sin ajustar por IVA aquí
        BigDecimal bd = new BigDecimal(importe);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        importe = bd.doubleValue();

        modelo.setValueAt(importe, fila, 5);  // Actualiza la columna de importe con el valor Double redondeado

        // Recalcula y actualiza los totales
        CalcularTotales();
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "Error en formato numérico: " + ex.getMessage(), "Error de Formato", JOptionPane.ERROR_MESSAGE);
    }
}

private void CalcularTotales() {
    DefaultTableModel modelo = (DefaultTableModel) TablaCobro.getModel();
    double subtotal = 0.0, totalIVA = 0.0, total = 0.0;
    for (int i = 0; i < modelo.getRowCount(); i++) {
        double importe = (Double) modelo.getValueAt(i, 5); // Columna 5 tiene el importe
        double precioSinIVA = importe / 1.16; // Calcula el precio sin IVA del importe
        subtotal += precioSinIVA;
        totalIVA += precioSinIVA * 0.16; // Calcula el IVA basado en el precio sin IVA
    }
    total = subtotal + totalIVA;

    lblSubtotal.setText(String.format("%.2f", subtotal));
    lblIva.setText(String.format("%.2f", totalIVA));
    lblTotal.setText(String.format("%.2f", total));
}



    
    private void cambiarSeleccionEnTabla(int keyCode) {
        int rowCount = TablaBusqueda.getRowCount();
        int selectedRow = TablaBusqueda.getSelectedRow();

        if (rowCount > 0) {
            if (keyCode == KeyEvent.VK_DOWN) {
                selectedRow = (selectedRow + 1) % rowCount;
            } else if (keyCode == KeyEvent.VK_UP) {
                selectedRow = (selectedRow - 1 + rowCount) % rowCount;
            }
            TablaBusqueda.setRowSelectionInterval(selectedRow, selectedRow);
            TablaBusqueda.scrollRectToVisible(TablaBusqueda.getCellRect(selectedRow, 0, true));
        }
    }


    
    
    
    private void ajustarAlturaComponentes() {
        int filaAltura = TablaBusqueda.getRowHeight();
        int numFilas = TablaBusqueda.getRowCount();
        int altura = filaAltura * numFilas + 24; // Asumiendo que el header tiene un alto de 24px

        if (numFilas > 5) {
            altura = filaAltura * 5 + 24; // Limita la altura si hay muchas filas
        }

        jScrollPane2.setPreferredSize(new Dimension(jScrollPane2.getWidth(), altura));
        jPanel6.setPreferredSize(new Dimension(jPanel6.getWidth(), altura + 10)); // Un poco más grande para acomodar márgenes
        jPanel6.revalidate(); // Revalida el layout para aplicar cambios de tamaño
        jPanel6.repaint(); // Redibuja el panel
    }
    


  




    private void initProductosConArea() {
        try {
            CONSULTASDAO dao = new CONSULTASDAO(Conexion_DB.getConexion());
            listaProductosConArea = dao.obtenerProductosConNombreArea(); // Carga inicial de productos
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar productos: " + e.getMessage(), "Error de Conexión", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void filtrarTablaPorTexto(String texto) {
        DefaultTableModel model = (DefaultTableModel) TablaBusqueda.getModel();
        model.setRowCount(0); // Limpia la tabla primero
        listaFiltrada.clear(); // Limpia la lista filtrada

        // Filtra la lista basado en el texto ingresado y actualiza la tabla
        for (Producto prod : listaProductosConArea) {
            if (prod.getNombre().toLowerCase().contains(texto.toLowerCase()) || prod.getCodigoBarras().toLowerCase().contains(texto.toLowerCase())) {
                model.addRow(new Object[]{
                    prod.getProductoID(),
                    prod.getCodigoBarras(),
                    prod.getNombre(),
                    prod.getMarca(),
                    prod.getPrecio(),
                    prod.getUnidadesDisponibles(),
                    prod.getContenido(),
                    prod.getNombreArea()
                });
                listaFiltrada.add(prod); // Añade al producto a la lista filtrada
            }
        }
    }

private void agregarProductoACobroYCerrarTabla() {
    int selectedRow = TablaBusqueda.getSelectedRow();
    if (selectedRow != -1) {
        Producto selectedProduct = listaFiltrada.get(selectedRow);
        DefaultTableModel modelCobro = (DefaultTableModel) TablaCobro.getModel();
        boolean existeProducto = false;

        // Revisar si el producto ya existe en la tabla de cobros
        for (int i = 0; i < modelCobro.getRowCount(); i++) {
            int productoIDTabla = Integer.parseInt(modelCobro.getValueAt(i, 0).toString());
            if (productoIDTabla == selectedProduct.getProductoID()) {
                int cantidadActual = Integer.parseInt(modelCobro.getValueAt(i, 2).toString());
                cantidadActual++;
                modelCobro.setValueAt(cantidadActual, i, 2);
                double precioUnitario = Double.parseDouble(modelCobro.getValueAt(i, 4).toString());
                modelCobro.setValueAt(precioUnitario * cantidadActual, i, 5);
                existeProducto = true;
                break;
            }
        }

        // Si el producto no existe en la tabla, añadirlo
        if (!existeProducto) {
            modelCobro.addRow(new Object[]{
                selectedProduct.getProductoID(),
                selectedProduct.getCodigoBarras(),
                1, // Unidades
                selectedProduct.getNombre(),
                selectedProduct.getPrecio(),
                selectedProduct.getPrecio() // Importe inicial es igual al precio unitario
            });
        }

        CalcularTotales();
        jPanel6.setVisible(false); // Oculta jPanel6 al seleccionar un producto
    }
}

    
    public double precio(int u, double p){
        double t = u * p;
        return t;
    }

    
       
 



    
    
    public double returnTotal() {
    double total = 0;
    DefaultTableModel model = (DefaultTableModel) TablaCobro.getModel();
    for (int i = 0; i < model.getRowCount(); i++) {
        total += (Double) model.getValueAt(i, 5); // Asumiendo que la columna 5 es el importe
    }
    return total;
}

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TablaBusqueda = new javax.swing.JTable();
        Panel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        sub = new javax.swing.JLabel();
        desc = new javax.swing.JLabel();
        total = new javax.swing.JLabel();
        iva = new javax.swing.JLabel();
        lblSubtotal = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        lblIva = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        Busqueda = new javax.swing.JTextField();
        btnEliminar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaCobro = new javax.swing.JTable();
        btnCobro = new javax.swing.JButton();
        MenuPlegable = new javax.swing.JPanel();
        Menu = new javax.swing.JLabel();
        Inicio = new javax.swing.JLabel();
        Usuarios = new javax.swing.JLabel();
        Configuracion = new javax.swing.JLabel();
        Ventas = new javax.swing.JLabel();
        Inventario = new javax.swing.JLabel();
        Analisis = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(102, 153, 0));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        TablaBusqueda.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "", "", "", ""
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(TablaBusqueda);
        if (TablaBusqueda.getColumnModel().getColumnCount() > 0) {
            TablaBusqueda.getColumnModel().getColumn(0).setResizable(false);
            TablaBusqueda.getColumnModel().getColumn(2).setResizable(false);
            TablaBusqueda.getColumnModel().getColumn(3).setResizable(false);
        }

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 1, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 90, 440, 70));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        sub.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        sub.setText("Subtotal:   $");

        desc.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        desc.setText("Descuento:   $");

        total.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        total.setText("Total:   $");

        iva.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        iva.setText("I.V.A.:   $");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(sub)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(iva)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblIva, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(desc)
                .addGap(144, 144, 144)
                .addComponent(total)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblIva, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(sub)
                        .addComponent(desc)
                        .addComponent(total)
                        .addComponent(iva)
                        .addComponent(lblSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));

        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setText("Nota - 0001");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(712, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addContainerGap())
        );

        Busqueda.setText("Producto");
        Busqueda.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                BusquedaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                BusquedaFocusLost(evt);
            }
        });
        Busqueda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BusquedaActionPerformed(evt);
            }
        });

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        TablaCobro.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        TablaCobro.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "PRODUCTOID", "CODIGO", "UNIDADES", "PRODUCTO", "PRECIO UNI.", "IMPORTE"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, true, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TablaCobro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TablaCobroKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(TablaCobro);
        if (TablaCobro.getColumnModel().getColumnCount() > 0) {
            TablaCobro.getColumnModel().getColumn(0).setHeaderValue("PRODUCTOID");
        }

        btnCobro.setText("Cobrar");
        btnCobro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCobroActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout Panel2Layout = new javax.swing.GroupLayout(Panel2);
        Panel2.setLayout(Panel2Layout);
        Panel2Layout.setHorizontalGroup(
            Panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel2Layout.createSequentialGroup()
                .addGroup(Panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(Panel2Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(Panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Busqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(Panel2Layout.createSequentialGroup()
                                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(490, 490, 490)
                                .addComponent(btnCobro, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 730, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(0, 30, Short.MAX_VALUE))
        );
        Panel2Layout.setVerticalGroup(
            Panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel2Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(Busqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(83, 83, 83)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addGroup(Panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEliminar)
                    .addComponent(btnCobro))
                .addContainerGap())
        );

        jPanel1.add(Panel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 0, 820, 530));

        MenuPlegable.setBackground(new java.awt.Color(204, 204, 204));
        MenuPlegable.setLayout(null);

        Menu.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        Menu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Menu32px.png"))); // NOI18N
        Menu.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 10));
        Menu.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Menu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MenuMouseClicked(evt);
            }
        });
        MenuPlegable.add(Menu);
        Menu.setBounds(0, 0, 170, 50);

        Inicio.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        Inicio.setForeground(new java.awt.Color(255, 255, 255));
        Inicio.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        Inicio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Inicio32px.png"))); // NOI18N
        Inicio.setText("Inicio");
        Inicio.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 10));
        Inicio.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Inicio.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        Inicio.setIconTextGap(15);
        Inicio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                InicioMouseClicked(evt);
            }
        });
        MenuPlegable.add(Inicio);
        Inicio.setBounds(0, 70, 170, 50);

        Usuarios.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        Usuarios.setForeground(new java.awt.Color(255, 255, 255));
        Usuarios.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        Usuarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Usuarios32px.png"))); // NOI18N
        Usuarios.setText("Usuarios");
        Usuarios.setToolTipText("");
        Usuarios.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 10));
        Usuarios.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Usuarios.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        Usuarios.setIconTextGap(15);
        Usuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                UsuariosMouseClicked(evt);
            }
        });
        MenuPlegable.add(Usuarios);
        Usuarios.setBounds(0, 430, 170, 50);

        Configuracion.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        Configuracion.setForeground(new java.awt.Color(255, 255, 255));
        Configuracion.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        Configuracion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Configuraciones32px.png"))); // NOI18N
        Configuracion.setText("Configuracion");
        Configuracion.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 10));
        Configuracion.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Configuracion.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        Configuracion.setIconTextGap(15);
        Configuracion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ConfiguracionMouseClicked(evt);
            }
        });
        MenuPlegable.add(Configuracion);
        Configuracion.setBounds(0, 520, 170, 50);

        Ventas.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        Ventas.setForeground(new java.awt.Color(255, 255, 255));
        Ventas.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        Ventas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Venta32px.png"))); // NOI18N
        Ventas.setText("Venta");
        Ventas.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 10));
        Ventas.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Ventas.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        Ventas.setIconTextGap(15);
        Ventas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                VentasMouseClicked(evt);
            }
        });
        MenuPlegable.add(Ventas);
        Ventas.setBounds(0, 170, 170, 50);

        Inventario.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        Inventario.setForeground(new java.awt.Color(255, 255, 255));
        Inventario.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        Inventario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Inventario32px.png"))); // NOI18N
        Inventario.setText("Inventario");
        Inventario.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 10));
        Inventario.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Inventario.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        Inventario.setIconTextGap(15);
        Inventario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                InventarioMouseClicked(evt);
            }
        });
        MenuPlegable.add(Inventario);
        Inventario.setBounds(0, 350, 170, 50);

        Analisis.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        Analisis.setForeground(new java.awt.Color(255, 255, 255));
        Analisis.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        Analisis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Graficas32px.png"))); // NOI18N
        Analisis.setText("Analisis");
        Analisis.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 10));
        Analisis.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Analisis.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        Analisis.setIconTextGap(15);
        Analisis.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AnalisisMouseClicked(evt);
            }
        });
        MenuPlegable.add(Analisis);
        Analisis.setBounds(0, 260, 170, 50);

        jPanel1.add(MenuPlegable, new org.netbeans.lib.awtextra.AbsoluteConstraints(-120, 0, 170, 610));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BusquedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BusquedaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BusquedaActionPerformed

    private void btnCobroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCobroActionPerformed
    List<Producto> productosSeleccionados = obtenerProductosSeleccionadosEnTabla();
    if (productosSeleccionados.isEmpty()) {
        JOptionPane.showMessageDialog(this, "No hay productos seleccionados para la venta.");
        return;
    }
    try {
        // Obtención del usuario logueado
        Usuario usuarioActual = SesionManager.getInstance().getUsuarioLogueado();
        if (usuarioActual == null) {
            JOptionPane.showMessageDialog(this, "No hay usuario logueado.");
            return;
        }

        // Creación de la instancia DAO y ejecución de la venta
        CONSULTASDAO dao = new CONSULTASDAO(Conexion_DB.getConexion());
        if (dao.completarVenta(usuarioActual.getUsuarioID(), productosSeleccionados)) {
            int ventaId = dao.getUltimaVentaIdPorUsuario(usuarioActual.getUsuarioID()); // Método para obtener el último ID de venta por usuario
            List<Producto> detallesVenta = dao.obtenerDetallesVenta(ventaId); // Método para obtener los detalles de la venta

            double totalVenta = returnTotal(); // Asegúrate de que este método calcula el total correcto
            Cobro ventanaCobro = new Cobro(totalVenta, productosSeleccionados);
            ventanaCobro.setVentaListener(() -> {
                // Limpiar la tabla cuando se completa la venta
                ((DefaultTableModel) TablaCobro.getModel()).setRowCount(0);
                // Aquí también podrías actualizar otros componentes si es necesario
            });
            ventanaCobro.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Error al completar la venta.");
        }
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(this, "Error de conexión: " + ex.getMessage(), "Error de Conexión", JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace();
    }
    }//GEN-LAST:event_btnCobroActionPerformed

/*
    private List<Producto> obtenerProductosSeleccionadosEnTabla() {
        List<Producto> productos = new ArrayList<>();
        DefaultTableModel modelo = (DefaultTableModel) TablaCobro.getModel();

        for (int i = 0; i < modelo.getRowCount(); i++) {
            Producto producto = new Producto();
            try {
                int productoID = Integer.parseInt(modelo.getValueAt(i, 0).toString()); // Suponiendo que la columna 0 tiene el ID
                int cantidadUnid = Integer.parseInt(modelo.getValueAt(i, 2).toString()); // Suponiendo que la columna 2 tiene la cantidad
                double precio = Double.parseDouble(modelo.getValueAt(i, 4).toString()); // Suponiendo que la columna 5 tiene el precio unitario

                producto.setProductoID(productoID);
                producto.setCantidad(cantidadUnid);
                producto.setPrecio(precio);

                productos.add(producto);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Error en formato de número en la fila " + (i + 1) + ".", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            } catch (NullPointerException e) {
                JOptionPane.showMessageDialog(this, "Valor nulo encontrado en la fila " + (i + 1) + ".", "Error de Nulo", JOptionPane.ERROR_MESSAGE);
            }
        }
        return productos;
    }
*/
    
    private List<Producto> obtenerProductosSeleccionadosEnTabla() {
    List<Producto> productos = new ArrayList<>();
    DefaultTableModel modelo = (DefaultTableModel) TablaCobro.getModel();

    for (int i = 0; i < modelo.getRowCount(); i++) {
        Producto producto = new Producto();
        try {
            // Asegúrate de que estos índices coincidan con cómo has configurado tu tabla
            int productoID = Integer.parseInt(modelo.getValueAt(i, 0).toString());  // ID del producto
            String codigo = modelo.getValueAt(i, 1).toString();                    // Código del producto
            String nombre = modelo.getValueAt(i, 3).toString();                    // Nombre del producto
            double precio = Double.parseDouble(modelo.getValueAt(i, 4).toString()); // Precio unitario
            int cantidad = Integer.parseInt(modelo.getValueAt(i, 2).toString());   // Cantidad

            producto.setProductoID(productoID);
            producto.setCodigoBarras(codigo);
            producto.setNombre(nombre);
            producto.setPrecio(precio);
            producto.setCantidad(cantidad);

            productos.add(producto);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Error en formato de número en la fila " + (i + 1) + ".", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(this, "Valor nulo encontrado en la fila " + (i + 1) + ".", "Error de Nulo", JOptionPane.ERROR_MESSAGE);
        }
    }
    return productos;
}


    private void BusquedaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_BusquedaFocusGained
        if(Busqueda.getText().equals("Producto")){
            Busqueda.setText(null);
            Busqueda.requestFocus();
            Estilos.removePlaceholderStyle(Busqueda);            
        }
    }//GEN-LAST:event_BusquedaFocusGained

    private void BusquedaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_BusquedaFocusLost
        if(Busqueda.getText().length()==0){
            Estilos.addPlaceholderStyle(Busqueda);
            Busqueda.setText("Producto");
        }
    }//GEN-LAST:event_BusquedaFocusLost

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
    // Obtener el modelo de la tabla
    DefaultTableModel modelo = (DefaultTableModel) TablaCobro.getModel();

    // Obtener el índice de la fila seleccionada
    int filaSeleccionada = TablaCobro.getSelectedRow();

    // Verificar si se ha seleccionado alguna fila
    if (filaSeleccionada != -1) {
        // Eliminar la fila seleccionada del modelo
        modelo.removeRow(filaSeleccionada);

        // Mostrar un mensaje de confirmación (opcional)
        JOptionPane.showMessageDialog(this, "Producto eliminad correctamente.", "Eliminar", JOptionPane.INFORMATION_MESSAGE);

        // Llamar a cualquier método que necesite ejecutar después de eliminar una fila, como recalcular totales si es necesario
        CalcularTotales();
    } else {
        // Mostrar un mensaje si no hay fila seleccionada
        JOptionPane.showMessageDialog(this, "Por favor seleccione una fila para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
    }


    }//GEN-LAST:event_btnEliminarActionPerformed

    private void TablaCobroKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TablaCobroKeyPressed
    if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
        int filaSeleccionada = TablaCobro.getSelectedRow();
        if (filaSeleccionada != -1) {
            ((DefaultTableModel) TablaCobro.getModel()).removeRow(filaSeleccionada);
            JOptionPane.showMessageDialog(this, "Producto eliminado correctamente.");
        } else {
            JOptionPane.showMessageDialog(this, "No hay fila seleccionada.");
        }
    }


    }//GEN-LAST:event_TablaCobroKeyPressed

    private void MenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MenuMouseClicked
        // Mover dependiendo de la posición actual
        if (MenuPlegable.getX() == 0) {
            // Mover ambos componentes hacia la izquierda
            animador.animar(MenuPlegable, Panel2, -120, Panel2.getX() - 120, false);
            MenuPlegable.setBackground(new Color(204, 204, 204)); // Cambiar al color cuando está plegado
            menuDesplegado = false;
        } else if (MenuPlegable.getX() == -120) {
            // Mover ambos componentes hacia la derecha
            animador.animar(MenuPlegable, Panel2, 0, Panel2.getX() + 120, true);
            MenuPlegable.setBackground(new Color(51, 51, 51)); // Cambiar al color cuando está desplegado
            menuDesplegado = true;
        }
        HoverEffect.setMenuDesplegado(menuDesplegado); // Actualizar el estado en HoverEffect

    }//GEN-LAST:event_MenuMouseClicked

    private void UsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_UsuariosMouseClicked

    }//GEN-LAST:event_UsuariosMouseClicked

    private void ConfiguracionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ConfiguracionMouseClicked
    Configuraciones configWindow = Configuraciones.getInstance();
    configWindow.setVisible(true);
    this.setVisible(false);
    }//GEN-LAST:event_ConfiguracionMouseClicked

    private void VentasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_VentasMouseClicked

    }//GEN-LAST:event_VentasMouseClicked

    private void InventarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_InventarioMouseClicked
    Principal2_0 principalWindow = Principal2_0.getInstance();
    principalWindow.initialize(SesionManager.getInstance().getUsuarioLogueado());
    principalWindow.setVisible(true);
    this.setVisible(false);
    }//GEN-LAST:event_InventarioMouseClicked

    private void AnalisisMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AnalisisMouseClicked
        AvisosFrame avisosFrame = AvisosFrame.getInstance();
        avisosFrame.initialize(SesionManager.getInstance().getUsuarioLogueado());
        avisosFrame.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_AnalisisMouseClicked

    private void InicioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_InicioMouseClicked
       MenuPrincipal menuPrincipal = MenuPrincipal.getInstance();
        //principalWindow.setVentanaPrincipal(this);
        menuPrincipal.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_InicioMouseClicked

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
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Venta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Venta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Venta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Venta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            Venta ventana = Venta.getInstance();
            ventana.setVisible(true);
            }
        });
    }
    


  


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Analisis;
    private javax.swing.JTextField Busqueda;
    private javax.swing.JLabel Configuracion;
    private javax.swing.JLabel Inicio;
    private javax.swing.JLabel Inventario;
    private javax.swing.JLabel Menu;
    private javax.swing.JPanel MenuPlegable;
    private javax.swing.JPanel Panel2;
    private javax.swing.JTable TablaBusqueda;
    private javax.swing.JTable TablaCobro;
    private javax.swing.JLabel Usuarios;
    private javax.swing.JLabel Ventas;
    private javax.swing.JButton btnCobro;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JLabel desc;
    private javax.swing.JLabel iva;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblIva;
    private javax.swing.JLabel lblSubtotal;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel sub;
    private javax.swing.JLabel total;
    // End of variables declaration//GEN-END:variables
}


