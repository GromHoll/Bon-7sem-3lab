package com.gromholl.dots.client;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.gromholl.dots.client.gui.GamePanel;
import com.gromholl.dots.client.gui.LoginPanel;
import com.gromholl.dots.client.gui.MainPanel;
import com.gromholl.dots.client.gui.RegistrationPanel;
import com.gromholl.dots.client.gui.StatisticPanel;
import com.gromholl.dots.client.gui.WaitPanel;
import com.gromholl.dots.shared.Packet;
import com.gromholl.dots.shared.PacketManager;


public class ClientFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    
    public static final String GAME_NAME = "GromHoll's Dots";
    
    public static final Dimension WINDOW_SIZE = new Dimension(720, 520); 

    /* GUI components */
    
    private LoginPanel loginPanel;
    private RegistrationPanel registrationPanel;
    private MainPanel mainPanel;
    private StatisticPanel statisticPanel;
    private GamePanel gamePanel;
    private WaitPanel waitPanel;
    
    /* Other components */

    private Socket socket;
    
    private DataOutputStream out;
    private BufferedReader in;
    
    private String nickName;
    
    
    public ClientFrame(String host, int port) {
        super(GAME_NAME);
        
        /* Create Panels */
        
        loginPanel = new LoginPanel();
        registrationPanel = new RegistrationPanel();
        mainPanel = new MainPanel();
        statisticPanel = new StatisticPanel();
        gamePanel = new GamePanel();        
        waitPanel = new WaitPanel();
        
        /* Setting panels interaction */
        
        loginPanel.addActionListenerForLoginButton(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                if(!loginPanel.isFillField())
                    return;
                
                if(!loginPanel.isValidField())
                    return;
                
                try {
                    //TODO Login Packet generation (don't fogged save nickname)
                    sendToServer(Packet.getSuccessPacket());
                    
                    Packet answer = PacketManager.readPacketFromStream(in);
                    
                    if(answer == null) {
                        fatalErrorExit("Server connection error.");                        
                    }
                    
                    if(answer.getCode().equals(Packet.PACKET_CODES.SUCCESS_CODE)) {
                        setContentPane(mainPanel);
                        revalidate();
                        
                    } else if(answer.getCode().equals(Packet.PACKET_CODES.INCORRECT_COMMAND_CODE)) {
                        fatalErrorExit("Packet generation error.");
                        
                    } else if(answer.getCode().equals(Packet.PACKET_CODES.SERVER_ERROR_CODE)) {
                        fatalErrorExit("Unknown server error.");
                        
                    } else if(answer.getCode().equals(Packet.PACKET_CODES.LOGIN_ERROR_CODE)) {
                        loginPanel.setMessage("Login failed. Check login and/or password.");
                    }
                    
                } catch(IOException e1) {
                    fatalErrorExit("Server connection error.");
                }
                
            }
        });        
        loginPanel.addActionListenerForRegButton(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setContentPane(registrationPanel);
                revalidate();
            }
        });
        
        registrationPanel.addActionListenerForBackButton(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setContentPane(loginPanel);
                revalidate();
            }
        });        
        registrationPanel.addActionListenerForRegButton(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {                

                if(!registrationPanel.isFillField())
                    return;
                
                if(!registrationPanel.isValidField())
                    return;
                
                try {
                    //TODO Registration packet generation (don't fogged save nickname)
                    sendToServer(Packet.getSuccessPacket());
                    
                    Packet answer = PacketManager.readPacketFromStream(in);
                    
                    if(answer == null) {
                        fatalErrorExit("Server connection error.");                        
                    }
                    
                    if(answer.getCode().equals(Packet.PACKET_CODES.SUCCESS_CODE)) {
                        setContentPane(mainPanel);
                        revalidate();
                        
                    } else if(answer.getCode().equals(Packet.PACKET_CODES.USER_ALREADY_EXIST_CODE)) {
                        fatalErrorExit("User with this nickname already exist.");
                        
                    } else if(answer.getCode().equals(Packet.PACKET_CODES.INCORRECT_COMMAND_CODE)) {
                        fatalErrorExit("Packet generation error.");
                        
                    } else if(answer.getCode().equals(Packet.PACKET_CODES.SERVER_ERROR_CODE)) {
                        fatalErrorExit("Unknown server error.");
                        
                    }
                    
                } catch(IOException e1) {
                    fatalErrorExit("Server connection error.");
                }
                
            }
        });
        
        mainPanel.addActionListenerForCreateButton(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO
            }
        });
        mainPanel.addActionListenerForJoinButton(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO
            }
        });
        mainPanel.addActionListenerForLogoutButton(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO
            }
        });
        mainPanel.addActionListenerForRefreshButton(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO
            }
        });
        mainPanel.addActionListenerForStatisticButton(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO
            }
        });
        
        statisticPanel.addActionListenerForBackButton(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setContentPane(mainPanel);
                revalidate();
            }
        });
        
        gamePanel.addActionListenerForFinishButton(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO
            }
        });
        gamePanel.addActionListenerForLeaveButton(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO
            }
        });
        gamePanel.addActionListenerForFinishButton(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO
            }
        });
        
        waitPanel.addActionListenerForLeaveButton(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO
            }
        });
        
        /* Tune main frame */
        
        this.getContentPane().add(loginPanel);
        
        this.setPreferredSize(WINDOW_SIZE);
        this.pack();
        this.setResizable(false);
        
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        this.setVisible(true);
        
        try {
            this.socket = new Socket(host, port);
            
            this.out  = new DataOutputStream(socket.getOutputStream());
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "US-ASCII"));
            
        } catch(IOException e) {
            fatalErrorExit("Can't connect to server!");
        }
        
    }
    
    private void fatalErrorExit(String error) {        
        JOptionPane.showConfirmDialog(this, error, "Error!", 
                JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
        this.dispose();
    }
    
    private void sendToServer(Packet packet) throws IOException {
        
        if(packet != null) {
            
            String srt = PacketManager.pack(packet);
            
            byte[] buf = srt.getBytes("US-ASCII");            
            out.write(buf, 0, buf.length);
        }
    }
    
    
    
    public static void main(String[] args) {
        
        final String host; 
        final int port;
        
        if(args.length == 2) {
            
            host = args[0];

            try {
                port = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.err.println("Port number error: " + args[1]);
                return;
            }
        } else {
            System.err.println("Incorrect arguments count (" + args.length + ").");
            return;
        }
        
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ClientFrame(host, port);
            }
        });

    }

}
