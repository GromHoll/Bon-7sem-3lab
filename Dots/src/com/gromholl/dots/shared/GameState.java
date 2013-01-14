package com.gromholl.dots.shared;

import java.util.ArrayList;
import java.util.List;

public class GameState {
    
    private String activePlayer;    
    private List<PlayerState> playerStates;
    private GameMap map;
    
    public GameState() {
        this.activePlayer = null;
        this.map = null;
        this.playerStates = new ArrayList<PlayerState>();
    }

    public String getActivePlayer() {
        return activePlayer;
    }
    public List<PlayerState> getPlayerStates() {
        return playerStates;
    }
    public GameMap getMap() {
        return map;
    }

    public void setActivePlayer(String activePlayer) {
        this.activePlayer = activePlayer;
    }
    public void setPlayerStates(List<PlayerState> playerStates) {
        this.playerStates = playerStates;
    }
    public void setMap(GameMap map) {
        this.map = map;
    }
    
    public void addPlayerState(PlayerState playerState) {
        this.playerStates.add(playerState);
    }
    
}
