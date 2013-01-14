package com.gromholl.dots.client.gui;

import java.awt.Color;
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

public class StatisticPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    

    private JButton btnBack;
    
    private JLabel lblUsername;
    
    private JLabel lblDrawCount;    
    private JLabel lblLoseCount;    
    private JLabel lblWinCount;    
    private JLabel lblTotalCount;
    
    private JProgressBar prbDraw;    
    private JProgressBar prbWin;    
    private JProgressBar prbLose;

    /**
     * Create the panel.
     */
    public StatisticPanel() {
        
        btnBack = new JButton("Back");
        
        JLabel lblPlayer = new JLabel("Player:");
        JLabel lblGamesStatistic = new JLabel("Games Statistic:");        
        JLabel lblFinish = new JLabel("Total:");        
        JLabel lblWin = new JLabel("Win:");        
        JLabel lblDraw = new JLabel("Draw:");        
        JLabel lblLose = new JLabel("Lose:");
                
        lblUsername = new JLabel("USERNAME");
        lblUsername.setHorizontalAlignment(SwingConstants.CENTER);
        lblUsername.setForeground(Color.BLUE);
        
        lblDrawCount = new JLabel("DRAW");        
        lblLoseCount = new JLabel("LOSE");        
        lblWinCount = new JLabel("WIN");        
        lblTotalCount = new JLabel("TOTAL");
        
        prbDraw = new JProgressBar();
        prbDraw.setForeground(Color.BLUE);
        
        prbWin = new JProgressBar();
        prbWin.setForeground(Color.GREEN);
        
        prbLose = new JProgressBar();
        prbLose.setForeground(Color.RED);
        
        GroupLayout groupLayout = new GroupLayout(this);
        groupLayout.setHorizontalGroup(
            groupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(groupLayout.createSequentialGroup()
                    .addGap(161)
                    .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
                            .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                .addComponent(lblLose)
                                .addComponent(lblDraw)
                                .addComponent(lblWin)
                                .addComponent(lblFinish))
                            .addComponent(lblGamesStatistic))
                        .addComponent(lblPlayer))
                    .addGap(18)
                    .addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
                        .addGroup(groupLayout.createSequentialGroup()
                            .addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
                                .addComponent(lblTotalCount, GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
                                .addComponent(lblWinCount, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblDrawCount, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblLoseCount, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
                                .addComponent(prbWin, GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
                                .addComponent(prbDraw, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(prbLose, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addComponent(lblUsername, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addContainerGap(142, Short.MAX_VALUE))
                .addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
                    .addContainerGap(582, Short.MAX_VALUE)
                    .addComponent(btnBack, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap())
        );
        groupLayout.setVerticalGroup(
            groupLayout.createParallelGroup(Alignment.TRAILING)
                .addGroup(groupLayout.createSequentialGroup()
                    .addGap(121)
                    .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblPlayer)
                        .addComponent(lblUsername))
                    .addGap(46)
                    .addComponent(lblGamesStatistic)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblFinish)
                        .addComponent(lblTotalCount))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                            .addComponent(lblWin)
                            .addComponent(lblWinCount))
                        .addComponent(prbWin, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                            .addComponent(lblDraw)
                            .addComponent(lblDrawCount))
                        .addComponent(prbDraw, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                            .addComponent(lblLose)
                            .addComponent(lblLoseCount))
                        .addComponent(prbLose, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGap(179)
                    .addComponent(btnBack)
                    .addContainerGap())
        );
        groupLayout.linkSize(SwingConstants.HORIZONTAL, new Component[] {lblFinish, lblWin, lblDraw, lblLose});
        setLayout(groupLayout);

    }
    
    public void addActionListenerForBackButton(ActionListener l) {
        if(l != null)
            btnBack.addActionListener(l);
    }
}
