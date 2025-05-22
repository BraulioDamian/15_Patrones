package PatronBridge;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List; // Importación específica para evitar conflictos

/**
 * Interfaz gráfica para el patrón Bridge - Sistema de Notificaciones
 * Permite gestionar y probar diferentes tipos de notificaciones e implementadores.
 */
public class NotificacionBridgeGUI extends JFrame {
    
    private NotificacionManager notificacionManager;
    
    // Componentes principales
    private JComboBox<String> comboTipoNotificacion;
    private JComboBox<String> comboImplementador;
    private JTextField txtDestinatario;
    private JTextArea txtMensaje;
    private JTextArea txtConsola;
    private JLabel lblEstadoSistema;
    private JLabel lblImplementadorActivo;
    
    // Componentes para administradores
    private JCheckBox chkEnvioAutomaticoAdmins;
    private JTextArea txtCorreosAdministradores;
    private JLabel lblCantidadAdmins;
    private JButton btnActualizarCorreosAdmins;
    private JButton btnEnviarAAdmins;
    private JButton btnVerificarCorreosAdmins;
    
    // Componentes para notificaciones específicas de inventario
    private JSpinner spinnerProductoID;
    private JTextField txtNombreProducto;
    private JSpinner spinnerStockActual;
    private JSpinner spinnerStockMinimo;
    private JSpinner spinnerCantidad;
    private JTextField txtFechaCaducidad;
    private JSpinner spinnerDiasRestantes;
    
    // Botones
    private JButton btnCambiarImplementador;
    private JButton btnEnviarNotificacion;
    private JButton btnNotificacionPrueba;
    private JButton btnStockBajo;
    private JButton btnProximoCaducar;
    private JButton btnNuevoProducto;
    private JButton btnLimpiarConsola;
    private JButton btnStockBajoAdmins;
    private JButton btnNuevoProductoAdmins;
    
    public NotificacionBridgeGUI() {
        notificacionManager = NotificacionManager.getInstance();
        initializeComponents();
        setupLayout();
        setupEventListeners();
        actualizarEstadoSistema();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Sistema de Notificaciones - Patrón Bridge");
        setSize(1200, 750); // Aumentado para acomodar el panel de administradores
        setLocationRelativeTo(null);
    }
    
    private void initializeComponents() {
        // Componentes principales
        comboTipoNotificacion = new JComboBox<>(notificacionManager.getNotificacionesDisponibles());
        comboImplementador = new JComboBox<>(notificacionManager.getImplementadoresDisponibles());
        txtDestinatario = new JTextField(20);
        txtMensaje = new JTextArea(4, 20);
        txtConsola = new JTextArea(8, 50);
        lblEstadoSistema = new JLabel("Estado: Inicializando...");
        lblImplementadorActivo = new JLabel("Implementador activo: Email");
        
        // Configurar áreas de texto
        txtMensaje.setLineWrap(true);
        txtMensaje.setWrapStyleWord(true);
        txtConsola.setEditable(false);
        txtConsola.setBackground(Color.BLACK);
        txtConsola.setForeground(Color.GREEN);
        txtConsola.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 11));
        
        // Componentes para inventario
        spinnerProductoID = new JSpinner(new SpinnerNumberModel(1, 1, 9999, 1));
        txtNombreProducto = new JTextField(15);
        spinnerStockActual = new JSpinner(new SpinnerNumberModel(0, 0, 9999, 1));
        spinnerStockMinimo = new JSpinner(new SpinnerNumberModel(10, 1, 100, 1));
        spinnerCantidad = new JSpinner(new SpinnerNumberModel(1, 1, 999, 1));
        txtFechaCaducidad = new JTextField(10);
        spinnerDiasRestantes = new JSpinner(new SpinnerNumberModel(30, 1, 365, 1));
        
        // Establecer fecha actual por defecto
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        txtFechaCaducidad.setText(sdf.format(new Date()));
        
        // Componentes para administradores
        chkEnvioAutomaticoAdmins = new JCheckBox("Envío automático a administradores", true);
        txtCorreosAdministradores = new JTextArea(4, 30);
        txtCorreosAdministradores.setEditable(false);
        txtCorreosAdministradores.setBackground(new Color(245, 245, 245));
        txtCorreosAdministradores.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 11));
        lblCantidadAdmins = new JLabel("Administradores: 0");
        btnActualizarCorreosAdmins = new JButton("Actualizar Correos");
        btnEnviarAAdmins = new JButton("Enviar a Administradores");
        btnVerificarCorreosAdmins = new JButton("Verificar Estado");
        btnStockBajoAdmins = new JButton("Stock Bajo → Admins");
        btnNuevoProductoAdmins = new JButton("Nuevo Producto → Admins");
        
        // Botones
        btnCambiarImplementador = new JButton("Cambiar Implementador");
        btnEnviarNotificacion = new JButton("Enviar Notificación");
        btnNotificacionPrueba = new JButton("Enviar Prueba");
        btnStockBajo = new JButton("Notificar Stock Bajo");
        btnProximoCaducar = new JButton("Producto Próximo a Caducar");
        btnNuevoProducto = new JButton("Nuevo Producto");
        btnLimpiarConsola = new JButton("Limpiar Consola");
        
        // Estilos de botones
        styleButton(btnCambiarImplementador, new Color(52, 152, 219));
        styleButton(btnEnviarNotificacion, new Color(46, 204, 113));
        styleButton(btnNotificacionPrueba, new Color(155, 89, 182));
        styleButton(btnStockBajo, new Color(231, 76, 60));
        styleButton(btnProximoCaducar, new Color(230, 126, 34));
        styleButton(btnNuevoProducto, new Color(26, 188, 156));
        styleButton(btnLimpiarConsola, new Color(149, 165, 166));
        
        // Estilos para botones de administradores
        styleButton(btnActualizarCorreosAdmins, new Color(41, 128, 185));
        styleButton(btnEnviarAAdmins, new Color(22, 160, 133));
        styleButton(btnVerificarCorreosAdmins, new Color(142, 68, 173));
        styleButton(btnStockBajoAdmins, new Color(192, 57, 43));
        styleButton(btnNuevoProductoAdmins, new Color(39, 174, 96));
        
        // Configurar checkbox
        chkEnvioAutomaticoAdmins.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 11));
        chkEnvioAutomaticoAdmins.setForeground(new Color(52, 152, 219));
    }
    
    private void styleButton(JButton button, Color color) {
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 11));
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));
        
        // Panel principal con márgenes
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        // Panel superior - Configuración del sistema
        JPanel configPanel = createConfigPanel();
        mainPanel.add(configPanel, BorderLayout.NORTH);
        
        // Panel central - Funcionalidades
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        
        // Panel superior del centro - Notificaciones generales y administradores
        JPanel upperCenterPanel = new JPanel(new BorderLayout(10, 10));
        
        // Panel de notificaciones generales
        JPanel notificationPanel = createNotificationPanel();
        upperCenterPanel.add(notificationPanel, BorderLayout.WEST);
        
        // Panel de administradores
        JPanel adminPanel = createAdminPanel();
        upperCenterPanel.add(adminPanel, BorderLayout.EAST);
        
        centerPanel.add(upperCenterPanel, BorderLayout.NORTH);
        
        // Panel de inventario
        JPanel inventoryPanel = createInventoryPanel();
        centerPanel.add(inventoryPanel, BorderLayout.CENTER);
        
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        
        // Panel inferior - Consola
        JPanel consolePanel = createConsolePanel();
        mainPanel.add(consolePanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private JPanel createConfigPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new TitledBorder("Configuración del Sistema"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Estado del sistema
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        lblEstadoSistema.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        lblEstadoSistema.setForeground(new Color(46, 204, 113));
        panel.add(lblEstadoSistema, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        lblImplementadorActivo.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 11));
        panel.add(lblImplementadorActivo, gbc);
        
        // Selección de implementador
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 1;
        panel.add(new JLabel("Tipo de Notificación:"), gbc);
        gbc.gridx = 1;
        panel.add(comboTipoNotificacion, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Implementador:"), gbc);
        gbc.gridx = 1;
        panel.add(comboImplementador, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        panel.add(btnCambiarImplementador, gbc);
        
        return panel;
    }
    
    private JPanel createAdminPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(new TitledBorder("Sistema de Administradores"));
        panel.setPreferredSize(new Dimension(350, 0)); // Ancho fijo
        
        // Panel superior - checkbox y estado
        JPanel topPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 3, 3, 3);
        
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.WEST;
        topPanel.add(chkEnvioAutomaticoAdmins, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        lblCantidadAdmins.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 11));
        lblCantidadAdmins.setForeground(new Color(52, 152, 219));
        topPanel.add(lblCantidadAdmins, gbc);
        
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.EAST;
        topPanel.add(btnActualizarCorreosAdmins, gbc);
        
        panel.add(topPanel, BorderLayout.NORTH);
        
        // Panel central - lista de correos
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBorder(new TitledBorder("Correos de Administradores"));
        
        JScrollPane scrollCorreos = new JScrollPane(txtCorreosAdministradores);
        scrollCorreos.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        centerPanel.add(scrollCorreos, BorderLayout.CENTER);
        
        panel.add(centerPanel, BorderLayout.CENTER);
        
        // Panel inferior - botones de acción
        JPanel bottomPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        bottomPanel.add(btnEnviarAAdmins);
        bottomPanel.add(btnVerificarCorreosAdmins);
        bottomPanel.add(btnStockBajoAdmins);
        bottomPanel.add(btnNuevoProductoAdmins);
        
        panel.add(bottomPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createNotificationPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new TitledBorder("Notificaciones Generales"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Destinatario:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(txtDestinatario, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE;
        panel.add(new JLabel("Mensaje:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.BOTH;
        panel.add(new JScrollPane(txtMensaje), gbc);
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(btnEnviarNotificacion);
        buttonPanel.add(btnNotificacionPrueba);
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(buttonPanel, gbc);
        
        return panel;
    }
    
    private JPanel createInventoryPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new TitledBorder("Notificaciones de Inventario"));
        
        // Panel de datos del producto
        JPanel productPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 3, 3, 3);
        
        gbc.gridx = 0; gbc.gridy = 0;
        productPanel.add(new JLabel("ID Producto:"), gbc);
        gbc.gridx = 1;
        productPanel.add(spinnerProductoID, gbc);
        
        gbc.gridx = 2;
        productPanel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 3; gbc.fill = GridBagConstraints.HORIZONTAL;
        productPanel.add(txtNombreProducto, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE;
        productPanel.add(new JLabel("Stock Actual:"), gbc);
        gbc.gridx = 1;
        productPanel.add(spinnerStockActual, gbc);
        
        gbc.gridx = 2;
        productPanel.add(new JLabel("Stock Mínimo:"), gbc);
        gbc.gridx = 3;
        productPanel.add(spinnerStockMinimo, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        productPanel.add(new JLabel("Cantidad:"), gbc);
        gbc.gridx = 1;
        productPanel.add(spinnerCantidad, gbc);
        
        gbc.gridx = 2;
        productPanel.add(new JLabel("Fecha Caducidad:"), gbc);
        gbc.gridx = 3; gbc.fill = GridBagConstraints.HORIZONTAL;
        productPanel.add(txtFechaCaducidad, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        productPanel.add(new JLabel("Días Restantes:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.NONE;
        productPanel.add(spinnerDiasRestantes, gbc);
        
        panel.add(productPanel, BorderLayout.CENTER);
        
        // Panel de botones de inventario
        JPanel inventoryButtonPanel = new JPanel(new FlowLayout());
        inventoryButtonPanel.add(btnStockBajo);
        inventoryButtonPanel.add(btnProximoCaducar);
        inventoryButtonPanel.add(btnNuevoProducto);
        
        panel.add(inventoryButtonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createConsolePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new TitledBorder("Consola de Eventos"));
        
        txtConsola.append("=== Sistema de Notificaciones Iniciado ===\\n");
        txtConsola.append("Patrón Bridge implementado correctamente\\n");
        txtConsola.append("Implementadores disponibles: Email, SMS, Alerta\\n");
        txtConsola.append("Envío automático a administradores: ACTIVADO\\n");
        txtConsola.append("======================================\\n\\n");
        
        JScrollPane scrollPane = new JScrollPane(txtConsola);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(btnLimpiarConsola);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void setupEventListeners() {
        btnCambiarImplementador.addActionListener(e -> cambiarImplementador());
        btnEnviarNotificacion.addActionListener(e -> enviarNotificacion());
        btnNotificacionPrueba.addActionListener(e -> enviarNotificacionPrueba());
        btnStockBajo.addActionListener(e -> notificarStockBajo());
        btnProximoCaducar.addActionListener(e -> notificarProximoCaducar());
        btnNuevoProducto.addActionListener(e -> notificarNuevoProducto());
        btnLimpiarConsola.addActionListener(e -> limpiarConsola());
        
        // Event listeners para administradores
        chkEnvioAutomaticoAdmins.addActionListener(e -> toggleEnvioAutomaticoAdmins());
        btnActualizarCorreosAdmins.addActionListener(e -> actualizarCorreosAdministradores());
        btnEnviarAAdmins.addActionListener(e -> enviarNotificacionAAdministradores());
        btnVerificarCorreosAdmins.addActionListener(e -> verificarEstadoAdministradores());
        btnStockBajoAdmins.addActionListener(e -> notificarStockBajoAAdministradores());
        btnNuevoProductoAdmins.addActionListener(e -> notificarNuevoProductoAAdministradores());
        
        // Actualizar destinatario según el implementador
        comboImplementador.addActionListener(e -> actualizarEjemploDestinatario());
    }
    
    private void cambiarImplementador() {
        String tipoNotificacion = (String) comboTipoNotificacion.getSelectedItem();
        String implementador = (String) comboImplementador.getSelectedItem();
        
        if (tipoNotificacion != null && implementador != null) {
            boolean exito = notificacionManager.cambiarImplementador(tipoNotificacion, implementador);
            
            if (exito) {
                logConsola("✓ Implementador cambiado: " + tipoNotificacion + " -> " + implementador);
                actualizarEstadoSistema();
                JOptionPane.showMessageDialog(this, 
                    "Implementador cambiado exitosamente\\n" +
                    "Tipo: " + tipoNotificacion + "\\n" +
                    "Implementador: " + implementador,
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                logConsola("✗ Error al cambiar implementador");
                JOptionPane.showMessageDialog(this, 
                    "Error al cambiar el implementador",
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void enviarNotificacion() {
        String destinatario = txtDestinatario.getText().trim();
        String mensaje = txtMensaje.getText().trim();
        String tipoNotificacion = (String) comboTipoNotificacion.getSelectedItem();
        
        if (destinatario.isEmpty() || mensaje.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, complete el destinatario y el mensaje",
                "Campos Requeridos", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            boolean exito = false;
            switch (tipoNotificacion) {
                case "sistema":
                    exito = notificacionManager.getNotificacionSistema().enviar(destinatario, mensaje);
                    break;
                case "inventario":
                    exito = notificacionManager.getNotificacionInventario().enviar(destinatario, mensaje);
                    break;
                case "venta":
                    exito = notificacionManager.getNotificacionVenta().enviar(destinatario, mensaje);
                    break;
            }
            
            if (exito) {
                logConsola("✓ Notificación " + tipoNotificacion + " enviada a: " + destinatario);
                JOptionPane.showMessageDialog(this, 
                    "Notificación enviada exitosamente",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                logConsola("✗ Error al enviar notificación " + tipoNotificacion);
                JOptionPane.showMessageDialog(this, 
                    "Error al enviar la notificación",
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            logConsola("✗ Excepción al enviar notificación: " + ex.getMessage());
            JOptionPane.showMessageDialog(this, 
                "Error inesperado: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void enviarNotificacionPrueba() {
        String destinatario = txtDestinatario.getText().trim();
        String tipoNotificacion = (String) comboTipoNotificacion.getSelectedItem();
        
        if (destinatario.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, ingrese un destinatario",
                "Campo Requerido", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        boolean exito = notificacionManager.enviarNotificacionPrueba(tipoNotificacion, destinatario);
        
        if (exito) {
            logConsola("✓ Notificación de prueba (" + tipoNotificacion + ") enviada a: " + destinatario);
            JOptionPane.showMessageDialog(this, 
                "Notificación de prueba enviada exitosamente",
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            logConsola("✗ Error al enviar notificación de prueba");
            JOptionPane.showMessageDialog(this, 
                "Error al enviar la notificación de prueba",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void notificarStockBajo() {
        String destinatario = txtDestinatario.getText().trim();
        if (destinatario.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un destinatario", 
                "Campo Requerido", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int productoID = (Integer) spinnerProductoID.getValue();
        String nombre = txtNombreProducto.getText().trim();
        int stockActual = (Integer) spinnerStockActual.getValue();
        int stockMinimo = (Integer) spinnerStockMinimo.getValue();
        
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese el nombre del producto", 
                "Campo Requerido", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        boolean exito = notificacionManager.getNotificacionInventario()
            .notificarStockBajo(destinatario, productoID, nombre, stockActual, stockMinimo);
        
        if (exito) {
            logConsola("✓ Notificación de stock bajo enviada - Producto: " + nombre + " (" + stockActual + " unidades)");
            JOptionPane.showMessageDialog(this, 
                "Notificación de stock bajo enviada exitosamente",
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            logConsola("✗ Error al enviar notificación de stock bajo");
            JOptionPane.showMessageDialog(this, 
                "Error al enviar la notificación de stock bajo",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void notificarProximoCaducar() {
        String destinatario = txtDestinatario.getText().trim();
        if (destinatario.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un destinatario", 
                "Campo Requerido", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int productoID = (Integer) spinnerProductoID.getValue();
        String nombre = txtNombreProducto.getText().trim();
        String fechaCaducidad = txtFechaCaducidad.getText().trim();
        int diasRestantes = (Integer) spinnerDiasRestantes.getValue();
        
        if (nombre.isEmpty() || fechaCaducidad.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos", 
                "Campos Requeridos", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        boolean exito = notificacionManager.getNotificacionInventario()
            .notificarProximoACaducar(destinatario, productoID, nombre, fechaCaducidad, diasRestantes);
        
        if (exito) {
            logConsola("✓ Notificación de próximo a caducar enviada - Producto: " + nombre + " (" + diasRestantes + " días)");
            JOptionPane.showMessageDialog(this, 
                "Notificación de producto próximo a caducar enviada exitosamente",
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            logConsola("✗ Error al enviar notificación de próximo a caducar");
            JOptionPane.showMessageDialog(this, 
                "Error al enviar la notificación",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void notificarNuevoProducto() {
        String destinatario = txtDestinatario.getText().trim();
        if (destinatario.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un destinatario", 
                "Campo Requerido", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int productoID = (Integer) spinnerProductoID.getValue();
        String nombre = txtNombreProducto.getText().trim();
        int cantidad = (Integer) spinnerCantidad.getValue();
        
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese el nombre del producto", 
                "Campo Requerido", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fecha = sdf.format(new Date());
        
        boolean exito = notificacionManager.getNotificacionInventario()
            .notificarNuevoProducto(destinatario, productoID, nombre, cantidad, fecha);
        
        if (exito) {
            logConsola("✓ Notificación de nuevo producto enviada - Producto: " + nombre + " (" + cantidad + " unidades)");
            JOptionPane.showMessageDialog(this, 
                "Notificación de nuevo producto enviada exitosamente",
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            logConsola("✗ Error al enviar notificación de nuevo producto");
            JOptionPane.showMessageDialog(this, 
                "Error al enviar la notificación",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void limpiarConsola() {
        txtConsola.setText("");
        logConsola("=== Consola limpiada ===");
    }
    
    private void actualizarEstadoSistema() {
        String tipoNotificacion = (String) comboTipoNotificacion.getSelectedItem();
        String implementador = (String) comboImplementador.getSelectedItem();
        
        lblEstadoSistema.setText("Estado: ✓ Sistema Activo");
        lblImplementadorActivo.setText("Implementador activo (" + tipoNotificacion + "): " + implementador);
        
        // Sincronizar checkbox con el estado del NotificationManager
        chkEnvioAutomaticoAdmins.setSelected(notificacionManager.isEnvioAutomaticoAdministradores());
        
        // Actualizar correos de administradores automáticamente
        actualizarCorreosAdministradores();
    }
    
    private void actualizarEjemploDestinatario() {
        String implementador = (String) comboImplementador.getSelectedItem();
        if (implementador != null) {
            switch (implementador) {
                case "email":
                    if (txtDestinatario.getText().isEmpty()) {
                        txtDestinatario.setText("admin@tienda.com");
                    }
                    break;
                case "sms":
                    if (txtDestinatario.getText().isEmpty()) {
                        txtDestinatario.setText("5555551234");
                    }
                    break;
                case "alerta":
                    if (txtDestinatario.getText().isEmpty()) {
                        txtDestinatario.setText("sistema");
                    }
                    break;
            }
        }
    }
    
    private void logConsola(String mensaje) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String timestamp = sdf.format(new Date());
        txtConsola.append("[" + timestamp + "] " + mensaje + "\\n");
        txtConsola.setCaretPosition(txtConsola.getDocument().getLength());
    }
    
    // ===== MÉTODOS PARA ADMINISTRADORES =====
    
    private void toggleEnvioAutomaticoAdmins() {
        boolean activar = chkEnvioAutomaticoAdmins.isSelected();
        notificacionManager.setEnvioAutomaticoAdministradores(activar);
        
        String estado = activar ? "ACTIVADO" : "DESACTIVADO";
        logConsola("⚙️ Envío automático a administradores: " + estado);
        
        JOptionPane.showMessageDialog(this,
            "Envío automático a administradores: " + estado,
            "Configuración Actualizada", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void actualizarCorreosAdministradores() {
        try {
            List<String> correos = notificacionManager.obtenerCorreosAdministradores();
            
            txtCorreosAdministradores.setText("");
            if (correos.isEmpty()) {
                txtCorreosAdministradores.setText("No se encontraron administradores con correo electrónico.");
                lblCantidadAdmins.setText("Administradores: 0");
            } else {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < correos.size(); i++) {
                    sb.append((i + 1)).append(". ").append(correos.get(i)).append("\\n");
                }
                txtCorreosAdministradores.setText(sb.toString());
                lblCantidadAdmins.setText("Administradores: " + correos.size());
            }
            
            logConsola("✓ Correos de administradores actualizados: " + correos.size() + " encontrados");
            
        } catch (Exception e) {
            logConsola("✗ Error al actualizar correos de administradores: " + e.getMessage());
            JOptionPane.showMessageDialog(this,
                "Error al obtener correos de administradores: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void enviarNotificacionAAdministradores() {
        String mensaje = txtMensaje.getText().trim();
        String tipoNotificacion = (String) comboTipoNotificacion.getSelectedItem();
        
        if (mensaje.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Por favor, ingrese un mensaje para enviar",
                "Campo Requerido", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            AdminEmailManager adminManager = AdminEmailManager.getInstance();
            int enviados = adminManager.enviarNotificacionATodosLosAdmins(mensaje, tipoNotificacion);
            
            if (enviados > 0) {
                logConsola("✓ Notificación enviada a " + enviados + " administradores");
                JOptionPane.showMessageDialog(this,
                    "Notificación enviada exitosamente a " + enviados + " administradores",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                logConsola("✗ No se pudo enviar la notificación a administradores");
                JOptionPane.showMessageDialog(this,
                    "No se pudo enviar la notificación.\\nVerifique que haya administradores registrados.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception e) {
            logConsola("✗ Error al enviar notificación a administradores: " + e.getMessage());
            JOptionPane.showMessageDialog(this,
                "Error inesperado: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void verificarEstadoAdministradores() {
        try {
            String estado = notificacionManager.obtenerEstadoSistemaAdministradores();
            
            JTextArea textArea = new JTextArea(estado);
            textArea.setEditable(false);
            textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
            
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(500, 300));
            
            JOptionPane.showMessageDialog(this, scrollPane,
                "Estado del Sistema de Administradores", JOptionPane.INFORMATION_MESSAGE);
            
            logConsola("📊 Estado del sistema de administradores verificado");
            
        } catch (Exception e) {
            logConsola("✗ Error al verificar estado de administradores: " + e.getMessage());
            JOptionPane.showMessageDialog(this,
                "Error al verificar el estado: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void notificarStockBajoAAdministradores() {
        int productoID = (Integer) spinnerProductoID.getValue();
        String nombre = txtNombreProducto.getText().trim();
        int stockActual = (Integer) spinnerStockActual.getValue();
        int stockMinimo = (Integer) spinnerStockMinimo.getValue();
        
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Por favor, ingrese el nombre del producto",
                "Campo Requerido", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            int enviados = notificacionManager.notificarStockBajoAAdministradores(
                productoID, nombre, stockActual, stockMinimo);
            
            if (enviados > 0) {
                logConsola("✓ Alerta de stock bajo enviada a " + enviados + " administradores - Producto: " + nombre);
                JOptionPane.showMessageDialog(this,
                    "Alerta de stock bajo enviada a " + enviados + " administradores",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                logConsola("✗ No se pudo enviar alerta de stock bajo");
                JOptionPane.showMessageDialog(this,
                    "No se pudo enviar la alerta de stock bajo",
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception e) {
            logConsola("✗ Error al enviar alerta de stock bajo: " + e.getMessage());
            JOptionPane.showMessageDialog(this,
                "Error inesperado: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void notificarNuevoProductoAAdministradores() {
        int productoID = (Integer) spinnerProductoID.getValue();
        String nombre = txtNombreProducto.getText().trim();
        int cantidad = (Integer) spinnerCantidad.getValue();
        
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Por favor, ingrese el nombre del producto",
                "Campo Requerido", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String fecha = sdf.format(new Date());
            
            int enviados = notificacionManager.notificarNuevoProductoAAdministradores(
                productoID, nombre, cantidad, fecha);
            
            if (enviados > 0) {
                logConsola("✓ Notificación de nuevo producto enviada a " + enviados + " administradores - Producto: " + nombre);
                JOptionPane.showMessageDialog(this,
                    "Notificación de nuevo producto enviada a " + enviados + " administradores",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                logConsola("✗ No se pudo enviar notificación de nuevo producto");
                JOptionPane.showMessageDialog(this,
                    "No se pudo enviar la notificación de nuevo producto",
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception e) {
            logConsola("✗ Error al enviar notificación de nuevo producto: " + e.getMessage());
            JOptionPane.showMessageDialog(this,
                "Error inesperado: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Método para mostrar la ventana
    public static void mostrarInterfaz() {
        SwingUtilities.invokeLater(() -> {
            try {
                for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (Exception e) {
                // Usar Look and Feel por defecto si hay problemas
                System.out.println("Usando Look and Feel por defecto");
            }
            new NotificacionBridgeGUI().setVisible(true);
        });
    }
    
    public static void main(String[] args) {
        mostrarInterfaz();
    }
}
