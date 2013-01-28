package com.gromholl.dots.shared;

public class ServerGameMapCell extends GameMapCell {
    
    public static final int WITHOUT_INVADER_OPT = 0;
    
    public int invader;
    
    public ServerGameMapCell() {
        super();
        invader = WITHOUT_INVADER_OPT;
    }
    
    public void invade(int invader) {
        this.status = SURROUNDED_OPT;
        this.invader = invader;
    }
    
}
