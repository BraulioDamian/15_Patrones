package PatronComposite;

import DBObjetos.Producto;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.tree.*;

/**
 * Interfaz gráfica para el sistema de catálogos usando el patrón Composite.
 */
public class CatalogoUI extends JFrame {
    
    private static final Logger LOGGER = Logger.getLogger(CatalogoUI.class.getName());
    private final CatalogoManager manager;
    private JTree catalogoTree;
    private JTextField busquedaField;
    private JTextArea infoArea;
    private JPanel productosPanel;
    private JLabel statusLabel;
    
    /**
     * Constructor que inicializa la interfaz.
     */
    public CatalogoUI() {
        // Configurar la ventana
        setTitle("Catálogo de Productos - Patrón Composite");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        
        // Inicializar el gestor de catálogos
        manager = CatalogoManager.getInstance();
        
        // Panel principal con BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panel izquierdo para categorías
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createTitledBorder("Categorías"));
        
        // Árbol de categorías
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Catálogo de Productos");
        DefaultTreeModel treeModel = new DefaultTreeModel(root);
        catalogoTree = new JTree(treeModel);
        catalogoTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        
        // Evento de selección para el árbol
        catalogoTree.addTreeSelectionListener(e -> {
            try {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) 
                        catalogoTree.getLastSelectedPathComponent();
                if (selectedNode != null) {
                    mostrarSeleccion(selectedNode);
                } else {
                    // Limpiar el panel de productos si no hay selección
                    productosPanel.removeAll();
                    productosPanel.revalidate();
                    productosPanel.repaint();
                    
                    // Actualizar mensaje de estado
                    actualizarEstado("No hay categoría seleccionada");
                }
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, "Error al procesar la selección del árbol", ex);
                JOptionPane.showMessageDialog(CatalogoUI.this, 
                        "Error al mostrar los productos: " + ex.getMessage(), 
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        JScrollPane treeScroll = new JScrollPane(catalogoTree);
        leftPanel.add(treeScroll, BorderLayout.CENTER);
        
        // Panel de búsqueda
        JPanel searchPanel = new JPanel(new BorderLayout(5, 0));
        busquedaField = new JTextField();
        JButton buscarButton = new JButton("Buscar");
        searchPanel.add(busquedaField, BorderLayout.CENTER);
        searchPanel.add(buscarButton, BorderLayout.EAST);
        leftPanel.add(searchPanel, BorderLayout.SOUTH);
        
        // Evento de búsqueda
        buscarButton.addActionListener(e -> buscarProductos());
        busquedaField.addActionListener(e -> buscarProductos());
        
        // Panel central para productos
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createTitledBorder("Productos"));
        
        productosPanel = new JPanel();
        productosPanel.setLayout(new BoxLayout(productosPanel, BoxLayout.Y_AXIS));
        JScrollPane productosScroll = new JScrollPane(productosPanel);
        centerPanel.add(productosScroll, BorderLayout.CENTER);
        
        // Panel derecho para información
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createTitledBorder("Detalles"));
        
        infoArea = new JTextArea();
        infoArea.setEditable(false);
        JScrollPane infoScroll = new JScrollPane(infoArea);
        rightPanel.add(infoScroll, BorderLayout.CENTER);
        
        // Panel inferior con botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton recargarButton = new JButton("Recargar Catálogo");
        JButton valorButton = new JButton("Calcular Valor Inventario");
        buttonPanel.add(recargarButton, BorderLayout.WEST);
        buttonPanel.add(valorButton, BorderLayout.EAST);
        
        // Eventos para los botones
        recargarButton.addActionListener(e -> recargarCatalogo());
        valorButton.addActionListener(e -> calcularValorInventario());
        
        // Barra de estado
        statusLabel = new JLabel("Estado: Listo");
        statusLabel.setBorder(BorderFactory.createEtchedBorder());
        
        // Agregar componentes al panel principal
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(rightPanel, BorderLayout.EAST);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Agregar el panel principal y la barra de estado
        add(mainPanel, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);
        
        // Cargar el árbol de categorías al iniciar
        SwingUtilities.invokeLater(this::cargarArbolCategorias);
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
            CategoriaComposite catalogoRaiz = manager.getCatalogoRaiz();
            
            // Configurar un renderer personalizado para que use toString() de los objetos
            catalogoTree.setCellRenderer(new DefaultTreeCellRenderer() {
                @Override
                public Component getTreeCellRendererComponent(JTree tree, Object value, 
                        boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
                    
                    // Usar la implementación estándar para la mayoria de las cosas
                    super.getTreeCellRendererComponent(tree, value, selected, expanded, 
                            leaf, row, hasFocus);
                    
                    // Obtener el texto que queremos mostrar
                    if (value instanceof DefaultMutableTreeNode) {
                        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
                        Object userObject = node.getUserObject();
                        
                        if (userObject instanceof CategoriaComponent) {
                            // Usar el nombre de la categoria o producto
                            CategoriaComponent componente = (CategoriaComponent) userObject;
                            setText(componente.getNombre());
                            
                            // Establecer icono según sea una categoría o un producto
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
            
            // Cargar las categorías
            for (CategoriaComponent componente : catalogoRaiz.getHijos()) {
                if (!componente.esHoja()) {
                    DefaultMutableTreeNode nodo = new DefaultMutableTreeNode(componente);
                    root.add(nodo);
                    cargarProductosCategoria(nodo, (CategoriaComposite) componente);
                }
            }
            
            // Actualizar el modelo
            model.reload();
            
            // Expandir el árbol
            expandirArbol();
            
            // Mostrar mensaje informativo
            String message = "Catálogo cargado con " + catalogoRaiz.contarComponentes() + " componentes";
            actualizarEstado(message);
            mostrarInformacion("CATÁLOGO DE PRODUCTOS", message);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al cargar el árbol de categorías: {0}", e.getMessage());
            JOptionPane.showMessageDialog(this, 
                    "Error al cargar las categorías: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            actualizarEstado("Error al cargar categorías");
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
            LOGGER.log(Level.WARNING, "Error al cargar productos de la categoría {0}: {1}", 
                    new Object[]{categoria.getNombre(), e.getMessage()});
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
                    mostrarProductos(manager.obtenerTodosLosProductos(), "Todos los Productos");
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
        
        // Botón de detalles
        JButton detallesButton = new JButton("Detalles");
        detallesButton.addActionListener(e -> mostrarDetallesProducto(producto));
        
        // Agregar componentes al panel
        panel.add(infoPanel);
        panel.add(Box.createHorizontalGlue());
        panel.add(detallesButton);
        
        return panel;
    }
    
    /**
     * Muestra los detalles de un producto en el área de información.
     * 
     * @param producto Producto a mostrar
     */
    private void mostrarDetallesProducto(ProductoLeaf producto) {
        if (producto == null) {
            infoArea.setText("No hay producto seleccionado");
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
            
            infoArea.setText(sb.toString());
            infoArea.setCaretPosition(0);
            
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error al mostrar detalles del producto", e);
            infoArea.setText("Error al mostrar detalles del producto: " + e.getMessage());
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
     */
    private void buscarProductos() {
        String texto = busquedaField.getText().trim();
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
            if (manager == null) {
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
            List<ProductoLeaf> resultados = manager.buscarProductosPorNombre(texto);
            
            // Mostrar los resultados
            if (resultados.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "No se encontraron productos que coincidan con: " + texto,
                        "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
                // Limpiar el panel de productos
                productosPanel.removeAll();
                productosPanel.revalidate();
                productosPanel.repaint();
            } else {
                mostrarProductos(resultados, "Resultados de búsqueda: " + texto);
            }
            
            actualizarEstado("Búsqueda completada: " + resultados.size() + " resultados");
            
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al buscar productos: " + e.getMessage(), e);
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
            
            manager.recargarCatalogo();
            cargarArbolCategorias();
            
            actualizarEstado("Catálogo recargado correctamente");
            setCursor(Cursor.getDefaultCursor());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al recargar el catálogo", e);
            JOptionPane.showMessageDialog(this, 
                    "Error al recargar el catálogo: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            actualizarEstado("Error al recargar el catálogo");
            setCursor(Cursor.getDefaultCursor());
        }
    }
    
    /**
     * Calcula y muestra el valor total del inventario.
     */
    private void calcularValorInventario() {
        try {
            double valorTotal = manager.calcularValorTotalInventario();
            String mensaje = String.format("Valor total del inventario: $%.2f", valorTotal);
            
            JOptionPane.showMessageDialog(this, mensaje, 
                    "Valor del Inventario", JOptionPane.INFORMATION_MESSAGE);
            
            actualizarEstado(mensaje);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al calcular el valor del inventario", e);
            JOptionPane.showMessageDialog(this, 
                    "Error al calcular el valor del inventario: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            actualizarEstado("Error al calcular el valor del inventario");
        }
    }
    
    /**
     * Muestra información en el área de detalles.
     * 
     * @param titulo Título del mensaje
     * @param mensaje Contenido del mensaje
     */
    private void mostrarInformacion(String titulo, String mensaje) {
        StringBuilder sb = new StringBuilder();
        sb.append(titulo).append("\n");
        sb.append("=".repeat(titulo.length())).append("\n\n");
        sb.append(mensaje).append("\n\n");
        sb.append("PATRÓN COMPOSITE - CATÁLOGO DE PRODUCTOS\n");
        sb.append("=======================================\n\n");
        sb.append("Este sistema demuestra el uso del patrón Composite para\n");
        sb.append("organizar productos en categorías jerárquicas.\n\n");
        sb.append("- Seleccione una categoría en el árbol a la izquierda\n");
        sb.append("- Use el campo de búsqueda para encontrar productos\n");
        sb.append("- Haga clic en \"Detalles\" para ver información completa\n");
        
        infoArea.setText(sb.toString());
        infoArea.setCaretPosition(0);
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
     * Cierra la conexión a la base de datos al cerrar la ventana.
     */
    @Override
    public void dispose() {
        manager.cerrarConexion();
        super.dispose();
    }
    
    /**
     * Método principal para probar la interfaz.
     * 
     * @param args Argumentos de línea de comandos
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                LOGGER.log(Level.WARNING, "No se pudo establecer el Look and Feel del sistema", e);
            }
            new CatalogoUI().setVisible(true);
        });
    }
}