package com.gromholl.dots.shared;

import java.io.Serializable;

public class PlayerStatistic implements Serializable {
    
    private static final long serialVersionUID = 2179216104296034674L;

    private String nickname;
    
    public Integer play;
    public Integer win;
    public Integer draw;
    public Integer lose;
    
    public PlayerStatistic(String nickname) {
        this.nickname = nickname;
        
        this.play = Integer.valueOf(0);
        this.win  = Integer.valueOf(0);
        this.draw = Integer.valueOf(0);
        this.lose = Integer.valueOf(0);
    }
    
    public String getNickname() {
        return nickname;
    }  
    
}
