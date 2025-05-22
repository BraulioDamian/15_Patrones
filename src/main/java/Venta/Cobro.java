
package Venta;

import DBObjetos.Producto;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import VentaMemento.CobroMemento;
import VentaMemento.CobroOriginator;
import VentaObserver.CobroObserver;
import VentaPayments.Efectivo;
import VentaPayments.MetodoPago;
import CobroFacade.CobroFacade;
import CobroFacade.PagoFacade;
import java.util.ArrayList;

import CobroState.*;


public class Cobro extends JFrame {

    private CobroState estadoActual;

    public CobroState getEstadoActual() {
        return estadoActual;
    }

    public void setEstadoActual(CobroState estado) {
        this.estadoActual = estado;
        System.out.println("Cambio de estado a: " + estado.getNombreEstado());
    }

    public VentaListener getVentaListener() {
        return this.ventaListener;
    }

    public List<Producto> getProductos() {
        return this.productos;
    }

    private ArrayList<CobroObserver> observers = new ArrayList<>();
    private static final Logger LOGGER = Logger.getLogger(Cobro.class.getName());

    public void addObserver(CobroObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(CobroObserver observer) {
        observers.remove(observer);
    }

    public void notifyMetodoPagoSeleccionado(String metodoPago) {
        System.out.println("Comenzando notificacion de metodo de pago: " + metodoPago);
        for (CobroObserver observer : observers) {
            System.out.println("Notificando a observador: " + observer.getClass().getSimpleName());
            LOGGER.log(Level.INFO, "Notificando método de pago: {0}", metodoPago);
            observer.onMetodoPagoSeleccionado(metodoPago);
        }
        System.out.println("Notificacion de metodo de pago completada");
    }

    public void notifyMontoRecibido(double monto) {
        for (CobroObserver observer : observers) {
            LOGGER.log(Level.INFO, "Notificando método de pago: {0}", metodoPago);
            observer.onMontoRecibido(monto);
        }
    }

    public void notifyCambioCalculado(double cambio) {
        for (CobroObserver observer : observers) {
            LOGGER.log(Level.INFO, "Notificando método de pago: {0}", metodoPago);
            observer.onCambioCalculado(cambio);
        }
    }

    public void notifyVentaCompleta(double totalVenta, double montoRecibido, double cambio) {
        for (CobroObserver observer : observers) {
            LOGGER.log(Level.INFO, "Notificando método de pago: {0}", metodoPago);
            observer.onVentaCompleta(totalVenta, montoRecibido, cambio);
        }
    }

    public void notifyVentaCancelada() {
        LOGGER.log(Level.INFO, "Notificando método de pago: {0}", metodoPago);
        for (CobroObserver observer : observers) {
            observer.onVentaCancelada();
        }
    }

    public void notifyEstadoCambiado(String nuevoEstado) {
        for (CobroObserver observer : observers) {
            // Este método tendría que añadirse a la interfaz CobroObserver
            // observer.onEstadoCambiado(nuevoEstado);
            System.out.println("Cambio de estado: " + nuevoEstado);
        }
        LOGGER.log(Level.INFO, "Cambio de estado a: {0}", nuevoEstado);
    }

    private double montoTotal;

    PagoFacade pagoFacade = new PagoFacade();

    private CobroFacade cobroFacade;

    public void iniciar() {
        montoTotal = 0.0;
    }

    public void agregarMonto(double monto) {
        montoTotal += monto;
    }

    private double pre; // Total a pagar
    public double cambio;

    public double getPre() {
        return pre;
    }

    public void setPre(double pre) {
        this.pre = pre;
    }

    public double getCambio() {
        return cambio;
    }

    public void setCambio(double cambio) {
        this.cambio = cambio;
    }

    public String getPrecioEnLetras() {
        return precioEnLetras;
    }

    public void setPrecioEnLetras(String precioEnLetras) {
        this.precioEnLetras = precioEnLetras;
    }

    public String getRecibi() {
        return recibi.getText();
    }

    public void setRecibi(String recibi) {
        this.recibi.setText(recibi);
    }

    public boolean isEfectivoSeleccionado() {
        return efectivo.isSelected();
    }

    public void setEfectivoSeleccionado(boolean efectivoSeleccionado) {
        this.efectivo.setSelected(efectivoSeleccionado);
    }

    public boolean isTarjetaSeleccionada() {
        return tarjeta.isSelected();
    }

    public void setTarjetaSeleccionada(boolean tarjetaSeleccionada) {
        this.tarjeta.setSelected(tarjetaSeleccionada);
    }

    public String getCorreo() {
        return txtCorreo.getText();
    }

    public void setCorreo(String correo) {
        this.txtCorreo.setText(correo);
    }

    public CobroMemento saveStateToMemento() {
        return new CobroMemento(pre, cambio, precioEnLetras, recibi.getText(), efectivo.isSelected(),
                tarjeta.isSelected(), txtCorreo.getText());
    }

    public void getStateFromMemento(CobroMemento memento) {
        pre = memento.getPre();
        cambio = memento.getCambio();
        precioEnLetras = memento.getPrecioEnLetras();
        recibi.setText(memento.getRecibi());
        efectivo.setSelected(memento.isEfectivoSeleccionado());
        tarjeta.setSelected(memento.isTarjetaSeleccionada());
        txtCorreo.setText(memento.getCorreo());
    }

    CobroOriginator originator = new CobroOriginator(this);
    CobroMemento savedState;

    NumeroEnPalabras converter = new NumeroEnPalabras();
    String precioEnLetras;
    List<Producto> productos;
    private VentaListener ventaListener;

    public interface VentaListener {
        void onVentaCompleta();
    }

    public Cobro() {

        VentaObserver.LoggerConfig.configureLogger();

        File logFile = new File("logs/cobro-events.log");
        if (logFile.exists()) {
            System.out.println("Archivo de log creado en " + logFile.getAbsolutePath());
        } else {
            System.out.println("No se pudo crear el archivo de log.");
        }

        initComponents();
        setLocationRelativeTo(null); // Centra la ventana en la pantalla

        addObserver(new VentaObserver.CobroLogObserver());
        addObserver(new VentaObserver.UICobroObserver());

        double importe = Double.parseDouble(recibi.getText());

        precio.setText("$" + pre);
        precioEnLetras = converter.convertir(pre);
        letras.setText(precioEnLetras);

        if (importe >= pre) {

        }

        agregarListeners();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Asegura que solo se cierre esta ventana
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                Venta.getInstance().setVisible(true); // Vuelve a hacer visible la ventana de venta
            }
        });

        cobroFacade = new CobroFacade();
        cobroFacade.iniciarCobro();

        this.estadoActual = new EstadoInicial();

    }

    public Cobro(double total, List<Producto> productos) {
        initComponents();
        setLocationRelativeTo(null); // Centra la ventana en la pantalla

        this.productos = productos;
        this.pre = total;
        precio.setText("$" + String.format("%.2f", total));
        precioEnLetras = converter.convertir(total);
        letras.setText(precioEnLetras);

        agregarListeners();

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Asegura que solo se cierre esta ventana
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                Venta.getInstance().setVisible(true); // Vuelve a hacer visible la ventana de venta
            }
        });

        this.estadoActual = new EstadoInicial();
    }

    public void setVentaListener(VentaListener listener) {
        this.ventaListener = listener;
    }

    private void agregarListeners() {
        recibi.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                calcularCambio();
            }

            public void removeUpdate(DocumentEvent e) {
                calcularCambio();
            }

            public void insertUpdate(DocumentEvent e) {
                calcularCambio();
            }
        });
    }

    private void calcularCambio() {
        try {
            double montoRecibido = Double.parseDouble(recibi.getText());
            System.out.println("Notificando a " + observers.size() + " observadores sobre método de pago...");
            notifyMontoRecibido(montoRecibido);

            if (estadoActual instanceof EstadoInicial) {
                return;
            }

            if (montoRecibido >= pre) {
                cambio = montoRecibido - pre;
                camb.setText(String.format("$%.2f", cambio));
            } else {
                camb.setText("Insuficiente");
            }
        } catch (NumberFormatException e) {
            camb.setText("");
        }
    }

    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        precio = new javax.swing.JLabel();
        letras = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        recibi = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        btnAceptar = new javax.swing.JButton();
        efectivo = new javax.swing.JRadioButton();
        tarjeta = new javax.swing.JRadioButton();
        camb = new javax.swing.JLabel();
        txtCorreo = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JButton();
        btnRestore = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 102, 0));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        jLabel4.setText("Total a pagar:");

        precio.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        precio.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        precio.setText("$0.00");

        letras.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        letras.setText("cero");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(144, 144, 144)
                                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 133,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(105, 105, 105)
                                                .addComponent(precio, javax.swing.GroupLayout.PREFERRED_SIZE, 199,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGap(0, 91, Short.MAX_VALUE)
                                .addComponent(letras, javax.swing.GroupLayout.PREFERRED_SIZE, 248,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(88, 88, 88)));
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 25,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(precio, javax.swing.GroupLayout.PREFERRED_SIZE, 36,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(letras, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                                .addContainerGap()));

        jLabel9.setText("Recibo");

        jLabel10.setText("Cambio");

        jButton4.setBackground(new java.awt.Color(255, 0, 0));
        jButton4.setText("Cancelar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        btnAceptar.setBackground(new java.awt.Color(0, 255, 0));
        btnAceptar.setText("Aceptar");
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        buttonGroup1.add(efectivo);
        efectivo.setText("Efectivo");
        efectivo.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        efectivo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                efectivoMouseClicked(evt);
            }
        });

        buttonGroup1.add(tarjeta);
        tarjeta.setText("Tarjeta (Credito / Debito)");

        camb.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        txtCorreo.setBorder(javax.swing.BorderFactory.createTitledBorder("Correo Electronico"));

        btnGuardar.setText("Guardar Estado");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnRestore.setText("Restaurar Estado");
        btnRestore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRestoreActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap(56, Short.MAX_VALUE)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(74, 74, 74))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addGroup(jPanel1Layout
                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtCorreo)
                                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 78,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(efectivo)
                                        .addComponent(tarjeta, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addGroup(jPanel1Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                false)
                                                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(recibi, javax.swing.GroupLayout.DEFAULT_SIZE, 127,
                                                                Short.MAX_VALUE)
                                                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(camb, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(btnRestore, javax.swing.GroupLayout.PREFERRED_SIZE, 119,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(btnGuardar)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(btnAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 80,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(42, 42, 42)
                                                .addComponent(efectivo)
                                                .addGap(18, 18, 18)
                                                .addGroup(jPanel1Layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(tarjeta)
                                                        .addComponent(jLabel10)))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(21, 21, 21)
                                                .addComponent(jLabel9)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(recibi, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(camb, javax.swing.GroupLayout.PREFERRED_SIZE, 25,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 66,
                                        Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButton4)
                                        .addComponent(btnAceptar)
                                        .addComponent(btnGuardar)
                                        .addComponent(btnRestore))
                                .addGap(71, 71, 71)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE));

        // JPanel panelEventos = new JPanel(new BorderLayout());
        // JTextArea areaEventos = new JTextArea(10, 10);
        // panelEventos.setBorder(BorderFactory.createTitledBorder("Eventos de Cobro"));
        // areaEventos.setEditable(false);
        // panelEventos.add(new JScrollPane(areaEventos), BorderLayout.CENTER);

        // jPanel1.add(panelEventos, BorderLayout.SOUTH);
        // addObserver(new VentaObserver.IntegratedUIObserver(areaEventos));

        // jPanel1.setLayout(new BorderLayout());
        // jPanel1.add(panelEventos, BorderLayout.SOUTH);

        // jPanel1.add(panelEventos, BorderLayout.SOUTH);
        pack();

        JFrame eventFrame = new JFrame("Monitor de Eventos - Patrón Observer");
        JTextArea areaEventos = new JTextArea(15, 40);
        areaEventos.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaEventos);
        eventFrame.add(scrollPane);
        eventFrame.pack();
        eventFrame.setLocation(this.getLocation().x + this.getWidth() + 10, this.getLocation().y);
        eventFrame.setVisible(true);

        addObserver(new VentaObserver.IntegratedUIObserver(areaEventos));

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                eventFrame.dispose(); // Cierra el JFrame de eventos al cerrar la ventana principal
            }

            public void windowClosed(WindowEvent e) {
                eventFrame.dispose(); // Cierra el JFrame de eventos al cerrar la ventana principal
            }
        });

        // addObserver(new VentaObserver.IntegratedUIObserver(areaEventos));
    }// </editor-fold>//GEN-END:initComponents

    private void efectivoMouseClicked(java.awt.event.MouseEvent evt) {
        setMetodoPago(new Efectivo());
        estadoActual.seleccionarMetodoPago(this, "Efectivo");
    }

    private void tarjetaMouseClicked(java.awt.event.MouseEvent evt) {
        setMetodoPago(new Efectivo());
        estadoActual.seleccionarMetodoPago(this, "Tarjeta");
    }

    private MetodoPago metodoPago;

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
        System.out.println("Notificando a " + observers.size() + " observadores sobre método de pago...");
        notifyMetodoPagoSeleccionado(metodoPago.getClass().getSimpleName());
    }

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {
        estadoActual.procesarPago(this);
        if (metodoPago == null) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un método de pago.");
            return;
        }

        // if (!pagoFacade.validarMetodoDePago(efectivo.isSelected(),
        // tarjeta.isSelected())) {
        // return;
        // }

        String textoRecibi = recibi.getText().trim();
        if (textoRecibi.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El campo 'Recibí' no puede estar vacío. Por favor, ingrese un monto.");
            return;
        }

        if (!textoRecibi.matches("\\d+(\\.\\d+)?")) {
            JOptionPane.showMessageDialog(this, "Por favor, introduzca un monto válido en el campo 'Recibí'.");
            return;
        }

        try {
            double pago = Double.parseDouble(textoRecibi);

            // if (!pagoFacade.validarMonto(pago, pre)) {
            // return;
            // }

            if (!metodoPago.validarMonto(pago, pre)) {
                JOptionPane.showMessageDialog(this, "El monto recibido no puede ser menor al total a pagar.");
                return;
            }

            // cambio = pagoFacade.calcularCambio(pago, pre);
            cambio = metodoPago.calcularCambio(pago, pre);
            camb.setText("$" + String.format("%.2f", cambio));

            String pdfPath = pagoFacade.generarPDF(productos, pre, pago, cambio);
            // String pdfPath = metodoPago.generarPDF(productos, pre, pago, cambio);

            if (pdfPath != null) {
                // boolean flag = pagoFacade.enviarTicketPorCorreo(txtCorreo.getText(),
                // pdfPath);
                boolean flag = metodoPago.enviarTicketPorCorreo(txtCorreo.getText(), pdfPath);
                if (flag) {
                    System.out.println("Notificando a " + observers.size() + " observadores sobre método de pago...");
                    notifyVentaCompleta(pago, pago, pago);
                    if (ventaListener != null) {
                        ventaListener.onVentaCompleta();
                    }
                    dispose();
                }

            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, introduzca un monto válido enel campo 'Recibí'.");
        }
    }

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton4ActionPerformed
        estadoActual.cancelarOperacion(this);
        System.out.println("Notificando a " + observers.size() + " observadores sobre método de pago...");
        notifyVentaCancelada();
        dispose();
    }// GEN-LAST:event_jButton4ActionPerformed

    private void btnRestoreActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnRestoreActionPerformed
        originator.restore(savedState);
        JOptionPane.showMessageDialog(this, "Estado Restaurado");
    }// GEN-LAST:event_btnRestoreActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnGuardarActionPerformed
        savedState = originator.save();
        JOptionPane.showMessageDialog(this, "Estado Guardado");
    }// GEN-LAST:event_btnGuardarActionPerformed

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Cobro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Cobro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Cobro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Cobro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Cobro().setVisible(true);
            }
        });

        VentaObserver.LoggerConfig.configureLogger();
        System.out.println("Logger configurado correctamente.");
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnRestore;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel camb;
    private javax.swing.JRadioButton efectivo;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel letras;
    private javax.swing.JLabel precio;
    private javax.swing.JTextField recibi;
    private javax.swing.JRadioButton tarjeta;
    private javax.swing.JTextField txtCorreo;
    // End of variables declaration//GEN-END:variables
}

