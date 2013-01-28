package com.gromholl.dots.client;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.gromholl.dots.client.gui.CreatePanel;
import com.gromholl.dots.client.gui.DotButton;
import com.gromholl.dots.client.gui.GameMapPane;
import com.gromholl.dots.client.gui.GamePanel;
import com.gromholl.dots.client.gui.LoginPanel;
import com.gromholl.dots.client.gui.MainPanel;
import com.gromholl.dots.client.gui.RegistrationPanel;
import com.gromholl.dots.client.gui.StatisticPanel;
import com.gromholl.dots.client.gui.WaitPanel;
import com.gromholl.dots.shared.GameLobby;
import com.gromholl.dots.shared.GameState;
import com.gromholl.dots.shared.Packet;
import com.gromholl.dots.shared.PacketManager;
import com.gromholl.dots.shared.PlayerStatistic;
import com.gromholl.dots.shared.TurnCoordinate;

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
    private CreatePanel createPanel;
    
    /* Other components */

    private class GameUpdateThread extends Thread {
        
        public volatile boolean stopFlag;
        
        public void run() {
            
            stopFlag = false;            
            try {
                while(!stopFlag) {
                    if(in.ready()) {
                        Packet packet = PacketManager.readPacketFromStream(in);
                        
                        if(packet.getCode().equals(Packet.PACKET_CODES.GAME_STATE_CHANGE_CMD)) {                            
                            sendToServer(Packet.getUpdatePacket());
                            
                        } else if(packet.getCode().equals(Packet.PACKET_CODES.CURRENT_STATE_CODE)) {                            
                            GameState gs = (GameState) packet.getData();                            
                            if(getContentPane() == waitPanel) {
                                gamePanel.setNewMap(gs.map.getXSize(), gs.map.getYSize());
                                final GameMapPane gmp = gamePanel.getGameMapPane();
                                for(int i = 0; i < gmp.x; i++) {
                                    for(int j = 0; j < gmp.y; j++) {                                        
                                        final DotButton db = gmp.buttons[i][j];                                        
                                        db.addActionListener(new ActionListener() {                                            
                                            @Override
                                            public void actionPerformed(ActionEvent arg0) {
                                                makeTurn(db.getXCoor(), db.getYCoor());
                                            }
                                        });
                                    }
                                }
                                setContentPane(gamePanel);
                                revalidate();                                
                            }                        
                            gamePanel.setNewGameState(gs);
                            
                        } else if(packet.getCode().equals(Packet.PACKET_CODES.SUCCESS_CODE)) {
                            gamePanel.setMessage("Success.");
                        } else if(packet.getCode().equals(Packet.PACKET_CODES.INCORRECT_TURN_CODE)) {
                            gamePanel.setMessage("Incorrect turn.");
                        } else {
                            warning("Unknow request.");
                        }
                        
                    } else {
                        Thread.sleep(100);
                    }
                }
            } catch (Exception e) {
               fatalErrorExit(e.toString());
            }            
        }
    }
    
    private GameUpdateThread updater;
    
    private Socket socket;
    
    private volatile DataOutputStream out;
    private volatile BufferedReader in;
    
    private String nickname;
        
    public ClientFrame(String host, int port) {
        super(GAME_NAME);
                
        loginPanel = new LoginPanel();
        registrationPanel = new RegistrationPanel();
        mainPanel = new MainPanel();
        statisticPanel = new StatisticPanel();
        gamePanel = new GamePanel();        
        waitPanel = new WaitPanel();
        createPanel = new CreatePanel();
        
        
        loginPanel.addActionListenerForLoginButton(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                Packet request = loginPanel.getLoginPacket();
                if(request == null)
                    return;
                
                try {
                    sendToServer(request);                    
                    Packet answer = PacketManager.readPacketFromStream(in);
                    
                    if(answer == null) {
                        fatalErrorExit("Server connection error.");                        
                    }
                    
                    if(answer.getCode().equals(Packet.PACKET_CODES.SUCCESS_CODE)) {
                        nickname = loginPanel.getLogin();
                        setTitle(GAME_NAME + " <" + nickname + ">");
                        setContentPane(mainPanel);
                        revalidate();
                        refreshGameList();
                        
                    } else if(answer.getCode().equals(Packet.PACKET_CODES.INCORRECT_COMMAND_CODE)) {
                        warning("Incorrect command.");
                        
                    } else if(answer.getCode().equals(Packet.PACKET_CODES.SERVER_ERROR_CODE)) {
                        warning("Unknown server error.");
                        
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

                Packet request = registrationPanel.getRegPacket();
                if(request == null)
                    return;
                
                try {
                    sendToServer(request);
                    Packet answer = PacketManager.readPacketFromStream(in);
                    
                    if(answer == null) {
                        fatalErrorExit("Server connection error.");                        
                    }
                    
                    if(answer.getCode().equals(Packet.PACKET_CODES.SUCCESS_CODE)) {
                        nickname = registrationPanel.getLogin();
                        setTitle(GAME_NAME + " <" + nickname + ">");
                        setContentPane(mainPanel);
                        revalidate();
                        refreshGameList();
                        
                    } else if(answer.getCode().equals(Packet.PACKET_CODES.USER_ALREADY_EXIST_CODE)) {
                        warning("User with this nickname already exist.");
                        
                    } else if(answer.getCode().equals(Packet.PACKET_CODES.INCORRECT_COMMAND_CODE)) {
                        warning("Incorrect command.");
                        
                    } else if(answer.getCode().equals(Packet.PACKET_CODES.SERVER_ERROR_CODE)) {
                        warning("Unknown server error.");                        
                    }
                    
                } catch(IOException e1) {
                    fatalErrorExit("Server connection error.");
                }
                
            }
        });
        
        mainPanel.addActionListenerForCreateButton(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setContentPane(createPanel);
                revalidate();
            }
        });
        mainPanel.addActionListenerForJoinButton(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                                
                Integer GameID = mainPanel.getSelectedGameID();                
                if(GameID == null) {
                    warning("Choose game in list.");
                    return;
                }
                
                Packet request = new Packet(Packet.PACKET_CODES.JOIN_TO_GAME_CMD);
                request.setData(GameID);
                try {
                    sendToServer(request);
                    Packet answer = PacketManager.readPacketFromStream(in); 
                                        
                    if(answer == null) {
                        fatalErrorExit("Server connection error.");                        
                    }
                                        
                    if(answer.getCode().equals(Packet.PACKET_CODES.SUCCESS_CODE)) {
                        setContentPane(waitPanel);
                        revalidate();
                        updater = new GameUpdateThread();
                        updater.start();
                        
                    } else if(answer.getCode().equals(Packet.PACKET_CODES.INCORRECT_COMMAND_CODE)) {
                        warning("Incorrect command.");
                        
                    } else if(answer.getCode().equals(Packet.PACKET_CODES.SERVER_ERROR_CODE)) {
                        warning("Unknown server error.");
                        
                    } else if(answer.getCode().equals(Packet.PACKET_CODES.USER_NOT_SINGING_UP_CODE)) {
                        warning("User not singing up.");
                        
                    } else if(answer.getCode().equals(Packet.PACKET_CODES.GAME_NOT_EXIST_CODE)) {
                        warning("Game not exist.");
                        
                    } else if(answer.getCode().equals(Packet.PACKET_CODES.GAME_IS_FULL_CODE)) {
                        warning("Game is full.");                        
                    }
                    
                } catch(IOException e1) {
                    e1.printStackTrace();
                    fatalErrorExit("Server connection error.");
                }
            }
        });
        mainPanel.addActionListenerForLogoutButton(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                close();
            }
        });
        mainPanel.addActionListenerForRefreshButton(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshGameList();
            }
        });
        mainPanel.addActionListenerForStatisticButton(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                try {
                    sendToServer(Packet.getStatisticPacket());                    
                    Packet answer = PacketManager.readPacketFromStream(in);
                    
                    if(answer == null) {
                        fatalErrorExit("Server connection error.");                        
                    }
                    
                    if(answer.getCode().equals(Packet.PACKET_CODES.PLAYER_STATISTIC_CODE)) {                        
                        PlayerStatistic ps = (PlayerStatistic) answer.getData();
                        statisticPanel.setStatistic(ps);
                        setContentPane(statisticPanel);
                        revalidate();
                        
                    } else if(answer.getCode().equals(Packet.PACKET_CODES.INCORRECT_COMMAND_CODE)) {
                        warning("Incorrect command.");
                        
                    } else if(answer.getCode().equals(Packet.PACKET_CODES.SERVER_ERROR_CODE)) {
                        warning("Unknown server error.");
                        
                    } else if(answer.getCode().equals(Packet.PACKET_CODES.USER_NOT_SINGING_UP_CODE)) {
                        warning("User not singing up.");
                    }
                    
                } catch(IOException e1) {
                    fatalErrorExit("Server connection error.");
                }
            }
        });
        
        statisticPanel.addActionListenerForBackButton(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setContentPane(mainPanel);
                revalidate();
            }
        });

        createPanel.addActionListenerForCreateButton(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                
                Packet request = createPanel.getCreateGamePacket();
                try {
                    sendToServer(request);
                    Packet answer = PacketManager.readPacketFromStream(in); 
                                        
                    if(answer == null) {
                        fatalErrorExit("Server connection error.");                        
                    }
                                        
                    if(answer.getCode().equals(Packet.PACKET_CODES.GAME_CREATED_CODE)) {
                        setContentPane(waitPanel);
                        revalidate();
                        updater = new GameUpdateThread();
                        updater.start();
                        
                    } else if(answer.getCode().equals(Packet.PACKET_CODES.INCORRECT_COMMAND_CODE)) {
                        warning("Incorrect command.");
                        
                    } else if(answer.getCode().equals(Packet.PACKET_CODES.SERVER_ERROR_CODE)) {
                        warning("Unknown server error.");
                        
                    } else if(answer.getCode().equals(Packet.PACKET_CODES.USER_NOT_SINGING_UP_CODE)) {
                        warning("User not singing up.");
                    }
                    
                } catch(IOException e1) {
                    e1.printStackTrace();
                    fatalErrorExit("Server connection error.");
                }
            }
        });
        createPanel.addActionListenerForCancelButton(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                setContentPane(mainPanel);
                revalidate();              
            }
        });

        gamePanel.addActionListenerForLeaveButton(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                leaveGame();
            }
        });
        gamePanel.addActionListenerForFinishButton(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    sendToServer(Packet.getFinishGamePacket());
                } catch(Exception e2) {
                    fatalErrorExit("Connection error.");
                }
            }
        });
        
        waitPanel.addActionListenerForLeaveButton(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                leaveGame();
            }
        });
                
        this.getContentPane().add(loginPanel);
        
        this.setPreferredSize(WINDOW_SIZE);
        this.pack();
        this.setResizable(false);
        
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                close();
            }
        });
        
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
    private void warning(String error) {        
        JOptionPane.showConfirmDialog(this, error, "Warning!", 
                JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE);
    }
    
    private void sendToServer(Packet packet) throws IOException {
        
        if(packet != null) {            
            String srt = PacketManager.pack(packet);
            
            //XXX Send from ClientFrame
            System.out.println("== SEND ==");
            System.out.println(srt);
            System.out.println("== END ==");
            
            byte[] buf = srt.getBytes("US-ASCII");            
            out.write(buf, 0, buf.length);
        }
    }
    
    private void makeTurn(int x, int y) {
        Packet packet = new Packet(Packet.PACKET_CODES.MAKE_TURN_CMD);
        packet.setData(new TurnCoordinate(x, y));        
        try {
            sendToServer(packet);
        } catch(IOException e) {
            fatalErrorExit("Connection error.");
        }
    }
    @SuppressWarnings("unchecked")
    private void refreshGameList() {
        
        try {
            sendToServer(Packet.getGameListPacket());                    
            Packet answer = PacketManager.readPacketFromStream(in);
            
            if(answer == null) {
                fatalErrorExit("Server connection error.");                        
            }
            
            if(answer.getCode().equals(Packet.PACKET_CODES.GAMES_LIST_CODE)) {                
                mainPanel.setGameList( (ArrayList<GameLobby>) answer.getData() );
                
            } else if(answer.getCode().equals(Packet.PACKET_CODES.INCORRECT_COMMAND_CODE)) {
                warning("Incorrect command.");
                
            } else if(answer.getCode().equals(Packet.PACKET_CODES.SERVER_ERROR_CODE)) {
                warning("Unknown server error.");
                
            } else if(answer.getCode().equals(Packet.PACKET_CODES.USER_NOT_SINGING_UP_CODE)) {
                warning("User not singing up.");
            }
            
        } catch(IOException e1) {
            fatalErrorExit("Server connection error.");
        }
    }    
    private void logout() {
        try {
            sendToServer(Packet.getLogoutPacket());
            PacketManager.readPacketFromStream(in);
        } catch(Exception e) {}

        try {
            out.close();
        } catch(Exception e) {}
        try {
            in.close();
        } catch(Exception e) {}
        try {
            socket.close();
        } catch(Exception e) {}
        
        dispose();
    }
    private void leaveGame() {
        int result = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to leave a game?",
                "Exit Application",
                JOptionPane.YES_NO_OPTION);
        
        if(result == JOptionPane.YES_OPTION) {
            try {
                while(updater.isAlive()) {
                    updater.stopFlag = true;
                    Thread.sleep(100);
                }
                
                sendToServer(Packet.getLeaveGamePacket());                
                PacketManager.readPacketFromStream(in);                
                
                setContentPane(mainPanel);
            } catch (Exception e) {
                fatalErrorExit("Connection error");
            }
        }
    }
    
    private void close() {
        int result = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to exit the application?",
            "Exit Application",
            JOptionPane.YES_NO_OPTION);

        if(result == JOptionPane.YES_OPTION) {
            if(getContentPane() == waitPanel || getContentPane() == gamePanel) {
                leaveGame();
            }
            logout();
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
