/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Graficas;


import DBObjetos.Producto;
import java.time.LocalDate;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
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
import java.text.ParseException;
import java.util.Date;
import java.util.Map;
import javax.swing.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import login.SesionManager;
import java.time.LocalDate;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import javax.swing.JPanel;
import login.SesionManager;

/**
 *
 * @author Luis
 */
public class AvisosFrame extends javax.swing.JFrame {

    /**
     * Creates new form AvisosFrame
     */
    
    private static AvisosFrame instance;
    private Usuario usuarioLogueado;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private boolean isCalendarOpen = false; // Controla si el calendario está abierto

    private AnimacionPanel animador;
            private boolean menuDesplegado = false;

    private DefaultTableModel modeloReabastecer;
    
    private DefaultTableModel modeloOferta;
    
    public AvisosFrame() {
        initComponents();
        agregarEfectoHover();
        modeloReabastecer = (DefaultTableModel)pocas.getModel();
        modeloOferta = (DefaultTableModel)prox.getModel();
        
        prox = new JTable(modeloOferta);
        pocas = new JTable(modeloReabastecer);
        
        comparar();
        animador = new AnimacionPanel(); // Inicializa el animador

    }

    
        private void agregarEfectoHover() {
        HoverEffect.applyHoverEffect(Menu2);
        HoverEffect.applyHoverEffect(Inicio2);
        HoverEffect.applyHoverEffect(Usuarios2);
        HoverEffect.applyHoverEffect(Configuracion2);
        HoverEffect.applyHoverEffect(Ventas2);
        HoverEffect.applyHoverEffect(Analisis2);
        HoverEffect.applyHoverEffect(Inventario2);
    }
    
        public static AvisosFrame getInstance() {
        if (instance == null) {
            instance = new AvisosFrame();
        }
        return instance;
    }
        
    public void initialize(Usuario usuario) {
        this.usuarioLogueado = usuario;
        configurarVisibilidadComponentes();
    }
    
    private void configurarVisibilidadComponentes() {
        if (usuarioLogueado.getRol() == Usuario.Rol.EMPLEADO) {
            Usuarios2.setVisible(false);
            Configuracion2.setVisible(false);
            Analisis2.setVisible(false);
        } else if (usuarioLogueado.getRol() == Usuario.Rol.ADMINISTRADOR) {
            // El administrador puede ver todo
        }
    }
    
    
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
    }

        
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Panel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        pocas = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        prox = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnCrearGrafica = new javax.swing.JButton();
        txtFechaFinal = new javax.swing.JTextField();
        txtFechaInicio = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        MenuPlegable2 = new javax.swing.JPanel();
        Menu2 = new javax.swing.JLabel();
        Inicio2 = new javax.swing.JLabel();
        Usuarios2 = new javax.swing.JLabel();
        Configuracion2 = new javax.swing.JLabel();
        Ventas2 = new javax.swing.JLabel();
        Analisis2 = new javax.swing.JLabel();
        Inventario2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pocas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CODIGO DE BARRAS", "NOMBRE", "UNIDADES", "FECHA DE CADUCIDAD"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(pocas);

        prox.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CODIGO DE BARRAS", "NOMBRE", "UNIDADES", "FECHA DE CADUCIDAD"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(prox);

        jLabel1.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel1.setText("POCAS UNIDADES DISPONIBLES");

        jLabel2.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel2.setText("PROXIMOS A CADUCAR");

        btnCrearGrafica.setText("Crear Grafica");
        btnCrearGrafica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearGraficaActionPerformed(evt);
            }
        });

        txtFechaFinal.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtFechaFinalFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFechaFinalFocusLost(evt);
            }
        });
        txtFechaFinal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtFechaFinalMouseClicked(evt);
            }
        });
        txtFechaFinal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFechaFinalActionPerformed(evt);
            }
        });

        txtFechaInicio.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtFechaInicioFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFechaInicioFocusLost(evt);
            }
        });
        txtFechaInicio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtFechaInicioMouseClicked(evt);
            }
        });
        txtFechaInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFechaInicioActionPerformed(evt);
            }
        });

        jLabel3.setText("Fecha de Inicio");

        jLabel4.setText("Fecha final");

        javax.swing.GroupLayout Panel2Layout = new javax.swing.GroupLayout(Panel2);
        Panel2.setLayout(Panel2Layout);
        Panel2Layout.setHorizontalGroup(
            Panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Panel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(Panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Panel2Layout.createSequentialGroup()
                        .addGroup(Panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(32, Short.MAX_VALUE))
                    .addGroup(Panel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(Panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(Panel2Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(33, 33, 33)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(Panel2Layout.createSequentialGroup()
                                .addComponent(txtFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(33, 33, 33)
                                .addComponent(txtFechaFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnCrearGrafica)))
                        .addGap(17, 17, 17))))
        );
        Panel2Layout.setVerticalGroup(
            Panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Panel2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(Panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addGroup(Panel2Layout.createSequentialGroup()
                        .addGroup(Panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(12, 12, 12)
                        .addGroup(Panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtFechaFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCrearGrafica))))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );

        getContentPane().add(Panel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 0, 700, 610));

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
        Usuarios2.setBounds(0, 450, 170, 50);

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
        Analisis2.setBounds(0, 360, 170, 50);

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

        getContentPane().add(MenuPlegable2, new org.netbeans.lib.awtextra.AbsoluteConstraints(-120, 0, 170, 610));

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
        UsuariosPanel usuariosWindow = UsuariosPanel.getInstance();
        //usuariosWindow.setVentanaPrincipal(this);
        usuariosWindow.setVisible(true);
        this.setVisible(false);
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

    }//GEN-LAST:event_Analisis2MouseClicked

    private void Inventario2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Inventario2MouseClicked
        Principal2_0 principalWindow = Principal2_0.getInstance();
        //principalWindow.setVentanaPrincipal(this);
        principalWindow.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_Inventario2MouseClicked

    private void btnCrearGraficaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearGraficaActionPerformed
    try {
        Date fechaInicio = dateFormat.parse(txtFechaInicio.getText());
        Date fechaFin = dateFormat.parse(txtFechaFinal.getText());

        // Crear una nueva instancia de ObservarGraficas y pasar las fechas
        ObservarGraficas graficasWindow = new ObservarGraficas(this);
        graficasWindow.initialize(fechaInicio, fechaFin);
        graficasWindow.setVisible(true);
        this.setVisible(false);

    } catch (ParseException e) {
        JOptionPane.showMessageDialog(this, "Error en el formato de fecha", "Error de Formato", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_btnCrearGraficaActionPerformed

    private void txtFechaFinalFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFechaFinalFocusGained

    }//GEN-LAST:event_txtFechaFinalFocusGained

    private void txtFechaFinalFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFechaFinalFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFechaFinalFocusLost

    private void txtFechaFinalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtFechaFinalMouseClicked
        if (!isCalendarOpen) {
            mostrarCalendario(txtFechaFinal, false); // false indica que es la fecha final
        }
    }//GEN-LAST:event_txtFechaFinalMouseClicked

    private void txtFechaFinalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFechaFinalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFechaFinalActionPerformed

    private void txtFechaInicioFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFechaInicioFocusGained

    }//GEN-LAST:event_txtFechaInicioFocusGained

    private void txtFechaInicioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFechaInicioFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFechaInicioFocusLost

    private void txtFechaInicioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtFechaInicioMouseClicked
        if (!isCalendarOpen) {
            mostrarCalendario(txtFechaInicio, true); // true indica que es la fecha de inicio
        }
    }//GEN-LAST:event_txtFechaInicioMouseClicked

    private void txtFechaInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFechaInicioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFechaInicioActionPerformed

    public void esReabastecible(Producto p) {
        LocalDate fechaCaducidad = p.getFechaCaducidad();
        LocalDate unMesAntes = fechaCaducidad.minusMonths(1);
        LocalDate hoy = LocalDate.now();

        int nivel = p.getNivelReorden();
        boolean reabastecible = p.getUnidadesDisponibles() <= nivel;
        boolean estaCercaCaducidad = !hoy.isBefore(unMesAntes);

        if (reabastecible) {
//            modeloReabastecer.addRow(new Object[]{
//                p.getCodigoBarras(),
//                p.getNombre(), 
//                p.getUnidadesDisponibles(), 
//                p.getFechaCaducidad()
//            });
            System.out.println("Reabastecer " + p.getNombre());
        }
        if (estaCercaCaducidad) {
//            modeloOferta.addRow(new Object[]{
//                p.getCodigoBarras(),
//                p.getNombre(), 
//                p.getUnidadesDisponibles(), 
//                p.getFechaCaducidad()
//            });
            System.out.println("Se sugiere poner en oferta el producto " + p.getNombre() + ", con fecha de caducidad " + p.getFechaCaducidad());
        }
    }
    
    public void comparar(){
        DefaultTableModel modelProx = (DefaultTableModel) prox.getModel();
        DefaultTableModel modelPocas = (DefaultTableModel) pocas.getModel();
        //model.setRowCount(0); // Limpia la tabla completamente.
        
        try{
            CONSULTASDAO dao = new CONSULTASDAO(Conexion_DB.getConexion());
            List<Producto> listaProductosConArea = dao.obtenerProductosConNombreArea();
            
            for(Producto prod : listaProductosConArea){
                LocalDate fechaCaducidad = prod.getFechaCaducidad();
                LocalDate unMesAntes = fechaCaducidad.minusMonths(1);
                LocalDate hoy = LocalDate.now();

                boolean esReabastecible = prod.getUnidadesDisponibles() <= 20;
                int nivel = prod.getNivelReorden();
                
                boolean estaCercaCaducidad = !hoy.isBefore(unMesAntes);
                
                if (esReabastecible) {
                    agregarFilaProducto(modelProx, prod);
                }
                if (estaCercaCaducidad) {
                    agregarFilaProducto(modelPocas, prod);
                }
            }
        }catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al conectar con la base de datos: " + e.getMessage(), "Error de Conexión", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }        
    }
    
    private void agregarFilaProducto(DefaultTableModel model, Producto prod) {
    model.addRow(new Object[]{
        prod.getCodigoBarras(),
        prod.getNombre(),
        prod.getUnidadesDisponibles(),
        prod.getFechaCaducidad()
    });
}
    
    public void rellenarOferta (Producto prod){
        DefaultTableModel model = (DefaultTableModel) prox.getModel();
        
        
        model.addRow(new Object[]{
            prod.getCodigoBarras(),
            prod.getNombre(),
            prod.getUnidadesDisponibles(),
            prod.getFechaCaducidad()
        });
        //System.out.println("Se sugiere poner en oferta el producto " + prod.getNombre() + ", con fecha de caducidad " + prod.getFechaCaducidad());
    }
    
    public void rellenarUnid (Producto prod){
        DefaultTableModel model = (DefaultTableModel) pocas.getModel();
        
        model.addRow(new Object[]{
            prod.getCodigoBarras(),
            prod.getNombre(),
            prod.getUnidadesDisponibles(),
            prod.getFechaCaducidad()
        });
        //System.out.println("Reabastecer " + prod.getNombre());
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
            java.util.logging.Logger.getLogger(AvisosFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AvisosFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AvisosFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AvisosFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AvisosFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Analisis2;
    private javax.swing.JLabel Configuracion2;
    private javax.swing.JLabel Inicio2;
    private javax.swing.JLabel Inventario2;
    private javax.swing.JLabel Menu2;
    private javax.swing.JPanel MenuPlegable2;
    private javax.swing.JPanel Panel2;
    private javax.swing.JLabel Usuarios2;
    private javax.swing.JLabel Ventas2;
    private javax.swing.JButton btnCrearGrafica;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable pocas;
    private javax.swing.JTable prox;
    private javax.swing.JTextField txtFechaFinal;
    private javax.swing.JTextField txtFechaInicio;
    // End of variables declaration//GEN-END:variables
}