package com.gromholl.dots.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;



public class Server {

    static public final int DEFAULT_PORT = 5555;
    static public final int DEFAULT_TIMEOUT = 1000;
    
    private int serverPort;
    
    private ThreadController tController;
    
    public Server(int port) {
        serverPort = port;
        
        tController = new ThreadController();
    }
    
    public void start() throws Exception {
        
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(serverPort);
            ss.setSoTimeout(DEFAULT_TIMEOUT);
        } catch(IOException e) {
            throw new Exception("Can't create socket for server.");
        }
        
        System.out.println("Server started.");
        
        try {
            
            while(System.in.available() == 0) {
                
                Socket cs = null;
                
                try {
                    cs = ss.accept();
                } catch (SocketTimeoutException e) {
                    continue;
                }
                
                tController.subscribe(cs);                
                
            }
            
        } catch(IOException e) {
            e.printStackTrace();
        }
        
        tController.unsubscribeAll();
        
    }
    
    /* Main */
    public static void main(String[] args) {
        
        final int port;
        
        if(args.length == 0) {
            port = DEFAULT_PORT;
        } else if(args.length == 1) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.err.println("Port number error: " + args[0]);
                return;
            }
        } else {
            System.err.println("Incorrect arguments count (" + args.length + ").");
            return;
        }
        
        Server s = new Server(port);
        
        try {
            s.start();
        } catch(Exception e) {
            System.err.println(e.getMessage());
        }

    }

}
