package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class DocEdit {
	private Login loginGUI;
	private DocTable tableGUI;
	
	private Socket serverSocket;
	
	private BufferedReader serverInput;
	private PrintWriter serverOutput;

	public DocEdit() throws UnknownHostException, IOException {
		this.serverSocket = new Socket("127.0.0.1",4444);
		this.serverInput = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
		this.serverOutput = new PrintWriter(serverSocket.getOutputStream(), true);
		
		this.loginGUI = new Login(serverOutput);
		this.tableGUI = new DocTable(serverOutput);
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
		final DocEdit main;
		try {
			main = new DocEdit();
		} catch (IOException e) {
			throw new RuntimeException("IO Exception caught while setting up the GUI");
		}
		
		Thread newThread = new Thread(new Runnable() {
			@Override
			public void run() {
				main.runLoginUI();
			}
		});
		newThread.start();

	}
}
