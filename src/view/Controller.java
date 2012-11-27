package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import model.Document;

import server.EtherpadServer;

public class Controller {
	private Login loginGUI;
	private DocTable docTableGUI = null;
	
	private Socket serverSocket;
	
	private BufferedReader serverInput;
	private PrintWriter serverOutput;
	private String userName;
	
	private DocEdit currentDoc = null;

	public Controller() throws UnknownHostException, IOException {
		this.serverSocket = new Socket("127.0.0.1",4444);
		this.serverInput = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
		this.serverOutput = new PrintWriter(serverSocket.getOutputStream(), true);
		
		this.loginGUI = new Login(serverOutput);
	}
		
	private void runLogin() {
		loginGUI.setVisible(true);
		try {
			for (String line = serverInput.readLine(); line!=null; line=serverInput.readLine()) {
				if (line.startsWith("loggedin")) {
					String[] lineSplit = line.split(" ");
					this.userName = lineSplit[1];
					this.docTableGUI = new DocTable(serverOutput, userName);
					updateDocTable();
					
					Thread newThread = new Thread(new Runnable() {
						@Override
						public void run() {
							runDocTable();
						}
					});
					newThread.start();
					return;
				} else if (line.startsWith("notloggedin")){
					loginGUI.failedLogin();
				}
	        }
		} catch (IOException e) {
			throw new RuntimeException("IO Exception encountered");
		}
	}
	

	private void updateDocTable() {
		try{
			List<String[]> documentInfo = new ArrayList<String[]>();
			for (String line = serverInput.readLine(); line!= null; line=serverInput.readLine()){
				if (line.startsWith("enddocinfo")){
					docTableGUI.updateTable(documentInfo);
					break;
				}
				else{
					String[] lineSplit = line.split("\t");
					String docName = lineSplit[0];
					String docDate = lineSplit[1];
					String docCollab = lineSplit[2];
					documentInfo.add(new String[]{docName, docDate, docCollab});
				}
			}
		} catch(IOException e){
			throw new RuntimeException();
		}
	}

	private void runDocTable() {
		loginGUI.setVisible(false);
		if (currentDoc != null)
			currentDoc.setVisible(false);
		docTableGUI.setVisible(true);
		try {
			for (String line = serverInput.readLine(); line!=null; line=serverInput.readLine()) {
				System.out.println(line);
				if (line.startsWith("created")) {
					String[] lineSplit = line.split(" ");
					if (lineSplit.length == 3){
						String userName = lineSplit[1];
						String docName = lineSplit[2];
						if(this.userName.equals(userName)){
							this.currentDoc = new DocEdit(serverOutput, docName, userName, "");							
							Thread newThread = new Thread(new Runnable() {
								@Override
								public void run() {
									runDocEdit();
								}
							});
							newThread.start();
							return;
						}
					}else{
						throw new RuntimeException("Invalid format");
					}					
				} else if (line.startsWith("opened")) {
					String[] lineSplit = line.split("\\|");
					if (lineSplit.length == 4){
						String userName = lineSplit[1];
						String docName = lineSplit[2];
						String docContent = lineSplit[3];
						if(this.userName.equals(userName)){
							this.currentDoc = new DocEdit(serverOutput, docName, userName, docContent);							
							Thread newThread = new Thread(new Runnable() {
								@Override
								public void run() {
									runDocEdit();
								}
							});
							newThread.start();
							return;
						}
					}else{
						throw new RuntimeException("Invalid format");
					}					
				}  else if (line.startsWith("changed")) {
					String[] lineSplit = line.split("\\|");
					if (lineSplit.length == 3){
						String docName = lineSplit[1];
						String docContent = lineSplit[2];
						if(this.userName.equals(userName)){
							this.currentDoc = new DocEdit(serverOutput, docName, userName, docContent);							
							Thread newThread = new Thread(new Runnable() {
								@Override
								public void run() {
									runDocEdit();
								}
							});
							newThread.start();
							return;
						}
					}else{
						throw new RuntimeException("Invalid format");
					}					
				}
			}
		} catch (IOException e) {
			throw new RuntimeException("IO Exception encountered");
		}
	}
	
	private void runDocEdit() {
		docTableGUI.setVisible(false);
		currentDoc.setVisible(true);
		try {
			for (String line = serverInput.readLine(); line!=null; line=serverInput.readLine()) {
				if (line.startsWith("exiteddoc ")) {
					String[] lineSplit = line.split(" ");
					if (lineSplit.length == 3){
						String userName = lineSplit[1];
						String docName = lineSplit[2];
						updateDocTable();
						if(this.userName.equals(userName)){							
							Thread newThread = new Thread(new Runnable() {
								@Override
								public void run() {
									runDocTable();
								}
							});
							newThread.start();
						}
					}else{
						throw new RuntimeException("Invalid format");
					}					
				}  else if(line.startsWith("exiteddoc")){
					String[] lineSplit = line.split(" ");
					if (lineSplit.length == 3){
						String userName = lineSplit[1];
						String docName = lineSplit[2];
						if(this.userName.equals(userName) && currentDoc.getName().equals(docName)){							
							Thread newThread = new Thread(new Runnable() {
								@Override
								public void run() {
									runDocTable();
								}
							});
							newThread.start();
							return;
						}
					}
				} else if (line.startsWith("changed")) {
					String[] lineSplit = line.split(" ");
					System.out.println(line);
				}
			}
		} catch (IOException e) {
			throw new RuntimeException("IO Exception encountered");
		}

	}

	public static void main(final String[] args) {
		final Controller main;
		try {
			main = new Controller();
		} catch (IOException e) {
			throw new RuntimeException("IO Exception caught while setting up the GUI");
		}
		
		Thread newThread = new Thread(new Runnable() {
			@Override
			public void run() {
				main.runLogin();
			}
		});
		newThread.start();

	}
}
