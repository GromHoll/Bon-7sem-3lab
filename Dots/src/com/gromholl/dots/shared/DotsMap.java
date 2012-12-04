package com.gromholl.dots.shared;

public class DotsMap {
    
    public DotsMapCell map[][];
    
    public DotsMap(int x, int y) {
        map = new DotsMapCell[x][y];
        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[i].length; j++) {
                map[i][j] = new DotsMapCell();
            }            
        }
    }
    
    public int getXSize() {
        return map.length;
    }
    public int getYSize() {
        if(map.length != 0)
            return map[0].length;
        else
            return 0;
    }

    public int getCellOwner(int x, int y) {
        return map[x][y].getOwner(); 
    }
    public boolean getCellStatus(int x, int y) {
        return map[x][y].isFree(); 
    }
    
}
