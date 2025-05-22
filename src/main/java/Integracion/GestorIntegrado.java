package Integracion;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.*;

import DBObjetos.Producto;
import PatronBridge.*;
import PatronComposite.*;

/**
 * Interfaz gráfica principal que integra los patrones Bridge y Composite.
 * Esta interfaz permite gestionar el catálogo de productos y enviar notificaciones.
 */
public class GestorIntegrado extends JFrame {
    
    private static final Logger LOGGER = Logger.getLogger(GestorIntegrado.class.getName());
    
    // Componentes para el patrón Composite (Catálogo)
    private CatalogoManager catalogoManager;
    private JTree catalogoTree;
    private JPanel productosPanel;
    private JTextArea detallesArea;
    
    // Componentes para el patrón Bridge (Notificaciones)
    private NotificacionManager notificacionManager;
    private JComboBox<String> tipoNotificacionCombo;
    private JComboBox<String> destinatarioCombo;
    private JTextField asuntoField;
    private JTextArea mensajeArea;
    
    // Componentes de integración
    private CatalogoNotificacionIntegrador integrador;
    private JTabbedPane tabbedPane;
    private JPanel catalogoPanel;
    private JPanel notificacionesPanel;
    private JPanel integracionPanel;
    private JLabel statusLabel;
    
    /**
     * Constructor de la interfaz gráfica integrada.
     */
    public GestorIntegrado() {
        // Configuración básica de la ventana
        setTitle("Sistema Integrado - Patrones Bridge y Composite");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        
        // Inicializar los gestores
        catalogoManager = CatalogoManager.getInstance();
        notificacionManager = NotificacionManager.getInstance();
        integrador = CatalogoNotificacionIntegrador.getInstance();
        
        // Crear e inicializar los componentes
        tabbedPane = new JTabbedPane();
        
        // Inicializar paneles principales
        catalogoPanel = crearPanelCatalogo();
        notificacionesPanel = crearPanelNotificaciones();
        integracionPanel = crearPanelIntegracion();
        
        // Agregar los paneles al TabbedPane
        tabbedPane.addTab("Catálogo de Productos", new ImageIcon(), catalogoPanel, "Gestión del catálogo (Patrón Composite)");
        tabbedPane.addTab("Sistema de Notificaciones", new ImageIcon(), notificacionesPanel, "Gestión de notificaciones (Patrón Bridge)");
        tabbedPane.addTab("Integración", new ImageIcon(), integracionPanel, "Integración de ambos patrones");
        
        // Barra de estado
        statusLabel = new JLabel("Estado: Listo");
        statusLabel.setBorder(BorderFactory.createEtchedBorder());
        
        // Agregar componentes al frame
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(tabbedPane, BorderLayout.CENTER);
        getContentPane().add(statusLabel, BorderLayout.SOUTH);
        
        // Cargar los datos iniciales
        cargarDatos();
    }
    
    /**
     * Crea el panel para la gestión del catálogo (Patrón Composite).
     * 
     * @return Panel configurado
     */
    private JPanel crearPanelCatalogo() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panel izquierdo para el árbol de categorías
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createTitledBorder("Categorías"));
        
        // Árbol de categorías
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Catálogo de Productos");
        DefaultTreeModel treeModel = new DefaultTreeModel(root);
        catalogoTree = new JTree(treeModel);
        catalogoTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        
        // Configurar el renderer del árbol
        catalogoTree.setCellRenderer(new DefaultTreeCellRenderer() {
            @Override
            public Component getTreeCellRendererComponent(JTree tree, Object value, 
                    boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
                
                super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
                
                if (value instanceof DefaultMutableTreeNode) {
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
                    Object userObject = node.getUserObject();
                    
                    if (userObject instanceof CategoriaComponent) {
                        CategoriaComponent componente = (CategoriaComponent) userObject;
                        setText(componente.getNombre());
                        
                        if (componente.esHoja()) {
                            setIcon(UIManager.getIcon("FileView.fileIcon"));
                        } else {
                            setIcon(expanded ? 
                                   UIManager.getIcon("Tree.openIcon") : 
                                   UIManager.getIcon("Tree.closedIcon"));
                        }
                    }
                }
                
                return this;
            }
        });
        
        // Evento de selección para el árbol
        catalogoTree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) 
                        catalogoTree.getLastSelectedPathComponent();
                
                if (selectedNode != null) {
                    mostrarSeleccion(selectedNode);
                }
            }
        });
        
        JScrollPane treeScroll = new JScrollPane(catalogoTree);
        leftPanel.add(treeScroll, BorderLayout.CENTER);
        
        // Panel de búsqueda
        JPanel searchPanel = new JPanel(new BorderLayout(5, 0));
        JTextField busquedaField = new JTextField();
        JButton buscarButton = new JButton("Buscar");
        searchPanel.add(busquedaField, BorderLayout.CENTER);
        searchPanel.add(buscarButton, BorderLayout.EAST);
        leftPanel.add(searchPanel, BorderLayout.SOUTH);
        
        // Evento de búsqueda
        ActionListener buscarAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String texto = busquedaField.getText().trim();
                if (!texto.isEmpty()) {
                    buscarProductos(texto);
                }
            }
        };
        
        buscarButton.addActionListener(buscarAction);
        busquedaField.addActionListener(buscarAction);
        
        // Panel central para mostrar productos
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createTitledBorder("Productos"));
        
        productosPanel = new JPanel();
        productosPanel.setLayout(new BoxLayout(productosPanel, BoxLayout.Y_AXIS));
        JScrollPane productosScroll = new JScrollPane(productosPanel);
        centerPanel.add(productosScroll, BorderLayout.CENTER);
        
        // Panel derecho para detalles
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createTitledBorder("Detalles"));
        
        detallesArea = new JTextArea();
        detallesArea.setEditable(false);
        JScrollPane detallesScroll = new JScrollPane(detallesArea);
        rightPanel.add(detallesScroll, BorderLayout.CENTER);
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton recargarButton = new JButton("Recargar Catálogo");
        JButton valorButton = new JButton("Calcular Valor Inventario");
        JButton agregarButton = new JButton("Agregar Producto");
        
        buttonPanel.add(agregarButton);
        buttonPanel.add(recargarButton);
        buttonPanel.add(valorButton);
        
        // Eventos para los botones
        recargarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recargarCatalogo();
            }
        });
        
        valorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calcularValorInventario();
            }
        });
        
        agregarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarDialogoAgregarProducto();
            }
        });
        
        // Agregar componentes al panel principal
        panel.add(leftPanel, BorderLayout.WEST);
        panel.add(centerPanel, BorderLayout.CENTER);
        panel.add(rightPanel, BorderLayout.EAST);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    /**
     * Crea el panel para la gestión de notificaciones (Patrón Bridge).
     * 
     * @return Panel configurado
     */
    private JPanel crearPanelNotificaciones() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panel superior para configuraciones
        JPanel configPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        configPanel.setBorder(BorderFactory.createTitledBorder("Configuración de Notificaciones"));
        
        // Combo para tipo de notificación
        JPanel tipoPanel = new JPanel(new BorderLayout());
        tipoPanel.add(new JLabel("Tipo de Notificación: "), BorderLayout.WEST);
        
        tipoNotificacionCombo = new JComboBox<>();
        for (String tipo : notificacionManager.getImplementadoresDisponibles()) {
            tipoNotificacionCombo.addItem(tipo);
        }
        
        tipoPanel.add(tipoNotificacionCombo, BorderLayout.CENTER);
        configPanel.add(tipoPanel);
        
        // Combo para destinatario
        JPanel destinatarioPanel = new JPanel(new BorderLayout());
        destinatarioPanel.add(new JLabel("Destinatario: "), BorderLayout.WEST);
        
        destinatarioCombo = new JComboBox<>();
        destinatarioCombo.setEditable(true);
        try {
            // Cargar algunos destinatarios de ejemplo
            destinatarioCombo.addItem("admin@tienda.com");
            destinatarioCombo.addItem("gerente@tienda.com");
            destinatarioCombo.addItem("sistema@tienda.com");
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error al cargar destinatarios", e);
        }
        
        destinatarioPanel.add(destinatarioCombo, BorderLayout.CENTER);
        configPanel.add(destinatarioPanel);
        
        // Panel para asunto
        JPanel asuntoPanel = new JPanel(new BorderLayout());
        asuntoPanel.add(new JLabel("Asunto: "), BorderLayout.WEST);
        
        asuntoField = new JTextField();
        asuntoPanel.add(asuntoField, BorderLayout.CENTER);
        configPanel.add(asuntoPanel);
        
        // Botones de acciones rápidas
        JPanel accionesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        JButton probarButton = new JButton("Probar Conexión");
        probarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                probarConexionNotificacion();
            }
        });
        
        JButton plantillasButton = new JButton("Usar Plantilla");
        plantillasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarPlantillasNotificacion();
            }
        });
        
        accionesPanel.add(probarButton);
        accionesPanel.add(plantillasButton);
        configPanel.add(accionesPanel);
        
        // Panel central para el mensaje
        JPanel mensajePanel = new JPanel(new BorderLayout());
        mensajePanel.setBorder(BorderFactory.createTitledBorder("Mensaje"));
        
        mensajeArea = new JTextArea();
        mensajeArea.setLineWrap(true);
        mensajeArea.setWrapStyleWord(true);
        JScrollPane mensajeScroll = new JScrollPane(mensajeArea);
        mensajePanel.add(mensajeScroll, BorderLayout.CENTER);
        
        // Panel inferior con botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        JButton limpiarButton = new JButton("Limpiar");
        limpiarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarFormularioNotificacion();
            }
        });
        
        JButton enviarButton = new JButton("Enviar Notificación");
        enviarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enviarNotificacion();
            }
        });
        
        buttonPanel.add(limpiarButton);
        buttonPanel.add(enviarButton);
        
        // Agregar componentes al panel principal
        panel.add(configPanel, BorderLayout.NORTH);
        panel.add(mensajePanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    /**
     * Crea el panel para integración de ambos patrones.
     * 
     * @return Panel configurado
     */
    private JPanel crearPanelIntegracion() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panel superior para configuración
        JPanel configPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        configPanel.setBorder(BorderFactory.createTitledBorder("Configuración de Integración"));
        
        // Campo para contacto del administrador
        JPanel contactoPanel = new JPanel(new BorderLayout());
        contactoPanel.add(new JLabel("Contacto Administrador: "), BorderLayout.WEST);
        
        JTextField contactoField = new JTextField("admin@tienda.com");
        contactoField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                integrador.setContactoAdmin(contactoField.getText());
                actualizarEstado("Contacto de administrador actualizado");
            }
        });
        
        contactoPanel.add(contactoField, BorderLayout.CENTER);
        configPanel.add(contactoPanel);
        
        // Combo para el tipo de notificación
        JPanel tipoPanel = new JPanel(new BorderLayout());
        tipoPanel.add(new JLabel("Tipo de Notificación: "), BorderLayout.WEST);
        
        JComboBox<String> tipoIntegracionCombo = new JComboBox<>();
        for (String tipo : notificacionManager.getImplementadoresDisponibles()) {
            tipoIntegracionCombo.addItem(tipo);
        }
        
        tipoIntegracionCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tipo = (String) tipoIntegracionCombo.getSelectedItem();
                if (tipo != null) {
                    integrador.cambiarTipoNotificacionInventario(tipo);
                    actualizarEstado("Tipo de notificación de inventario cambiado a: " + tipo);
                }
            }
        });
        
        tipoPanel.add(tipoIntegracionCombo, BorderLayout.CENTER);
        configPanel.add(tipoPanel);
        
        // Campo para umbral de stock bajo
        JPanel umbralPanel = new JPanel(new BorderLayout());
        umbralPanel.add(new JLabel("Umbral de Stock Bajo: "), BorderLayout.WEST);
        
        JSpinner umbralSpinner = new JSpinner(new SpinnerNumberModel(5, 0, 100, 1));
        umbralSpinner.addChangeListener(e -> {
            integrador.setUmbralStockBajo((Integer) umbralSpinner.getValue());
            actualizarEstado("Umbral de stock bajo actualizado a: " + umbralSpinner.getValue());
        });
        
        umbralPanel.add(umbralSpinner, BorderLayout.CENTER);
        configPanel.add(umbralPanel);
        
        // Checkbox para activar notificaciones
        JPanel activarPanel = new JPanel(new BorderLayout());
        activarPanel.add(new JLabel("Notificaciones Activas: "), BorderLayout.WEST);
        
        JCheckBox activarCheck = new JCheckBox("", true);
        activarCheck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                integrador.setNotificacionesActivas(activarCheck.isSelected());
                actualizarEstado("Notificaciones " + (activarCheck.isSelected() ? "activadas" : "desactivadas"));
            }
        });
        
        activarPanel.add(activarCheck, BorderLayout.CENTER);
        configPanel.add(activarPanel);
        
        // Vacío para el layout
        configPanel.add(new JLabel());
        
        // Vacío para el layout
        configPanel.add(new JLabel());
        
        // Panel central con las acciones de integración
        JPanel accionesPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        accionesPanel.setBorder(BorderFactory.createTitledBorder("Acciones de Integración"));
        
        // Botón para verificar stock bajo
        JButton stockBajoButton = new JButton("Verificar Productos con Stock Bajo");
        stockBajoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verificarStockBajo();
            }
        });
        accionesPanel.add(stockBajoButton);
        
        // Botón para notificar valor del inventario
        JButton valorInventarioButton = new JButton("Notificar Valor del Inventario");
        valorInventarioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                notificarValorInventario();
            }
        });
        accionesPanel.add(valorInventarioButton);
        
        // Botón para simular agregar producto
        JButton agregarProductoButton = new JButton("Simular Agregar Producto");
        agregarProductoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simularAgregarProducto();
            }
        });
        accionesPanel.add(agregarProductoButton);
        
        // Botón para generar reporte
        JButton reporteButton = new JButton("Generar Reporte de Catálogo");
        reporteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarReporteCatalogo();
            }
        });
        accionesPanel.add(reporteButton);
        
        // Panel para mostrar resultados
        JPanel resultadosPanel = new JPanel(new BorderLayout());
        resultadosPanel.setBorder(BorderFactory.createTitledBorder("Resultados"));
        
        JTextArea resultadosArea = new JTextArea();
        resultadosArea.setEditable(false);
        JScrollPane resultadosScroll = new JScrollPane(resultadosArea);
        resultadosPanel.add(resultadosScroll, BorderLayout.CENTER);
        
        // Agregar componentes al panel principal
        panel.add(configPanel, BorderLayout.NORTH);
        panel.add(accionesPanel, BorderLayout.CENTER);
        panel.add(resultadosPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    /**
     * Carga los datos iniciales en la interfaz.
     */
    private void cargarDatos() {
        // Cargar el árbol de categorías
        cargarArbolCategorias();
        
        // Establecer valores iniciales para la integración
        integrador.setContactoAdmin("admin@tienda.com");
        integrador.setUmbralStockBajo(5);
        integrador.setNotificacionesActivas(true);
        
        // Mostrar mensaje informativo
        mostrarInformacion();
    }
    
    /**
     * Carga el árbol de categorías desde el CatalogoManager.
     */
    private void cargarArbolCategorias() {
        try {
            // Obtener el modelo del árbol
            DefaultTreeModel model = (DefaultTreeModel) catalogoTree.getModel();
            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
            root.removeAllChildren();
            
            // Obtener la raíz del catálogo
            CategoriaComposite catalogoRaiz = catalogoManager.getCatalogoRaiz();
            
            // Cargar las categorías
            for (CategoriaComponent componente : catalogoRaiz.getHijos()) {
                if (!componente.esHoja()) {
                    DefaultMutableTreeNode nodo = new DefaultMutableTreeNode(componente);
                    root.add(nodo);
                    cargarProductosCategoria(nodo, (CategoriaComposite) componente);
                }
            }
            
            // Actualizar el modelo y expandir el árbol
            model.reload();
            expandirArbol();
            
            // Actualizar estado
            actualizarEstado("Catálogo cargado con " + catalogoRaiz.contarComponentes() + " componentes");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al cargar el árbol de categorías", e);
            JOptionPane.showMessageDialog(this, 
                    "Error al cargar las categorías: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Carga los productos de una categoría en el árbol.
     * 
     * @param nodoCategoria Nodo de la categoría
     * @param categoria Objeto de la categoría
     */
    private void cargarProductosCategoria(DefaultMutableTreeNode nodoCategoria, CategoriaComposite categoria) {
        try {
            // Obtener los productos de la categoría
            List<ProductoLeaf> productos = categoria.obtenerTodosLosProductos();
            
            // Añadir cada producto como un nodo hijo
            for (ProductoLeaf producto : productos) {
                DefaultMutableTreeNode nodoProducto = new DefaultMutableTreeNode(producto);
                nodoCategoria.add(nodoProducto);
            }
            
            // Si no hay productos, añadir un mensaje
            if (productos.isEmpty()) {
                DefaultMutableTreeNode nodoVacio = new DefaultMutableTreeNode("No hay productos");
                nodoCategoria.add(nodoVacio);
            }
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error al cargar productos: {0}", e.getMessage());
            // Añadir un nodo de error
            DefaultMutableTreeNode nodoError = new DefaultMutableTreeNode("Error al cargar productos");
            nodoCategoria.add(nodoError);
        }
    }
    
    /**
     * Expande todos los nodos del árbol.
     */
    private void expandirArbol() {
        for (int i = 0; i < catalogoTree.getRowCount(); i++) {
            catalogoTree.expandRow(i);
        }
    }
    
    /**
     * Muestra los productos de la categoría seleccionada.
     * 
     * @param node Nodo seleccionado en el árbol
     */
    private void mostrarSeleccion(DefaultMutableTreeNode node) {
        if (node == null) {
            LOGGER.log(Level.WARNING, "Se intentó mostrar la selección de un nodo nulo");
            return;
        }
        
        Object userObject = node.getUserObject();
        
        try {
            if (userObject instanceof String) {
                if ("Catálogo de Productos".equals(userObject)) {
                    // Es la raíz, mostrar todos los productos
                    mostrarProductos(catalogoManager.obtenerTodosLosProductos(), "Todos los Productos");
                } else if ("No hay productos".equals(userObject) || "Error al cargar productos".equals(userObject)) {
                    // Es un mensaje informativo, no hacer nada
                    productosPanel.removeAll();
                    productosPanel.revalidate();
                    productosPanel.repaint();
                    actualizarEstado("No hay productos para mostrar");
                }
            } else if (userObject instanceof CategoriaComponent) {
                CategoriaComponent componente = (CategoriaComponent) userObject;
                if (!componente.esHoja()) {
                    // Es una categoría, mostrar sus productos
                    CategoriaComposite categoria = (CategoriaComposite) componente;
                    List<ProductoLeaf> productos = categoria.obtenerTodosLosProductos();
                    mostrarProductos(productos, categoria.getNombre());
                } else {
                    // Es un producto, mostrar sus detalles
                    ProductoLeaf producto = (ProductoLeaf) componente;
                    mostrarDetallesProducto(producto);
                    // También mostrar el producto en el panel central
                    List<ProductoLeaf> productos = new ArrayList<>();
                    productos.add(producto);
                    mostrarProductos(productos, "Producto: " + producto.getNombre());
                }
            } else {
                // Tipo desconocido
                LOGGER.log(Level.WARNING, "Tipo de objeto desconocido en el árbol: {0}", 
                        userObject != null ? userObject.getClass().getName() : "null");
                productosPanel.removeAll();
                productosPanel.revalidate();
                productosPanel.repaint();
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al mostrar la selección: {0}", e.getMessage());
            JOptionPane.showMessageDialog(this, 
                    "Error al mostrar los productos: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Muestra una lista de productos en el panel central.
     * 
     * @param productos Lista de productos a mostrar
     * @param titulo Título para el panel
     */
    private void mostrarProductos(List<ProductoLeaf> productos, String titulo) {
        // Limpiar el panel
        productosPanel.removeAll();
        
        // Título
        JLabel tituloLabel = new JLabel(titulo);
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 16));
        tituloLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        productosPanel.add(tituloLabel);
        productosPanel.add(Box.createVerticalStrut(10));
        
        if (productos.isEmpty()) {
            JLabel emptyLabel = new JLabel("No hay productos en esta categoría");
            emptyLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            productosPanel.add(emptyLabel);
        } else {
            // Agregar cada producto
            for (ProductoLeaf producto : productos) {
                JPanel productoPanel = crearPanelProducto(producto);
                productoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
                productosPanel.add(productoPanel);
                productosPanel.add(Box.createVerticalStrut(5));
            }
            
            // Mostrar total
            JLabel totalLabel = new JLabel(String.format("Total: %d productos", productos.size()));
            totalLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            totalLabel.setFont(new Font("Arial", Font.ITALIC, 12));
            productosPanel.add(Box.createVerticalStrut(10));
            productosPanel.add(totalLabel);
        }
        
        // Agregar espacio flexible al final
        productosPanel.add(Box.createVerticalGlue());
        
        // Actualizar el panel
        productosPanel.revalidate();
        productosPanel.repaint();
        
        // Actualizar estado
        actualizarEstado("Mostrando " + productos.size() + " productos de " + titulo);
    }
    
    /**
     * Crea un panel para mostrar un producto.
     * 
     * @param producto Producto a mostrar
     * @return Panel con la información del producto
     */
    private JPanel crearPanelProducto(ProductoLeaf producto) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBorder(BorderFactory.createEtchedBorder());
        panel.setMaximumSize(new Dimension(Short.MAX_VALUE, 60));
        
        // Panel para la información
        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        JLabel nombreLabel = new JLabel(producto.getNombre());
        nombreLabel.setFont(new Font("Arial", Font.BOLD, 14));
        JLabel precioLabel = new JLabel(String.format("Precio: $%.2f - Stock: %d", 
                producto.getPrecio(), producto.getUnidadesDisponibles()));
        
        infoPanel.add(nombreLabel);
        infoPanel.add(precioLabel);
        
        // Panel de botones
        JPanel botonesPanel = new JPanel(new GridLayout(1, 2, 5, 0));
        
        JButton detallesButton = new JButton("Detalles");
        detallesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarDetallesProducto(producto);
            }
        });
        
        JButton notificarButton = new JButton("Notificar");
        notificarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                notificarProducto(producto);
            }
        });
        
        botonesPanel.add(detallesButton);
        botonesPanel.add(notificarButton);
        
        // Agregar componentes al panel
        panel.add(infoPanel);
        panel.add(Box.createHorizontalGlue());
        panel.add(botonesPanel);
        
        return panel;
    }
    
    /**
     * Muestra los detalles de un producto en el área de información.
     * 
     * @param producto Producto a mostrar
     */
    private void mostrarDetallesProducto(ProductoLeaf producto) {
        if (producto == null) {
            detallesArea.setText("No hay producto seleccionado");
            return;
        }
        
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("DETALLES DEL PRODUCTO\n");
            sb.append("====================\n\n");
            sb.append("Nombre: ").append(getSafeString(producto.getNombre())).append("\n");
            sb.append("Descripción: ").append(getSafeString(producto.getDescripcion())).append("\n");
            sb.append("Precio: $").append(String.format("%.2f", producto.getPrecio())).append("\n");
            sb.append("Unidades disponibles: ").append(producto.getUnidadesDisponibles()).append("\n");
            sb.append("Valor inventario: $").append(String.format("%.2f", producto.calcularValorInventario())).append("\n");
            
            // Información adicional si está disponible
            Producto prod = producto.getProducto();
            if (prod != null) {
                sb.append("\nINFORMACIÓN ADICIONAL\n");
                sb.append("====================\n");
                
                if (prod.getCodigoBarras() != null && !prod.getCodigoBarras().isEmpty()) {
                    sb.append("Código de barras: ").append(prod.getCodigoBarras()).append("\n");
                }
                
                if (prod.getMarca() != null && !prod.getMarca().isEmpty()) {
                    sb.append("Marca: ").append(prod.getMarca()).append("\n");
                }
                
                if (prod.getTamañoNeto() != null && !prod.getTamañoNeto().isEmpty()) {
                    sb.append("Tamaño: ").append(prod.getTamañoNeto()).append("\n");
                }
                
                if (prod.getContenido() != null && !prod.getContenido().isEmpty()) {
                    sb.append("Contenido: ").append(prod.getContenido()).append("\n");
                }
                
                if (prod.getFechaCaducidad() != null) {
                    sb.append("Fecha de caducidad: ").append(prod.getFechaCaducidad()).append("\n");
                }
                
                if (prod.getNombreArea() != null && !prod.getNombreArea().isEmpty()) {
                    sb.append("Área: ").append(prod.getNombreArea()).append("\n");
                }
                
                // Añadir más información del área si está disponible
                sb.append("\n");
                sb.append("ID del área: ").append(prod.getAreaID()).append("\n");
                if (prod.getNivelReorden() > 0) {
                    sb.append("Nivel de reorden: ").append(prod.getNivelReorden()).append("\n");
                }
            }
            
            detallesArea.setText(sb.toString());
            detallesArea.setCaretPosition(0);
            
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error al mostrar detalles del producto", e);
            detallesArea.setText("Error al mostrar detalles del producto: " + e.getMessage());
        }
    }
    
    /**
     * Método auxiliar para manejar cadenas potencialmente nulas.
     * 
     * @param s Cadena a verificar
     * @return La cadena o "No disponible" si es nula
     */
    private String getSafeString(String s) {
        return (s != null && !s.trim().isEmpty()) ? s : "No disponible";
    }
    
    /**
     * Busca productos por nombre.
     * 
     * @param texto Texto a buscar
     */
    private void buscarProductos(String texto) {
        if (texto.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                    "Por favor, introduce un texto para buscar", 
                    "Búsqueda vacía", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Cambiar el cursor para indicar que se está procesando
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        actualizarEstado("Buscando productos que coincidan con: " + texto);
        
        try {
            // Validar que el manager esté disponible
            if (catalogoManager == null) {
                throw new IllegalStateException("El gestor de catálogo no está disponible");
            }
            
            // Validar que la cadena de búsqueda tenga al menos 2 caracteres
            if (texto.length() < 2) {
                JOptionPane.showMessageDialog(this,
                        "Por favor, introduce al menos 2 caracteres para la búsqueda",
                        "Búsqueda muy corta", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Realizar la búsqueda
            List<ProductoLeaf> resultados = catalogoManager.buscarProductosPorNombre(texto);
            
            // Mostrar los resultados
            if (resultados.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "No se encontraron productos que coincidan con: " + texto,
                        "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
                productosPanel.removeAll();
                productosPanel.revalidate();
                productosPanel.repaint();
            } else {
                mostrarProductos(resultados, "Resultados de búsqueda: " + texto);
            }
            
            actualizarEstado("Búsqueda completada: " + resultados.size() + " resultados");
            
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al buscar productos: {0}", e.getMessage());
            JOptionPane.showMessageDialog(this, 
                    "Error al buscar productos: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            actualizarEstado("Error al buscar productos");
        } finally {
            // Restaurar el cursor
            setCursor(Cursor.getDefaultCursor());
        }
    }
    
    /**
     * Recarga el catálogo desde la base de datos.
     */
    private void recargarCatalogo() {
        try {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            actualizarEstado("Recargando catálogo...");
            
            catalogoManager.recargarCatalogo();
            cargarArbolCategorias();
            
            actualizarEstado("Catálogo recargado correctamente");
            JOptionPane.showMessageDialog(this, 
                    "Catálogo recargado correctamente", 
                    "Recarga Exitosa", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al recargar el catálogo: {0}", e.getMessage());
            JOptionPane.showMessageDialog(this, 
                    "Error al recargar el catálogo: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            actualizarEstado("Error al recargar el catálogo");
        } finally {
            setCursor(Cursor.getDefaultCursor());
        }
    }
    
    /**
     * Calcula y muestra el valor total del inventario.
     */
    private void calcularValorInventario() {
        try {
            double valorTotal = catalogoManager.calcularValorTotalInventario();
            String mensaje = String.format("Valor total del inventario: $%.2f", valorTotal);
            
            JOptionPane.showMessageDialog(this, mensaje, 
                    "Valor del Inventario", JOptionPane.INFORMATION_MESSAGE);
            
            actualizarEstado(mensaje);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al calcular el valor del inventario: {0}", e.getMessage());
            JOptionPane.showMessageDialog(this, 
                    "Error al calcular el valor del inventario: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            actualizarEstado("Error al calcular el valor del inventario");
        }
    }
    
    /**
     * Muestra el diálogo para agregar un nuevo producto.
     */
    private void mostrarDialogoAgregarProducto() {
        // Crear un panel con los campos necesarios
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        
        // Campos para el producto
        panel.add(new JLabel("Nombre:"));
        JTextField nombreField = new JTextField();
        panel.add(nombreField);
        
        panel.add(new JLabel("Descripción:"));
        JTextField descripcionField = new JTextField();
        panel.add(descripcionField);
        
        panel.add(new JLabel("Área:"));
        JComboBox<String> areaCombo = new JComboBox<>();
        
        // Cargar las áreas disponibles
        try {
            CategoriaComposite catalogoRaiz = catalogoManager.getCatalogoRaiz();
            for (CategoriaComponent componente : catalogoRaiz.getHijos()) {
                if (!componente.esHoja()) {
                    areaCombo.addItem(componente.getNombre());
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error al cargar áreas para el diálogo: {0}", e.getMessage());
            areaCombo.addItem("Sin clasificar");
        }
        
        panel.add(areaCombo);
        
        panel.add(new JLabel("Precio:"));
        JSpinner precioSpinner = new JSpinner(new SpinnerNumberModel(10.0, 0.0, 10000.0, 0.01));
        panel.add(precioSpinner);
        
        panel.add(new JLabel("Unidades disponibles:"));
        JSpinner unidadesSpinner = new JSpinner(new SpinnerNumberModel(10, 0, 10000, 1));
        panel.add(unidadesSpinner);
        
        panel.add(new JLabel("Código de barras:"));
        JTextField codigoField = new JTextField();
        panel.add(codigoField);
        
        panel.add(new JLabel("Marca:"));
        JTextField marcaField = new JTextField();
        panel.add(marcaField);
        
        panel.add(new JLabel("Tamaño:"));
        JTextField tamanoField = new JTextField();
        panel.add(tamanoField);
        
        panel.add(new JLabel("Fecha caducidad (YYYY-MM-DD):"));
        JTextField fechaField = new JTextField();
        panel.add(fechaField);
        
        // Mostrar el diálogo
        int result = JOptionPane.showConfirmDialog(this, panel, 
                "Agregar Nuevo Producto", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        // Procesar el resultado
        if (result == JOptionPane.OK_OPTION) {
            try {
                // Validar campos requeridos
                if (nombreField.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, 
                            "El nombre del producto es obligatorio", 
                            "Error de Validación", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Crear el objeto producto
                Producto.ProductoBuilder builder = new Producto.ProductoBuilder()
                        .nombre(nombreField.getText())
                        .descripcion(descripcionField.getText())
                        .precio((Double) precioSpinner.getValue())
                        .unidadesDisponibles((Integer) unidadesSpinner.getValue())
                        .codigoBarras(codigoField.getText())
                        .marca(marcaField.getText())
                        .tamañoNeto(tamanoField.getText());
                
                // Convertir la cadena de fecha a LocalDate si no está vacía
                if (!fechaField.getText().trim().isEmpty()) {
                    try {
                        LocalDate fechaCaducidad = LocalDate.parse(fechaField.getText().trim());
                        builder.fechaCaducidad(fechaCaducidad);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, 
                                "Formato de fecha incorrecto. Use YYYY-MM-DD", 
                                "Error de Validación", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                
                // Obtener el ID del área seleccionada
                int areaId = areaCombo.getSelectedIndex() + 1; // Asumiendo que empiezan en 1
                builder.areaID(areaId);
                
                // Establecer el nombre del área
                builder.nombreArea((String) areaCombo.getSelectedItem());
                
                // Crear el producto
                Producto nuevoProducto = builder.build();
                
                // Agregar el producto al catálogo
                boolean exito = catalogoManager.agregarProducto(nuevoProducto, areaId);
                
                if (exito) {
                    JOptionPane.showMessageDialog(this, 
                            "Producto agregado correctamente", 
                            "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    
                    // Recargar el catálogo
                    recargarCatalogo();
                } else {
                    JOptionPane.showMessageDialog(this, 
                            "No se pudo agregar el producto", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error al agregar producto: {0}", e.getMessage());
                JOptionPane.showMessageDialog(this, 
                        "Error al agregar producto: " + e.getMessage(), 
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Envía una notificación para un producto específico.
     * 
     * @param producto Producto para notificar
     */
    private void notificarProducto(ProductoLeaf producto) {
        if (producto == null) {
            return;
        }
        
        // Cambiar a la pestaña de notificaciones
        tabbedPane.setSelectedIndex(1);
        
        // Configurar los campos para el producto
        String subject = "Información de Producto: " + producto.getNombre();
        asuntoField.setText(subject);
        
        // Crear contenido de la notificación
        StringBuilder contenido = new StringBuilder();
        contenido.append("Información del producto:\n\n");
        contenido.append("Nombre: ").append(producto.getNombre()).append("\n");
        contenido.append("Descripción: ").append(producto.getDescripcion()).append("\n");
        contenido.append("Precio: $").append(String.format("%.2f", producto.getPrecio())).append("\n");
        contenido.append("Unidades disponibles: ").append(producto.getUnidadesDisponibles()).append("\n");
        contenido.append("Valor inventario: $").append(String.format("%.2f", producto.calcularValorInventario())).append("\n");
        
        // Agregar información adicional si está disponible
        Producto prod = producto.getProducto();
        if (prod != null) {
            if (prod.getMarca() != null && !prod.getMarca().isEmpty()) {
                contenido.append("Marca: ").append(prod.getMarca()).append("\n");
            }
            
            if (prod.getNombreArea() != null && !prod.getNombreArea().isEmpty()) {
                contenido.append("Área: ").append(prod.getNombreArea()).append("\n");
            }
            
            if (prod.getFechaCaducidad() != null) {
                contenido.append("Fecha de caducidad: ").append(prod.getFechaCaducidad()).append("\n");
            }
        }
        
        mensajeArea.setText(contenido.toString());
        
        // Indicar que la notificación está lista para ser enviada
        actualizarEstado("Notificación preparada para el producto: " + producto.getNombre());
    }
    
    /**
     * Prueba la conexión del sistema de notificaciones.
     */
    private void probarConexionNotificacion() {
        try {
            String tipoSeleccionado = (String) tipoNotificacionCombo.getSelectedItem();
            if (tipoSeleccionado == null) {
                JOptionPane.showMessageDialog(this, 
                        "Seleccione un tipo de notificación", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Cambiar el implementador
            notificacionManager.cambiarImplementador("sistema", tipoSeleccionado);
            
            // Obtener la notificación de sistema
            NotificacionSistema notificacion = notificacionManager.getNotificacionSistema();
            
            // Verificar si está listo
            boolean listo = notificacion.getImplementor().estaListo();
            if (listo) {
                JOptionPane.showMessageDialog(this, 
                        "El sistema de notificaciones está correctamente configurado para: " + tipoSeleccionado, 
                        "Prueba de Conexión", JOptionPane.INFORMATION_MESSAGE);
                actualizarEstado("Sistema de notificaciones listo para: " + tipoSeleccionado);
            } else {
                JOptionPane.showMessageDialog(this, 
                        "El sistema de notificaciones NO está correctamente configurado para: " + tipoSeleccionado, 
                        "Prueba de Conexión", JOptionPane.WARNING_MESSAGE);
                actualizarEstado("Sistema de notificaciones NO listo para: " + tipoSeleccionado);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al probar la conexión: {0}", e.getMessage());
            JOptionPane.showMessageDialog(this, 
                    "Error al probar la conexión: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Muestra un diálogo con plantillas de notificación predefinidas.
     */
    private void mostrarPlantillasNotificacion() {
        // Crear las opciones de plantillas
        String[] opciones = {
            "Notificación de Stock Bajo",
            "Notificación de Nuevo Producto",
            "Notificación de Informe de Inventario",
            "Notificación de Caducidad Próxima",
            "Notificación de Mantenimiento del Sistema"
        };
        
        // Mostrar el diálogo de selección
        String seleccion = (String) JOptionPane.showInputDialog(this, 
                "Seleccione una plantilla:", 
                "Plantillas de Notificación", 
                JOptionPane.QUESTION_MESSAGE, 
                null, 
                opciones, 
                opciones[0]);
        
        // Procesar la selección
        if (seleccion != null) {
            switch (seleccion) {
                case "Notificación de Stock Bajo":
                    asuntoField.setText("Alerta de Stock Bajo");
                    mensajeArea.setText(
                            "Estimado Administrador,\n\n" +
                            "Le informamos que los siguientes productos están por debajo del nivel mínimo de stock:\n\n" +
                            "- [Nombre del Producto 1] - Stock actual: [X] unidades - Nivel mínimo: [Y] unidades\n" +
                            "- [Nombre del Producto 2] - Stock actual: [X] unidades - Nivel mínimo: [Y] unidades\n\n" +
                            "Por favor, considere realizar un pedido para reponer el inventario.\n\n" +
                            "Saludos cordiales,\n" +
                            "Sistema de Gestión de Inventario"
                    );
                    break;
                case "Notificación de Nuevo Producto":
                    asuntoField.setText("Nuevo Producto Agregado al Catálogo");
                    mensajeArea.setText(
                            "Estimado Administrador,\n\n" +
                            "Le informamos que se ha agregado un nuevo producto al catálogo:\n\n" +
                            "Nombre: [Nombre del Producto]\n" +
                            "Descripción: [Descripción]\n" +
                            "Precio: $[Precio]\n" +
                            "Stock inicial: [Unidades] unidades\n" +
                            "Área: [Nombre del Área]\n\n" +
                            "El producto ya está disponible en el sistema.\n\n" +
                            "Saludos cordiales,\n" +
                            "Sistema de Gestión de Inventario"
                    );
                    break;
                case "Notificación de Informe de Inventario":
                    asuntoField.setText("Informe de Inventario - " + new Date().toString());
                    mensajeArea.setText(
                            "Estimado Administrador,\n\n" +
                            "Adjunto encontrará el informe de inventario con fecha [Fecha Actual].\n\n" +
                            "Resumen:\n" +
                            "- Total de productos: [Número de Productos]\n" +
                            "- Valor total del inventario: $[Valor Total]\n" +
                            "- Productos con stock bajo: [Número de Productos]\n\n" +
                            "Para más detalles, consulte el informe adjunto o el sistema de gestión.\n\n" +
                            "Saludos cordiales,\n" +
                            "Sistema de Gestión de Inventario"
                    );
                    break;
                case "Notificación de Caducidad Próxima":
                    asuntoField.setText("Alerta de Productos Próximos a Caducar");
                    mensajeArea.setText(
                            "Estimado Administrador,\n\n" +
                            "Le informamos que los siguientes productos están próximos a caducar:\n\n" +
                            "- [Nombre del Producto 1] - Fecha de caducidad: [Fecha]\n" +
                            "- [Nombre del Producto 2] - Fecha de caducidad: [Fecha]\n\n" +
                            "Por favor, tome las medidas necesarias para gestionar estos productos.\n\n" +
                            "Saludos cordiales,\n" +
                            "Sistema de Gestión de Inventario"
                    );
                    break;
                case "Notificación de Mantenimiento del Sistema":
                    asuntoField.setText("Mantenimiento Programado del Sistema");
                    mensajeArea.setText(
                            "Estimado Usuario,\n\n" +
                            "Le informamos que se realizará un mantenimiento programado del sistema de gestión de inventario el día [Fecha] desde las [Hora Inicio] hasta las [Hora Fin].\n\n" +
                            "Durante este período, el sistema no estará disponible. Le recomendamos planificar sus actividades teniendo en cuenta esta interrupción.\n\n" +
                            "Disculpe las molestias.\n\n" +
                            "Saludos cordiales,\n" +
                            "Equipo de Sistemas"
                    );
                    break;
            }
            
            actualizarEstado("Plantilla '" + seleccion + "' cargada");
        }
    }
    
    /**
     * Limpia los campos del formulario de notificación.
     */
    private void limpiarFormularioNotificacion() {
        asuntoField.setText("");
        mensajeArea.setText("");
        actualizarEstado("Formulario de notificación limpiado");
    }
    
    /**
     * Envía una notificación con los datos del formulario.
     */
    private void enviarNotificacion() {
        try {
            // Validar campos
            String destinatario = (String) destinatarioCombo.getSelectedItem();
            String asunto = asuntoField.getText().trim();
            String mensaje = mensajeArea.getText().trim();
            
            if (destinatario == null || destinatario.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                        "Seleccione un destinatario", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (asunto.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                        "El asunto es obligatorio", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (mensaje.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                        "El mensaje es obligatorio", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Obtener el tipo de notificación seleccionado
            String tipoSeleccionado = (String) tipoNotificacionCombo.getSelectedItem();
            if (tipoSeleccionado == null) {
                JOptionPane.showMessageDialog(this, 
                        "Seleccione un tipo de notificación", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Cambiar el implementador
            notificacionManager.cambiarImplementador("sistema", tipoSeleccionado);
            
            // Obtener la notificación de sistema
            NotificacionSistema notificacion = notificacionManager.getNotificacionSistema();
            
            // Enviar la notificación
            boolean exito = notificacion.enviar(destinatario, asunto + "\n\n" + mensaje);
            
            if (exito) {
                JOptionPane.showMessageDialog(this, 
                        "Notificación enviada correctamente", 
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                actualizarEstado("Notificación enviada correctamente a: " + destinatario);
                
                // Limpiar el formulario
                limpiarFormularioNotificacion();
            } else {
                JOptionPane.showMessageDialog(this, 
                        "No se pudo enviar la notificación", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                actualizarEstado("Error al enviar la notificación");
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al enviar la notificación: {0}", e.getMessage());
            JOptionPane.showMessageDialog(this, 
                    "Error al enviar la notificación: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Verifica los productos con stock bajo y envía notificaciones.
     */
    private void verificarStockBajo() {
        try {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            actualizarEstado("Verificando productos con stock bajo...");
            
            int count = integrador.verificarYNotificarStockBajo();
            
            if (count > 0) {
                JOptionPane.showMessageDialog(this, 
                        "Se encontraron " + count + " productos con stock bajo.\n" +
                        "Se ha enviado una notificación al administrador.", 
                        "Stock Bajo", JOptionPane.WARNING_MESSAGE);
                actualizarEstado("Notificación enviada por " + count + " productos con stock bajo");
            } else {
                JOptionPane.showMessageDialog(this, 
                        "No se encontraron productos con stock bajo.", 
                        "Stock Adecuado", JOptionPane.INFORMATION_MESSAGE);
                actualizarEstado("No hay productos con stock bajo");
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al verificar stock bajo: {0}", e.getMessage());
            JOptionPane.showMessageDialog(this, 
                    "Error al verificar stock bajo: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            setCursor(Cursor.getDefaultCursor());
        }
    }
    
    /**
     * Notifica el valor total del inventario.
     */
    private void notificarValorInventario() {
        try {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            actualizarEstado("Notificando valor del inventario...");
            
            boolean exito = integrador.notificarValorInventario();
            
            if (exito) {
                JOptionPane.showMessageDialog(this, 
                        "Se ha enviado una notificación con el valor total del inventario al administrador.", 
                        "Notificación Enviada", JOptionPane.INFORMATION_MESSAGE);
                actualizarEstado("Notificación del valor del inventario enviada");
            } else {
                JOptionPane.showMessageDialog(this, 
                        "No se pudo enviar la notificación del valor del inventario.", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                actualizarEstado("Error al enviar la notificación del valor del inventario");
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al notificar valor del inventario: {0}", e.getMessage());
            JOptionPane.showMessageDialog(this, 
                    "Error al notificar valor del inventario: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            setCursor(Cursor.getDefaultCursor());
        }
    }
    
    /**
     * Simula la adición de un nuevo producto con notificación automática.
     */
    private void simularAgregarProducto() {
        try {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            actualizarEstado("Simulando agregar producto...");
            
            // Crear un producto de ejemplo
            Producto nuevoProducto = new Producto.ProductoBuilder()
                    .nombre("Producto Demo " + System.currentTimeMillis())
                    .descripcion("Producto de demostración para el sistema integrado")
                    .precio(Math.round(Math.random() * 100 * 100) / 100.0) // Precio aleatorio
                    .unidadesDisponibles((int) (Math.random() * 50) + 1) // Stock aleatorio
                    .areaID(1) // Área fija para el ejemplo
                    .nombreArea("Lácteos") // Nombre de área fijo para el ejemplo
                    .fechaCaducidad(LocalDate.now().plusMonths(6)) // Fecha de caducidad en 6 meses
                    .marca("Marca Demo")
                    .tamañoNeto("1 kg")
                    .contenido("Contenido de demostración")
                    .codigoBarras("DEMO" + System.currentTimeMillis())
                    .build();
            
            // Asignar un ID al producto (solo para la demostración)
            try {
                java.lang.reflect.Field field = nuevoProducto.getClass().getDeclaredField("productoID");
                field.setAccessible(true);
                field.set(nuevoProducto, (int) (Math.random() * 1000) + 1000); // ID aleatorio
            } catch (Exception e) {
                LOGGER.log(Level.WARNING, "No se pudo asignar ID al producto: {0}", e.getMessage());
            }
            
            // Notificar la adición del producto
            boolean exito = integrador.notificarProductoAgregado(nuevoProducto, 1);
            
            if (exito) {
                JOptionPane.showMessageDialog(this, 
                        "Se ha simulado la adición del producto '" + nuevoProducto.getNombre() + "'\n" +
                        "y se ha enviado una notificación al administrador.", 
                        "Simulación Exitosa", JOptionPane.INFORMATION_MESSAGE);
                actualizarEstado("Simulación completada y notificación enviada");
            } else {
                JOptionPane.showMessageDialog(this, 
                        "Se ha simulado la adición del producto pero no se pudo enviar la notificación.", 
                        "Simulación Parcial", JOptionPane.WARNING_MESSAGE);
                actualizarEstado("Simulación completada pero la notificación falló");
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al simular la adición de producto: {0}", e.getMessage());
            JOptionPane.showMessageDialog(this, 
                    "Error al simular la adición de producto: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            setCursor(Cursor.getDefaultCursor());
        }
    }
    
    /**
     * Genera un reporte del catálogo y lo notifica.
     */
    private void generarReporteCatalogo() {
        try {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            actualizarEstado("Generando reporte del catálogo...");
            
            // Obtener datos para el reporte
            CategoriaComposite catalogoRaiz = catalogoManager.getCatalogoRaiz();
            List<ProductoLeaf> todosProductos = catalogoManager.obtenerTodosLosProductos();
            double valorTotal = catalogoManager.calcularValorTotalInventario();
            
            // Generar el reporte
            StringBuilder reporte = new StringBuilder();
            reporte.append("REPORTE DE CATÁLOGO DE PRODUCTOS\n");
            reporte.append("===============================\n\n");
            reporte.append("Fecha del reporte: ").append(new Date()).append("\n\n");
            reporte.append("RESUMEN:\n");
            reporte.append("- Total de productos: ").append(todosProductos.size()).append("\n");
            reporte.append("- Valor total del inventario: $").append(String.format("%.2f", valorTotal)).append("\n");
            reporte.append("- Categorías: ").append(catalogoRaiz.getHijos().size()).append("\n\n");
            
            // Detalles por categoría
            reporte.append("DETALLE POR CATEGORÍA:\n");
            for (CategoriaComponent componente : catalogoRaiz.getHijos()) {
                if (!componente.esHoja()) {
                    CategoriaComposite categoria = (CategoriaComposite) componente;
                    List<ProductoLeaf> productosCategoria = categoria.obtenerTodosLosProductos();
                    double valorCategoria = categoria.calcularValorInventario();
                    
                    reporte.append("\n").append(categoria.getNombre()).append(":\n");
                    reporte.append("- Productos: ").append(productosCategoria.size()).append("\n");
                    reporte.append("- Valor: $").append(String.format("%.2f", valorCategoria)).append("\n");
                    reporte.append("- Detalle de productos:\n");
                    
                    for (ProductoLeaf producto : productosCategoria) {
                        reporte.append("  * ").append(producto.getNombre())
                                .append(" - $").append(String.format("%.2f", producto.getPrecio()))
                                .append(" - Stock: ").append(producto.getUnidadesDisponibles())
                                .append("\n");
                    }
                }
            }
            
            // Verificar productos con stock bajo
            reporte.append("\nPRODUCTOS CON STOCK BAJO:\n");
            boolean hayStockBajo = false;
            for (ProductoLeaf producto : todosProductos) {
                if (producto.getUnidadesDisponibles() <= integrador.getUmbralStockBajo()) {
                    reporte.append("- ").append(producto.getNombre())
                            .append(" - Stock actual: ").append(producto.getUnidadesDisponibles())
                            .append(" - Umbral: ").append(integrador.getUmbralStockBajo())
                            .append("\n");
                    hayStockBajo = true;
                }
            }
            
            if (!hayStockBajo) {
                reporte.append("No hay productos con stock bajo.\n");
            }
            
            // Mostrar el reporte en un diálogo
            JTextArea reporteArea = new JTextArea(reporte.toString());
            reporteArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(reporteArea);
            scrollPane.setPreferredSize(new Dimension(600, 400));
            
            JOptionPane.showMessageDialog(this, scrollPane, 
                    "Reporte de Catálogo", JOptionPane.INFORMATION_MESSAGE);
            
            // Preguntar si desea enviar el reporte por correo
            int option = JOptionPane.showConfirmDialog(this, 
                    "¿Desea enviar este reporte como notificación al administrador?", 
                    "Enviar Reporte", JOptionPane.YES_NO_OPTION);
            
            if (option == JOptionPane.YES_OPTION) {
                // Cambiar a la pestaña de notificaciones
                tabbedPane.setSelectedIndex(1);
                
                // Configurar los campos para el reporte
                asuntoField.setText("Reporte de Catálogo - " + new Date());
                mensajeArea.setText(reporte.toString());
                
                actualizarEstado("Reporte preparado para enviar como notificación");
            } else {
                actualizarEstado("Reporte generado (no enviado)");
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al generar reporte: {0}", e.getMessage());
            JOptionPane.showMessageDialog(this, 
                    "Error al generar reporte: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            setCursor(Cursor.getDefaultCursor());
        }
    }
    
    /**
     * Actualiza el mensaje de estado en la barra inferior.
     * 
     * @param mensaje Mensaje a mostrar
     */
    private void actualizarEstado(String mensaje) {
        statusLabel.setText("Estado: " + mensaje);
    }
    
    /**
     * Muestra información introductoria en el área de detalles.
     */
    private void mostrarInformacion() {
        StringBuilder sb = new StringBuilder();
        sb.append("SISTEMA INTEGRADO DE GESTIÓN DE INVENTARIO\n");
        sb.append("=========================================\n\n");
        sb.append("Este sistema demuestra la integración de dos patrones de diseño importantes:\n\n");
        sb.append("1. PATRÓN COMPOSITE (Catálogo de Productos)\n");
        sb.append("   Permite organizar productos en categorías jerárquicas, facilitando\n");
        sb.append("   la gestión y visualización del catálogo.\n\n");
        sb.append("2. PATRÓN BRIDGE (Sistema de Notificaciones)\n");
        sb.append("   Separa la abstracción de las notificaciones de su implementación,\n");
        sb.append("   permitiendo cambiar fácilmente el medio de notificación (email, SMS, etc.).\n\n");
        sb.append("3. INTEGRACIÓN DE AMBOS PATRONES\n");
        sb.append("   Combina ambos patrones para proporcionar funcionalidades avanzadas como\n");
        sb.append("   notificaciones automáticas para productos con stock bajo, informes de inventario, etc.\n\n");
        sb.append("Explore las diferentes pestañas para ver cada patrón en acción y su integración.\n");
        
        detallesArea.setText(sb.toString());
        detallesArea.setCaretPosition(0);
    }
    
    /**
     * Método para cerrar recursos al cerrar la ventana.
     */
    @Override
    public void dispose() {
        // Cerrar la conexión a la base de datos
        if (catalogoManager != null) {
            catalogoManager.cerrarConexion();
        }
        
        super.dispose();
    }
    
    /**
     * Método principal para ejecutar la aplicación integrada.
     * 
     * @param args Argumentos de línea de comandos
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Establecer el Look and Feel del sistema
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                LOGGER.log(Level.WARNING, "No se pudo establecer el Look and Feel del sistema", e);
            }
            
            try {
                GestorIntegrado app = new GestorIntegrado();
                app.setVisible(true);
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error al iniciar la aplicación", e);
                JOptionPane.showMessageDialog(null, 
                        "Error al iniciar la aplicación: " + e.getMessage(), 
                        "Error Fatal", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}