package com.gromholl.dots.shared;

import java.util.ArrayList;
import java.util.List;

public class GameState {
    
    public String activePlayer;    
    public List<PlayerState> playerStates;
    public GameMap map;
    
    public GameState() {
        this.activePlayer = null;
        this.map = null;
        this.playerStates = new ArrayList<PlayerState>();
    }
    
    public void addPlayerState(PlayerState playerState) {
        this.playerStates.add(playerState);
    }
    
}
