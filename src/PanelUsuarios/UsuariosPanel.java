/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package PanelUsuarios;
import ConexionDB.*;
import DBObjetos.Area;
import INVENTARIO.*;
import DBObjetos.Usuario;
import DBObjetos.Usuario.Rol;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import login.*;
import javax.swing.SwingConstants;
import net.coobird.thumbnailator.Thumbnails;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import Configuraciones.Configuraciones;
import Venta.Venta;
import Consultas.CONSULTASDAO;
import Graficas.AvisosFrame;
import Graficas.ObservarGraficas;
import java.awt.Color;
import login.SesionManager;
import Principal.MenuPrincipal;
import javax.swing.ImageIcon;
import javax.swing.table.TableCellRenderer;


/**
 *
 * @author braul
 */
public class UsuariosPanel extends javax.swing.JFrame {

    
    private Principal2_0 ventanaPrincipal;
    private static UsuariosPanel instance;
    private AnimacionPanel animador; // Añade esta línea
    private boolean menuDesplegado = false;
    private Usuario usuarioLogueado;

     
     
     private Venta venta;

       // Se modificó el constructor para ser privado

    private  UsuariosPanel() {
        

        animador = new AnimacionPanel(); // Inicializa el animador

        initComponents();
        setLocationRelativeTo(null); // Centramos la ventana en la pantalla

        configurarEncabezadosTabla();
        
        ajustarTamanioColumnasYFilas();
        configurarTablaYEventos();

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                cerrarYVolver();
            }
        });
      
        ajustarAlturaFilas();
        
        
 
                configureButton();
                
         cargarAreasEnComboBox();

    }
    

    
        // Se agregó este método para obtener la instancia única de UsuariosPanel
    public static UsuariosPanel getInstance() {
        if (instance == null) {
            instance = new UsuariosPanel();
        }
        return instance;
    }

    public void setVentanaPrincipal(Principal2_0 principal) {
        this.ventanaPrincipal = principal;
    }

    private void cerrarYVolver() {
        if (ventanaPrincipal != null) {
            ventanaPrincipal.setVisible(true); // Hace visible la ventana principal nuevamente
        }
        dispose(); // Cierra esta ventana
    }
    
    
    public void initialize(Usuario usuario) {
        this.usuarioLogueado = usuario;
        configurarVisibilidadComponentes();
        setVisible(true);  // Asegúrate de que la ventana se hace visible aquí o en el lugar desde donde llamas a initialize
    }
    
    
        private void configurarVisibilidadComponentes() {
        if (usuarioLogueado.getRol() == Usuario.Rol.EMPLEADO) {
            Usuarios2.setVisible(false);
            Configuracion2.setVisible(false);
            Analisis2.setVisible(false);
        } else if (usuarioLogueado.getRol() == Usuario.Rol.ADMINISTRADOR) {
            // El administrador puede ver todo, así que no necesitas hacer nada aquí
        }
    }
    
            
        private void configureButton() {
        try {
            BufferedImage icon = Thumbnails.of(getClass().getResourceAsStream("/icons/Agregar.png"))
                    .size(20, 20)
                    .asBufferedImage();
            NuevoUsuario.setIcon(new ImageIcon(icon));
            NuevoUsuario.setHorizontalTextPosition(SwingConstants.RIGHT);
            NuevoUsuario.setIconTextGap(5);  // Ajusta el espacio entre el ícono y el texto
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar la imagen: " + e.getMessage(), "Error de Carga de Imagen", JOptionPane.ERROR_MESSAGE);
        }

        try {
            BufferedImage icon = Thumbnails.of(getClass().getResourceAsStream("/icons/Token.png"))
                    .size(20, 20)
                    .asBufferedImage();
            btnRecuperarToken.setIcon(new ImageIcon(icon));
            btnRecuperarToken.setHorizontalTextPosition(SwingConstants.RIGHT);
            btnRecuperarToken.setIconTextGap(5);  // Ajusta el espacio entre el ícono y el texto
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar la imagen: " + e.getMessage(), "Error de Carga de Imagen", JOptionPane.ERROR_MESSAGE);
        }
        
    }
        
    
    private void ajustarAlturaFilas() {
    for (int row = 0; row < Empleados.getRowCount(); row++) {
        int rowHeight = Empleados.getRowHeight(row); // Obtén la altura actual de la fila

        for (int column = 0; column < Empleados.getColumnCount(); column++) {
            TableCellRenderer renderer = Empleados.getCellRenderer(row, column);
            Component comp = Empleados.prepareRenderer(renderer, row, column);
            rowHeight = Math.max(rowHeight, comp.getPreferredSize().height); // Encuentra el máximo entre la altura actual y la del componente
        }

        Empleados.setRowHeight(row, rowHeight); // Establece la nueva altura de la fila
    }
}

    
    
    

    private void configurarTablaYEventos() {
        
    // Rutas de los íconos, asegúrate de que son accesibles y correctas
    String eliminarIconPath = "/icons/Eliminar.png";
    String modificarIconPath = "/icons/Modificar.png";

    // Crear renderizadores para las acciones de eliminar y modificar
    ButtonRenderer buttonRendererEliminar = new ButtonRenderer(eliminarIconPath, 32, 32);
    ButtonRenderer buttonRendererModificar = new ButtonRenderer(modificarIconPath, 32, 32);

    // Configurar los renderizadores para las columnas correspondientes
    Empleados.getColumnModel().getColumn(5).setCellRenderer(buttonRendererEliminar);
    Empleados.getColumnModel().getColumn(6).setCellRenderer(buttonRendererModificar);

    
    // Agregar un MouseListener para cambiar el estado del botón
 Empleados.addMouseMotionListener(new MouseAdapter() {
        private int lastRow = -1;
        private int lastColumn = -1;
        @Override
        public void mouseMoved(MouseEvent e) {
            int row = Empleados.rowAtPoint(e.getPoint());
            int column = Empleados.columnAtPoint(e.getPoint());
            if (column == 5 || column == 6) {
                if (row != lastRow || column != lastColumn) {
                    if (lastRow != -1 && lastColumn != -1) {
                        // Limpiar el hover anterior
                        ((ButtonRenderer) Empleados.getCellRenderer(lastRow, lastColumn)).setPressed(false, lastRow, lastColumn);
                        Empleados.repaint(Empleados.getCellRect(lastRow, lastColumn, false));
                    }
                    // Establecer nuevo hover
                    ((ButtonRenderer) Empleados.getCellRenderer(row, column)).setPressed(true, row, column);
                    Empleados.repaint(Empleados.getCellRect(row, column, false));
                    lastRow = row;
                    lastColumn = column;
                }
            } else {
                if (lastRow != -1 && lastColumn != -1) {
                    // Limpiar el hover si se mueve fuera de las columnas activas
                    ((ButtonRenderer) Empleados.getCellRenderer(lastRow, lastColumn)).setPressed(false, lastRow, lastColumn);
                    Empleados.repaint(Empleados.getCellRect(lastRow, lastColumn, false));
                    lastRow = -1;
                    lastColumn = -1;
                }
            }
        }
        
                @Override
        public void mouseExited(MouseEvent e) {
            if (lastRow != -1 && lastColumn != -1) {
                // Limpiar el hover cuando el ratón sale de la tabla
                ((ButtonRenderer) Empleados.getCellRenderer(lastRow, lastColumn)).setPressed(false, lastRow, lastColumn);
                Empleados.repaint(Empleados.getCellRect(lastRow, lastColumn, false));
                lastRow = -1;
                lastColumn = -1;
            }
        }
    });

    Empleados.addMouseListener(new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            int row = Empleados.rowAtPoint(e.getPoint());
            int column = Empleados.columnAtPoint(e.getPoint());
            if (column == 5 || column == 6) {
                if (column == 5) {
                    buttonRendererEliminar.setPressed(true, row, column);
                } else {
                    buttonRendererModificar.setPressed(true, row, column);
                }
                Empleados.repaint();
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            int row = Empleados.rowAtPoint(e.getPoint());
            int column = Empleados.columnAtPoint(e.getPoint());
            if (column == 5 || column == 6) {
                if (column == 5) {
                    buttonRendererEliminar.setPressed(false, row, column);
                    int usuarioId = Integer.parseInt(Empleados.getValueAt(row, 0).toString());
                    confirmarYEliminarUsuario(usuarioId, row);
                } else if (column == 6) {
                    buttonRendererModificar.setPressed(false, row, column);
                    int usuarioId = Integer.parseInt(Empleados.getValueAt(row, 0).toString());
                    mostrarDialogoModificarUsuario(usuarioId, row);
                }
                Empleados.repaint();
            }
        }
    });
    
   actualizarTablaEmpleados();
   ajustarAlturaFilas();
}

     private void clearHoverEffects() {
    for (int row = 0; row < Empleados.getRowCount(); row++) {
        for (int col : new int[]{5, 6}) {
            ButtonRenderer renderer = (ButtonRenderer) Empleados.getCellRenderer(row, col);
            if (renderer != null) {
                renderer.setPressed(false, row, col);
                Empleados.repaint(Empleados.getCellRect(row, col, false));
            }
        }
    }
}    
    
    
    private void confirmarYEliminarUsuario(int usuarioId, int row) {
    int confirm = JOptionPane.showConfirmDialog(this, "¿Estás seguro de querer eliminar este usuario?", "Confirmación", JOptionPane.YES_NO_OPTION);
    if (confirm == JOptionPane.YES_OPTION) {
        try {
            CONSULTASDAO dao = new CONSULTASDAO(Conexion_DB.getConexion());
            if (dao.eliminarUsuario(usuarioId)) {
                ((DefaultTableModel) Empleados.getModel()).removeRow(row);
                JOptionPane.showMessageDialog(this, "Usuario eliminado correctamente.");
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar el usuario.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al conectar con la base de datos: " + ex.getMessage(), "Error de conexión", JOptionPane.ERROR_MESSAGE);
        }
    }
}
private void mostrarDialogoModificarUsuario(int usuarioId, int row) {
    try {
        CONSULTASDAO dao = new CONSULTASDAO(Conexion_DB.getConexion());
        Usuario usuarioActual = dao.obtenerDetallesUsuario(usuarioId);
        if (usuarioActual == null) {
            JOptionPane.showMessageDialog(null, "No se encontró el usuario.");
            return;
        }

        // Recuperar datos desde la tabla y aplicar trim() para eliminar espacios extra
        String nombreCompletoNuevo = Empleados.getValueAt(row, 1).toString().trim();
        String emailNuevo = Empleados.getValueAt(row, 2).toString().trim();
        String rolStrNuevo = Empleados.getValueAt(row, 3).toString().trim(); // Recuperado como String
        String nombreUsuarioNuevo = Empleados.getValueAt(row, 4).toString().trim();

        // Convertir String a Enum
        Rol rolNuevo = Rol.valueOf(rolStrNuevo.toUpperCase()); // Asumiendo que Rol es el nombre del enum

        System.out.println("Actual: " + usuarioActual.getNombreCompleto() + ", Nuevo: " + nombreCompletoNuevo);
        System.out.println("Actual: " + usuarioActual.getEmail() + ", Nuevo: " + emailNuevo);
        System.out.println("Actual: " + usuarioActual.getRol() + ", Nuevo: " + rolNuevo);
        System.out.println("Actual: " + usuarioActual.getNombreUsuario() + ", Nuevo: " + nombreUsuarioNuevo);

        
         // Usar equalsIgnoreCase para comparar sin tener en cuenta mayúsculas y minúsculas
        if (!nombreCompletoNuevo.equalsIgnoreCase(usuarioActual.getNombreCompleto()) ||
            !emailNuevo.equalsIgnoreCase(usuarioActual.getEmail()) ||
            usuarioActual.getRol() != rolNuevo  ||
            !nombreUsuarioNuevo.equalsIgnoreCase(usuarioActual.getNombreUsuario())) {

            int confirm = JOptionPane.showConfirmDialog(null, "Cambios detectados. ¿Desea guardar los cambios?", "Confirmar Actualización", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                boolean actualizado = dao.actualizarUsuario(usuarioId, nombreUsuarioNuevo, "", rolNuevo.toString(), emailNuevo, nombreCompletoNuevo, "", "");
                if (actualizado) {
                    JOptionPane.showMessageDialog(null, "Usuario actualizado con éxito.");
                    actualizarTablaEmpleados();
                } else {
                    JOptionPane.showMessageDialog(null, "Error al actualizar el usuario.");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "No se detectaron cambios.");
        }
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(null, "Error al conectar con la base de datos: " + ex.getMessage(), "Error de conexión", JOptionPane.ERROR_MESSAGE);
    }
}





private void configurarEncabezadosTabla() {
    // Nombres de columnas como en el primer código
    String[] titulo = new String[]{"UsuarioID", "Nombre Completo", "Email", "Rol", "Nombre de Usuario", "Eliminar", "Modificar"};
    DefaultTableModel dtm = (DefaultTableModel) Empleados.getModel();
    dtm.setColumnIdentifiers(titulo);
    Empleados.setModel(dtm);

}



/**h
 * Ajusta el tamaño de las columnas y filas de la tabla.
 */
private void ajustarTamanioColumnasYFilas() {
    // Ajustar el tamaño de cada columna según su contenido
    TableColumnModel columnModel = Empleados.getColumnModel();
    for (int column = 0; column < Empleados.getColumnCount(); column++) {
        int width = 50; // Min width
        for (int row = 0; row < Empleados.getRowCount(); row++) {
            TableCellRenderer renderer = Empleados.getCellRenderer(row, column);
            Component comp = Empleados.prepareRenderer(renderer, row, column);
            width = Math.max(comp.getPreferredSize().width + 1 , width);
        }
        if (width > 300)
            width = 300; // Máximo ancho permitido para cualquier columna
        columnModel.getColumn(column).setPreferredWidth(width);
    }

    // Ajustar la altura de las filas para ajustarse al contenido
    for (int row = 0; row < Empleados.getRowCount(); row++) {
        int rowHeight = Empleados.getRowHeight();
        for (int column = 0; column < Empleados.getColumnCount(); column++) {
            TableCellRenderer renderer = Empleados.getCellRenderer(row, column);
            Component comp = Empleados.prepareRenderer(renderer, row, column);
            rowHeight = Math.max(rowHeight, comp.getPreferredSize().height);
        }
        Empleados.setRowHeight(row, rowHeight);
    }
    actualizarTablaEmpleados();
}


    
    private void actualizarTablaEmpleados() {
    DefaultTableModel model = (DefaultTableModel) Empleados.getModel();
    model.setRowCount(0); // Limpia la tabla completamente.

    try {
        CONSULTASDAO dao = new CONSULTASDAO(Conexion_DB.getConexion());
         List<Usuario> listaUsuarios = dao.obtenerUsuariosSimplificado(); // Obtiene la lista de productos con su área

        // Recorre la lista y añade filas al modelo de la tabla
        for (Usuario usuario : listaUsuarios) {
            model.addRow(new Object[]{
                usuario.getUsuarioID(),
                usuario.getNombreCompleto(),
                usuario.getEmail(),
                usuario.getRol().toString(),
                usuario.getNombreUsuario(),
                "", // Campo vacío para 'Eliminar'
                ""  // Campo vacío para 'Modificar'
                
            });
        }
 
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error al conectar con la base de datos: " + e.getMessage(), "Error de Conexión", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel6 = new javax.swing.JPanel();
        MenuPlegable2 = new javax.swing.JPanel();
        Menu2 = new javax.swing.JLabel();
        Inicio2 = new javax.swing.JLabel();
        Usuarios2 = new javax.swing.JLabel();
        Configuracion2 = new javax.swing.JLabel();
        Ventas2 = new javax.swing.JLabel();
        Analisis2 = new javax.swing.JLabel();
        Inventario2 = new javax.swing.JLabel();
        Panel2 = new javax.swing.JPanel();
        btnRecuperarToken = new javax.swing.JButton();
        NuevoUsuario = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        Empleados = new javax.swing.JTable();
        btnPrecios = new javax.swing.JButton();
        txtPorcentaje = new javax.swing.JTextField();
        areaSeleccionada = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        MenuPlegable2.setBackground(new java.awt.Color(204, 204, 204));
        MenuPlegable2.setLayout(null);

        Menu2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        Menu2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Menu32px.png"))); // NOI18N
        Menu2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 10));
        Menu2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Menu2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Menu2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Menu2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Menu2MouseExited(evt);
            }
        });
        MenuPlegable2.add(Menu2);
        Menu2.setBounds(0, 0, 170, 50);

        Inicio2.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        Inicio2.setForeground(new java.awt.Color(255, 255, 255));
        Inicio2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        Inicio2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Inicio32px.png"))); // NOI18N
        Inicio2.setText("Inicio");
        Inicio2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 10));
        Inicio2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Inicio2.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        Inicio2.setIconTextGap(15);
        Inicio2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Inicio2MouseClicked(evt);
            }
        });
        MenuPlegable2.add(Inicio2);
        Inicio2.setBounds(0, 70, 170, 50);

        Usuarios2.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        Usuarios2.setForeground(new java.awt.Color(255, 255, 255));
        Usuarios2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        Usuarios2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Usuarios32px.png"))); // NOI18N
        Usuarios2.setText("Usuarios");
        Usuarios2.setToolTipText("");
        Usuarios2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 10));
        Usuarios2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Usuarios2.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        Usuarios2.setIconTextGap(15);
        Usuarios2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Usuarios2MouseClicked(evt);
            }
        });
        MenuPlegable2.add(Usuarios2);
        Usuarios2.setBounds(0, 440, 170, 50);

        Configuracion2.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        Configuracion2.setForeground(new java.awt.Color(255, 255, 255));
        Configuracion2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        Configuracion2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Configuraciones32px.png"))); // NOI18N
        Configuracion2.setText("Configuracion");
        Configuracion2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 10));
        Configuracion2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Configuracion2.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        Configuracion2.setIconTextGap(15);
        Configuracion2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Configuracion2MouseClicked(evt);
            }
        });
        MenuPlegable2.add(Configuracion2);
        Configuracion2.setBounds(0, 530, 170, 50);

        Ventas2.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        Ventas2.setForeground(new java.awt.Color(255, 255, 255));
        Ventas2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        Ventas2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Venta32px.png"))); // NOI18N
        Ventas2.setText("Venta");
        Ventas2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 10));
        Ventas2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Ventas2.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        Ventas2.setIconTextGap(15);
        Ventas2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Ventas2MouseClicked(evt);
            }
        });
        MenuPlegable2.add(Ventas2);
        Ventas2.setBounds(0, 160, 170, 50);

        Analisis2.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        Analisis2.setForeground(new java.awt.Color(255, 255, 255));
        Analisis2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        Analisis2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Graficas32px.png"))); // NOI18N
        Analisis2.setText("Analisis");
        Analisis2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 10));
        Analisis2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Analisis2.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        Analisis2.setIconTextGap(15);
        Analisis2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Analisis2MouseClicked(evt);
            }
        });
        MenuPlegable2.add(Analisis2);
        Analisis2.setBounds(0, 350, 170, 50);

        Inventario2.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        Inventario2.setForeground(new java.awt.Color(255, 255, 255));
        Inventario2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        Inventario2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/Inventario32px.png"))); // NOI18N
        Inventario2.setText("Inventario");
        Inventario2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 10));
        Inventario2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Inventario2.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        Inventario2.setIconTextGap(15);
        Inventario2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Inventario2MouseClicked(evt);
            }
        });
        MenuPlegable2.add(Inventario2);
        Inventario2.setBounds(0, 260, 170, 50);

        jPanel6.add(MenuPlegable2, new org.netbeans.lib.awtextra.AbsoluteConstraints(-120, 0, 170, 610));

        Panel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Panel2MouseClicked(evt);
            }
        });

        btnRecuperarToken.setText("Recuperar Token");
        btnRecuperarToken.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRecuperarTokenActionPerformed(evt);
            }
        });

        NuevoUsuario.setBackground(new java.awt.Color(190, 184, 209));
        NuevoUsuario.setText("Registrar");
        NuevoUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NuevoUsuarioActionPerformed(evt);
            }
        });

        Empleados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(Empleados);

        btnPrecios.setText("jButton1");
        btnPrecios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreciosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout Panel2Layout = new javax.swing.GroupLayout(Panel2);
        Panel2.setLayout(Panel2Layout);
        Panel2Layout.setHorizontalGroup(
            Panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1220, Short.MAX_VALUE)
            .addGroup(Panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(Panel2Layout.createSequentialGroup()
                    .addGap(20, 20, 20)
                    .addGroup(Panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(Panel2Layout.createSequentialGroup()
                            .addGap(9, 9, 9)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 734, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 437, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel2Layout.createSequentialGroup()
                            .addComponent(NuevoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(35, 35, 35)
                            .addComponent(btnRecuperarToken)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(Panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtPorcentaje)
                                .addComponent(btnPrecios)
                                .addComponent(areaSeleccionada, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGap(20, 20, 20)))
        );
        Panel2Layout.setVerticalGroup(
            Panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 610, Short.MAX_VALUE)
            .addGroup(Panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(Panel2Layout.createSequentialGroup()
                    .addGap(29, 29, 29)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 227, Short.MAX_VALUE)
                    .addComponent(areaSeleccionada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(txtPorcentaje, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(Panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(Panel2Layout.createSequentialGroup()
                            .addGroup(Panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(NuevoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnRecuperarToken, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(10, 10, 10))
                        .addComponent(btnPrecios, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addGap(30, 30, 30)))
        );

        jPanel6.add(Panel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 0, 1220, 610));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void NuevoUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NuevoUsuarioActionPerformed
        // Obtener el rol para la creación del nuevo usuario
        String[] roles = {"ADMINISTRADOR", "GERENTE", "SUPERVISOR", "EMPLEADO"};
        String rolSeleccionado = (String) JOptionPane.showInputDialog(
            this,
            "Seleccione el tipo de usuario a crear:",
            "Creación de usuario",
            JOptionPane.QUESTION_MESSAGE,
            null,
            roles,
            roles[0]
        );

        if (rolSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un rol.");
            return;
        }

        // Solicitar la llave maestra
        String contraseñaIngresada = JOptionPane.showInputDialog(this, "Ingrese la llave maestra:");
        if (contraseñaIngresada == null || contraseñaIngresada.isEmpty()) {
            JOptionPane.showMessageDialog(this, "La llave maestra no puede estar vacía.");
            return;
        }
        try {
            CONSULTASDAO consultasDAO = new CONSULTASDAO(Conexion_DB.getConexion());
            String contraseñaHasheadaIngresada = HashingUtil.hashPassword(contraseñaIngresada);
            boolean esValido = false;

            // Verifica la llave maestra para cualquier rol con permiso si el usuario a crear es EMPLEADO
            if ("EMPLEADO".equals(rolSeleccionado)) {
                for (String rol : new String[]{"ADMINISTRADOR", "GERENTE", "SUPERVISOR"}) {
                    String contraseñaHasheadaAlmacenada = consultasDAO.obtenerContraseñaTokenPorRol(rol);
                    if (contraseñaHasheadaIngresada.equals(contraseñaHasheadaAlmacenada)) {
                        esValido = true;
                        break;  // Sale del bucle si encuentra una coincidencia
                    }
                }
            }else {
                // Para los roles ADMINISTRADOR, GERENTE y SUPERVISOR, verifica la llave maestra directamente
                String contraseñaHasheadaAlmacenada = consultasDAO.obtenerContraseñaTokenPorRol(rolSeleccionado);
                esValido = contraseñaHasheadaIngresada != null && contraseñaHasheadaIngresada.equals(contraseñaHasheadaAlmacenada);
            }

            if (esValido) {
                abrirFormularioCreacionUsuario(rolSeleccionado);
            } else {
                JOptionPane.showMessageDialog(this, "Llave maestra incorrecta.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al verificar la llave maestra.");
        }
    }//GEN-LAST:event_NuevoUsuarioActionPerformed

    private void btnRecuperarTokenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRecuperarTokenActionPerformed
    Usuario usuario = SesionManager.getInstance().getUsuarioLogueado();
    if (usuario == null) {
        JOptionPane.showMessageDialog(this, "Ningún usuario está logueado.");
        return;
    }

    String email = usuario.getEmail();
    if (email == null || email.isEmpty()) {
        JOptionPane.showMessageDialog(this, "No hay un correo electrónico asociado con el usuario logueado.");
        return;
    }

    // Generar y enviar el código de verificación
    VerificacionContraseña verificacion = new VerificacionContraseña();
    int codigo = VerificacionContraseña.getCodigoVerificacion();
    MandarCorreos correoService = new MandarCorreos();
    correoService.enviarCodigoVerificacion(email, codigo);

    // Informar al usuario que el correo ha sido enviado
    JOptionPane.showMessageDialog(this, "Correo de verificación enviado a: " + email);

    
    // Solicitar el código del usuario
    String codigoIngresado = JOptionPane.showInputDialog(this, "Ingrese el código de verificación enviado a su correo:");
    try {
        int codigoVerif = Integer.parseInt(codigoIngresado);
    if (codigoVerif == codigo) {
        PasswordDialog passwordDialog = new PasswordDialog(null);  // Asumiendo que no tienes un JFrame, usa null o pasa tu JFrame
        if (passwordDialog.showDialog() == JOptionPane.OK_OPTION) {
            String nuevaContraseñaToken = passwordDialog.getPassword();
            if (UpdatePassword.updatePasswordToken(usuario.getUsuarioID(), nuevaContraseñaToken)) {
                JOptionPane.showMessageDialog(this, "Contraseña token actualizada correctamente.");
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar la contraseña token.");
            }
        }
    } else {
        JOptionPane.showMessageDialog(this, "Código de verificación incorrecto.");
    }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Entrada inválida. Por favor, ingrese solo números.");
    }     
        
    }//GEN-LAST:event_btnRecuperarTokenActionPerformed




private void cargarAreasEnComboBox() {
    try {
        CONSULTASDAO dao = new CONSULTASDAO(Conexion_DB.getConexion());
        List<Area> areas = dao.obtenerAreas();
        areaSeleccionada.removeAllItems();  // Limpiar el JComboBox antes de llenarlo
        for (Area area : areas) {
            areaSeleccionada.addItem(area.getNombreArea());  // Añade solo el nombre al JComboBox
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error al cargar las áreas: " + e.getMessage(), "Error de Conexión", JOptionPane.ERROR_MESSAGE);
    }
}

// Método para obtener el ID del área seleccionada basado en el nombre
private int obtenerAreaID(String nombreAreaSeleccionada) throws SQLException, IllegalArgumentException {
    CONSULTASDAO dao = new CONSULTASDAO(Conexion_DB.getConexion());
    List<Area> areas = dao.obtenerAreas();
    for (Area area : areas) {
        if (area.getNombreArea().equals(nombreAreaSeleccionada)) {
            return area.getAreaID();
        }
    }
    throw new IllegalArgumentException("Área no encontrada.");
}

    private void btnPreciosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreciosActionPerformed
        String nombreAreaSeleccionada = (String) areaSeleccionada.getSelectedItem();
        if (nombreAreaSeleccionada == null || txtPorcentaje.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un área y especifique un porcentaje.");
            return;
        }

        try {
            double porcentaje = Double.parseDouble(txtPorcentaje.getText().trim());
            int areaID = obtenerAreaID(nombreAreaSeleccionada);

            CONSULTASDAO dao = new CONSULTASDAO(Conexion_DB.getConexion());
            boolean actualizado = dao.actualizarPorcentajeGanancia(areaID, porcentaje);
            if (actualizado) {
                JOptionPane.showMessageDialog(this, "Porcentaje de ganancia actualizado con éxito para " + nombreAreaSeleccionada + ".");
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo actualizar el porcentaje.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, introduzca un valor numérico válido para el porcentaje.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al conectar con la base de datos: " + e.getMessage(), "Error de Conexión", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnPreciosActionPerformed

    private void Menu2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Menu2MouseClicked
    // Mover dependiendo de la posición actual
    if (MenuPlegable2.getX() == 0) {
        // Mover ambos componentes hacia la izquierda
        animador.animar(MenuPlegable2, Panel2, -120, Panel2.getX() - 120, false);
        MenuPlegable2.setBackground(new Color(204, 204, 204)); // Cambiar al color cuando está plegado
        menuDesplegado = false;
    } else if (MenuPlegable2.getX() == -120) {
        // Mover ambos componentes hacia la derecha
        animador.animar(MenuPlegable2, Panel2, 0, Panel2.getX() + 120, true);
        MenuPlegable2.setBackground(new Color(51, 51, 51)); // Cambiar al color cuando está desplegado
        menuDesplegado = true;
    }
        HoverEffect.setMenuDesplegado(menuDesplegado); // Actualizar el estado en HoverEffect
    }//GEN-LAST:event_Menu2MouseClicked

    private void Menu2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Menu2MouseEntered

    }//GEN-LAST:event_Menu2MouseEntered

    private void Menu2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Menu2MouseExited

    }//GEN-LAST:event_Menu2MouseExited

    private void Inicio2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Inicio2MouseClicked
        MenuPrincipal menuPrincipal = MenuPrincipal.getInstance();
        //principalWindow.setVentanaPrincipal(this);
        menuPrincipal.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_Inicio2MouseClicked

    private void Usuarios2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Usuarios2MouseClicked

    }//GEN-LAST:event_Usuarios2MouseClicked

    private void Configuracion2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Configuracion2MouseClicked
        Configuraciones configWindow = Configuraciones.getInstance();
        //configWindow.setVentanaPrincipal(this);
        configWindow.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_Configuracion2MouseClicked

    private void Ventas2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Ventas2MouseClicked
        Venta ventaWindow = Venta.getInstance();
        //ventaWindow.setVentanaPrincipal(this);
        ventaWindow.initialize(SesionManager.getInstance().getUsuarioLogueado());

        ventaWindow.setVisible(true);
        this.setVisible(false);

    }//GEN-LAST:event_Ventas2MouseClicked

    private void Analisis2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Analisis2MouseClicked
        AvisosFrame avisosFrame = AvisosFrame.getInstance();
        avisosFrame.initialize(SesionManager.getInstance().getUsuarioLogueado());
        avisosFrame.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_Analisis2MouseClicked

    private void Inventario2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Inventario2MouseClicked
        Principal2_0 principalWindow = Principal2_0.getInstance();
        //principalWindow.setVentanaPrincipal(this);
        principalWindow.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_Inventario2MouseClicked

    private void Panel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Panel2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_Panel2MouseClicked
/*
    private void abrirFormularioCreacionUsuario(String rol) {
    FormularioNuevoUsuario formularioNuevoUsuario = new FormularioNuevoUsuario(rol);
    formularioNuevoUsuario.setVisible(true);
    this.setVisible(false); // Opcional: Oculta el formulario de login
}
*/
    private void abrirFormularioCreacionUsuario(String rol) {
        FormularioNuevoUsuario formularioNuevoUsuario = new FormularioNuevoUsuario(rol);
        formularioNuevoUsuario.setUsuariosPanel(this);  // Asegúrate de definir este método en FormularioNuevoUsuario
        formularioNuevoUsuario.setVisible(true);
        this.setVisible(false);
    }

    public void mostrar() {
        this.setVisible(true);
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
            java.util.logging.Logger.getLogger(UsuariosPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UsuariosPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UsuariosPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UsuariosPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UsuariosPanel().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Analisis2;
    private javax.swing.JLabel Configuracion2;
    private javax.swing.JTable Empleados;
    private javax.swing.JLabel Inicio2;
    private javax.swing.JLabel Inventario2;
    private javax.swing.JLabel Menu2;
    private javax.swing.JPanel MenuPlegable2;
    private javax.swing.JButton NuevoUsuario;
    private javax.swing.JPanel Panel2;
    private javax.swing.JLabel Usuarios2;
    private javax.swing.JLabel Ventas2;
    private javax.swing.JComboBox<String> areaSeleccionada;
    private javax.swing.JButton btnPrecios;
    private javax.swing.JButton btnRecuperarToken;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtPorcentaje;
    // End of variables declaration//GEN-END:variables
}
