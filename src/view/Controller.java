package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

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
	
	/**
	 * Runs the login GUI; makes all other GUI elements invisible, if they already exist.
	 * Tries to log the user into the server
	 */
	private void runLogin() {
		loginGUI.setVisible(true);
		if (docTableGUI != null) {
			docTableGUI.setVisible(false);
		}
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
	
	/**
	 * Updates the information stored in the document table.
	 */
	private void updateDocTable() {
		try{
			List<String[]> documentInfo = new ArrayList<String[]>();
			for (String line = serverInput.readLine(); line!= null; line=serverInput.readLine()){
				//System.out.println(line);
				if (line.startsWith("enddocinfo")){
					docTableGUI.updateTable(documentInfo);
					return ;
				} else{
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

	/**
	 * Runs the document table. Makes the document table GUI visible. All other GUI elements are made invisible.
	 */
	private void runDocTable() {
		loginGUI.setVisible(false);
		if (currentDoc != null)
			currentDoc.setVisible(false);
		docTableGUI.setVisible(true);
		try {
			for (String line = serverInput.readLine(); line!=null; line=serverInput.readLine()) {
				if (line.startsWith("created")) {
					String[] lineSplit = line.split("\\|");
					if (lineSplit.length == 4){
						String userName = lineSplit[1];
						String docName = lineSplit[2];
						String collaborators = lineSplit[3];
						if(this.userName.equals(userName)){
							this.currentDoc = new DocEdit(serverOutput, docName, userName, "", collaborators);							
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
					if (lineSplit.length == 5){
						String userName = lineSplit[1];
						String docName = lineSplit[2];
						String docContent = lineSplit[3];
						String collaborators = lineSplit[4];
						docContent = docContent.replace("\t", "\n");
						if(this.userName.equals(userName)){
							this.currentDoc = new DocEdit(serverOutput, docName, userName, docContent, collaborators);							
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
				} else if (line.startsWith("loggedout")) {
					String[] lineSplit = line.split(" ");
					if (lineSplit.length == 2) {
						String userName = lineSplit[1];
						if (this.userName.equals(userName)) {
							Thread newThread = new Thread(new Runnable() {
								@Override
								public void run() {
									runLogin();
								}
							});
							newThread.start();
							return;
						}
					}
				}
			}
		} catch (IOException e) {
			throw new RuntimeException("IO Exception encountered");
		}
	}
	
	/**
	 * Runs the document editor. All other GUI elements are made invisible.
	 */
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
						//System.out.println(userName + "," + this.userName);
						if(this.userName.equals(userName)){							
							Thread newThread = new Thread(new Runnable() {
								@Override
								public void run() {
									runDocTable();
								}
							});
							newThread.start();
							return ;
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
					String[] lineSplit = line.split("\\|");
					if (lineSplit.length == 4) {
						String docName = lineSplit[1];
						String content = lineSplit[2];
						int position = Integer.valueOf(lineSplit[3]);
						int length = Integer.valueOf(lineSplit[4]);
						System.out.println("Updating content");
						content.replace("\t", "\n");
						currentDoc.removeListener();
						currentDoc.updateContent(content, position, length);
						currentDoc.addListener();
					}
					// System.out.println(line);
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
