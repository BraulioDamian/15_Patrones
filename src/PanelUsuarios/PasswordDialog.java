/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PanelUsuarios;

/**
 *
 * @author braul
 */
import Configuraciones.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PasswordDialog extends JDialog {
    private JPasswordField passwordField;
    private JButton toggleVisibilityButton;
    private boolean isVisible = false;
    private JButton confirmButton;
    private int result = JOptionPane.CANCEL_OPTION;

    public PasswordDialog(JFrame owner) {
        super(owner, "Ingrese la nueva contraseña token", true);
        setupUI();
        setupActions();
        pack();
        setLocationRelativeTo(owner);
    }

    private void setupUI() {
        passwordField = new JPasswordField(20);
        toggleVisibilityButton = new JButton(new ImageIcon(getClass().getResource("/Icons/cerrarOjo.png")));
        confirmButton = new JButton("Confirmar");

        JPanel contentPanel = new JPanel(new FlowLayout());
        contentPanel.add(passwordField);
        contentPanel.add(toggleVisibilityButton);
        contentPanel.add(confirmButton);

        setContentPane(contentPanel);
    }

    private void setupActions() {
        toggleVisibilityButton.addActionListener(e -> toggleVisibility());
        confirmButton.addActionListener(e -> {
            result = JOptionPane.OK_OPTION;
            dispose();
        });

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void toggleVisibility() {
        if (isVisible) {
            passwordField.setEchoChar('*');
            toggleVisibilityButton.setIcon(new ImageIcon(getClass().getResource("/Icons/cerrarOjo.png")));
            isVisible = false;
        } else {
            passwordField.setEchoChar((char) 0);
            toggleVisibilityButton.setIcon(new ImageIcon(getClass().getResource("/Icons/abrirOjo.png")));
            isVisible = true;
        }
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public int showDialog() {
        setVisible(true);
        return result;
    }
}
