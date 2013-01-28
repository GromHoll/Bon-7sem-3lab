package com.gromholl.dots.shared;

import java.util.ArrayList;
import java.util.List;

public class ServerGameState {
    
    public String activePlayer;
    public List<PlayerState> playerStates;
    public ServerGameMap map;
    
    public ServerGameState() {
        this.activePlayer = null;
        this.map = null;
        this.playerStates = new ArrayList<PlayerState>();
    }
    
    public ServerGameState(ArrayList<PlayerState> pss) {
        this.activePlayer = null;
        this.map = null;
        this.playerStates = pss;
    }
    
    public void addPlayerState(PlayerState playerState) {
        this.playerStates.add(playerState);
    }
    
}
