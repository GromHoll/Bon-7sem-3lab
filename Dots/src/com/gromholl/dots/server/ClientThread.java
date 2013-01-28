package com.gromholl.dots.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import com.gromholl.dots.server.db.DBController;
import com.gromholl.dots.shared.GameLobby;
import com.gromholl.dots.shared.GameSettings;
import com.gromholl.dots.shared.Packet;
import com.gromholl.dots.shared.PacketManager;
import com.gromholl.dots.shared.PlayerStatistic;

public class ClientThread extends Thread {

    private boolean finishFlag;
    
    private ThreadController controller;
    private Socket socket;
    
    private DataOutputStream out = null;
    private BufferedReader in = null;
    
    private String nickname;
    
    public volatile Integer gameID;
    
    public ClientThread(Socket s, ThreadController controller) {
        this.controller = controller;
        this.socket = s;
        this.nickname = null;
        this.gameID = null;
    }
    
    @Override
    public void run() {
        
        try {
            this.out  = new DataOutputStream(socket.getOutputStream());
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "US-ASCII"));

            finishFlag = false;            
            while(!finishFlag) {              
                if(in.ready()) {
                    processRequest(readFromClient());
                } else {
                    Thread.sleep(100);
                }
                
                while(gameID != null) {
                    Thread.sleep(100);
                }
            }
        } catch(IOException | InterruptedException e) {
            System.out.println("Lost connection with " + nickname + " (" + socket.getInetAddress() + ")");
        } 
        close();
    }
    
    public void sendToClient(Packet packet) throws IOException {
        
        if(packet != null) {            
            String srt = PacketManager.pack(packet);
            
            //XXX Send from ClientThread
            System.out.println("== SEND==");
            System.out.println(srt);
            System.out.println("== END ==");
            
            byte[] buf = srt.getBytes("US-ASCII");            
            out.write(buf, 0, buf.length);
        }
    }
    public Packet readFromClient() throws IOException {
        return PacketManager.readPacketFromStream(in);
    }

    private void processRequest(Packet packet) throws IOException {
        
        Packet out = null;
        
        if(packet == null) {
            out = Packet.getIncorrectCommandPacket();
        } else {
            String code = packet.getCode();
            
            if(code.equals(Packet.PACKET_CODES.REGISTRATION_CMD)) {
                out = register(packet);
                
            } else if(code.equals(Packet.PACKET_CODES.LOGIN_CMD)) {
                out = login(packet);
                
            } else if(code.equals(Packet.PACKET_CODES.LOGOUT_CMD)) {
                out = logout(packet);
                
            } else if(code.equals(Packet.PACKET_CODES.GET_USER_STATISTIC_CMD)) {
                out = getStatistic(packet);
                
            } else if(code.equals(Packet.PACKET_CODES.GET_GAME_LIST_CMD)) {
                out = getGameList(packet);
                
            } else if(code.equals(Packet.PACKET_CODES.NEW_GAME_CMD)) {
                out = createNewGame(packet);
                
            } else if(code.equals(Packet.PACKET_CODES.JOIN_TO_GAME_CMD)) {
                out = joinToGame(packet);
                
            } else {
                out = Packet.getIncorrectCommandPacket();
            }
        }
        
        sendToClient(out);        
    }    
    
    private Packet register(Packet packet) {

        if(nickname != null) {
            return Packet.getIncorrectCommandPacket();
        }
        
        @SuppressWarnings("unchecked")
        HashMap<String, String> data = (HashMap<String, String>) packet.getData();        
        if(getDBController().containsUser(data.get(Packet.LOGIN_KEY))) {
            return Packet.getUserAlreadyExistPacket();
        }
        
        nickname = data.get(Packet.LOGIN_KEY);
        getDBController().addUser(nickname, data.get(Packet.PASSWORD_KEY));        
        return Packet.getSuccessPacket();
    }
    private Packet login(Packet packet) {
        
        if(nickname != null) {
            return Packet.getIncorrectCommandPacket();
        }
        
        @SuppressWarnings("unchecked")
        HashMap<String, String> data = (HashMap<String, String>) packet.getData();        
        if(!getDBController().equalsPassword(data.get(Packet.LOGIN_KEY), data.get(Packet.PASSWORD_KEY))) {
            return Packet.getLoginErrorPacket();
        }
        
        nickname = data.get(Packet.LOGIN_KEY);     
        return Packet.getSuccessPacket();
    }
    private Packet logout(Packet packet) {
        if(nickname == null) {
            return Packet.getUserNotSingingUpPacket();            
        } else {
            finish();
            return Packet.getSuccessPacket();
        }
    }
    private Packet getStatistic(Packet packet) {
        
        if(nickname == null) {
            return Packet.getUserNotSingingUpPacket();
        }
        
        PlayerStatistic ps = getDBController().getStatisticForUser(nickname);        
        if(ps == null) {
            return Packet.getServerErrorPacket();
        }
        
        Packet answer = new Packet(Packet.PACKET_CODES.PLAYER_STATISTIC_CODE);
        answer.setData(ps);  
        return answer;
    }
    private Packet getGameList(Packet packet) {
        
        if(nickname == null) {
            return Packet.getUserNotSingingUpPacket();
        }
        
        ArrayList<GameLobby> games = controller.getLobbyController().getGameLobbies();
        Packet answer = new Packet(Packet.PACKET_CODES.GAMES_LIST_CODE);                
        answer.setData(games);
        return answer;
    }
    private Packet createNewGame(Packet packet) {
        
        if(nickname == null) {
            return Packet.getUserNotSingingUpPacket();
        }
        
        GameSettings gs = (GameSettings) packet.getData();        
        controller.getLobbyController().addLobby(gs, this);
        return null;
    }
    private Packet joinToGame(Packet packet) {
        
        if(nickname == null) {
            return Packet.getUserNotSingingUpPacket();
        }
        
        Integer ID = (Integer) packet.getData();
        
        switch(controller.getLobbyController().joinToLobby(ID, this)) {
            case LobbyController.OK_OPT:
                return null;
            case LobbyController.NOT_EXIST_OPT:
                return Packet.getGameNotExistPacket();
            case LobbyController.FULL_OPT:
                return Packet.getGameIsFullPacket();
        }
        
        return Packet.getServerErrorPacket();        
    }
       
    public String getNickname() {
        return nickname;
    }
    public void leaveGame() {
        gameID = null;
    }
    
    private void close() {
        try {
            out.close();
        } catch(Exception e) {}
        try {
            in.close();
        } catch(Exception e) {}      
        try {
            socket.close();
        } catch(Exception e) {}
        
        System.out.println("Thread closed.");
    }
    public void finish() {
        finishFlag = true;
    }

    public DBController getDBController() {
        return controller.getDBController();
    }
}
