package com.gromholl.dots.server.db;

import java.io.Serializable;

import com.gromholl.dots.shared.PlayerStatistic;

public class DBRow implements Serializable {
    
    private static final long serialVersionUID = 3893323617408110560L;
    
    private PlayerStatistic statistic;
    private String password;
    
    public DBRow(String nickname, String password) {        
        this.statistic = new PlayerStatistic(nickname);
        this.password = password;
    }    
    
    public PlayerStatistic getStatistic() {
        return statistic;
    }
    
    public boolean isPassword(String password) {
        return this.password.equals(password);
    }
    
}
