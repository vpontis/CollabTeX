package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EtherpadServer {
	
	private List<Document> currentDocuments;
	private int port = 4444;
	private final ServerSocket serverSocket;
	private Map<String, String> user_passwordMappings;
	
	public EtherpadServer() throws IOException {
		this.port = port;
		this.currentDocuments = new ArrayList<Document> ();
		this.serverSocket = new ServerSocket(port);
		this.user_passwordMappings = new HashMap<String,String> ();
	}
	
	public EtherpadServer(int port) throws IOException {
		this.port = port;
		this.currentDocuments = new ArrayList<Document> ();
		this.serverSocket = new ServerSocket(port);
		this.user_passwordMappings = new HashMap<String, String> ();
	}
	
	private String handleRequest(String input) {
		if (input.startsWith("login")) {
			String[] tokens = input.split(" ");
			String userName = tokens[1];
			String password = tokens[2];
			if (user_passwordMappings.containsKey(userName)) {
				String expectedPassword = user_passwordMappings.get(userName);
				if (password.equals(expectedPassword)) {
					return "Login success";
				}
			}
			return "Login failed";
		} else if (input.startsWith("logon")) {
			String[] tokens = input.split(" ");
			String userName = tokens[1];
			String password = tokens[2];
			user_passwordMappings.put(userName, password);
			return "Logon success";
		}
		throw new UnsupportedOperationException();
	}
	
	public void addDocument(Document newDocument, String userName) {
		currentDocuments.add(newDocument);
	}
	
	public List<Document> getDocumentNames(String userName) {
		return currentDocuments;
	}

}
