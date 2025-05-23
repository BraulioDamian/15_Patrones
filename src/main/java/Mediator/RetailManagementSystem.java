package Mediator;

import DBObjetos.Producto; // Assuming Producto and InventarioMediator are relevant DB objects or data structures

import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 * Sistema Moderno de Gestión de Ventas al por Menor demostrando el Patrón Mediador
 * Caso de uso real: Gestión completa de una tienda minorista con inventario, ventas y análisis
 */
public class RetailManagementSystem extends JFrame {

    // UI Components
    private JPanel mainPanel;
    private JTabbedPane tabbedPane;
    private JLabel statusLabel;
    private JLabel dateTimeLabel;
    private JPanel dashboardPanel;
    private JPanel inventoryPanel;
    private JPanel salesPanel;
    private JPanel analyticsPanel;

    // Data display components
    private JTable inventoryTable;
    private JTable salesTable;
    private DefaultTableModel inventoryModel;
    private DefaultTableModel salesModel;
    private JTextArea notificationsArea;

    // Dashboard metrics
    private JLabel totalSalesLabel;
    private JLabel totalProductsLabel;
    private JLabel lowStockLabel;
    private JLabel todayRevenueLabel;

    // Mediator pattern components
    // Asumiendo que estas clases están definidas en el paquete Mediator
    private final MediadorConcreto mediador;
    private final ComponenteInventario componenteInventario;
    private final ComponenteVenta componenteVenta;
    private final ComponenteMenuPrincipal componenteMenu;

    // Sample data
    private final Random random = new Random();
    private int totalSales = 0; // Using this for a simple count across all sales
    private double todayRevenue = 0.0; // Using this for a simple sum across all sales

    // Colors and styling
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private static final Color SUCCESS_COLOR = new Color(39, 174, 96);
    private static final Color WARNING_COLOR = new Color(241, 196, 15);
    private static final Color DANGER_COLOR = new Color(231, 76, 60);
    private static final Color LIGHT_GRAY = new Color(245, 245, 245);

    // Helper method to create tab icons
    private Icon createIcon(String emoji) {
        JLabel icon = new JLabel(emoji);
        icon.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        // For some emojis, this might return null or a default icon.
        // A more robust approach might use a library or direct image files.
        // For this context, it's fine as an example.
        return icon.getIcon();
    }

    public RetailManagementSystem() {
        // Initialize mediator and components
        // Assuming these classes have appropriate constructors as used here
        mediador = MediadorConcreto.getInstance(); // Assuming MediadorConcreto has a static getInstance method
        componenteInventario = new ComponenteInventario("RetailSystem"); // Assuming constructor takes a String
        componenteVenta = new ComponenteVenta("RetailSystem"); // Assuming constructor takes a String
        componenteMenu = new ComponenteMenuPrincipal(); // Assuming no-arg constructor

        // Register components with mediator
        // Assuming MediadorConcreto has a registrarComponente method that accepts Componente objects
        mediador.registrarComponente(componenteInventario);
        mediador.registrarComponente(componenteVenta);
        mediador.registrarComponente(componenteMenu);

        initializeUI();
        setupLayout();
        populateSampleData();
        startRealTimeUpdates();
    }

    private void initializeUI() {
        // Título de la ventana
        setTitle("Sistema de Gestión de Ventas - Demo Patrón Mediador");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);

        // Panel principal con estilo moderno
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Panel de cabecera
        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Panel de pestañas para diferentes módulos
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        // Crear los diferentes paneles
        createDashboardPanel();
        createInventoryPanel();
        createSalesPanel();
        createAnalyticsPanel();

        // Añadir pestañas con iconos
        tabbedPane.addTab("Panel de Control", createIcon("📊"), dashboardPanel);
        tabbedPane.addTab("Inventario", createIcon("📦"), inventoryPanel);
        tabbedPane.addTab("Ventas", createIcon("💰"), salesPanel);
        tabbedPane.addTab("Análisis", createIcon("📈"), analyticsPanel);

        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        // Barra de estado
        JPanel statusPanel = createStatusPanel();
        mainPanel.add(statusPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setBorder(new EmptyBorder(15, 20, 15, 20));

        // Título
        JLabel titleLabel = new JLabel("Sistema de Gestión de Ventas");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);

        // Fecha y hora
        dateTimeLabel = new JLabel();
        dateTimeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        dateTimeLabel.setForeground(Color.WHITE);
        updateDateTime(); // Actualizar la fecha y hora al inicio

        // Información del usuario
        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        userPanel.setBackground(PRIMARY_COLOR);

        JLabel userIcon = new JLabel("👤");
        userIcon.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        JLabel userLabel = new JLabel("Usuario Administrador");
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        userLabel.setForeground(Color.WHITE);

        JButton logoutButton = new JButton("Cerrar Sesión");
        logoutButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        logoutButton.setBackground(Color.WHITE);
        logoutButton.setForeground(PRIMARY_COLOR);
        logoutButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        logoutButton.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(this,
                "¿Estás seguro de que quieres cerrar sesión?", // Mensaje de confirmación
                "Confirmar Cierre de Sesión", // Título del diálogo
                JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                System.exit(0); // Salir de la aplicación
            }
        });

        userPanel.add(userIcon);
        userPanel.add(userLabel);
        userPanel.add(Box.createHorizontalStrut(10));
        userPanel.add(logoutButton);

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(dateTimeLabel, BorderLayout.CENTER);
        headerPanel.add(userPanel, BorderLayout.EAST);

        return headerPanel;
    }

    private void createDashboardPanel() {
        dashboardPanel = new JPanel(new BorderLayout());
        dashboardPanel.setBackground(LIGHT_GRAY);

        // Panel de métricas
        JPanel metricsPanel = createMetricsPanel();
        dashboardPanel.add(metricsPanel, BorderLayout.NORTH);

        // Panel de contenido con notificaciones y acciones rápidas
        JPanel contentPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        contentPanel.setBackground(LIGHT_GRAY);
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Panel de notificaciones
        JPanel notificationsPanel = createNotificationsPanel();
        contentPanel.add(notificationsPanel);

        // Panel de acciones rápidas
        JPanel quickActionsPanel = createQuickActionsPanel();
        contentPanel.add(quickActionsPanel);

        dashboardPanel.add(contentPanel, BorderLayout.CENTER);
    }

    private JPanel createMetricsPanel() {
        JPanel metricsPanel = new JPanel(new GridLayout(1, 4, 20, 0));
        metricsPanel.setBackground(LIGHT_GRAY);
        metricsPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Tarjetas de métricas
        JPanel salesCard = createMetricCard("Total Ventas", "0", "💰", SUCCESS_COLOR);
        JPanel productsCard = createMetricCard("Productos", "0", "📦", PRIMARY_COLOR);
        JPanel lowStockCard = createMetricCard("Poco Stock", "0", "⚠️", WARNING_COLOR);
        JPanel revenueCard = createMetricCard("Ingresos de Hoy", "$0.00", "💵", SUCCESS_COLOR);

        // Almacenar referencias para actualizaciones
        totalSalesLabel = (JLabel) ((JPanel) salesCard.getComponent(1)).getComponent(0);
        totalProductsLabel = (JLabel) ((JPanel) productsCard.getComponent(1)).getComponent(0);
        lowStockLabel = (JLabel) ((JPanel) lowStockCard.getComponent(1)).getComponent(0);
        todayRevenueLabel = (JLabel) ((JPanel) revenueCard.getComponent(1)).getComponent(0);

        metricsPanel.add(salesCard);
        metricsPanel.add(productsCard);
        metricsPanel.add(lowStockCard);
        metricsPanel.add(revenueCard);

        return metricsPanel;
    }

    private JPanel createMetricCard(String title, String value, String icon, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230)),
            new EmptyBorder(20, 20, 20, 20)
        ));

        // Icono
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        iconLabel.setForeground(color);

        // Valor y título
        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.setBackground(Color.WHITE);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        valueLabel.setForeground(color);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        titleLabel.setForeground(Color.GRAY);

        textPanel.add(valueLabel);
        textPanel.add(titleLabel);

        card.add(iconLabel, BorderLayout.WEST);
        card.add(textPanel, BorderLayout.CENTER);

        return card;
    }

    private JPanel createNotificationsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230)),
            new EmptyBorder(15, 15, 15, 15)
        ));

        JLabel titleLabel = new JLabel("📢 Notificaciones del Sistema");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setBorder(new EmptyBorder(0, 0, 10, 0));

        notificationsArea = new JTextArea();
        notificationsArea.setEditable(false);
        notificationsArea.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        notificationsArea.setBackground(LIGHT_GRAY);
        notificationsArea.setBorder(new EmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(notificationsArea);
        scrollPane.setPreferredSize(new Dimension(300, 200));

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createQuickActionsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230)),
            new EmptyBorder(15, 15, 15, 15)
        ));

        JLabel titleLabel = new JLabel("⚡ Acciones Rápidas");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setBorder(new EmptyBorder(0, 0, 10, 0));

        JPanel buttonsPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        buttonsPanel.setBackground(Color.WHITE);

        JButton addProductBtn = createActionButton("Añadir Producto", "➕", PRIMARY_COLOR);
        JButton newSaleBtn = createActionButton("Nueva Venta", "💰", SUCCESS_COLOR);
        JButton checkInventoryBtn = createActionButton("Ver Inventario", "📦", WARNING_COLOR);
        JButton generateReportBtn = createActionButton("Generar Informe", "📊", DANGER_COLOR);
        JButton updateStockBtn = createActionButton("Actualizar Stock", "🔄", PRIMARY_COLOR);
        JButton viewAnalyticsBtn = createActionButton("Ver Análisis", "📈", SUCCESS_COLOR);

        // Add action listeners
        addProductBtn.addActionListener(e -> simulateAddProduct());
        newSaleBtn.addActionListener(e -> simulateNewSale());
        checkInventoryBtn.addActionListener(e -> tabbedPane.setSelectedIndex(1)); // Seleccionar pestaña Inventario
        generateReportBtn.addActionListener(e -> generateReport());
        updateStockBtn.addActionListener(e -> simulateStockUpdate());
        viewAnalyticsBtn.addActionListener(e -> tabbedPane.setSelectedIndex(3)); // Seleccionar pestaña Análisis

        buttonsPanel.add(addProductBtn);
        buttonsPanel.add(newSaleBtn);
        buttonsPanel.add(checkInventoryBtn);
        buttonsPanel.add(generateReportBtn);
        buttonsPanel.add(updateStockBtn);
        buttonsPanel.add(viewAnalyticsBtn);

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(buttonsPanel, BorderLayout.CENTER);

        return panel;
    }

    private JButton createActionButton(String text, String icon, Color color) {
        // Formato HTML para el texto del botón con icono y texto multilinea
        JButton button = new JButton("<html><div style='text-align: center'>" +
                                     "<font size='4'>" + icon + "</font><br>" +
                                     "<font size='2'>" + text + "</font></div></html>");
        button.setFont(new Font("Segoe UI", Font.PLAIN, 10)); // El tamaño de la fuente se ajusta en el HTML
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        button.setFocusPainted(false);

        // Efecto hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.darker());
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });

        return button;
    }

    private void createInventoryPanel() {
        inventoryPanel = new JPanel(new BorderLayout());
        inventoryPanel.setBackground(LIGHT_GRAY);
        inventoryPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Cabecera
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(LIGHT_GRAY);

        JLabel titleLabel = new JLabel("📦 Gestión de Inventario");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));

        JButton refreshBtn = new JButton("Actualizar");
        refreshBtn.setBackground(PRIMARY_COLOR);
        refreshBtn.setForeground(Color.WHITE);
        refreshBtn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        refreshBtn.addActionListener(e -> refreshInventory());

        headerPanel.add(titleLabel);
        headerPanel.add(Box.createHorizontalStrut(20));
        headerPanel.add(refreshBtn);

        // Tabla
        String[] inventoryColumns = {"ID", "Nombre Producto", "Categoría", "Stock", "Precio", "Estado"};
        inventoryModel = new DefaultTableModel(inventoryColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Las celdas no son editables
            }
        };

        inventoryTable = new JTable(inventoryModel);
        inventoryTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        inventoryTable.setRowHeight(25);
        inventoryTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        inventoryTable.setSelectionBackground(new Color(184, 207, 229));

        // Renderizador de celda personalizado para la columna "Estado"
        inventoryTable.getColumn("Estado").setCellRenderer(new StatusCellRenderer());

        JScrollPane scrollPane = new JScrollPane(inventoryTable);
        scrollPane.setBackground(Color.WHITE);

        inventoryPanel.add(headerPanel, BorderLayout.NORTH);
        inventoryPanel.add(scrollPane, BorderLayout.CENTER);
    }

    private void createSalesPanel() {
        salesPanel = new JPanel(new BorderLayout());
        salesPanel.setBackground(LIGHT_GRAY);
        salesPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Cabecera
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(LIGHT_GRAY);

        JLabel titleLabel = new JLabel("💰 Gestión de Ventas");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));

        JButton newSaleBtn = new JButton("Nueva Venta");
        newSaleBtn.setBackground(SUCCESS_COLOR);
        newSaleBtn.setForeground(Color.WHITE);
        newSaleBtn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        newSaleBtn.addActionListener(e -> simulateNewSale());

        headerPanel.add(titleLabel);
        headerPanel.add(Box.createHorizontalStrut(20));
        headerPanel.add(newSaleBtn);

        // Tabla
        String[] salesColumns = {"ID Venta", "Fecha", "Producto", "Cantidad", "Precio Unitario", "Total", "Cliente"};
        salesModel = new DefaultTableModel(salesColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Las celdas no son editables
            }
        };

        salesTable = new JTable(salesModel);
        salesTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        salesTable.setRowHeight(25);
        salesTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        salesTable.setSelectionBackground(new Color(184, 207, 229));

        JScrollPane scrollPane = new JScrollPane(salesTable);
        scrollPane.setBackground(Color.WHITE);

        salesPanel.add(headerPanel, BorderLayout.NORTH);
        salesPanel.add(scrollPane, BorderLayout.CENTER);
    }

    private void createAnalyticsPanel() {
        analyticsPanel = new JPanel(new BorderLayout());
        analyticsPanel.setBackground(LIGHT_GRAY);
        analyticsPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Cabecera
        JLabel titleLabel = new JLabel("📈 Análisis e Informes");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setBorder(new EmptyBorder(0, 0, 20, 0));

        // Contenido
        JPanel contentPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        contentPanel.setBackground(LIGHT_GRAY);

        // Marcadores de posición para gráficos (en una implementación real, usar JFreeChart o similar)
        JPanel salesChart = createChartPanel("Tendencia de Ventas", "📈");
        JPanel inventoryChart = createChartPanel("Niveles de Inventario", "📊");
        JPanel revenueChart = createChartPanel("Análisis de Ingresos", "💹");
        JPanel categoryChart = createChartPanel("Rendimiento por Categoría", "🥧");

        contentPanel.add(salesChart);
        contentPanel.add(inventoryChart);
        contentPanel.add(revenueChart);
        contentPanel.add(categoryChart);

        analyticsPanel.add(titleLabel, BorderLayout.NORTH);
        analyticsPanel.add(contentPanel, BorderLayout.CENTER);
    }

    private JPanel createChartPanel(String title, String icon) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230)),
            new EmptyBorder(15, 15, 15, 15)
        ));

        JLabel titleLabel = new JLabel(icon + " " + title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JPanel chartArea = new JPanel();
        chartArea.setBackground(LIGHT_GRAY);
        chartArea.setPreferredSize(new Dimension(300, 200));

        JLabel placeholderLabel = new JLabel("La visualización del gráfico iría aquí");
        placeholderLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        placeholderLabel.setForeground(Color.GRAY);
        chartArea.add(placeholderLabel);

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(chartArea, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createStatusPanel() {
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusPanel.setBackground(new Color(52, 73, 94));
        statusPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        statusLabel = new JLabel("Sistema Listo"); // Estado inicial
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        statusLabel.setForeground(Color.WHITE);

        statusPanel.add(statusLabel);

        return statusPanel;
    }

    private void setupLayout() {
        // Este método puede usarse para configuraciones de diseño adicionales si son necesarias.
    }

    private void populateSampleData() {
        // Datos de inventario de muestra
        String[] products = {
            "Laptop Dell XPS", "Ratón Logitech", "Teclado Mecánico", "Monitor 24\"", "Tablet Samsung",
            "Smartphone iPhone", "Auriculares Sony", "Webcam HD", "Router WiFi", "Disco Duro Externo"
        };

        String[] categories = {"Electrónica", "Accesorios", "Informática", "Móviles", "Almacenamiento"};

        for (int i = 0; i < products.length; i++) {
            int stock = random.nextInt(100) + 1;
            double price = random.nextDouble() * 1000 + 50;
            // Traducir los estados del stock
            String status = stock < 10 ? "Poco Stock" : stock < 30 ? "Medio" : "En Stock";

            inventoryModel.addRow(new Object[]{
                i + 1,
                products[i],
                categories[random.nextInt(categories.length)],
                stock,
                String.format("$%.2f", price), // Mantener formato de precio simple por ahora
                status
            });
        }

        // Datos de ventas de muestra
        String[] customers = {"Juan Pérez", "Ana Gómez", "Carlos Rodríguez", "María Fernández", "Jorge López"};

        for (int i = 0; i < 15; i++) {
            int quantity = random.nextInt(5) + 1;
            // Seleccionar un producto aleatorio para la venta
            int productIndex = random.nextInt(products.length);
            String productName = products[productIndex];
            // Para el precio, podríamos usar el precio del producto del inventario si tuviéramos una lista de productos más rica.
            // Usaremos uno aleatorio por simplicidad, como en el original.
            double unitPrice = random.nextDouble() * 500 + 50;
            double total = quantity * unitPrice;

            salesModel.addRow(new Object[]{
                String.format("V%03d", salesModel.getRowCount() + 1), // Prefijo V para Venta
                LocalDateTime.now().minusDays(random.nextInt(30)).format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")), // Formato de fecha/hora en español
                productName,
                quantity,
                String.format("$%.2f", unitPrice),
                String.format("$%.2f", total),
                customers[random.nextInt(customers.length)]
            });

            // Actualizar métricas globales (simple demo)
            totalSales++; // Simplemente incrementa un contador
            todayRevenue += total; // Suma los totales de ventas simuladas
        }

        updateMetrics(); // Actualizar la visualización de las métricas
    }

    private void updateMetrics() {
        totalSalesLabel.setText(String.valueOf(totalSales));
        totalProductsLabel.setText(String.valueOf(inventoryModel.getRowCount()));

        // Contar artículos con poco stock (usando el estado traducido)
        int lowStockCount = 0;
        for (int i = 0; i < inventoryModel.getRowCount(); i++) {
            String status = (String) inventoryModel.getValueAt(i, 5); // Columna Estado (índice 5)
            if ("Poco Stock".equals(status)) { // Usar la cadena traducida
                lowStockCount++;
            }
        }
        lowStockLabel.setText(String.valueOf(lowStockCount));

        todayRevenueLabel.setText(String.format("$%.2f", todayRevenue)); // Mantener formato de moneda simple
    }

    private void startRealTimeUpdates() {
        // Temporizador para actualizar la fecha y hora
        Timer timer = new Timer(1000, e -> updateDateTime());
        timer.start();

        // Simular notificaciones en tiempo real
        Timer notificationTimer = new Timer(5000, e -> generateRandomNotification());
        notificationTimer.start();
    }

    private void updateDateTime() {
        LocalDateTime now = LocalDateTime.now();
        // Formato de fecha y hora en español (ej: martes, 24 de octubre de 2023 - 15:30:00)
        dateTimeLabel.setText(now.format(DateTimeFormatter.ofPattern("EEEE, dd 'de' MMMM 'de' yyyy - HH:mm:ss")));
    }

    private void generateRandomNotification() {
        // Notificaciones de muestra traducidas
        String[] notifications = {
            "Nuevo pedido recibido del cliente",
            "Alerta de poco stock para artículos populares",
            "Informe de ventas diario generado",
            "Sincronización de inventario completada",
            "Nuevo cliente registrado"
        };

        String notification = "[" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "] " +
                            notifications[random.nextInt(notifications.length)] + "\n";

        notificationsArea.append(notification);
        notificationsArea.setCaretPosition(notificationsArea.getDocument().getLength());

        // Demostrar patrón Mediador - notificar a todos los componentes
        Map<String, Object> data = new HashMap<>();
        data.put("message", notification); // La clave sigue siendo "message"
        data.put("timestamp", LocalDateTime.now()); // La clave sigue siendo "timestamp"

        // Asumiendo que componenteMenu tiene un método recibirNotificacion
        componenteMenu.recibirNotificacion("SYSTEM_NOTIFICATION", data); // El tipo de evento se mantiene
    }

    private void simulateAddProduct() {
        // Solicitar nombre del producto en español
        String productName = JOptionPane.showInputDialog(this, "Introduce el nombre del producto:");
        if (productName != null && !productName.trim().isEmpty()) {
            int stock = random.nextInt(50) + 10;
            double price = random.nextDouble() * 500 + 50;
            // Traducir los estados del stock
            String status = stock < 10 ? "Poco Stock" : stock < 30 ? "Medio" : "En Stock";

            inventoryModel.addRow(new Object[]{
                inventoryModel.getRowCount() + 1, // ID simple basado en el número de filas
                productName,
                "Electrónica", // Categoría de muestra
                stock,
                String.format("$%.2f", price),
                status
            });

            updateMetrics(); // Actualizar métricas después de añadir un producto
            updateStatus("Producto añadido: " + productName); // Mensaje de estado traducido

            // Demostrar patrón Mediador
            // Asumiendo que Producto tiene una clase anidada estática ProductoBuilder
            Producto producto = new Producto.ProductoBuilder()
                .productoID(inventoryModel.getRowCount()) // Asignando el número de fila como ID
                .nombre(productName)
                .precio(price)
                .build(); // Asumiendo que build() devuelve un objeto Producto

            // Asumiendo que InventarioMediator y sus setters existen
            InventarioMediator inv = new InventarioMediator(); // Asumiendo un constructor sin argumentos
            inv.setId(inventoryModel.getRowCount()); // Usando el número de fila como ID de Inventario
            inv.setProducto(producto); // Asumiendo que setProducto acepta un objeto Producto
            inv.setCantidad(stock); // Asumiendo que setCantidad acepta un int

            // Asumiendo que componenteInventario tiene un método notificarCambioInventario
            componenteInventario.notificarCambioInventario(inv); // Notificar cambio a través del mediador
        }
    }

    private void simulateNewSale() {
        if (inventoryModel.getRowCount() == 0) {
            // Mensaje de error traducido
            JOptionPane.showMessageDialog(this, "¡No hay productos disponibles para la venta!");
            return;
        }

        // Seleccionar un producto aleatorio del inventario
        int productIndex = random.nextInt(inventoryModel.getRowCount());
        String productName = (String) inventoryModel.getValueAt(productIndex, 1); // Nombre del producto
        Integer currentStockObj = (Integer) inventoryModel.getValueAt(productIndex, 3); // Stock actual (puede ser null)
        int currentStock = (currentStockObj != null) ? currentStockObj : 0;


        if (currentStock <= 0) {
             // Mensaje de error traducido
            JOptionPane.showMessageDialog(this, "¡El producto seleccionado está agotado!");
            return;
        }

        int quantity = Math.min(random.nextInt(5) + 1, currentStock); // Cantidad aleatoria, no excede el stock
        // Usar el precio real de la tabla de inventario (parsear el string formateado)
        String priceString = (String) inventoryModel.getValueAt(productIndex, 4); // Precio (ej: "$123.45")
        double unitPrice;
        try {
             unitPrice = Double.parseDouble(priceString.replace("$", ""));
        } catch (NumberFormatException e) {
            unitPrice = random.nextDouble() * 500 + 50; // Fallback si falla el parseo
        }

        double total = quantity * unitPrice;

        // Añadir a la tabla de ventas
        salesModel.addRow(new Object[]{
            String.format("V%03d", salesModel.getRowCount() + 1), // ID de venta simple
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")), // Fecha/hora actual formateada
            productName,
            quantity,
            String.format("$%.2f", unitPrice), // Precio unitario formateado
            String.format("$%.2f", total), // Total formateado
            "Cliente de paso" // Cliente de muestra traducido
        });

        // Actualizar inventario: reducir stock
        int newStock = currentStock - quantity;
        inventoryModel.setValueAt(newStock, productIndex, 3); // Actualizar celda de stock

        // Actualizar estado del stock (usando las cadenas traducidas)
        String newStatus = newStock < 10 ? "Poco Stock" : newStock < 30 ? "Medio" : "En Stock";
        inventoryModel.setValueAt(newStatus, productIndex, 5); // Actualizar celda de estado

        totalSales++; // Incrementar contador global de ventas
        todayRevenue += total; // Sumar al total de ingresos (global en esta demo)
        updateMetrics(); // Actualizar la visualización de las métricas
        // Mensaje de estado traducido
        updateStatus(String.format("Venta completada: %s (Cant: %d, Total: $%.2f)", productName, quantity, total));

        // Demostrar patrón Mediador - notificar al componente de ventas
        Map<String, Object> saleData = new HashMap<>();
        // Usar el ID del producto de la tabla de inventario si está disponible
        saleData.put("producto_id", inventoryModel.getValueAt(productIndex, 0));
        saleData.put("cantidad", quantity);
        saleData.put("precio_unitario", unitPrice);
        saleData.put("total", total);
        // Añadir otros detalles de la venta si es necesario (cliente, fecha/hora)

        // Asumiendo que componenteVenta tiene un método recibirNotificacion
        componenteVenta.recibirNotificacion("NUEVA_VENTA", saleData); // Notificar al componente de ventas
    }

    private void simulateStockUpdate() {
        if (inventoryModel.getRowCount() == 0) {
            // Mensaje traducido
            JOptionPane.showMessageDialog(this, "¡No hay productos en inventario!");
            return;
        }

        // Seleccionar un producto aleatorio
        int productIndex = random.nextInt(inventoryModel.getRowCount());
        String productName = (String) inventoryModel.getValueAt(productIndex, 1); // Nombre del producto
        int additionalStock = random.nextInt(20) + 5; // Cantidad adicional aleatoria
        Integer currentStockObj = (Integer) inventoryModel.getValueAt(productIndex, 3); // Stock actual
        int currentStock = (currentStockObj != null) ? currentStockObj : 0;
        int newStock = currentStock + additionalStock; // Nuevo stock

        inventoryModel.setValueAt(newStock, productIndex, 3); // Actualizar celda de stock

        // Actualizar estado del stock (usando las cadenas traducidas)
        String newStatus = newStock < 10 ? "Poco Stock" : newStock < 30 ? "Medio" : "En Stock";
        inventoryModel.setValueAt(newStatus, productIndex, 5); // Actualizar celda de estado

        updateMetrics(); // Actualizar métricas
        // Mensaje de estado traducido
        updateStatus(String.format("Stock actualizado: %s (+%d unidades)", productName, additionalStock));

        // Opcional: Demostrar patrón Mediador para notificación de actualización de stock
        // Map<String, Object> stockData = new HashMap<>();
        // stockData.put("producto_id", inventoryModel.getValueAt(productIndex, 0));
        // stockData.put("nueva_cantidad", newStock);
        // componenteInventario.notificarCambioStock("STOCK_ACTUALIZADO", stockData); // Asumiendo que existe tal método/evento
    }

    private void refreshInventory() {
        // En un sistema real, esto cargaría los datos desde la DB a través del mediador/componentes.
        // Para esta demo, simplemente actualiza la vista y las métricas basándose en el modelo actual.
        updateStatus("Inventario actualizado"); // Mensaje de estado traducido
        updateMetrics(); // Recalcular métricas basadas en los datos de la tabla actual
    }

    private void generateReport() {
        // Mensaje de informe traducido
        JOptionPane.showMessageDialog(this,
            String.format("""
                          Informe de Ventas Generado
                          
                          Total Ventas: %d
                          Total Productos: %d
                          Ingresos de Hoy: $%.2f
                          Artículos con poco stock: %s""",
                         totalSales, // totalSales es un contador global en esta demo
                         inventoryModel.getRowCount(),
                         todayRevenue, // todayRevenue es una suma global en esta demo
                         lowStockLabel.getText()), // Obtener el texto actual del label
            "Informe de Ventas", // Título del diálogo traducido
            JOptionPane.INFORMATION_MESSAGE);
    }

    private void updateStatus(String message) {
        statusLabel.setText(message);

        // Restablecer estado después de 3 segundos
        Timer timer = new Timer(3000, e -> statusLabel.setText("Sistema Listo")); // Estado de reposo traducido
        timer.setRepeats(false);
        timer.start();
    }


    // Renderizador de celda personalizado para la columna "Estado"
    private class StatusCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                     boolean hasFocus, int row, int column) {
            // Obtener el renderizador por defecto
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            String status = (String) value;
            if (!isSelected) { // Aplicar color de fondo personalizado solo cuando no está seleccionado
                // Usar las cadenas de estado traducidas para la comparación
                switch (status) {
                    case "Poco Stock" -> {
                        c.setBackground(new Color(255, 235, 235)); // Rojo claro
                        c.setForeground(DANGER_COLOR); // Rojo
                    }
                    case "Medio" -> {
                        c.setBackground(new Color(255, 248, 220)); // Amarillo claro
                        c.setForeground(WARNING_COLOR); // Naranja/Amarillo
                    }
                    case "En Stock" -> {
                        c.setBackground(new Color(235, 255, 235)); // Verde claro
                        c.setForeground(SUCCESS_COLOR); // Verde
                    }
                    default -> {
                        // Para cualquier otro estado
                        c.setBackground(Color.WHITE);
                        c.setForeground(Color.BLACK);
                    }
                }
            } else { // Asegurar que el fondo de selección se maneje por la superclase
                 c.setBackground(table.getSelectionBackground());
                 c.setForeground(table.getSelectionForeground());
            }

            setHorizontalAlignment(CENTER); // Centrar el texto en la celda
            return c;
        }
    }

    public static void main(String[] args) {
        // Ejecutar la GUI en el hilo de despacho de eventos (Event Dispatch Thread)
        SwingUtilities.invokeLater(() -> {
            try {
                // Establecer el Look and Feel del sistema para una apariencia nativa
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                new RetailManagementSystem().setVisible(true);
            } catch (Exception e) {
                e.printStackTrace(); // Imprimir errores en la consola
            }
        });
    }
}

// Nota: Las clases ComponenteInventario, ComponenteVenta, ComponenteMenuPrincipal,
// MediadorConcreto, Producto, y InventarioMediator no están incluidas en este código,
// se asume que existen y están correctamente implementadas según el Patrón Mediador
// y las interacciones simuladas en esta clase.