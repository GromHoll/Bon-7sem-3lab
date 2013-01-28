package com.gromholl.dots.shared;

import java.util.ArrayList;
import java.util.List;

public class GameLobby {

    public Integer ID;

    public Integer currentCountOfPlayer;
    public List<String> players;
    
    public GameSettings settings;
    
    public GameLobby() {
        players = new ArrayList<String>();
        settings = new GameSettings();
        currentCountOfPlayer = Integer.valueOf(0);
        ID = Integer.valueOf(0);
    }
    
}
