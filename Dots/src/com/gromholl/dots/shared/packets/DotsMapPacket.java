package com.gromholl.dots.shared.packets;

import java.util.ArrayList;
import java.util.List;

import com.gromholl.dots.shared.DotsMap;
import com.gromholl.dots.shared.PlayerInfo;

public class DotsMapPacket {
    
    private String activePlayer;
    private List<PlayerInfo> players;
    
    private DotsMap map = null;
    
    public DotsMapPacket() {
        players = new ArrayList<PlayerInfo>();
    }
    
    public void addPlayer(PlayerInfo player) {
        players.add(player);
    }
    public void addDotsMap(DotsMap map) {
        this.map = map;
    }
    public void setActivePlayer(String nickname) {
        activePlayer = nickname;
    }
    
    public List<PlayerInfo> getPlayerList() {
        return players;
    }
    public String getActivePlayer() {
        return activePlayer;
    }
    public DotsMap getMap() {
        return map;
    }
    
}
