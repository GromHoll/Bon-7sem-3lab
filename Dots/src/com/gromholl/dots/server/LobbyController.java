package com.gromholl.dots.server;

import java.util.ArrayList;
import java.util.HashMap;

import com.gromholl.dots.shared.GameLobby;
import com.gromholl.dots.shared.GameSettings;
import com.gromholl.dots.shared.Packet;

public class LobbyController {
   
    public static final int OK_OPT = 0;
    public static final int NOT_EXIST_OPT = 1;
    public static final int FULL_OPT = 2;
    
    private volatile int lastID;
    private volatile HashMap<Integer, Lobby> lobbies;
   
    public LobbyController() {
        lastID = Integer.valueOf(0);
        lobbies = new HashMap<Integer, Lobby>();
    }
   
    public synchronized void addLobby(GameSettings gs, ClientThread ct) {
        
        Packet answer = null;
        
        if(gs == null || ct == null) {
            answer = Packet.getServerErrorPacket();
        } else {        
            lastID++;       
            Lobby l = new Lobby(lastID, gs, this);
            lobbies.put(lastID, l);        
            l.addPlayer(ct);
            
            ct.gameID = new Integer(lastID);
            answer = new Packet(Packet.PACKET_CODES.GAME_CREATED_CODE);
            answer.setData(ct.gameID);
        }
        
        try {
            ct.sendToClient(answer);
        } catch(Exception e) {}
    }
    
    public synchronized void deleteLobby(Lobby l) {
        lobbies.remove(l.getID());
    }
    
    public synchronized ArrayList<GameLobby> getGameLobbies() {
        
        ArrayList<GameLobby> list = new ArrayList<GameLobby>();                
        for(Integer i : lobbies.keySet()) {
            GameLobby gl = new GameLobby();
            gl.ID = i;
            gl.settings = lobbies.get(i).settings;   
            gl.players = lobbies.get(i).getPlayersList();
            gl.currentCountOfPlayer = gl.players.size();
            list.add(gl);
        }
        
        return list;
    }
    
    public synchronized int joinToLobby(Integer ID, ClientThread ct) {        
        Lobby l = lobbies.get(ID);        
        if(l == null) {
            return NOT_EXIST_OPT;
        }        
        if(l.addPlayer(ct)) {
            try {
                ct.gameID = ID;
                ct.sendToClient(Packet.getSuccessPacket());
                l.tryStart();
            } catch(Exception e) {}
            return OK_OPT;
        } else {
            return FULL_OPT;
        }
    }
    
}
