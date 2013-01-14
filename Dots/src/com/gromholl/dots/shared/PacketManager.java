package com.gromholl.dots.shared;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


public class PacketManager {
       
    public static Packet unpack(String packet) {
        
        Packet res = null;
                
        if(packet != null) {
            
            if(packet.length() < 3)
                return res;  
            
            String code = packet.substring(0, 3);
            
            if(code.equals(Packet.PACKET_CODES.SUCCESS_CODE)) {
                res = Packet.getSuccessPacket();
                
            } else if(code.equals(Packet.PACKET_CODES.PLAYER_STATISTIC_CODE)) {
                res = getPlayerStatisticPacket(packet);
                
            } else if(code.equals(Packet.PACKET_CODES.GAMES_LIST_CODE)) {
                res = getGamesListPacket(packet);
                
            } else if(code.equals(Packet.PACKET_CODES.GAME_CREATED_CODE)) {
                res = getGameCreatedPacket(packet);
                
            } else if(code.equals(Packet.PACKET_CODES.CURRENT_STATE_CODE)) {
                res = getGameCurrentStatePacket(packet);
                
            } else if(code.equals(Packet.PACKET_CODES.USER_ALREADY_EXIST_CODE)) {                
                res = Packet.getUserAlreadyExistPacket();
                
            } else if(code.equals(Packet.PACKET_CODES.INCORRECT_COMMAND_CODE)) {                
                res = Packet.getIncorrectCommandPacket();
                
            } else if(code.equals(Packet.PACKET_CODES.SERVER_ERROR_CODE)) {                
                res = Packet.getServerErrorPacket();
                
            } else if(code.equals(Packet.PACKET_CODES.LOGIN_ERROR_CODE)) {                
                res = Packet.getLoginErrorPacket();
                
            } else if(code.equals(Packet.PACKET_CODES.USER_NOT_SINGING_UP_CODE)) {                
                res = Packet.getUserNotSingingUpPacket();

            } else if(code.equals(Packet.PACKET_CODES.GAME_NOT_EXIST_CODE)) {                
                res = Packet.getGameNotExistPacket();
                
            } else if(code.equals(Packet.PACKET_CODES.GAME_IS_FULL_CODE)) {                
                res = Packet.getGameIsFullPacket();

            } else if(code.equals(Packet.PACKET_CODES.CANNOT_LEAVE_GAME_CODE)) {                
                res = Packet.getCannotLeaveGamePacket();

            } else if(code.equals(Packet.PACKET_CODES.INCORRECT_TURN_CODE)) {                
                res = Packet.getIncorrectTurnPacket();

            } else if(code.equals(Packet.PACKET_CODES.GAME_STATE_CHANGE_CMD)) {
                res = Packet.getGameStateChangePacket();
                
            } else if(code.equals(Packet.PACKET_CODES.REGISTRATION_CMD)) {
                res = getRegistrationPacket(packet);
                
            } else if(code.equals(Packet.PACKET_CODES.LOGIN_CMD)) {
                res = getLoginPacket(packet);
                
            } else if(code.equals(Packet.PACKET_CODES.LOGOUT_CMD)) {
                res = Packet.getLogoutPacket();
                
            } else if(code.equals(Packet.PACKET_CODES.GET_USER_STATISTIC_CMD)) {                
                res = new Packet(Packet.PACKET_CODES.GET_USER_STATISTIC_CMD);
                
            } else if(code.equals(Packet.PACKET_CODES.GET_GAME_LIST_CMD)) {                
                res = Packet.getGameListPacket();
                
            } else if(code.equals(Packet.PACKET_CODES.NEW_GAME_CMD)) {
                res = getNewGamePacket(packet);
                
            } else if(code.equals(Packet.PACKET_CODES.JOIN_TO_GAME_CMD)) {
                res = getJoinPacket(packet);
                
            } else if(code.equals(Packet.PACKET_CODES.LEAVE_GAME_CMD)) {
                res = Packet.getLeaveGamePacket();
                
            } else if(code.equals(Packet.PACKET_CODES.MAKE_TURN_CMD)) {
                res = getMakeTurnPacket(packet);
                
            } else if(code.equals(Packet.PACKET_CODES.FINISH_GAME_CMD)) {
                res = Packet.getFinishGamePacket(); 
                
            }
            
        }        
        
        return res;
    }
       
    private static Packet getPlayerStatisticPacket(String packet) {
        
        Scanner sc = new Scanner(packet);
        
        if(!sc.next().equals(Packet.PACKET_CODES.PLAYER_STATISTIC_CODE))    return null;        
        Packet res = new Packet(Packet.PACKET_CODES.PLAYER_STATISTIC_CODE);
        
        if(!sc.next().equals(Packet.PACKET_CODES.LOGIN_CMD))    return null;
        PlayerStatistic ps = new PlayerStatistic(sc.next());

        if(!sc.next().equals(Packet.PACKET_CODES.NUM_PLAYED_GAME))  return null;        
        if(!sc.hasNextInt())    return null;
        ps.setPlay(sc.nextInt());
        
        if(!sc.next().equals(Packet.PACKET_CODES.NUM_WIN_GAME))     return null;
        if(!sc.hasNextInt())    return null;
        ps.setWin(sc.nextInt());
        
        if(!sc.next().equals(Packet.PACKET_CODES.NUM_LOSE_GAME))    return null;
        if(!sc.hasNextInt())    return null;
        ps.setLose(sc.nextInt());
        
        if(!sc.next().equals(Packet.PACKET_CODES.NUM_DRAW_GAME))    return null;
        if(!sc.hasNextInt())    return null;
        ps.setDraw(sc.nextInt());
        
        res.setData(ps);
        
        return res;
    }
    
    private static Packet getGamesListPacket(String packet) {
        
        Scanner sc = new Scanner(packet);
        
        if(!sc.next().equals(Packet.PACKET_CODES.GAMES_LIST_CODE))    return null;
        Packet res = new Packet(Packet.PACKET_CODES.GAMES_LIST_CODE);
        
        ArrayList<GameLobby> games = new ArrayList<GameLobby>();
        
        while(sc.hasNext()) {
            
            GameLobby gl = new GameLobby();
            
            if(!sc.next().equals(Packet.PACKET_CODES.GAME_ID))  break;
            if(!sc.hasNextInt())    break;
            gl.setID(sc.nextInt());
            
            if(!sc.next().equals(Packet.PACKET_CODES.GAME_PLAYER_MAX))  break;
            if(!sc.hasNextInt())    break;
            gl.setMaxCountOfPlayer(sc.nextInt());
            
            if(!sc.next().equals(Packet.PACKET_CODES.GAME_PLAYER_CURRECT))  break;
            if(!sc.hasNextInt())    break;
            gl.setCurrentCountOfPlayer(sc.nextInt());
            
            if(!sc.next().equals(Packet.PACKET_CODES.GAME_AREA_SIZE))  break;
            if(!sc.hasNextInt())    break;
            gl.setXSize(sc.nextInt());
            if(!sc.hasNextInt())    break;
            gl.setYSize(sc.nextInt());
            
            if(!sc.next().equals(Packet.PACKET_CODES.GAME_TURN_TIMEOUT))  break;
            if(!sc.hasNextInt())    break;
            gl.setTurnTime(sc.nextInt());
            
            if(!sc.next().equals(Packet.PACKET_CODES.GAME_EXTRA_TURN))  break;
            if(!sc.hasNextInt())    break;
            int f = sc.nextInt();
            if(f == 0)
                gl.setExtraTurn(false);
            else if (f == 1) 
                gl.setExtraTurn(true);
            else
                break;
            
            Scanner temp = new Scanner(sc.nextLine());        
            while(temp.hasNext()) {
                gl.addPlayer(temp.next());
            }
            
            games.add(gl);
        }
                
        res.setData(games);
        
        return res;
    }
    
    private static Packet getGameCreatedPacket(String packet) {
        
        Scanner sc = new Scanner(packet);
        
        if(!sc.next().equals(Packet.PACKET_CODES.GAME_CREATED_CODE))    return null;
        Packet res = new Packet(Packet.PACKET_CODES.GAME_CREATED_CODE);
        
        Integer ID;
        
        if(!sc.next().equals(Packet.PACKET_CODES.GAME_ID))    return null;
        if(!sc.hasNextInt())    return null;
        ID = new Integer(sc.nextInt());
        
        res.setData(ID);
        
        return res;
    }
    
    private static Packet getGameCurrentStatePacket(String packet) {
        
        Scanner sc = new Scanner(packet);
        Scanner temp;
        
        if(!sc.next().equals(Packet.PACKET_CODES.CURRENT_STATE_CODE))    return null;
        Packet res = new Packet(Packet.PACKET_CODES.CURRENT_STATE_CODE);
        
        GameState gs = new GameState();
        ArrayList<PlayerState> ps = new ArrayList<PlayerState>();
        
        if(!sc.next().equals(Packet.PACKET_CODES.ACTIVE_USER))    return null;
        gs.setActivePlayer(sc.next());

        if(!sc.next().equals(Packet.PACKET_CODES.GAME_USERS_LIST))  return null;
        temp = new Scanner(sc.nextLine());        
        while(temp.hasNext()) {
            ps.add(new PlayerState(temp.next()));
        }
        
        if(!sc.next().equals(Packet.PACKET_CODES.ACTIVE_USER_FLAGS))  return null;
        temp = new Scanner(sc.nextLine());
        for(int i = 0; i < ps.size(); i++) {
            
            if(!sc.hasNextInt())    return null;
            int f = temp.nextInt();
            
            if(f == 1)
                ps.get(i).setActive(Boolean.valueOf(true));
            else if (f == 0)
                ps.get(i).setActive(Boolean.valueOf(false));
            else 
                return null;                
        }
        
        if(!sc.next().equals(Packet.PACKET_CODES.SCORE))  return null;
        temp = new Scanner(sc.nextLine());
        for(int i = 0; i < ps.size(); i++) {
            
            if(!sc.hasNextInt())    return null;
            
            ps.get(i).setScore(temp.nextInt());
        }
        
        gs.setPlayerStates(ps);
        
        ArrayList<String> mapLines = new ArrayList<String>();
        while(sc.hasNext()) {
            if(!sc.next().equals(Packet.PACKET_CODES.GAME_AREA_LINE))  return null;
            
            if(!sc.hasNext())  return null;            
            mapLines.add(sc.nextLine());
        }
        
        if(mapLines.isEmpty())  return null;
        
        GameMap gm = new GameMap(mapLines.get(0).length(), mapLines.size());
        for(int i = 0; i < mapLines.get(0).length(); i++) {
            try {
                for(int j = 0; j < mapLines.size(); j++) {
                    gm.getCell(i, j).setCode(mapLines.get(j).charAt(i) - '0');
                }
            } catch (IndexOutOfBoundsException e) {
                return null;
            }
        }
            
        gs.setMap(gm);
        
        res.setData(gs);
        
        return res;
    }
    
    private static Packet getRegistrationPacket(String packet) {
        
        Scanner sc = new Scanner(packet);
        
        if(!sc.next().equals(Packet.PACKET_CODES.REGISTRATION_CMD))    return null;
        Packet res = new Packet(Packet.PACKET_CODES.REGISTRATION_CMD);
        
        HashMap<String, String> data = new HashMap<String, String>();
        
        if(!sc.hasNext())    return null;
        data.put("Login", sc.next());
        
        if(!sc.hasNext())    return null;
        data.put("Password", sc.next());
        
        res.setData(data);
        
        return res;
    }
    
    private static Packet getLoginPacket(String packet) {
        
        Scanner sc = new Scanner(packet);
        
        if(!sc.next().equals(Packet.PACKET_CODES.LOGIN_CMD))    return null;
        Packet res = new Packet(Packet.PACKET_CODES.LOGIN_CMD);
        
        HashMap<String, String> data = new HashMap<String, String>();
        
        if(!sc.hasNext())    return null;
        data.put("Login", sc.next());
        
        if(!sc.hasNext())    return null;
        data.put("Password", sc.next());
        
        res.setData(data);
        
        return res;
    }
    
    private static Packet getNewGamePacket(String packet) {
        
        Scanner sc = new Scanner(packet);
        
        if(!sc.next().equals(Packet.PACKET_CODES.NEW_GAME_CMD))    return null;
        Packet res = new Packet(Packet.PACKET_CODES.NEW_GAME_CMD);
        
        GameLobby gl = new GameLobby();
        
        if(!sc.hasNextInt())    return null;
        gl.setMaxCountOfPlayer(sc.nextInt());
        
        if(!sc.hasNextInt())    return null;
        gl.setXSize(sc.nextInt());
        
        if(!sc.hasNextInt())    return null;
        gl.setYSize(sc.nextInt());
        
        if(!sc.hasNextInt())    return null;
        gl.setTurnTime(sc.nextInt());
        
        if(!sc.hasNextInt())    return null;
        int f = sc.nextInt();
        
        if(f == 0)
            gl.setExtraTurn(false);
        else if(f == 1)
            gl.setExtraTurn(true);
        else
            return null;
        
        res.setData(gl);
        
        return res;
    }
    
    private static Packet getJoinPacket(String packet) {
        
        Scanner sc = new Scanner(packet);
        
        if(!sc.next().equals(Packet.PACKET_CODES.JOIN_TO_GAME_CMD))    return null;
        Packet res = new Packet(Packet.PACKET_CODES.JOIN_TO_GAME_CMD);
        
        Integer ID;
        
        if(!sc.hasNextInt())    return null;
        ID = new Integer(sc.nextInt());
        
        res.setData(ID);
        
        return res;
    }
    
    private static Packet getMakeTurnPacket(String packet) {
        
        Scanner sc = new Scanner(packet);
        
        if(!sc.next().equals(Packet.PACKET_CODES.MAKE_TURN_CMD))    return null;        
        Packet res = new Packet(Packet.PACKET_CODES.MAKE_TURN_CMD);
        
        TurnCoordinate tc = new TurnCoordinate();
        
        if(!sc.hasNextInt())    return null;
        tc.x = sc.nextInt();
        
        if(!sc.hasNextInt())    return null;
        tc.y = sc.nextInt();
        
        res.setData(tc);
        
        return res;
    }

    public static String pack(Packet packet) {
        
        StringBuilder res = new StringBuilder();
        
        res.append(packet.getCode());
        
        if(packet.getData() != null) {
            //TODO
        } else {
            res.append("\n");
        }

        res.append("\n\n");
        
        return res.toString();
    }

    public static Packet readPacketFromStream(BufferedReader in) {
        
        StringBuilder sb = new StringBuilder();
        String line;
        
        try {
            while(true) {                        
                line = in.readLine();
                if(line.isEmpty()) {
                    
                    sb.append("\n");
                    line = in.readLine();
                    
                    if(line.isEmpty()) {
                        sb.append("\n");
                        break;
                    }
                    
                } else {
                    sb.append(line + "\n");
                }
            }
        } catch(IOException e) {
            return null;
        }         
        
        return unpack(sb.toString());
    }

}
