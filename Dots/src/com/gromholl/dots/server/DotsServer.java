package com.gromholl.dots.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Scanner;

public class DotsServer {

	private Thread consoleThread = null;
	private Thread waiterThread = null;	
	private volatile boolean stopServerFlag = false;
	
	private int waiterPort = 0;
	
	class ConsoleRunnable implements Runnable {
		public static final String STOP_CMD = "stop";
		
		private Scanner in;
				
		public ConsoleRunnable() {
			in = new Scanner(System.in);
		}
		
		@Override
		public void run() {
			String cmd;
			while(!stopServerFlag) {
				try {
					if(System.in.available() > 0) {
						cmd = in.next();
						if(cmd.toLowerCase().equals(STOP_CMD))
							stopServerFlag = true;
					} else {
						sleep();
					}
				} catch(IOException e) {
					e.printStackTrace();
				}
			}			
		}
		
		private void sleep() {
			try {
				Thread.sleep(1000);
			} catch(Exception e) {
				// Do nothing
			}
		}
			
	}
	
	class WaiterRunnable implements Runnable {

		public static final int DEFAULT_TIMEOUT = 1000; 
		
		private ServerSocket welcomeSocket = null;
		private UserThreadPoolManager poolManager = null;
		
		public WaiterRunnable() {
			try {
				System.out.println("Binding to port " + waiterPort + ", please wait...");
				welcomeSocket = new ServerSocket(waiterPort);			
				welcomeSocket.setSoTimeout(DEFAULT_TIMEOUT);
				poolManager = new UserThreadPoolManager();
				System.out.println("Server started.");
			} catch(Exception e) {
				stopServerFlag = true;
				e.printStackTrace();
			}
		}
		
	    @Override
		public void run() {
	        
	    	Socket clientSocket;
			while(!stopServerFlag) {
				try {				    
					clientSocket = welcomeSocket.accept();					
					poolManager.addUserThread(clientSocket);
					clientSocket = null;
				} catch(SocketTimeoutException e) {
                    // Do nothing
                } catch(IOException e) {
					stopServerFlag = true;	
					e.printStackTrace();
				}
			}
		}
		
	}	
	
	public DotsServer(int port) {
		waiterPort = port;
		start();
	}
	
	public void start() {
		stopServerFlag = false;
		
		if (consoleThread == null) {
			consoleThread = new Thread(new ConsoleRunnable());
			consoleThread.start();
		}
		if(waiterThread == null) {
			waiterThread = new Thread(new WaiterRunnable());
			waiterThread.start();
		}		
	}
		
	public static void main(String[] args) {
		
		@SuppressWarnings("unused")
		DotsServer server = null;
		if(args.length != 1)
			System.out.println("Usage: java DotsServer <port>");
		else
			server = new DotsServer(Integer.parseInt(args[0]));		
	}

}
