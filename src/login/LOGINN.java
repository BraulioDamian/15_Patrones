/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;
import Consultas.CONSULTASDAO;
import INVENTARIO.Principal2_0;
import ConexionDB.Conexion_DB;
import DBObjetos.*;
import Principal.MenuPrincipal;
import UniVentanas.MenuPrincipalWindow;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LOGINN extends javax.swing.JFrame {

    /**
     * Creates new form LOGINN
     */
    boolean visible = false;
    private static final Logger LOGGER = Logger.getLogger(LOGINN.class.getName());

    
    public LOGINN() {
        initComponents();
        
        Estilos.addPlaceholderStyle(fieldUser);
        Estilos.addPlaceholderStyle(fieldPass);
        setupTextFieldNavigation();
        
         btnContraseña.setIcon(new ImageIcon(getClass().getResource("/Icons/cerrarOjo.png")));

         
         
    try {
        CONSULTASDAO consultasDAO = new CONSULTASDAO(Conexion_DB.getConexion());
        boolean adminExists = consultasDAO.existeAdministrador();
        if (!adminExists) {
            
            java.awt.EventQueue.invokeLater(() -> {
                FormPrimera formPrimera = new FormPrimera("ADMINISTRADOR");
                formPrimera.setVisible(true);
                this.setVisible(false);  // Opcional: Ocultar el LOGINN si ya no es necesario
            });
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error al iniciar la aplicación: " + e.getMessage(), "Error de Inicio", JOptionPane.ERROR_MESSAGE);
    }


    
             
             
             
    }

    
        private void setupTextFieldNavigation() {
    // Array de todos tus campos de texto
    JTextField[] fields = {fieldUser, fieldPass};

    for (int i = 0; i < fields.length; i++) {
        JTextField field = fields[i];
        final int index = i; // Debe ser final para usarlo dentro de la clase anónima

        field.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                             switch (e.getKeyCode()) {
                    case KeyEvent.VK_ENTER:
                        txtAcceder.doClick(); // Simula un click en el botón Acceder
                        break;
              
                    case KeyEvent.VK_DOWN:
                        // Mueve el foco al siguiente campo de texto, si existe
                        if (index + 1 < fields.length) {
                            fields[index + 1].requestFocus();
                        }
                        break;
                    case KeyEvent.VK_UP:
                        // Mueve el foco al campo de texto anterior, si existe
                        if (index - 1 >= 0) {
                            fields[index - 1].requestFocus();
                        }
                        break;
                
           }
            }
        });
    }
}
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        fieldUser = new javax.swing.JTextField();
        fieldPass = new javax.swing.JPasswordField();
        txtAcceder = new javax.swing.JButton();
        RecuperarContraseña = new javax.swing.JButton();
        btnContraseña = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(153, 140, 192));
        jPanel1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jPanel1FocusGained(evt);
            }
        });
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jPanel3FocusGained(evt);
            }
        });

        fieldUser.setText("Usuario");
        fieldUser.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(102, 102, 102), new java.awt.Color(102, 102, 102), null, null));
        fieldUser.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                fieldUserFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                fieldUserFocusLost(evt);
            }
        });
        fieldUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldUserActionPerformed(evt);
            }
        });

        fieldPass.setText("Contraseña");
        fieldPass.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(102, 102, 102), new java.awt.Color(102, 102, 102), null, null));
        fieldPass.setEchoChar('\u0000');
        fieldPass.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                fieldPassFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                fieldPassFocusLost(evt);
            }
        });
        fieldPass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldPassActionPerformed(evt);
            }
        });

        txtAcceder.setBackground(new java.awt.Color(153, 140, 192));
        txtAcceder.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtAcceder.setText("Login");
        txtAcceder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAccederActionPerformed(evt);
            }
        });

        RecuperarContraseña.setBackground(new java.awt.Color(153, 140, 192));
        RecuperarContraseña.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        RecuperarContraseña.setText("¿Olvidate tu contraseña?");
        RecuperarContraseña.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RecuperarContraseñaActionPerformed(evt);
            }
        });

        btnContraseña.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnContraseñaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(92, 92, 92)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtAcceder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(fieldPass, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
                            .addComponent(fieldUser))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(114, 114, 114)
                        .addComponent(RecuperarContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(91, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(fieldUser, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fieldPass, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addComponent(txtAcceder)
                .addGap(27, 27, 27)
                .addComponent(RecuperarContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(75, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 170, 460, 320));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 711, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtAccederActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAccederActionPerformed
    Usuario usuario = obtenerUsuarioLogueado();

    if (usuario != null) {
        System.out.println("El rol del usuario es: " + usuario.getRol());

        SesionManager.getInstance().login(usuario);
        JOptionPane.showMessageDialog(this, "Inicio de sesión exitoso");

        // Aquí cambiamos Principal2_0 por MenuPrincipal
        MenuPrincipal menuPrincipal = MenuPrincipal.getInstance(); // Asegúrate de que MenuPrincipal también use el patrón Singleton
        menuPrincipal.initialize(usuario); // Inicializar MenuPrincipal con el usuario logueado
        menuPrincipal.setVisible(true);
        this.dispose();
    } else {
        JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos");
    }
    }//GEN-LAST:event_txtAccederActionPerformed

    private Usuario obtenerUsuarioLogueado() {
        String nombreUsuario = fieldUser.getText();
        String contrasena = new String(fieldPass.getPassword());

        try {
            CONSULTASDAO consultasDAO = new CONSULTASDAO(Conexion_DB.getConexion());
            Usuario usuario = consultasDAO.validarUsuario(nombreUsuario, contrasena);
            if (usuario != null) {
                // Usuario validado correctamente, ahora actualizar el último login
                if (consultasDAO.updateLastLogin(usuario.getUsuarioID())) {
                    LOGGER.log(Level.INFO, "Último login actualizado correctamente para el usuario: {0}", nombreUsuario);
                } else {
                    LOGGER.log(Level.WARNING, "Falló al actualizar el último login para el usuario: {0}", nombreUsuario);
                }
                return usuario;
            } else {
                JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos");
                return null;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al conectar con la base de datos: " + e.getMessage());
            LOGGER.log(Level.SEVERE, "Error al conectar con la base de datos", e);
            return null;
        }
    }
    
    
    private void RecuperarContraseñaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RecuperarContraseñaActionPerformed
     // Solicitar al usuario su nombre de usuario
        String nombreUsuario = JOptionPane.showInputDialog(this, "Ingrese su nombre de usuario:");
        if (nombreUsuario == null || nombreUsuario.trim().isEmpty()) {
            
            JOptionPane.showMessageDialog(this, "El nombre de usuario no puede estar vacío.");
            return;
        }
        
    // Intentar obtener la pregunta de seguridad para el usuario
     try {
        
        CONSULTASDAO productoDao = new CONSULTASDAO(Conexion_DB.getConexion());

        
        // Verificar si el usuario existe y obtener su email
        String emailUsuario = productoDao.obtenerEmailPorNombreUsuario(nombreUsuario);
        if (emailUsuario == null) {
            JOptionPane.showMessageDialog(this, "No se encontró un usuario con ese nombre.");
            return;
        }

        // Generar y enviar el código de verificación
        VerificacionContraseña verificacion = new VerificacionContraseña();  // Esto genera un nuevo código
        MandarCorreos mandarCorreos = new MandarCorreos();
        mandarCorreos.enviarCodigoVerificacion(emailUsuario, verificacion.getCodigoVerificacion());

        // Solicitar al usuario que ingrese el código de verificación
        String codigoIngresado = JOptionPane.showInputDialog(this, "Ingrese el código de 4 dígitos enviado a su correo electrónico:");
        if (codigoIngresado == null || codigoIngresado.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No ha ingresado ningún código.");
            return;
        }

        if (!codigoIngresado.equals(String.valueOf(verificacion.getCodigoVerificacion()))) {
            JOptionPane.showMessageDialog(this, "Código de verificación incorrecto.");
            return;
        }

        // Si el código es correcto, solicitar nueva contraseña
        String nuevaContraseña = JOptionPane.showInputDialog(this, "Ingrese su nueva contraseña:");
        if (nuevaContraseña == null || nuevaContraseña.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "La nueva contraseña no puede estar vacía.");
            return;
        }

        // Cambiar la contraseña en la base de datos
        boolean cambioExitoso = productoDao.cambiarContraseña(nombreUsuario, nuevaContraseña);
        if (cambioExitoso) {
            JOptionPane.showMessageDialog(this, "La contraseña se ha actualizado correctamente.");
        } else {
            JOptionPane.showMessageDialog(this, "Error al actualizar la contraseña.");
        }
        
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error al conectar con la base de datos: " + e.getMessage());
        e.printStackTrace();
    }
    }//GEN-LAST:event_RecuperarContraseñaActionPerformed



    private void fieldUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldUserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fieldUserActionPerformed

    private void fieldPassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldPassActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fieldPassActionPerformed

    private void fieldUserFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fieldUserFocusGained
        // TODO add your handling code here:
        if(fieldUser.getText().equals("Usuario")){
            fieldUser.setText(null);
            fieldUser.requestFocus();
            Estilos.removePlaceholderStyle(fieldUser);
            
        }
    }//GEN-LAST:event_fieldUserFocusGained

    private void fieldPassFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fieldPassFocusGained
        // TODO add your handling code here:
        if(fieldPass.getText().equals("Contraseña")){
            fieldPass.setText(null);
            fieldPass.requestFocus();
            fieldPass.setEchoChar('*');
            Estilos.removePlaceholderStyle(fieldPass);
            //jPasswordField2.setEchoChar('\u0000');
            
        }
    }//GEN-LAST:event_fieldPassFocusGained

    private void fieldUserFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fieldUserFocusLost
        // TODO add your handling code here:
        if(fieldUser.getText().length()==0){
            Estilos.addPlaceholderStyle(fieldUser);
            fieldUser.setText("Usuario");
        }
    }//GEN-LAST:event_fieldUserFocusLost

    private void fieldPassFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fieldPassFocusLost
        // TODO add your handling code here:
        if(fieldPass.getText().length()==0){
            Estilos.addPlaceholderStyle(fieldPass);
            fieldPass.setText("Contraseña");
            //jPasswordField2.setEchoChar('*');
        }
    }//GEN-LAST:event_fieldPassFocusLost

    @Override
    public void pack() {
        super.pack(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt){
        this.requestFocusInWindow();
    }
    
    private void jPanel1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPanel1FocusGained
        // TODO add your handling code here:
        this.requestFocusInWindow();
    }//GEN-LAST:event_jPanel1FocusGained

    private void jPanel3FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPanel3FocusGained
        // TODO add your handling code here:
        this.requestFocusInWindow();
    }//GEN-LAST:event_jPanel3FocusGained

    private void btnContraseñaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnContraseñaActionPerformed
        // TODO add your handling code here:
        if (visible) {
                    fieldPass.setEchoChar('*'); // Oculta la contraseña
                    visible = false;
        btnContraseña.setIcon(new ImageIcon(getClass().getResource("/Icons/cerrarOjo.png")));

                } else {
                    fieldPass.setEchoChar((char) 0); // Muestra la contraseña
                    visible = true;
        // Cambia el icono a abrirOjo
        btnContraseña.setIcon(new ImageIcon(getClass().getResource("/Icons/abrirOjo.png")));
    
                }
    }//GEN-LAST:event_btnContraseñaActionPerformed


    
    
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
            java.util.logging.Logger.getLogger(LOGINN.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LOGINN.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LOGINN.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LOGINN.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new LOGINN().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton RecuperarContraseña;
    private javax.swing.JButton btnContraseña;
    private javax.swing.JPasswordField fieldPass;
    private javax.swing.JTextField fieldUser;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JButton txtAcceder;
    // End of variables declaration//GEN-END:variables
}
