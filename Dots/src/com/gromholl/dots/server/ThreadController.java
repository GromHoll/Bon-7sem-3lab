package com.gromholl.dots.server;

import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class ThreadController {

    // DB connect
    
    private List<ClientThread> threads;
    
    public ThreadController() {
        threads = new LinkedList<ClientThread>();
    }
    
    public void subscribe(Socket s) {
        System.out.println(s.getInetAddress() + " connected.");
        
        ClientThread ct = new ClientThread(s, this);
        threads.add(ct);
        ct.start();
    }
    
    public void unsubscribe(ClientThread clientThread) {
        System.out.println("Client thread leave.");
    }
    
    public void unsubscribeAll() {
        
        for(ClientThread ct : threads) {
            ct.finish();
        }
        
        threads.clear();
    }
    
}
