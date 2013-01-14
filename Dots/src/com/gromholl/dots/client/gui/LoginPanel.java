package com.gromholl.dots.client.gui;

import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

public class LoginPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    
    private JTextField tfLogin;
    private JPasswordField pfPassword;
    
    private JButton btnLogin;    
    private JButton btnRegistration;
    
    private JLabel lblMessage;

    /**
     * Create the panel.
     */
    public LoginPanel() {
        
        JLabel lblLogin = new JLabel("Login:");
        
        JLabel lblPassword = new JLabel("Password:");
        
        tfLogin = new JTextField();
        tfLogin.setColumns(10);
        
        pfPassword = new JPasswordField();
        pfPassword.setColumns(10);
        
        lblMessage = new JLabel("Input login info and press login button");
        lblMessage.setForeground(Color.RED);
        lblMessage.setHorizontalAlignment(SwingConstants.CENTER);
        
        btnLogin = new JButton("Login");
        
        btnRegistration = new JButton("Registration");
        GroupLayout groupLayout = new GroupLayout(this);
        groupLayout.setHorizontalGroup(
            groupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(groupLayout.createSequentialGroup()
                    .addGap(237)
                    .addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
                        .addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
                            .addComponent(btnLogin, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(ComponentPlacement.UNRELATED)
                            .addComponent(btnRegistration, GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE))
                        .addComponent(lblMessage, GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                        .addGroup(groupLayout.createSequentialGroup()
                            .addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
                                .addComponent(lblPassword, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblLogin, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                .addComponent(pfPassword, GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                                .addComponent(tfLogin, GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE))))
                    .addGap(237))
        );
        groupLayout.setVerticalGroup(
            groupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(groupLayout.createSequentialGroup()
                    .addGap(198)
                    .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblLogin)
                        .addComponent(tfLogin, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblPassword)
                        .addComponent(pfPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(ComponentPlacement.UNRELATED)
                    .addComponent(lblMessage)
                    .addPreferredGap(ComponentPlacement.UNRELATED)
                    .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                        .addComponent(btnRegistration)
                        .addComponent(btnLogin))
                    .addContainerGap(197, Short.MAX_VALUE))
        );
        setLayout(groupLayout);

    }
    
    public void addActionListenerForLoginButton(ActionListener l) {
        if(l != null)
            btnLogin.addActionListener(l);
    }
    
    public void addActionListenerForRegButton(ActionListener l) {
        if(l != null)
            btnRegistration.addActionListener(l);
    }
    
    public void setMessage(String msg) {
        if(msg != null)
            lblMessage.setText(msg);
    }
    
    public String getLogin() {
        return tfLogin.getText();
    }
    public String getPassword() {
        return new String(pfPassword.getPassword());
    }
    
    public boolean isFillField() {
        if(getLogin().isEmpty() || getPassword().isEmpty()) {
            setMessage("Fill all field.");
            return false;
        } else {
            return true;
        }
    }
    
    public boolean isValidField() {
        if(getLogin().indexOf(' ') != -1 || getPassword().indexOf(' ') != -1) {
            setMessage("Login and password can't contain spases.");
            return false;
        } else {
            return true;
        }        
    }
    
    
}
