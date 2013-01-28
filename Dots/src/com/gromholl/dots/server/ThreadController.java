package com.gromholl.dots.server;

import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import com.gromholl.dots.server.db.DBController;

public class ThreadController {

    public static final String DB_DEF_FILE = "users.db";    
    private DBController dbController;
    private LobbyController lobbyController;
    
    private List<ClientThread> threads;
    
    public ThreadController() {
        threads = new LinkedList<ClientThread>();
        dbController = new DBController(DB_DEF_FILE);
        lobbyController = new LobbyController();
    }
    
    public void subscribe(Socket s) {
        System.out.println(s.getInetAddress() + " connected.");
        
        ClientThread ct = new ClientThread(s, this);
        threads.add(ct);
        ct.start();
    }
    public void unsubscribeAll() {
        
        for(ClientThread ct : threads) {
            ct.finish();
        }
        
        threads.clear();
    }

    public DBController getDBController() {
        return dbController;
    }    
    public LobbyController getLobbyController() {
        return lobbyController;
    }
}
