package com.gromholl.dots.shared;

public class DotsMapCell {
    
    public static final boolean SURROUNDED_OPT = false;
    public static final boolean FREE_OPT = true;
    
    public static final int WITHOUT_OWNER_OPT = 0;
    
    private boolean status;
    private int owner;
    
    public DotsMapCell(int owner, boolean status) {
        this.owner = owner;
        this.status = status;
    }
    public DotsMapCell() {
        this.owner = WITHOUT_OWNER_OPT;
        this.status = FREE_OPT;
    }

    public boolean isFree() {
        return status;
    }
    public int getOwner() {
        return owner;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    public void setOwner(int owner) {
        this.owner = owner;
    }
    public void setInfo(int owner, boolean status) {
        this.owner = owner;
        this.status = status;
    }
    
}
