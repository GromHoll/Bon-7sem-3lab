package com.gromholl.dots.shared;

import java.util.ArrayList;
import java.util.List;

public class GameLobby {

    private Integer ID;
    
    private Integer maxCountOfPlayer;
    private Integer currentCountOfPlayer;
    
    private Integer XSize;
    private Integer YSize;
    
    private Integer turnTime;
    private Boolean extraTurn;
    
    private List<String> players;
    
    public GameLobby() {
        ID = Integer.valueOf(0);
        
        maxCountOfPlayer = Integer.valueOf(0);
        currentCountOfPlayer = Integer.valueOf(0);
        
        XSize = Integer.valueOf(0);
        YSize = Integer.valueOf(0);
        
        turnTime = Integer.valueOf(0);
        extraTurn = Boolean.valueOf(false);
        
        players = new ArrayList<String>();
    }
    

    public Integer getID() {
        return ID;
    }

    public Integer getMaxCountOfPlayer() {
        return maxCountOfPlayer;
    }

    public Integer getCurrentCountOfPlayer() {
        return currentCountOfPlayer;
    }

    public Integer getXSize() {
        return XSize;
    }

    public Integer getYSize() {
        return YSize;
    }

    public Integer getTurnTime() {
        return turnTime;
    }

    public Boolean getExtraTurn() {
        return extraTurn;
    }

    public List<String> getPlayers() {
        return players;
    }
    

    public void setID(Integer iD) {
        ID = iD;
    }

    public void setMaxCountOfPlayer(Integer maxCountOfPlayer) {
        this.maxCountOfPlayer = maxCountOfPlayer;
    }

    public void setCurrentCountOfPlayer(Integer currentCountOfPlayer) {
        this.currentCountOfPlayer = currentCountOfPlayer;
    }

    public void setXSize(Integer XSize) {
        this.XSize = XSize;
    }

    public void setYSize(Integer YSize) {
        this.YSize = YSize;
    }

    public void setTurnTime(Integer turnTime) {
        this.turnTime = turnTime;
    }

    public void setExtraTurn(Boolean extraTurn) {
        this.extraTurn = extraTurn;
    }

    public void setPlayers(List<String> players) {
        this.players = players;
    }
    
    
    public void addPlayer(String player) {
        this.players.add(player);
    }
    
}
