package com.gromholl.dots.shared;

public class PlayerInfo {
    
    public static final boolean ACTIVE_PLAYER_STATUS = true;
    public static final boolean FINISH_PLAYER_STATUS = false;

    public static final String DEFAULT_NAME = "Anonymous";
        
    private String nickname;
    private int score;
    private boolean activityStatus;
    
    public PlayerInfo() {
        nickname = DEFAULT_NAME;
        activityStatus = ACTIVE_PLAYER_STATUS;
        score = 0;        
    }

    public String getNickname() {
        return nickname;
    }
    public int getScore() {
        return score;
    }
    public boolean isActivityStatus() {
        return activityStatus;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public void setActivityStatus(boolean activityStatus) {
        this.activityStatus = activityStatus;
    }
     
}
