package com.gromholl.dots.client.gui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;

public class DotButton extends JButton {
    
    public static final Dimension SIZE = new Dimension(9, 9);    

    public static final Color FREE_COLOR = Color.white;
    public static final Color SRD_COLOR = Color.lightGray;
    public static final Color PLAYER_1_COLOR = new Color(139, 0, 0);
    public static final Color PLAYER_1_COLOR_SRD = new Color(205, 92, 92);
    public static final Color PLAYER_2_COLOR = new Color(0, 100, 0);
    public static final Color PLAYER_2_COLOR_SRD = new Color(127, 255, 0);
    public static final Color PLAYER_3_COLOR = new Color(0, 0, 139 );
    public static final Color PLAYER_3_COLOR_SRD = new Color(173, 216, 230);
    public static final Color PLAYER_4_COLOR = new Color(0, 0, 0);
    public static final Color PLAYER_4_COLOR_SRD = new Color(127, 127, 127);
    
    private static final long serialVersionUID = 0L;
    
    private int xCoor;
    private int yCoor;
    
    public DotButton(int x, int y) {
        xCoor = x;
        yCoor = y;

        this.setMaximumSize(SIZE);
        this.setMinimumSize(SIZE);
        this.setPreferredSize(SIZE);
        
        setStatus(0, true);
    }
    
    public int getXCoor() {
        return xCoor;
    }
    public int getYCoor() {
        return yCoor;
    }
    
    public void setStatus(int owner, boolean isFree) {
        if(isFree) {
            switch(owner) {
                case 0: setBackground(FREE_COLOR);
                        break;
                case 1: setBackground(PLAYER_1_COLOR);
                        break;
                case 2: setBackground(PLAYER_2_COLOR);
                        break;
                case 3: setBackground(PLAYER_3_COLOR);
                        break;
                case 4: setBackground(PLAYER_4_COLOR);
                        break;
            }
        } else {
            switch(owner) {
                case 0: setBackground(SRD_COLOR);
                        break;
                case 1: setBackground(PLAYER_1_COLOR_SRD);
                        break;
                case 2: setBackground(PLAYER_2_COLOR_SRD);
                        break;
                case 3: setBackground(PLAYER_3_COLOR_SRD);
                        break;
                case 4: setBackground(PLAYER_4_COLOR_SRD);
                        break;
            }
        }
                
        
    }
    
}
