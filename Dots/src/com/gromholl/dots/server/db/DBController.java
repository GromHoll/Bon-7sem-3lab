package com.gromholl.dots.server.db;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import com.gromholl.dots.shared.PlayerStatistic;

public class DBController {
    
    public static final int WIN_OPT = 0;
    public static final int LOSE_OPT = 1;
    public static final int DRAW_OPT = 2;

    private HashMap<String, DBRow> db;
    private String filePath;
    
    public DBController(String filePath) {
        this.filePath = filePath; 
        loadDB();
    }
    
    @SuppressWarnings("unchecked")
    private void loadDB() {
        
        ObjectInputStream ois = null;        
        try {
            ois = new ObjectInputStream(new FileInputStream(filePath));
            Object dbObj = ois.readObject();
            
                db = (HashMap<String, DBRow>) dbObj;            
        } catch(Exception e) {
            System.out.println("Problem with loading DB.");
            e.printStackTrace();
            db = new HashMap<String, DBRow>();
        } finally {
            try {
                ois.close();
            } catch(Exception e) {
                // Do nothing
            }
        }        
    }
    private void saveDB() {
        
        ObjectOutputStream oos = null;        
        try {
            oos = new ObjectOutputStream(new FileOutputStream(filePath));
            oos.writeObject(db);
        } catch(Exception e) {
            System.out.println("Can't save DB.");
        } finally {
            try {
                oos.close();
            } catch(IOException e) {
                // Do nothing
            }
        } 
    }
    
    public boolean containsUser(String name) {
        return db.containsKey(name);
    }
    public boolean equalsPassword(String name, String password) {
        if(!containsUser(name)) {
            return false;
        }
        return db.get(name).isPassword(password);
    }
    
    public void addUser(String name, String password) {
        db.put(name, new DBRow(name, password));
        System.out.println("Add user " + name);
        saveDB();
    }
    public PlayerStatistic getStatisticForUser(String name) {        
        DBRow user = db.get(name);
        
        if(user == null) {
            return null;
        } else {
            return user.getStatistic();
        }        
    }
    public void addGameResult(String name, int result) {        
        switch(result) {
            case WIN_OPT:   db.get(name).getStatistic().win++;  break;
            case LOSE_OPT:  db.get(name).getStatistic().lose++; break;
            case DRAW_OPT:  db.get(name).getStatistic().draw++; break;
            default:    return;
        }
        db.get(name).getStatistic().play++;
        saveDB();
    }
    
}
