package com.gromholl.dots.client.gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import com.gromholl.dots.shared.GameSettings;
import com.gromholl.dots.shared.Packet;

public class CreatePanel extends JPanel {

    private static final long serialVersionUID = 1L;
    
    public static int X_SIZE_DEF = 39;
    public static int Y_SIZE_DEF = 32;
    

    JSpinner spPlayersNum;
    JSpinner spXSize;
    JSpinner spYSize;
    JSpinner spTurnTime;
    
    JCheckBox chbxExtraTurn;
    
    JButton btnCreate;        
    JButton btnCancel;
    
    /**
     * Create the panel.
     */
    public CreatePanel() {
        
        JLabel lblNumberOfPlayers = new JLabel("Number of players:");        
        JLabel lblGameAreaSize = new JLabel("Game area size:");        
        JLabel lblTurnTime = new JLabel("Turn time:");
        
        spPlayersNum = new JSpinner();
        spPlayersNum.setModel(new SpinnerNumberModel(2, 2, 4, 1));
        
        spXSize = new JSpinner();
        spXSize.setModel(new SpinnerNumberModel(X_SIZE_DEF, 10, 50, 1));
        
        JLabel lblX = new JLabel("X");
        
        spYSize = new JSpinner();
        spYSize.setModel(new SpinnerNumberModel(Y_SIZE_DEF, 10, 50, 1));
        
        JButton btnClassicSize = new JButton("Classic size");
        btnClassicSize.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                spXSize.setValue(X_SIZE_DEF);
                spYSize.setValue(Y_SIZE_DEF);
            }
        });
        
        spTurnTime = new JSpinner();
        spTurnTime.setModel(new SpinnerNumberModel(60, 15, 90, 1));
        
        chbxExtraTurn = new JCheckBox("Extra turn");
        chbxExtraTurn.setSelected(true);
        
        btnCreate = new JButton("Create");        
        btnCancel = new JButton("Cancel");
        
        GroupLayout groupLayout = new GroupLayout(this);
        groupLayout.setHorizontalGroup(
            groupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
                    .addContainerGap(238, Short.MAX_VALUE)
                    .addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
                        .addComponent(lblGameAreaSize)
                        .addComponent(lblTurnTime)
                        .addComponent(lblNumberOfPlayers)
                        .addComponent(btnCreate, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(ComponentPlacement.UNRELATED)
                    .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                        .addComponent(chbxExtraTurn)
                        .addComponent(spTurnTime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
                            .addGroup(groupLayout.createSequentialGroup()
                                .addComponent(spXSize, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(4)
                                .addComponent(lblX)
                                .addGap(7)
                                .addComponent(spYSize, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addComponent(spPlayersNum, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnClassicSize, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnCancel, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)))
                    .addGap(234))
        );
        groupLayout.setVerticalGroup(
            groupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
                    .addContainerGap(127, Short.MAX_VALUE)
                    .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblNumberOfPlayers)
                        .addComponent(spPlayersNum, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGap(18)
                    .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                        .addComponent(spXSize, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblX, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
                        .addComponent(spYSize, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblGameAreaSize))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(btnClassicSize)
                    .addGap(18)
                    .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                        .addComponent(spTurnTime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblTurnTime))
                    .addGap(18)
                    .addComponent(chbxExtraTurn)
                    .addGap(54)
                    .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                        .addComponent(btnCancel)
                        .addComponent(btnCreate))
                    .addGap(122))
        );
        groupLayout.linkSize(SwingConstants.HORIZONTAL, new Component[] {btnCreate, btnCancel});
        setLayout(groupLayout);
    }
    
    public void addActionListenerForCreateButton(ActionListener l) {
        if(l != null)
            btnCreate.addActionListener(l);
    }
    public void addActionListenerForCancelButton(ActionListener l) {
        if(l != null)
            btnCancel.addActionListener(l);
    }

    public Packet getCreateGamePacket() {
        
        Packet res = new Packet(Packet.PACKET_CODES.NEW_GAME_CMD);
        GameSettings gs = new GameSettings();
        
        gs.extraTurn = chbxExtraTurn.isSelected();
        gs.maxCountOfPlayer = (Integer) spPlayersNum.getValue();
        gs.turnTime = (Integer) spTurnTime.getValue();
        gs.XSize = (Integer) spXSize.getValue();
        gs.YSize = (Integer) spYSize.getValue();
        
        res.setData(gs);
        return res;
    }
}
