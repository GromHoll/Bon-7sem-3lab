package com.gromholl.dots.client.gui;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerNumberModel;

public class CreatePanel extends JPanel {

    private static final long serialVersionUID = 1L;

    /**
     * Create the panel.
     */
    public CreatePanel() {
        
        JLabel lblNumberOfPlayers = new JLabel("Number of players:");
        
        JLabel lblGameAreaSize = new JLabel("Game area size:");
        
        JLabel lblTurnTime = new JLabel("Turn time:");
        
        JSpinner spinner = new JSpinner();
        spinner.setModel(new SpinnerNumberModel(2, 2, 4, 1));
        
        JSpinner spinner_1 = new JSpinner();
        spinner_1.setModel(new SpinnerNumberModel(39, 10, 50, 1));
        
        JLabel lblX = new JLabel("X");
        
        JSpinner spinner_2 = new JSpinner();
        spinner_2.setModel(new SpinnerNumberModel(32, 10, 50, 1));
        
        JButton btnClassicSize = new JButton("Classic size");
        
        JSpinner spinner_3 = new JSpinner();
        spinner_3.setModel(new SpinnerNumberModel(60, 15, 90, 1));
        
        JCheckBox chckbxExtraTurn = new JCheckBox("Extra turn");
        chckbxExtraTurn.setSelected(true);
        GroupLayout groupLayout = new GroupLayout(this);
        groupLayout.setHorizontalGroup(
            groupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(groupLayout.createSequentialGroup()
                    .addGap(240)
                    .addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
                        .addComponent(lblGameAreaSize)
                        .addComponent(lblNumberOfPlayers)
                        .addComponent(lblTurnTime))
                    .addPreferredGap(ComponentPlacement.UNRELATED)
                    .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                        .addComponent(chckbxExtraTurn)
                        .addComponent(spinner_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
                            .addGroup(groupLayout.createSequentialGroup()
                                .addComponent(spinner_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(4)
                                .addComponent(lblX)
                                .addGap(7)
                                .addComponent(spinner_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addComponent(spinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnClassicSize, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addContainerGap(239, Short.MAX_VALUE))
        );
        groupLayout.setVerticalGroup(
            groupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(groupLayout.createSequentialGroup()
                    .addGap(165)
                    .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblNumberOfPlayers)
                        .addComponent(spinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGap(18)
                    .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                        .addComponent(spinner_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblX, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
                        .addComponent(spinner_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblGameAreaSize))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(btnClassicSize)
                    .addGap(18)
                    .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                        .addComponent(spinner_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblTurnTime))
                    .addGap(18)
                    .addComponent(chckbxExtraTurn)
                    .addContainerGap(164, Short.MAX_VALUE))
        );
        setLayout(groupLayout);

    }
}
