package com.gromholl.dots.shared;

import java.util.ArrayList;

public class ServerGameMap {
    
    public int freePoint;
    public ServerGameMapCell map[][];
    
    private int scores[];
    
    public ServerGameMap(int x, int y) {
        
        map = new ServerGameMapCell[x][y];
        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[i].length; j++) {
                map[i][j] = new ServerGameMapCell();
            }            
        }
        scores = new int[4];
        freePoint = x*y;
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

    public int getScore(int invader) {
        return scores[invader-1];
    }
    private void countScores() {
        
        for(int i = 0; i < scores.length; i++) {
            scores[i] = 0;
        }        
        for(int i = 0; i < getXSize(); i++) {
            for(int j = 0; j < getYSize(); j++) {
                if(map[i][j].owner != ServerGameMapCell.WITHOUT_OWNER_OPT
                && map[i][j].invader != ServerGameMapCell.WITHOUT_INVADER_OPT
                && map[i][j].invader != map[i][j].owner) {
                    System.out.println(map[i][j].invader + "-1)++");
                    scores[map[i][j].invader-1]++;
                }
            }
        }        

    }
    
    public ServerGameMapCell getCell(int x, int y) {
        return map[x][y]; 
    }

    public void setNewDot(int x, int y, int owner) {
        
        freePoint--;
        
        map[x][y].owner = owner;        
        if(!isLinkedDot(x, y, owner)) {
            return;
        }
        
        findCaptureArea(x+1, y, owner);
        findCaptureArea(x-1, y, owner);
        findCaptureArea(x, y+1, owner);
        findCaptureArea(x, y-1, owner);
        
    }
    
    private void findCaptureArea(int x, int y, int owner) {
        
        if(!isOnMap(x, y)) {
            return;
        }
        
        if(map[x][y].owner == owner) {
            return;
        }
        
        boolean mask[][] = new boolean[getXSize()][getYSize()];
        for(int i = 0; i < getXSize(); i++) {
            for(int j = 0; j < getYSize(); j++) {
                mask[i][j] = false;
            }
        }
        
        if(!processDot_r(x, y, owner, mask)) {
            return;
        }
        
        for(int i = 0; i < getXSize(); i++) {
            for(int j = 0; j < getYSize(); j++) {
                if(mask[i][j]) {
                    map[i][j].invade(owner);
                    if(map[i][j].owner == ServerGameMapCell.WITHOUT_OWNER_OPT) {
                        freePoint--;
                    }
                }
            }
        }

        countScores();        
    }
    private boolean processDot_r(int x, int y, int owner, boolean mask[][]) {
        if(isOnBorder(x, y)) {
            return false;
        }
        
        if(map[x][y].owner == owner && map[x][y].status) {
            return true;
        }
        
        if(mask[x][y]) {
            return true;
        }
        mask[x][y] = true;
        
        if(!processDot_r(x+1, y, owner, mask)) {
            return false;
        }
        if(!processDot_r(x-1, y, owner, mask)) {
            return false;
        }
        if(!processDot_r(x, y+1, owner, mask)) {
            return false;
        }
        if(!processDot_r(x, y-1, owner, mask)) {
            return false;
        }        
        
        return true;
    }
    
    public boolean isOnBorder(int x, int y) {
        if(x == 0 || y == 0 || x == (getXSize() - 1) || y == (getYSize() - 1)) {
            return true;
        }
        return false;
    }
    public boolean isCorrectTurn(int x, int y) {
        
        if(!isOnMap(x, y)) {
            return false;
        }
        
        if(map[x][y].status == ServerGameMapCell.SURROUNDED_OPT) {
            return false;
        }
        
        if(map[x][y].owner != ServerGameMapCell.WITHOUT_OWNER_OPT) {
            return false;
        }
        
        return true;
    }
    private boolean isOnMap(int x, int y) {
        if(x < 0 || x >= getXSize() || y < 0 || y >= getYSize()) {
            return false;
        } else {
            return true;
        }
    }
    private boolean isLinkedDot(int x, int y, int owner) {
        
        ArrayList<TurnCoordinate> points = new ArrayList<TurnCoordinate>();

        for(int i = x - 1; i <= x + 1; i++) {
            for(int j = y - 1; j <= y + 1; j++) {
                if(isOnMap(i, j)) {
                    if(map[i][j].owner == owner && map[i][j].status == ServerGameMapCell.FREE_OPT) {
                        points.add(new TurnCoordinate(i, j));
                    }
                }
            }
        }
        
        if(points.size() < 2) {
            return false;
        }

        for(TurnCoordinate tc1 : points) {
            for(TurnCoordinate tc2 : points) {
                if(!isNearby(tc1, tc2)) {
                    return true;
                }
            }
        }
        
        return false;        
    }
    private boolean isNearby(TurnCoordinate tc1, TurnCoordinate tc2) {
        if((tc1.x - tc2.x) == 2 || (tc1.x - tc2.x) == -2) {
            return false;
        }        
        if((tc1.y - tc2.y) == 2 || (tc1.y - tc2.y) == -2) {
            return false;
        }        
        return true;
    }
    
    
}