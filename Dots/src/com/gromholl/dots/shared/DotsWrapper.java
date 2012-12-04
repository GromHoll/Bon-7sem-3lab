package com.gromholl.dots.shared;

import java.util.ArrayList;
import java.util.Scanner;

import com.gromholl.dots.shared.packets.DotsMapPacket;




public class DotsWrapper {
    
    public static class PACKET_NAMES {
        /* "Server to client" commands and codes */
        public static final String SUCCESS_CODE          = "200";
        public static final String PLAYER_STATISTIC_CODE = "201";
        public static final String GAMES_LIST_CODE       = "202";
        public static final String GAME_CREATED_CODE     = "203";
        public static final String CURRENT_STATE_CODE    = "204";
        
        public static final String USER_ALREADY_EXIST_CODE  = "400";
        public static final String INCORRECT_COMMAND_CODE   = "401";
        public static final String SERVER_ERROR_CODE        = "402";
        public static final String LOGIN_ERROR_CODE         = "403";
        public static final String USER_NOT_SINGING_UP_CODE = "404";
        public static final String GAME_NOT_EXIST_CODE      = "405";
        public static final String GAME_IS_FULL_CODE        = "406";
        public static final String CANNOT_LEAVE_GAME_CODE   = "407";
        public static final String INCORRECT_TURN_CODE      = "408";
        
        public static final String GAME_STATE_CHANGE_CMD = "GSC";

        /* "Client to server" commands and codes */
        public static final String REGISTRATION_CMD       = "REG";
        public static final String LOGIN_CMD              = "LOG";
        public static final String LOGOUT_CMD             = "LGT";
        public static final String GET_USER_STATISTIC_CMD = "UST";
        public static final String GET_GAME_LIST_CMD      = "GLS";
        public static final String NEW_GAME_CMD           = "NEW";
        public static final String JOIN_TO_GAME_CMD       = "JOI";
        public static final String LEAVE_GAME_CMD         = "FIN"; 
        public static final String MAKE_TURN_CMD          = "TRN"; 
        public static final String FINISH_GAME_CMD        = "SRD";
        
        /* Packet's commands */       
        public static final String NUM_PLAYED_GAME = "NPG";
        public static final String NUM_WIN_GAME = "NWG";
        public static final String NUM_LOSE_GAME = "NLG";
        public static final String NUM_DRAW_GAME = "NDG";
        public static final String GAME_ID = "GID";
        public static final String GAME_PLAYER_MAX = "GPM";
        public static final String GAME_PLAYER_CURRECT = "GPC";
        public static final String GAME_AREA_SIZE = "GAS";
        public static final String GAME_TURN_TIMEOUT = "GTT";
        public static final String GAME_EXTRA_TURN = "GET";
        public static final String GAME_USERS_LIST = "GUL";
        
        public static final String ACTIVE_USER = "ACU";
        public static final String ACTIVE_USER_FLAGS = "AUF";
        public static final String SCORE = "SCR";
        public static final String GAME_AREA_LINE = "GAL";
    }
    
    
    public static Object unpack(String packet) {
        Object res = null;
        
        if(packet != null) {
            if(packet.length() < 3)
                return res;
            
            String code = packet.substring(0, 2);            
            if(code.equals(PACKET_NAMES.SUCCESS_CODE)) {
                
            } else if(code.equals(PACKET_NAMES.PLAYER_STATISTIC_CODE)) {
                
            } else if(code.equals(PACKET_NAMES.GAMES_LIST_CODE)) {
                
            } else if(code.equals(PACKET_NAMES.GAME_CREATED_CODE)) {
                
            } else if(code.equals(PACKET_NAMES.CURRENT_STATE_CODE)) {
                res = unpackDotsMapPacket(packet);
            } else if(code.equals(PACKET_NAMES.USER_ALREADY_EXIST_CODE)) {
                
            } else if(code.equals(PACKET_NAMES.INCORRECT_COMMAND_CODE)) {
                
            } else if(code.equals(PACKET_NAMES.SERVER_ERROR_CODE)) {
                
            } else if(code.equals(PACKET_NAMES.LOGIN_ERROR_CODE)) {
                
            } else if(code.equals(PACKET_NAMES.USER_NOT_SINGING_UP_CODE)) {
                
            } else if(code.equals(PACKET_NAMES.GAME_NOT_EXIST_CODE)) {
                
            } else if(code.equals(PACKET_NAMES.GAME_IS_FULL_CODE)) {
                
            } else if(code.equals(PACKET_NAMES.CANNOT_LEAVE_GAME_CODE)) {
                
            } else if(code.equals(PACKET_NAMES.INCORRECT_TURN_CODE)) {
                
            } else if(code.equals(PACKET_NAMES.GAME_STATE_CHANGE_CMD)) {
                
            } else if(code.equals(PACKET_NAMES.REGISTRATION_CMD)) {
                
            } else if(code.equals(PACKET_NAMES.LOGIN_CMD)) {
                
            } else if(code.equals(PACKET_NAMES.LOGOUT_CMD)) {
                
            } else if(code.equals(PACKET_NAMES.GET_USER_STATISTIC_CMD)) {
                
            } else if(code.equals(PACKET_NAMES.GET_GAME_LIST_CMD)) {
                
            } else if(code.equals(PACKET_NAMES.NEW_GAME_CMD)) {
                
            } else if(code.equals(PACKET_NAMES.JOIN_TO_GAME_CMD)) {
                
            } else if(code.equals(PACKET_NAMES.LEAVE_GAME_CMD)) {
                
            } else if(code.equals(PACKET_NAMES.MAKE_TURN_CMD)) {
                
            } else if(code.equals(PACKET_NAMES.FINISH_GAME_CMD)) {
                
            }

        }        
        
        return res;
    }
    
    private static DotsMapPacket unpackDotsMapPacket(String packet) {
        
        DotsMapPacket res = new DotsMapPacket();
        
        ArrayList<String> userNames = new ArrayList<String>();
        ArrayList<Integer> statuses = new ArrayList<Integer>();
        ArrayList<Integer> scores = new ArrayList<Integer>();
        
               
        try {            
            Scanner sc = new Scanner(packet);
            Scanner subsc;
            String line;
            
            sc.nextLine(); // Skip packet's code
            
            line = sc.nextLine();
            if(!line.startsWith(PACKET_NAMES.ACTIVE_USER))
                return null;
            res.setActivePlayer(line.substring(3));
            
            subsc = new Scanner(sc.nextLine());
            if(!subsc.next().equals(PACKET_NAMES.GAME_USERS_LIST))
                return null;            
            while(subsc.hasNext()) {
                userNames.add(subsc.next());
            }
            
            subsc = new Scanner(sc.nextLine());
            if(!subsc.next().equals(PACKET_NAMES.ACTIVE_USER_FLAGS))
                return null;
            while(subsc.hasNext()) {
                statuses.add(subsc.nextInt());
            }
            
            subsc = new Scanner(sc.nextLine());
            if(!subsc.next().equals(PACKET_NAMES.SCORE))
                return null;
            while(subsc.hasNext()) {
                scores.add(subsc.nextInt());
            }
            
            
            
        } catch (Exception e) {
            return null;
        }
        
        return res;
    }
    
    
    
    
    public static String pack(Object data) {
        return new String();
    }
    
}
