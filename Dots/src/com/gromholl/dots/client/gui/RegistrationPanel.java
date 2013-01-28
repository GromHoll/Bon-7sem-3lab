package com.gromholl.dots.client.gui;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

import com.gromholl.dots.shared.Packet;

public class RegistrationPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    
    private JTextField tfLogin;
    private JPasswordField pfPassword;
    private JPasswordField pfConfirm;
    
    private JLabel lblMessage;
    
    private JButton btnRegistration;    
    private JButton btnBack;

    /**
     * Create the panel.
     */
    public RegistrationPanel() {
        
        btnRegistration = new JButton("Registration");
        
        btnBack = new JButton("Back");
        
        lblMessage = new JLabel("Input your information");
        lblMessage.setHorizontalAlignment(SwingConstants.CENTER);
        lblMessage.setForeground(Color.RED);
        
        JLabel lblLogin = new JLabel("Login:");
        
        JLabel lblPassword = new JLabel("Password:");
        
        pfPassword = new JPasswordField();
        pfPassword.setColumns(10);
        
        tfLogin = new JTextField();
        tfLogin.setColumns(10);
        
        pfConfirm = new JPasswordField();
        pfConfirm.setColumns(10);
        
        JLabel lblConfirm = new JLabel("Confirm:");
        GroupLayout groupLayout = new GroupLayout(this);
        groupLayout.setHorizontalGroup(
            groupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(groupLayout.createSequentialGroup()
                    .addGap(233)
                    .addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
                        .addComponent(lblMessage, GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
                        .addGroup(groupLayout.createSequentialGroup()
                            .addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
                                .addComponent(lblConfirm, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblPassword, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblLogin, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                .addComponent(pfConfirm, GroupLayout.PREFERRED_SIZE, 148, GroupLayout.PREFERRED_SIZE)
                                .addComponent(pfPassword, GroupLayout.PREFERRED_SIZE, 148, GroupLayout.PREFERRED_SIZE)
                                .addComponent(tfLogin, GroupLayout.PREFERRED_SIZE, 148, GroupLayout.PREFERRED_SIZE)))
                        .addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
                            .addComponent(btnRegistration, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
                            .addGap(18)
                            .addComponent(btnBack, GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)))
                    .addGap(232))
        );
        groupLayout.setVerticalGroup(
            groupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(groupLayout.createSequentialGroup()
                    .addGap(187)
                    .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblLogin)
                        .addComponent(tfLogin, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblPassword)
                        .addComponent(pfPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblConfirm)
                        .addComponent(pfConfirm, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(ComponentPlacement.UNRELATED)
                    .addComponent(lblMessage)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                        .addComponent(btnRegistration)
                        .addComponent(btnBack))
                    .addContainerGap(187, Short.MAX_VALUE))
        );
        setLayout(groupLayout);

    }

    public void addActionListenerForBackButton(ActionListener l) {
        if(l != null)
            btnBack.addActionListener(l);
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
    private String getPassword() {
        return new String(pfPassword.getPassword());
    }
    private String getConfirmPassword() {
        return new String(pfConfirm.getPassword());
    }
    
    private boolean isValidPassword() {
        
        String pass1 = new String(pfPassword.getPassword());
        String pass2 = new String(pfConfirm.getPassword());
        
        return (pass1.equals(pass2));
    }
    private boolean isFillField() {
        if(getLogin().isEmpty() || getPassword().isEmpty() || getConfirmPassword().isEmpty()) {
            setMessage("Fill all field.");
            return false;
        } else {
            return true;
        }
    }
    private boolean isValidField() {
        if(getLogin().indexOf(' ') != -1 || getPassword().indexOf(' ') != -1 || getConfirmPassword().indexOf(' ') != -1) {
            setMessage("Login and password can't contain spases.");
            return false;
        } else {
            if(isValidPassword()) {
                return true;
            } else {
                setMessage("Passwprds not equals.");
                return false;
            }
        }        
    }
    
    public Packet getRegPacket() {
        
        Packet res = null;
        
        if(!isFillField())
            return res;        
        if(!isValidField())
            return res;
        
        res = new Packet(Packet.PACKET_CODES.REGISTRATION_CMD);
        HashMap<String, String> data = new HashMap<String, String>();
        data.put(Packet.LOGIN_KEY, getLogin());
        data.put(Packet.PASSWORD_KEY, getPassword());
        res.setData(data);
        
        return res;
    }
    
}
