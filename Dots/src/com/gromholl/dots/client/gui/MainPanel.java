package com.gromholl.dots.client.gui;

import java.awt.Component;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class MainPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private JTable table;

    JButton btnRefresh;    
    JButton btnJoin;    
    JButton btnCreate;    
    JButton btnStatistic;    
    JButton btnLogout;
    
    /**
     * Create the panel.
     */
    @SuppressWarnings("serial")
    public MainPanel() {
        
        JScrollPane scrollPane = new JScrollPane();
        
        btnRefresh = new JButton("Refresh");        
        btnJoin = new JButton("Join");        
        btnCreate = new JButton("Create");        
        btnStatistic = new JButton("Statistic");        
        btnLogout = new JButton("Logout");
        
        GroupLayout groupLayout = new GroupLayout(this);
        groupLayout.setHorizontalGroup(
            groupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(groupLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                        .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 680, Short.MAX_VALUE)
                        .addGroup(groupLayout.createSequentialGroup()
                            .addComponent(btnRefresh, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
                            .addGap(75)
                            .addComponent(btnJoin)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(btnCreate)
                            .addPreferredGap(ComponentPlacement.RELATED, 91, Short.MAX_VALUE)
                            .addComponent(btnStatistic)
                            .addGap(88)
                            .addComponent(btnLogout)))
                    .addContainerGap())
        );
        groupLayout.setVerticalGroup(
            groupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(groupLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGap(18)
                    .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                        .addComponent(btnRefresh)
                        .addComponent(btnLogout)
                        .addComponent(btnCreate)
                        .addComponent(btnJoin)
                        .addComponent(btnStatistic))
                    .addContainerGap(21, Short.MAX_VALUE))
        );
        groupLayout.linkSize(SwingConstants.HORIZONTAL, new Component[] {btnRefresh, btnJoin, btnCreate, btnStatistic, btnLogout});
        
        table = new JTable();
        table.setModel(new DefaultTableModel(
            new Object[][] {
            },
            new String[] {
                "Game ID", "Map Size", "Turn Time", "Extra Turn", "Player Count", "Players"
            }
        ) {
            @SuppressWarnings("rawtypes")
            Class[] columnTypes = new Class[] {
                Integer.class, String.class, Integer.class, Boolean.class, String.class, String.class
            };
            @SuppressWarnings({ "unchecked", "rawtypes" })
            public Class getColumnClass(int columnIndex) {
                return columnTypes[columnIndex];
            }
            boolean[] columnEditables = new boolean[] {
                false, false, false, false, false, false
            };
            public boolean isCellEditable(int row, int column) {
                return columnEditables[column];
            }
        });
        table.getColumnModel().getColumn(5).setPreferredWidth(300);
        scrollPane.setViewportView(table);
        setLayout(groupLayout);

    }
    
    public void addActionListenerForRefreshButton(ActionListener l) {
        if(l != null)
            btnRefresh.addActionListener(l);
    }
    
    public void addActionListenerForJoinButton(ActionListener l) {
        if(l != null)
            btnJoin.addActionListener(l);
    }
    
    public void addActionListenerForCreateButton(ActionListener l) {
        if(l != null)
            btnCreate.addActionListener(l);
    }
    
    public void addActionListenerForStatisticButton(ActionListener l) {
        if(l != null)
            btnStatistic.addActionListener(l);
    }
    
    public void addActionListenerForLogoutButton(ActionListener l) {
        if(l != null)
            btnLogout.addActionListener(l);
    }
    
}
