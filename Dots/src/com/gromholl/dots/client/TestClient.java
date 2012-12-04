package com.gromholl.dots.client;

import javax.swing.JFrame;

import com.gromholl.dots.client.graphic.DotsMapCanvas;
import com.gromholl.dots.shared.DotsMap;

public class TestClient extends JFrame {

    private static final long serialVersionUID = 792775863244150592L;

    private DotsMapCanvas mapArea;
    
    public TestClient() {
        mapArea = new DotsMapCanvas();
        mapArea.setSize(200, 200);
        this.setSize(202, 202);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }
    
    public static void main(String[] args) {
        new TestClient();
        
        DotsMap testMap = new DotsMap(5, 10);
        
        for(int i = 0; i < testMap.getYSize(); i++) {
            for(int j = 0; j < testMap.getXSize(); j++) {
                System.out.print(testMap.getCellOwner(j, i) + "=" + testMap.getCellStatus(j, i) + " ");
            }
            System.out.println();
        }
    }

}
