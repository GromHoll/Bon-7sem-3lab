package com.gromholl.dots.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import com.gromholl.dots.shared.Packet;
import com.gromholl.dots.shared.PacketManager;

public class ClientThread extends Thread {

    private boolean finishFlag;
    
    private ThreadController controller;
    private Socket socket;
    
    private DataOutputStream out = null;
    private BufferedReader in = null;
    
    public ClientThread(Socket s, ThreadController controller) {
        this.controller = controller;
        this.socket = s;
    }
    
    @Override
    public void run() {
        
        try {
            this.out  = new DataOutputStream(socket.getOutputStream());
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "US-ASCII"));

            finishFlag = false;
            
            while(!finishFlag) {
                
                if(in.ready()) {
                    
                    Packet packet = PacketManager.readPacketFromStream(in);
                    
                    // TODO work with packet
                    
                    // TODO send 200 packet
                    sendToClient(Packet.getSuccessPacket());
                    
                } else {
                    Thread.sleep(1000);
                }
            }

            // TODO: Logic
        } catch(IOException | InterruptedException e) {
            e.printStackTrace();
        }
        
        controller.unsubscribe(this);
    }
    
    public void sendToClient(Packet packet) throws IOException {
        
        if(packet != null) {
            
            String srt = PacketManager.pack(packet);
            
            byte[] buf = srt.getBytes("US-ASCII");            
            out.write(buf, 0, buf.length);
        }
    }
    
    public void finish() {
        // TODO
    }
}
