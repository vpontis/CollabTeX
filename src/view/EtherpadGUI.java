package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class EtherpadGUI {
	private LoginGUI loginGUI;
	private TableGUI tableGUI;
	
	private Socket serverSocket;
	
	private BufferedReader serverInput;
	private PrintWriter serverOutput;

	public EtherpadGUI() throws UnknownHostException, IOException {
		serverSocket = new Socket("127.0.0.1",4444);
		serverInput = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
		serverOutput = new PrintWriter(serverSocket.getOutputStream(), true);
		
		this.loginGUI = new LoginGUI(serverOutput);
		this.tableGUI = new TableGUI();
	}
	
	public void setupLoginUI() {
		loginGUI.setVisible(true);
	}
	
	public void runLoginUI() {
		loginGUI.setVisible(true);
		try {
			for (String line = serverInput.readLine(); line!=null; line=serverInput.readLine()) {
				if (line.startsWith("Success")) {
					loginGUI.setVisible(false);
					tableGUI.setVisible(true);
					return;
				} else {
					System.out.println("Here");
					loginGUI.setResult(line);
					loginGUI.resetName();
					loginGUI.resetPassword();
				}
	        }
		} catch (IOException e) {
			throw new RuntimeException("IO Exception encountered");
		}
	}

	public static void main(final String[] args) {
		final EtherpadGUI main;
		try {
			main = new EtherpadGUI();
		} catch (IOException e) {
			throw new RuntimeException("IO Exception caught while setting up the GUI");
		}
		
		//main.setupLoginUI();
		Thread newThread = new Thread(new Runnable() {
			@Override
			public void run() {
				main.runLoginUI();
			}
		});
		newThread.start();

	}
}
