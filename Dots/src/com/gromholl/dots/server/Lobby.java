package com.gromholl.dots.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import com.gromholl.dots.server.db.DBController;
import com.gromholl.dots.shared.GameSettings;
import com.gromholl.dots.shared.Packet;
import com.gromholl.dots.shared.PlayerState;
import com.gromholl.dots.shared.ServerGameMap;
import com.gromholl.dots.shared.ServerGameState;
import com.gromholl.dots.shared.TurnCoordinate;

public class Lobby {
    
    private class ClientGameTread extends Thread {
        
        private PlayerState playerState;
        private ClientThread ct;
        private volatile boolean subscrible;
        
        public ClientGameTread(ClientThread ct, PlayerState playerState) {
            this.ct = ct;
            this.subscrible = true;
            this.playerState = playerState;
        }
        
        public void run() {
            try {
                while(subscrible) {
                    processRequest(ct.readFromClient());
                }
            } catch (Exception e) {
                ct.leaveGame();
                leaveGame(this);
            }
        }
        
        private synchronized void processRequest(Packet packet) {
            
            if(packet == null) {
                sendToClient(Packet.getIncorrectCommandPacket());
            } else {
                String code = packet.getCode();
                                
                if(code.equals(Packet.PACKET_CODES.LEAVE_GAME_CMD)) {
                    leave();
                    
                } else if(code.equals(Packet.PACKET_CODES.FINISH_GAME_CMD)) {
                    finish();
                    
                } else if(code.equals(Packet.PACKET_CODES.MAKE_TURN_CMD)) {
                    makeTurn(packet);
                    
                } else if(code.equals(Packet.PACKET_CODES.UPDATE_CMD)) {
                    update();
                    
                } else {
                    sendToClient(Packet.getIncorrectCommandPacket());
                }
            }
        }
        
        private synchronized void leave() {
            subscrible = false;
            sendToClient(Packet.getSuccessPacket());
            leaveGame(this);
        }
        private synchronized void finish() {
            if(playerState.isActive()) {
                finishGame(ct.getNickname());
            }
            sendToClient(Packet.getSuccessPacket());
        }
        private synchronized void makeTurn(Packet packet) {
            
            if(!gameIsRun || !playerState.isActive() || players.indexOf(playerState) == currectPlayer) {
                sendToClient(Packet.getIncorrectTurnPacket());
                return;
            }
            
            TurnCoordinate tc = (TurnCoordinate) packet.getData();
            
            
            if(!map.isCorrectTurn(tc.x, tc.y)) {
                sendToClient(Packet.getIncorrectTurnPacket());
            }

            updateMap(tc.x, tc.y, currectPlayer+1);
            sendToClient(Packet.getSuccessPacket()); 
            setNextPlayer();            
            sendToAllPlayers(Packet.getGameStateChangePacket());
        }
        private synchronized void update() {
                        
            Packet packet = new Packet(Packet.PACKET_CODES.CURRENT_STATE_CODE);
            
            ServerGameState sgs = new ServerGameState();
            sgs.activePlayer = players.get(currectPlayer).getNickname();
            sgs.map = map;
            sgs.playerStates = players;            
            packet.setData(sgs);
            
            sendToClient(packet);
        }

        public synchronized void sendToClient(Packet packet) {
            try {
                ct.sendToClient(packet);
            } catch(IOException e) {
                ct.leaveGame();
                leaveGame(this);
            }
        }
        
    }
    
    private class TimerThread  extends Thread {
                
        public TimerThread() {
            pauseFlag = false;
        }
        
        public void run() {
            while(gameIsRun) {
                
                while(pauseFlag) {
                    try {
                        Thread.sleep(100);
                    } catch(InterruptedException e) {
                        stopGameWithoutSaving();
                        sendToAllPlayers(Packet.getServerErrorPacket());
                    }
                }
                
                if(time == 0) {
                    setNextPlayer();
                    sendToAllPlayers(Packet.getGameStateChangePacket());
                } else {
                    time--;
                    try {
                        Thread.sleep(1000);
                    } catch(InterruptedException e) {
                        stopGameWithoutSaving();
                        sendToAllPlayers(Packet.getServerErrorPacket());
                    }
                }
            }            
        }
        
    }

    private LobbyController controller;
    
    public volatile GameSettings settings;

    private volatile Integer ID;
    private volatile ArrayList<PlayerState> players;
    private volatile LinkedList<ClientGameTread> playerThreads;
    private volatile int currectPlayer;
    private volatile ServerGameMap map;
    
    private volatile int time;
    private volatile boolean gameIsRun;
    private volatile boolean gameStarted;
    public volatile boolean pauseFlag;
    
    public Lobby(Integer ID, GameSettings settings, LobbyController controller) {
        this.ID = ID;
        this.controller = controller;
        this.settings = settings;
        this.map = new ServerGameMap(settings.XSize, settings.YSize);
        this.players = new ArrayList<PlayerState>();
        this.playerThreads= new  LinkedList<ClientGameTread>();
        this.currectPlayer = -1;
        this.time = 0;
        this.gameIsRun = false;
        this.gameStarted = false;
    }
    
    public Integer getID() {
        return ID;
    }
    
    private synchronized void setNextPlayer() {
        currectPlayer = (currectPlayer + 1) % players.size();
        time = settings.turnTime;
        if(!players.get(currectPlayer).isActive()) {
            setNextPlayer();
        }
    }
   
    private synchronized void updateMap(int x, int y, int owner) {
        pauseFlag = true;
        map.setNewDot(x, y, owner);        
        
        for(int i = 0; i < settings.maxCountOfPlayer; i++) {
            players.get(i).setScore(map.getScore(i+1));
        }        
        if(map.freePoint <= 0) {
            stopGame();
        }
        pauseFlag = false;
    }
    
    private synchronized void leaveGame(ClientGameTread cgt) {
        if(gameStarted) {
            if(cgt.playerState.isActive()) {
                finishGame(cgt.ct.getNickname());
            }
        } else {
            for(PlayerState ps : players) {
                if(ps.getNickname().equals(cgt.ct.getNickname())) {
                    players.remove(ps);
                    if(players.size() == 0) {
                        controller.deleteLobby(this);
                    }
                    break;
                }
            }
        }
        cgt.ct.leaveGame();
    }
    private synchronized void finishGame(String nickname) {
        pauseFlag = true;
        for(PlayerState ps : players) {
            if(ps.getNickname().equals(nickname)) {
                ps.setActive(false);
                if(!hasActivePlayer()) {
                    stopGame();
                } else {
                    if(players.indexOf(ps) == currectPlayer) {
                        setNextPlayer();
                    }
                }
                break;
            }
        }
        pauseFlag = false;
    }
    
    private synchronized void sendToAllPlayers(Packet packet) {
        for(ClientGameTread cgt : playerThreads) {
            if(cgt.isAlive()) {
                cgt.sendToClient(packet);
            }
        }
    }
    
    private synchronized void startGame() {
        gameIsRun = true;
        gameStarted = true;
        TimerThread tt = new TimerThread();   
        tt.start();        
    }
    private synchronized void stopGame() {
        stopGameWithoutSaving();
        saveResult();
    }
    private synchronized void stopGameWithoutSaving() {
        gameIsRun = false;
        controller.deleteLobby(this);
        for(PlayerState ps : players) {
            ps.setActive(false);
        }
    }
    private synchronized void saveResult() {
        
        int maxScore = -1;
        for(ClientGameTread cgt : playerThreads) {
            if(cgt.playerState.getScore() > maxScore) {
                maxScore = cgt.playerState.getScore();
            }
        }
        
        boolean isDraw = true;
        for(ClientGameTread cgt : playerThreads) {
            if(cgt.playerState.getScore() != maxScore) {
                isDraw = false;
                break;
            }
        }
        for(ClientGameTread cgt : playerThreads) {
            if(isDraw) {
                cgt.ct.getDBController().addGameResult(cgt.ct.getNickname(), DBController.DRAW_OPT);
            } else {
                if(cgt.playerState.getScore() == maxScore) {
                    cgt.ct.getDBController().addGameResult(cgt.ct.getNickname(), DBController.WIN_OPT);
                } else {
                    cgt.ct.getDBController().addGameResult(cgt.ct.getNickname(), DBController.LOSE_OPT);
                }
            }
        }
    }
    
    
    private synchronized boolean hasActivePlayer() {
        boolean res = false;
        for(PlayerState ps : players) {
            if(ps.isActive()) {
                res = true;
                break;
            }
        }
        return res;
    }
    
    public synchronized boolean addPlayer(ClientThread ct) {
        if(ct == null || gameIsRun) {
            return false;
        }
                
        PlayerState ps = new PlayerState(ct.getNickname());
        
        ClientGameTread cgt = new ClientGameTread(ct, ps);
        playerThreads.add(cgt);
        cgt.start();
        players.add(ps);
       
        return true;        
    }
    public synchronized void tryStart() {
        if(players.size() == settings.maxCountOfPlayer) {
            startGame();
        }
    }
    
    public synchronized ArrayList<String> getPlayersList() {
        ArrayList<String> list = new ArrayList<String>();
        for(PlayerState ps : players) {
            list.add(ps.getNickname());
        }
        return list;
    }
}
