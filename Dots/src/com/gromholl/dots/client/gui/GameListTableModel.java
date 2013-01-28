package com.gromholl.dots.client.gui;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import com.gromholl.dots.shared.GameLobby;

public class GameListTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 1L;
    
    ArrayList<GameLobby> gl = null;

    public void setGameList(ArrayList<GameLobby> gl) {
        this.gl = gl;
        fireTableDataChanged();
    }
    
    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public int getRowCount() {
        if(gl == null)
            return 0;
        else
            return gl.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        
        if(columnIndex < 0 || columnIndex >= getColumnCount())
            return null;
        if(rowIndex < 0 || rowIndex >= getRowCount())
            return null;
        
        switch (columnIndex) {
        case 0:
            return gl.get(rowIndex).ID;
        case 1:
            return new String(gl.get(rowIndex).settings.XSize + "x" + gl.get(rowIndex).settings.YSize);
        case 2:
            return gl.get(rowIndex).settings.turnTime;
        case 3:
            return gl.get(rowIndex).settings.extraTurn;
        case 4:
            return new String(gl.get(rowIndex).currentCountOfPlayer + "/" + gl.get(rowIndex).settings.maxCountOfPlayer);
        case 5:
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < gl.get(rowIndex).players.size(); i++) {
                sb.append(gl.get(rowIndex).players.get(i));
                if(i < gl.get(rowIndex).players.size() - 1)
                    sb.append(", ");
            }
            return sb.toString();
        }
        
        return null;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
        case 0:
            return "Game ID";
        case 1:
            return "Map size";
        case 2:
            return "Turn time";
        case 3:
            return "Extra turn";
        case 4:
            return "Players count";
        case 5:
            return "Players";
        }
        return null;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
    
    public Integer getID(int rowIndex) {
        return (Integer) getValueAt(rowIndex, 0);
    }

}
