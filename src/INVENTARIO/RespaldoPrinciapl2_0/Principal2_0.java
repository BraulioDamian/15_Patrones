/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package INVENTARIO;

import ConexionDB.Conexion_DB;
import DBObjetos.*;


import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import Configuraciones.Configuraciones;
import Venta.Venta;
import Consultas.CONSULTASDAO;
import Graficas.AvisosFrame;
import Graficas.ObservarGraficas;
import java.awt.Color;
import java.time.LocalDate;
import login.Estilos;
import login.LOGINN;
import login.SesionManager;
import PanelUsuarios.UsuariosPanel;
import Principal.MenuPrincipal;


/**
 *
 * @author master
 */
public class Principal2_0 extends javax.swing.JFrame {

    /**
     * Creates new form Panel
     */

   private AnimacionPanel animador; // Añade esta línea
    private Usuario usuarioLogueado;

     private static Principal2_0 instance;

        private boolean menuDesplegado = false;

    
       public Principal2_0() {
        setUndecorated(true); // Hacer que el JFrame sea indecorado

        initComponents();
        setLocationRelativeTo(null); // Centra la ventana en la pantalla
  
         animador = new AnimacionPanel(); // Inicializa el animador

        
        configurarEncabezadosTabla();

    Buscar.addKeyListener(new KeyAdapter() {
        @Override
        public void keyReleased(KeyEvent e) {
            String textoBuscado = Buscar.getText().trim();
            filtrarTablaPorTexto(textoBuscado);
        }
    });

    initProductosConArea();
    

         // Configurar la operación de cierre
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Aquí no hacemos nada para deshabilitar la funcionalidad del botón de cierre
            }
        });
    
        agregarEfectoHover(); // Método para agregar el efecto hover a los labels


    }
       
      
       
    private void agregarEfectoHover() {
        HoverEffect.applyHoverEffect(Menu);
        HoverEffect.applyHoverEffect(Inicio);
        HoverEffect.applyHoverEffect(Usuarios);
        HoverEffect.applyHoverEffect(Configuracion);
        HoverEffect.applyHoverEffect(Ventas);
        HoverEffect.applyHoverEffect(Analisis);
        HoverEffect.applyHoverEffect(Logout);
    }



       




       
       
    private Principal2_0(Usuario usuario) {
        setUndecorated(true); // Hacer que el JFrame sea indecorado
        setLocationRelativeTo(null); // Centra la ventana en la pantalla

        this.usuarioLogueado = usuario;
        
        initComponents();
        
        animador = new AnimacionPanel(); // Inicializa el animador
        
        configurarEncabezadosTabla();
        
         ajustarTamanioColumnas();

    

    initProductosConArea();
    
    configurarVisibilidadComponentes();
    
    Buscar.addKeyListener(new KeyAdapter() {
        @Override
        public void keyReleased(KeyEvent e) {
            String textoBuscado = Buscar.getText().trim();
            filtrarTablaPorTexto(textoBuscado);
        }
    });

 
         Estilos.addPlaceholderStyle(Unidades);
        Estilos.addPlaceholderStyle(Caducidad);
        Estilos.addPlaceholderStyle(Contenido);
        Estilos.addPlaceholderStyle(Precio);
        Estilos.addPlaceholderStyle(Marca);
        Estilos.addPlaceholderStyle(Precio);
        Estilos.addPlaceholderStyle(Nombre);


        agregarEfectoHover(); // Método para agregar el efecto hover a los labels

}

       // Método estático para obtener la instancia única
    public static Principal2_0 getInstance() {
        if (instance == null) {
            instance = new Principal2_0();
        }
        return instance;
    } 
    
    
    private void configurarVisibilidadComponentes() {
        // Asegúrate de que los componentes están inicializados antes de llamar este método
        if (usuarioLogueado.getRol() == Usuario.Rol.EMPLEADO) {
            Usuarios.setVisible(false);
            Configuracion.setVisible(false);
            // Hacer invisibles otros labels que no deben verse por el empleado
        } else if (usuarioLogueado.getRol() == Usuario.Rol.ADMINISTRADOR) {
            // El administrador puede ver todo, así que no necesitas hacer nada aquí
        }
    }
 
    
    public void initialize(Usuario usuario) {
        this.usuarioLogueado = usuario;
        configurarVisibilidadComponentes();
    }    
    


      
       
    private void configurarEncabezadosTabla() {
    // Nombres de columnas como en el primer código
    String[] titulo = new String[]{"COD. BARRAS", "NOMBRE", "MARCA", "UNIDADES", "CONTENIDO", "AREA", "PRECIO"};
    DefaultTableModel dtm = (DefaultTableModel) tablita.getModel();
    dtm.setColumnIdentifiers(titulo);
    tablita.setModel(dtm);
    actualizarTablaInventario();
    
    ajustarTamanioColumnas();
    
}

    
// Método que filtra la tabla basado en el texto ingresado
private List<Producto> listaProductosConArea; // Mantiene la lista de productos para evitar múltiples accesos a BD

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
    DefaultTableModel model = (DefaultTableModel) tablita.getModel();
    model.setRowCount(0); // Limpia la tabla primero

    // Filtra la lista basado en el texto ingresado y actualiza la tabla
    for (Producto prod : listaProductosConArea) {
        if (prod.getNombre().toLowerCase().startsWith(texto.toLowerCase()) || prod.getCodigoBarras().toLowerCase().startsWith(texto.toLowerCase())) {
            model.addRow(new Object[]{
                prod.getCodigoBarras(),
                prod.getNombre(),
                prod.getMarca(),
                prod.getUnidadesDisponibles(),
                prod.getContenido(),
                prod.getNombreArea(),
                prod.getPrecio()
            });
        }
    }
}

    


public void actualizarTablaInventario() {
    DefaultTableModel model = (DefaultTableModel) tablita.getModel();
    model.setRowCount(0); // Limpia la tabla completamente.

    try {
        CONSULTASDAO dao = new CONSULTASDAO(Conexion_DB.getConexion());
        List<Producto> listaProductosConArea = dao.obtenerProductosConNombreArea();

        for (Producto prod : listaProductosConArea) {
            LocalDate fechaCaducidad = prod.getFechaCaducidad();
            LocalDate unMesAntes = fechaCaducidad.minusMonths(1);
            LocalDate hoy = LocalDate.now();

            boolean esReabastecible = prod.getUnidadesDisponibles() <= 20;
            boolean estaCercaCaducidad = !hoy.isBefore(unMesAntes);

            agregarFilaProducto(model, prod);

            if (esReabastecible) {
                System.out.println("Reabastecer " + prod.getNombre());
            }
            if (estaCercaCaducidad) {
                System.out.println("Se sugiere poner en oferta el producto " + prod.getNombre() + ", con fecha de caducidad " + prod.getFechaCaducidad());
            }
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error al conectar con la base de datos: " + e.getMessage(), "Error de Conexión", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}

private void agregarFilaProducto(DefaultTableModel model, Producto prod) {
    model.addRow(new Object[]{
        prod.getCodigoBarras(),
        prod.getNombre(),
        prod.getMarca(),
        prod.getUnidadesDisponibles(),
        prod.getContenido(),
        prod.getNombreArea(),
        prod.getPrecio()
    });
}





    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        MenuPlegable = new javax.swing.JPanel();
        Menu = new javax.swing.JLabel();
        Inicio = new javax.swing.JLabel();
        Usuarios = new javax.swing.JLabel();
        Configuracion = new javax.swing.JLabel();
        Ventas = new javax.swing.JLabel();
        Analisis = new javax.swing.JLabel();
        Inventario = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        Panel2 = new javax.swing.JPanel();
        Buscar = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablita = new javax.swing.JTable();
        desplegable = new javax.swing.JComboBox<>();
        cod = new javax.swing.JLabel();
        Nombre = new javax.swing.JTextField();
        Precio = new javax.swing.JTextField();
        Marca = new javax.swing.JTextField();
        Caducidad = new javax.swing.JTextField();
        Contenido = new javax.swing.JTextField();
        Unidades = new javax.swing.JTextField();
        add = new javax.swing.JButton();
        modificar = new javax.swing.JButton();
        txtElimiarProd = new javax.swing.JButton();
        Logout = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                MenuMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                MenuMouseExited(evt);
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
        Configuracion.setBounds(0, 530, 170, 50);

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
        Ventas.setBounds(0, 160, 170, 50);

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
        Analisis.setBounds(0, 360, 170, 50);

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
        Inventario.setBounds(0, 260, 170, 50);

        jPanel2.add(MenuPlegable, new org.netbeans.lib.awtextra.AbsoluteConstraints(-120, 0, 170, 610));

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));

        jLabel3.setForeground(new java.awt.Color(102, 102, 102));
        jLabel3.setText("Nota - 0001");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(932, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 0, 1010, 40));

        Panel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Panel2MouseClicked(evt);
            }
        });

        Buscar.setText("Buscar");
        Buscar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                BuscarFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                BuscarFocusLost(evt);
            }
        });
        Buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarActionPerformed(evt);
            }
        });
        Buscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                BuscarKeyReleased(evt);
            }
        });

        tablita.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "COD. BARRAS", "NOMBRE", "MARCA", "CANTIDAD", "UNIDADES", "AREA", "PRECIO"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablita.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablitaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablita);

        desplegable.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos los Productos", "Salchichonería", "Frutas y Verduras", "Panadería", "Galletas y Cereales", "Bebidas", "Productos de Limpieza", "Botanas", "Cuidado Personal", "Congelados" }));
        desplegable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                desplegableActionPerformed(evt);
            }
        });

        cod.setText("CODIGO DE BARRAS");
        cod.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        Nombre.setText("Nombre");
        Nombre.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                NombreFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                NombreFocusLost(evt);
            }
        });

        Precio.setText("Precio");
        Precio.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                PrecioFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                PrecioFocusLost(evt);
            }
        });

        Marca.setText("Marca");
        Marca.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                MarcaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                MarcaFocusLost(evt);
            }
        });

        Caducidad.setText("Fecha de Caducidad");
        Caducidad.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                CaducidadFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                CaducidadFocusLost(evt);
            }
        });

        Contenido.setText("Contenido");
        Contenido.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                ContenidoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                ContenidoFocusLost(evt);
            }
        });

        Unidades.setText("Unidades Disponibles");
        Unidades.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                UnidadesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                UnidadesFocusLost(evt);
            }
        });

        add.setText("Añadir Productos");
        add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addActionPerformed(evt);
            }
        });

        modificar.setText("Modificar Productos");
        modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modificarActionPerformed(evt);
            }
        });

        txtElimiarProd.setText("Eliminar Productos");
        txtElimiarProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtElimiarProdActionPerformed(evt);
            }
        });

        Logout.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Logout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/CerrarSesion32px.png"))); // NOI18N
        Logout.setToolTipText("");
        Logout.setAlignmentX(0.5F);
        Logout.setFocusable(false);
        Logout.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        Logout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LogoutMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                LogoutMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                LogoutMouseExited(evt);
            }
        });

        javax.swing.GroupLayout Panel2Layout = new javax.swing.GroupLayout(Panel2);
        Panel2.setLayout(Panel2Layout);
        Panel2Layout.setHorizontalGroup(
            Panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel2Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(Panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 966, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(Panel2Layout.createSequentialGroup()
                        .addComponent(Buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(150, 150, 150)
                        .addComponent(desplegable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(41, 41, 41)
                .addGroup(Panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Panel2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(add))
                    .addComponent(modificar)
                    .addComponent(txtElimiarProd)
                    .addComponent(cod, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Nombre, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Precio, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Marca, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Caducidad, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Contenido, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Unidades, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(Logout, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        Panel2Layout.setVerticalGroup(
            Panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel2Layout.createSequentialGroup()
                .addComponent(Logout, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 96, Short.MAX_VALUE)
                .addGroup(Panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel2Layout.createSequentialGroup()
                        .addGroup(Panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(desplegable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(41, 41, 41)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(108, 108, 108))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel2Layout.createSequentialGroup()
                        .addComponent(cod, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(Nombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(Precio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(Marca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(Caducidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(Contenido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(Unidades, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46)
                        .addComponent(add)
                        .addGap(17, 17, 17)
                        .addComponent(modificar)
                        .addGap(17, 17, 17)
                        .addComponent(txtElimiarProd)
                        .addGap(33, 33, 33))))
        );

        jPanel2.add(Panel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 0, 1220, 610));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
    }//GEN-LAST:event_formComponentResized

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

    

    
    private void actualizarTablaInventarioPorArea(String area) {
    DefaultTableModel model = (DefaultTableModel) tablita.getModel();
    model.setRowCount(0); // Limpia la tabla completamente.

    try {
         CONSULTASDAO dao = new CONSULTASDAO(Conexion_DB.getConexion());
        List<Producto> productosFiltrados = dao.obtenerProductosPorArea(area); // Implementar este método
        
        for (Producto p : productosFiltrados) {
            model.addRow(new Object[]{
                p.getCodigoBarras(),
                p.getNombre(),
                p.getMarca(),
                p.getUnidadesDisponibles(),
                p.getContenido(),
                p.getNombreArea(),
                p.getPrecio()
            });
        }
  } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error al conectar con la base de datos: " + e.getMessage(),
                                      "Error de Conexión", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}

    private void VentasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_VentasMouseClicked
    Venta ventaWindow = Venta.getInstance();
        ventaWindow.setVentanaPrincipal(this);
        ventaWindow.initialize(SesionManager.getInstance().getUsuarioLogueado());

        ventaWindow.setVisible(true);
        this.setVisible(false);
       

    }//GEN-LAST:event_VentasMouseClicked

    private void UsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_UsuariosMouseClicked
        UsuariosPanel usuariosWindow = UsuariosPanel.getInstance();
        usuariosWindow.setVentanaPrincipal(this);
        usuariosWindow.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_UsuariosMouseClicked

    private void ConfiguracionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ConfiguracionMouseClicked
    Configuraciones configWindow = Configuraciones.getInstance();
    configWindow.setVentanaPrincipal(this);
    configWindow.setVisible(true);
    this.setVisible(false);
    }//GEN-LAST:event_ConfiguracionMouseClicked

    private void MenuMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MenuMouseEntered

    }//GEN-LAST:event_MenuMouseEntered

    private void MenuMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MenuMouseExited

    }//GEN-LAST:event_MenuMouseExited

    private void InventarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_InventarioMouseClicked
       Principal2_0 principalWindow = Principal2_0.getInstance();
        //principalWindow.setVentanaPrincipal(this);
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

    private void Panel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Panel2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_Panel2MouseClicked

    private void LogoutMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LogoutMouseExited
        Logout.setOpaque(false); // Elimina el color de fondo
        Logout.setBackground(null); // Restaura el color de fondo original
    }//GEN-LAST:event_LogoutMouseExited

    private void LogoutMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LogoutMouseEntered
        Logout.setOpaque(true); // Permite que el JLabel tenga color de fondo
        Logout.setBackground(new Color(255, 0, 0, 192)); // Color rojo opaco
    }//GEN-LAST:event_LogoutMouseEntered

    private void LogoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LogoutMouseClicked
        logout();
    }//GEN-LAST:event_LogoutMouseClicked

    private void txtElimiarProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtElimiarProdActionPerformed
        String nombree = Nombre.getText();
        int areaID = desplegable.getSelectedIndex();

        int codigoB = Integer.parseInt(cod.getText());

        try{
            CONSULTASDAO dao = new CONSULTASDAO(Conexion_DB.getConexion());
            boolean exito = dao.eliminarProd(codigoB);

            if(exito){
                System.out.println("Producto " + nombree + " eliminado");
                String areaSeleccionada = (String) desplegable.getSelectedItem();
                actualizarTablaInventarioPorArea(areaSeleccionada);
                //actualizarTablaInventario();

            }else System.out.println("Producto " + nombree + " no eliminado");
        }catch (SQLException ex) {
            ex.printStackTrace();  // Imprimir detalles de error
        }
    }//GEN-LAST:event_txtElimiarProdActionPerformed

    private void modificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modificarActionPerformed
        String nombree = Nombre.getText();
        int areaID = desplegable.getSelectedIndex();

        double precioo = Double.parseDouble(Precio.getText());
        int unidadess = Integer.parseInt(Unidades.getText());
        int codigoB = Integer.parseInt(cod.getText());

        try{
            CONSULTASDAO dao = new CONSULTASDAO(Conexion_DB.getConexion());
            boolean exito = dao.actualizarProd(codigoB, precioo, unidadess);

            if(exito){
                System.out.println("Producto " + nombree + " actualizado");
                String areaSeleccionada = (String) desplegable.getSelectedItem();
                actualizarTablaInventarioPorArea(areaSeleccionada);
                //actualizarTablaInventario();

            }else System.out.println("Producto " + nombree + " no actualizado");
        }catch (SQLException ex) {
            ex.printStackTrace();  // Imprimir detalles de error
        }
    }//GEN-LAST:event_modificarActionPerformed

    private void addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addActionPerformed
        String nombree = Nombre.getText();
        int areaID = desplegable.getSelectedIndex();
        double precioo = Double.parseDouble(Precio.getText());
        int unidadess = Integer.parseInt(Unidades.getText());
        String fechaa = Caducidad.getText();
        String codigoB = cod.getText();
        String marcaa = Marca.getText();
        String contenidoo = Contenido.getText();

        try {
            CONSULTASDAO dao = new CONSULTASDAO(Conexion_DB.getConexion());

            Producto productoExistente = dao.obtenerProductoPorNombreYContenido(nombree, contenidoo);
            if (productoExistente != null) {
                // El producto ya existe
                JOptionPane.showMessageDialog(this, "El producto con el nombre " + nombree + " y contenido " + contenidoo + " ya existe.", "Producto Duplicado", JOptionPane.WARNING_MESSAGE);
            } else {
                // El producto no existe, proceder a agregar
                boolean exito = dao.crearProd(nombree, areaID, precioo, unidadess, fechaa, Integer.parseInt(codigoB), marcaa, contenidoo);
                if (exito) {
                    JOptionPane.showMessageDialog(this, "Producto agregado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    String areaSeleccionada = (String) desplegable.getSelectedItem();
                    actualizarTablaInventarioPorArea(areaSeleccionada);
                    desplegableActionPerformed(evt);
                    Nombre.setText("Nombre");
                    Precio.setText("Precio");
                    Marca.setText("Marca");
                    Caducidad.setText("Fecha de Caducidad");
                    Contenido.setText("Contenido");
                    Unidades.setText("Unidades Disponibles");
                } else {
                    JOptionPane.showMessageDialog(this, "Error al agregar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al conectar con la base de datos: " + ex.getMessage(), "Error de Conexión", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();  // Imprimir detalles de error
        }
    }//GEN-LAST:event_addActionPerformed

    private void UnidadesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_UnidadesFocusLost
        if(Unidades.getText().length()==0){
            Estilos.addPlaceholderStyle(Unidades);
            Unidades.setText("Unidades Disponibles");
        }
    }//GEN-LAST:event_UnidadesFocusLost

    private void UnidadesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_UnidadesFocusGained
        if(Unidades.getText().equals("Unidades Disponibles")){
            Unidades.setText(null);
            Unidades.requestFocus();
            Estilos.removePlaceholderStyle(Unidades);
        }
    }//GEN-LAST:event_UnidadesFocusGained

    private void ContenidoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ContenidoFocusLost
        if(Contenido.getText().length()==0){
            Estilos.addPlaceholderStyle(Contenido);
            Contenido.setText("Contenido");
        }
    }//GEN-LAST:event_ContenidoFocusLost

    private void ContenidoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ContenidoFocusGained
        if(Contenido.getText().equals("Contenido")){
            Contenido.setText(null);
            Contenido.requestFocus();
            Estilos.removePlaceholderStyle(Contenido);
        }
    }//GEN-LAST:event_ContenidoFocusGained

    private void CaducidadFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_CaducidadFocusLost
        if(Caducidad.getText().length()==0){
            Estilos.addPlaceholderStyle(Caducidad);
            Caducidad.setText("Fecha de Caducidad");
        }
    }//GEN-LAST:event_CaducidadFocusLost

    private void CaducidadFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_CaducidadFocusGained
        if(Caducidad.getText().equals("Fecha de Caducidad")){
            Caducidad.setText(null);
            Caducidad.requestFocus();
            Estilos.removePlaceholderStyle(Caducidad);
        }
    }//GEN-LAST:event_CaducidadFocusGained

    private void MarcaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_MarcaFocusLost
        if(Marca.getText().length()==0){
            Estilos.addPlaceholderStyle(Marca);
            Marca.setText("Marca");
        }
    }//GEN-LAST:event_MarcaFocusLost

    private void MarcaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_MarcaFocusGained
        if(Marca.getText().equals("Marca")){
            Marca.setText(null);
            Marca.requestFocus();
            Estilos.removePlaceholderStyle(Marca);
        }
    }//GEN-LAST:event_MarcaFocusGained

    private void PrecioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_PrecioFocusLost
        if(Precio.getText().length()==0){
            Estilos.addPlaceholderStyle(Precio);
            Precio.setText("Precio");
        }
    }//GEN-LAST:event_PrecioFocusLost

    private void PrecioFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_PrecioFocusGained
        if(Precio.getText().equals("Precio")){
            Precio.setText(null);
            Precio.requestFocus();
            Estilos.removePlaceholderStyle(Precio);
        }
    }//GEN-LAST:event_PrecioFocusGained

    private void NombreFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_NombreFocusLost
        if(Nombre.getText().length()==0){
            Estilos.addPlaceholderStyle(Nombre);
            Nombre.setText("Nombre");
        }
    }//GEN-LAST:event_NombreFocusLost

    private void NombreFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_NombreFocusGained
        if(Nombre.getText().equals("Nombre")){
            Nombre.setText(null);
            Nombre.requestFocus();
            Estilos.removePlaceholderStyle(Nombre);
        }
    }//GEN-LAST:event_NombreFocusGained

    private void desplegableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_desplegableActionPerformed
        String areaSeleccionada = (String) desplegable.getSelectedItem();

        int num = desplegable.getSelectedIndex();
        int num1 = 0;

        if (desplegable.getSelectedIndex() != 0) {
            actualizarTablaInventarioPorArea(areaSeleccionada);

            try{
                CONSULTASDAO dao = new CONSULTASDAO(Conexion_DB.getConexion());
                int codigo = dao.getCod(num);
                num1 = codigo + 1;
                cod.setText("" + num1);

            } catch (SQLException ex) {
                //Logger.getLogger(Add.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("QUE WEY SOY X2");
            }

        } else {
            actualizarTablaInventario();
        }
    }//GEN-LAST:event_desplegableActionPerformed

    private void tablitaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablitaMouseClicked
        int fila = tablita.getSelectedRow();

        if (fila != -1) {
            String codigoBarras = tablita.getValueAt(fila, 0).toString();
            String nombre = tablita.getValueAt(fila, 1).toString();
            String marca = tablita.getValueAt(fila, 2).toString();
            String unidades = tablita.getValueAt(fila, 3).toString();
            String contenido = tablita.getValueAt(fila, 4).toString();
            String precio = tablita.getValueAt(fila, 6).toString();

            // Asumiendo que tienes JTextFields para cada uno de estos valores
            cod.setText(codigoBarras);
            Nombre.setText(nombre);
            Marca.setText(marca);
            Unidades.setText(unidades);
            Contenido.setText(contenido);
            Precio.setText(precio);
        } else {
            JOptionPane.showMessageDialog(null, "No se seleccionó ninguna fila");
        }
    }//GEN-LAST:event_tablitaMouseClicked

    private void BuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BuscarKeyReleased

    }//GEN-LAST:event_BuscarKeyReleased

    private void BuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarActionPerformed

    }//GEN-LAST:event_BuscarActionPerformed

    private void BuscarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_BuscarFocusLost
        if(Buscar.getText().length()==0){
            //addPlaceholderStyle(fieldUser);
            Buscar.setText("Buscar");
        }
    }//GEN-LAST:event_BuscarFocusLost

    private void BuscarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_BuscarFocusGained
        if(Buscar.getText().equals("Buscar")){
            Buscar.setText(null);
            Buscar.requestFocus();
            //removePlaceholderStyle(fieldUser);

        }
    }//GEN-LAST:event_BuscarFocusGained
    // Método para cerrar sesión
    private void logout() {
        SesionManager.getInstance().logout();
        LOGINN loginWindow = new LOGINN();
        loginWindow.setVisible(true);
        this.dispose();
    }
    
    private void ajustarTamanioColumnas() {
    TableColumnModel modeloColumna = tablita.getColumnModel();
    modeloColumna.getColumn(0).setPreferredWidth(50); 
    modeloColumna.getColumn(1).setPreferredWidth(190); 
    modeloColumna.getColumn(2).setPreferredWidth(100); 
    modeloColumna.getColumn(3).setPreferredWidth(50); 
    modeloColumna.getColumn(4).setPreferredWidth(100); 
    modeloColumna.getColumn(5).setPreferredWidth(100); 
    modeloColumna.getColumn(6).setPreferredWidth(40);
 
    // Ajustar el alto de las filas
    tablita.setRowHeight(25); // Altura de 25 píxeles para las filas

    modeloColumna.getColumn(0).setResizable(false);
        modeloColumna.getColumn(6).setResizable(false);

    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment(JLabel.CENTER);
    
    // Aplica este renderizador a cada columna de la tabla
    for (int i = 0; i < tablita.getColumnCount(); i++) {
        tablita.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
    }
      // Centrar encabezados de la tabla
    ((DefaultTableCellRenderer) tablita.getTableHeader().getDefaultRenderer())
            .setHorizontalAlignment(JLabel.CENTER);

  
}
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
            java.util.logging.Logger.getLogger(Principal2_0.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal2_0.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal2_0.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal2_0.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Principal2_0().setVisible(true);
            }
        });
        // Ajuste del ancho de columna basado en el contenido
        
        
    }

    
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Analisis;
    private javax.swing.JTextField Buscar;
    private javax.swing.JTextField Caducidad;
    private javax.swing.JLabel Configuracion;
    private javax.swing.JTextField Contenido;
    private javax.swing.JLabel Inicio;
    private javax.swing.JLabel Inventario;
    private javax.swing.JLabel Logout;
    private javax.swing.JTextField Marca;
    private javax.swing.JLabel Menu;
    private javax.swing.JPanel MenuPlegable;
    private javax.swing.JTextField Nombre;
    private javax.swing.JPanel Panel2;
    private javax.swing.JTextField Precio;
    private javax.swing.JTextField Unidades;
    private javax.swing.JLabel Usuarios;
    private javax.swing.JLabel Ventas;
    private javax.swing.JButton add;
    private javax.swing.JLabel cod;
    private javax.swing.JComboBox<String> desplegable;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton modificar;
    private javax.swing.JTable tablita;
    private javax.swing.JButton txtElimiarProd;
    // End of variables declaration//GEN-END:variables
}
