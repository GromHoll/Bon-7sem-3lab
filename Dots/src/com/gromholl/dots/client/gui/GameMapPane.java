package com.gromholl.dots.client.gui;

import java.awt.GridLayout;

import javax.swing.JPanel;

import com.gromholl.dots.shared.GameMap;

public class GameMapPane extends JPanel {
    
    private static final long serialVersionUID = 1L;
    
    public DotButton buttons[][];
    public int x, y;
        
    public GameMapPane() {        
        buttons = null;
        x = 0;
        y = 0;
    }
    
    public void prepareNewSize(int x, int y) {
        buttons = new DotButton[x][y];
        this.removeAll();
        this.setLayout(new GridLayout(x, y));
        this.x = x;
        this.y = y;
        
        for(int i = 0; i < x; i++) {
            for(int j = 0; j < y; j++) {
                buttons[i][j] = new DotButton(i, j);
                this.add(buttons[i][j]);
            }
        }
    }
    
    public void setMap(GameMap gm) throws Exception {
        if(gm.getXSize() != x || gm.getYSize() != y) {
            throw new Exception("Old map size and new map size not equals.");
        }
        
        for(int i = 0; i < x; i++) {
            for(int j = 0; j < y; j++) {
                buttons[i][j].setStatus(gm.getCell(i, j).owner,
                                        gm.getCell(i, j).status);
            }
        }
    }
    
}
