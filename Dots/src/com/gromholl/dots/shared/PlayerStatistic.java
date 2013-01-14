package com.gromholl.dots.shared;

public class PlayerStatistic {

    private String nickname;
    
    private Integer play;
    private Integer win;
    private Integer draw;
    private Integer lose;
    
    public PlayerStatistic(String nickname) {
        this.nickname = nickname;
        
        this.play = Integer.valueOf(0);
        this.win = Integer.valueOf(0);
        this.draw = Integer.valueOf(0);
        this.lose = Integer.valueOf(0);
    }
    
    public String getNickname() {
        return nickname;
    }
    public Integer getPlay() {
        return play;
    }
    public Integer getWin() {
        return win;
    }
    public Integer getDraw() {
        return draw;
    }
    public Integer getLose() {
        return lose;
    }
    
    public void setPlay(Integer play) {
        this.play = play;
    }
    public void setWin(Integer win) {
        this.win = win;
    }
    public void setDraw(Integer draw) {
        this.draw = draw;
    }
    public void setLose(Integer lose) {
        this.lose = lose;
    }   
    
}
