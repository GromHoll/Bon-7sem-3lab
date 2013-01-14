package com.gromholl.dots.client.gui;

import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class GamePanel extends JPanel {

    private static final long serialVersionUID = 1L;
    
    private JTable table;
    
    private JButton btnTurn;        
    private JButton btnFinish;        
    private JButton btnLeave;
    
    private JProgressBar prbTime;
    
    private JTextArea txtrMessages;
    
    /**
     * Create the panel.
     */
    @SuppressWarnings("serial")
    public GamePanel() {
        
        JScrollPane scpMap = new JScrollPane();
        
        JScrollPane scrollPane = new JScrollPane();
        
        btnTurn = new JButton("TURN");        
        btnFinish = new JButton("Finish");        
        btnLeave = new JButton("Leave");
        
        prbTime = new JProgressBar();
        prbTime.setToolTipText("");
        prbTime.setValue(100);
        
        JLabel lblTime = new JLabel("Time");
        lblTime.setHorizontalAlignment(SwingConstants.CENTER);
        
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
                        .addComponent(lblTime, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(prbTime, 0, 0, Short.MAX_VALUE)
                        .addComponent(btnTurn, GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                        .addComponent(txtrMessages, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE))
                    .addContainerGap())
        );
        groupLayout.setVerticalGroup(
            groupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(groupLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
                            .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
                            .addGap(60)
                            .addComponent(lblTime)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(prbTime, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(btnTurn)
                            .addGap(18)
                            .addComponent(txtrMessages, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                            .addComponent(btnFinish)
                            .addPreferredGap(ComponentPlacement.UNRELATED)
                            .addComponent(btnLeave))
                        .addComponent(scpMap, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 476, Short.MAX_VALUE))
                    .addContainerGap())
        );
        
        table = new JTable();
        table.setRowSelectionAllowed(false);
        table.setModel(new DefaultTableModel(
            new Object[][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
            },
            new String[] {
                "Player", "Status", "Score"
            }
        ) {
            @SuppressWarnings("rawtypes")
            Class[] columnTypes = new Class[] {
                String.class, String.class, Long.class
            };
            @SuppressWarnings({ "rawtypes", "unchecked" })
            public Class getColumnClass(int columnIndex) {
                return columnTypes[columnIndex];
            }
            boolean[] columnEditables = new boolean[] {
                false, false, false
            };
            public boolean isCellEditable(int row, int column) {
                return columnEditables[column];
            }
        });
        scrollPane.setViewportView(table);
        setLayout(groupLayout);

    }
    
    public void addActionListenerForTurnButton(ActionListener l) {
        if(l != null)
            btnTurn.addActionListener(l);
    }
    public void addActionListenerForFinishButton(ActionListener l) {
        if(l != null)
            btnFinish.addActionListener(l);
    }
    public void addActionListenerForLeaveButton(ActionListener l) {
        if(l != null)
            btnLeave.addActionListener(l);
    }
    
}
