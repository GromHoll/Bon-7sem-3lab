package com.gromholl.dots.client.gui;

import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;

import com.gromholl.dots.shared.GameState;

public class GamePanel extends JPanel {

    private static final long serialVersionUID = 1L;
            
    private ScoreListTableModel scoreModel;
         
    private JButton btnFinish;        
    private JButton btnLeave;
    
    private JTextArea txtrMessages;
    
    private GameMapPane gameMapPane;
    
    /**
     * Create the panel.
     */
    public GamePanel() {
        
        JScrollPane scpMap = new JScrollPane();
        
        JScrollPane scrollPane = new JScrollPane();
        
        btnFinish = new JButton("Finish");        
        btnLeave = new JButton("Leave");
        
        gameMapPane = new GameMapPane();
        
        txtrMessages = new JTextArea();
        txtrMessages.setWrapStyleWord(true);
        txtrMessages.setEditable(false);
        txtrMessages.setLineWrap(true);
        txtrMessages.setText("Messages");
        GroupLayout groupLayout = new GroupLayout(this);
        groupLayout.setHorizontalGroup(
            groupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(groupLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(scpMap, GroupLayout.PREFERRED_SIZE, 519, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
                        .addComponent(scrollPane, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 146, GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnFinish, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                        .addComponent(btnLeave, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                        .addComponent(txtrMessages, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE))
                    .addContainerGap())
        );
        groupLayout.setVerticalGroup(
            groupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(groupLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
                        .addGroup(groupLayout.createSequentialGroup()
                            .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
                            .addGap(113)
                            .addComponent(txtrMessages, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                            .addComponent(btnFinish)
                            .addPreferredGap(ComponentPlacement.UNRELATED)
                            .addComponent(btnLeave))
                        .addComponent(scpMap, GroupLayout.DEFAULT_SIZE, 476, Short.MAX_VALUE))
                    .addContainerGap())
        );
        
        scoreModel = new ScoreListTableModel();
        
        JTable table = new JTable(scoreModel);
        scrollPane.setViewportView(table);
        setLayout(groupLayout);
        
        scpMap.setViewportView(gameMapPane);

    }
    
    public void addActionListenerForFinishButton(ActionListener l) {
        if(l != null)
            btnFinish.addActionListener(l);
    }
    public void addActionListenerForLeaveButton(ActionListener l) {
        if(l != null)
            btnLeave.addActionListener(l);
    }
    
    public void setNewMap(int x, int y) {
        gameMapPane.prepareNewSize(x, y);        
    }    
    public void setNewGameState(GameState gs) throws Exception { 
        gameMapPane.setMap(gs.map);
        scoreModel.setPlayerScores(gs);
        
    }
    
    public void setMessage(String message) {
        txtrMessages.setText(message);
    }
    
    public GameMapPane getGameMapPane() {
        return gameMapPane;
    }
}
