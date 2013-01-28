package com.gromholl.dots.shared;

public class PlayerState {
    
    private String nickname;
    private Integer score;
    private Boolean active;
    
    public PlayerState(String nickname) {
        this.nickname = nickname;            
        this.score = Integer.valueOf(0);
        this.active = Boolean.valueOf(true);
    }

    public String getNickname() {
        return nickname;
    }
    public Integer getScore() {
        return score;
    }
    public Boolean isActive() {
        return active;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
    public void setActive(Boolean active) {
        this.active = active;
    }
    
}
