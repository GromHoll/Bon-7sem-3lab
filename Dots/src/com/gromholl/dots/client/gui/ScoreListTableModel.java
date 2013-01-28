package com.gromholl.dots.client.gui;

import javax.swing.table.AbstractTableModel;

import com.gromholl.dots.shared.GameState;

public class ScoreListTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 1L;
    
    private GameState gs;

    public void setPlayerScores(GameState gs) {
        this.gs = gs;
        fireTableDataChanged();
    }
    
    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public int getRowCount() {
        if(gs == null)
            return 0;
        else
            return gs.playerStates.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        
        if(columnIndex < 0 || columnIndex >= getColumnCount())
            return null;
        if(rowIndex < 0 || rowIndex >= getRowCount())
            return null;
        
        switch (columnIndex) {
        case 0:
            return gs.playerStates.get(rowIndex).getNickname();
        case 1:
            if(gs.playerStates.get(rowIndex).getNickname().equals(gs.activePlayer)) {
                return "turn";
            }
            if(gs.playerStates.get(rowIndex).isActive()) {
                return "wait";
            } else {
                return "finished";
            }
        case 2:
            return gs.playerStates.get(rowIndex).getScore();
        }
        
        return null;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
        case 0:
            return "Player";
        case 1:
            return "Status";
        case 2:
            return "Score";
        }
        return null;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

}
