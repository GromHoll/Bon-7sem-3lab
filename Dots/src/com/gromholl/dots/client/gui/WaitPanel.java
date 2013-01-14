package com.gromholl.dots.client.gui;

import java.awt.Component;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

public class WaitPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    
    private JButton btnLeave;

    /**
     * Create the panel.
     */
    public WaitPanel() {
        
        JLabel lblWarning = new JLabel("You enter in game. Please wait other players.");
        
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        
        btnLeave = new JButton("Leave");
        GroupLayout groupLayout = new GroupLayout(this);
        groupLayout.setHorizontalGroup(
            groupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(groupLayout.createSequentialGroup()
                    .addGap(223)
                    .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                        .addComponent(lblWarning, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(progressBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGroup(groupLayout.createSequentialGroup()
                            .addGap(82)
                            .addComponent(btnLeave, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(ComponentPlacement.RELATED, 82, GroupLayout.PREFERRED_SIZE)))
                    .addGap(222))
        );
        groupLayout.setVerticalGroup(
            groupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(groupLayout.createSequentialGroup()
                    .addGap(213)
                    .addComponent(lblWarning)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(progressBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGap(12)
                    .addComponent(btnLeave)
                    .addContainerGap(213, Short.MAX_VALUE))
        );
        groupLayout.linkSize(SwingConstants.HORIZONTAL, new Component[] {lblWarning, progressBar});
        setLayout(groupLayout);

    }
    
    public void addActionListenerForLeaveButton(ActionListener l) {
        if(l != null)
            btnLeave.addActionListener(l);
    }

}
