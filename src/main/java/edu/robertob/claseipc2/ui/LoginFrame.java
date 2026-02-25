/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.robertob.claseipc2.ui;

import edu.robertob.claseipc2.data.UserDB;
import edu.robertob.claseipc2.models.User;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author robertob
 */
public class LoginFrame extends JFrame {
    private final JTextField txtUser = new JTextField(15);
    private final JPasswordField txtPass = new JPasswordField(15);
    private final JButton btnLogin = new JButton("Login");
    
    private final UserDB userDb = new UserDB();
    
    public LoginFrame() {
        super("To-Do Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 180);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        panel.add(new JLabel("Usuario:"));
        panel.add(txtUser);

        panel.add(new JLabel("Contraseña:"));
        panel.add(txtPass);

        panel.add(new JLabel()); // espacio
        panel.add(btnLogin);

        btnLogin.addActionListener(e -> login());

        setContentPane(panel);
    }
    
    private void login() {
        String username = txtUser.getText().trim();
        String password = new String(txtPass.getPassword());
        
        User user = userDb.login(username, password);
        
        if (user == null) {
            JOptionPane.showMessageDialog(this, "Credenciales incorrectas");
            return;
        }
        
        new TodoFrame(user).setVisible(true);
    }
    
}
