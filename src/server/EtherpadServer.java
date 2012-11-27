package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Document;
import model.User;

public class EtherpadServer {
	
	private List<Document> currentDocuments;
	private int port = 4444;
	private final ServerSocket serverSocket;
	private Map<String, User> name_userMappings;
	
	/**
	 * Initializes the EtherpadServer with the default port number
	 * @throws IOException If there is an error creating the server socket
	 */
	public EtherpadServer() throws IOException {
		currentDocuments = new ArrayList<Document>();
		name_userMappings = new HashMap<String,User>();
		serverSocket = new ServerSocket(port);
	}
	
	/**
	 * Initializes the EtherpadServer with the given port number
	 * @param port The port number to which the server publishes messages
	 * @throws IOException If there is an error creating the server socket
	 */
	public EtherpadServer(int port) throws IOException {
		this.port = port;
		this.serverSocket = new ServerSocket(port);
		currentDocuments = new ArrayList<Document> ();
		name_userMappings = new HashMap<String, User> ();
	}
	
	/**
     * Run the server, listening for client connections and handling them.  
     * Never returns unless an exception is thrown.
     * @throws IOException if the main server socket is broken
     * (IOExceptions from individual clients do *not* terminate serve()).
     */
    public void serve() throws IOException {
        while (true) {
            // block until a client connects
            Socket socket = serverSocket.accept();
            User user = getUser(socket);
            System.out.println("Ready to start a new thread now!");
            Thread socketThread = new Thread(new RunnableServer(socket, user));
            socketThread.start();
        }
    }
    
    /**
     * Class defined to help start a new thread every time a new client connects to the server
     */
    private class RunnableServer implements Runnable {
    	Socket socket;
    	User user;
    	
    	public RunnableServer(Socket socket, User user) {
    		this.socket = socket;
    		this.user = user;
    	}
    	
		@Override
		public void run() {
			try {
				handleConnection(socket, user);
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}
    	
    }
    
    /**
     * Method that returns a User object representing the user of the current client
     * @param socket Socket corresponding to the current client
     * @return An object of the User class, that represents the user of the current client
     * @throws IOException
     */
    private User getUser(Socket socket) throws IOException {
    	BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        
        for (String line = in.readLine(); line!=null; line=in.readLine()) {
            String output = handleLogin(line);
            out.println(output);
            if (output.startsWith("loggedin")) {
            	String[] outputTokens = output.split(" ");
            	String userName = outputTokens[1];
            	return name_userMappings.get(userName);
            } 
        }
        
        throw new RuntimeException("Should not reach here");
    }
    
    /**
     * Handles the client logging in
     * @param input Message sent from the client to the server
     * @return A String representing the response of the server to the client
     */
    private String handleLogin(String input) {
    	if (input.startsWith("login")) {
			String[] tokens = input.split(" ");
			String userName = tokens[1].trim();
			if (name_userMappings.containsKey(userName)) {
				return "notloggedin";
			} else {
				name_userMappings.put("username", new User(userName, ""));
				StringBuilder stringBuilder = new StringBuilder("loggedin " + userName);
				stringBuilder.append("\n");
				for (Document document : currentDocuments){
					stringBuilder.append(document.getName());
					stringBuilder.append("\t");
					stringBuilder.append(document.getDate());
					stringBuilder.append("\t");
					stringBuilder.append(document.getCollab());
					stringBuilder.append("\n");
				}
				stringBuilder.append("enddocinfo");
				return stringBuilder.toString();
			}
		} 
    	throw new RuntimeException("Should not reach here");
    }
	
    private String handleRequest(String input, User user) {
		String output = "";
		System.out.println(input);
		if (input.equals("table")) {
			for (Document document : currentDocuments) {
				output += document.getName();
				output += "\t";
				return output;
			}
		} 
		else if (input.startsWith("NEWDOC")){
			String[] inputSplit = input.split(" ");
			if(inputSplit.length == 3){
				String userName = inputSplit[1];
				String docName = inputSplit[2];
				currentDocuments.add(new Document("asdf", docName));
				return "created " + userName + " " + docName; 
			}else{
				throw new RuntimeException("Invalid formatted newdoc request");
			}
		} 
		else if (input.startsWith("OPENDOC")){
			String[] inputSplit = input.split(" ");

			if(inputSplit.length == 3){
				String userName = inputSplit[1];
				String docName = inputSplit[2];
				Document currentDocument = getDoc(docName);
				String docContent = currentDocument.toString();
				System.out.println(docContent);
				return "opened|" + userName + "|" + docName + "|" + docContent; 
			}else{
				throw new RuntimeException("Invalid formatted opendoc request");
			}
		} 
		else if (input.startsWith("CHANGE")){
			String[] inputSplit = input.split("\\|");
			System.out.println(inputSplit.length);
			if(inputSplit.length == 3){
				String docName = inputSplit[1];
				String content = inputSplit[2];
				Document currentDocument = getDoc(docName);
				currentDocument.updateContent(content);
				String docContent = currentDocument.toString();
				System.out.println(docContent);
				return "changed|" + docName + "|" + docContent; 
			} else if (inputSplit.length == 2) {
				String docName = inputSplit[1];
				String content = "";
				Document currentDocument = getDoc(docName);
				currentDocument.updateContent(content);
				String docContent = currentDocument.toString();
				System.out.println(docContent);
				return "changed|" + docName + "|" + docContent; 
			}
			else{
				throw new RuntimeException("Invalid formatted change request");
			}
		} 
		else if (input.startsWith("EXITDOC")){
			String[] inputSplit = input.split(" ");
			String userName = inputSplit[1];
			String docName = inputSplit[2];
			StringBuilder stringBuilder = new StringBuilder("exiteddoc " + userName + " " + docName);
			stringBuilder.append("\n");
			for (Document document : currentDocuments){
				stringBuilder.append(document.getName());
				stringBuilder.append("\t");
				stringBuilder.append(document.getDate());
				stringBuilder.append("\t");
				stringBuilder.append(document.getCollab());
				stringBuilder.append("\n");
			}
			stringBuilder.append("enddocinfo");
			return stringBuilder.toString();
		} 
		else if (input.startsWith("LOGOUT")){
			String[] inputSplit = input.split(" ");
			String userName = inputSplit[1];
			name_userMappings.remove(userName);
			return "loggedout " + userName;
		}
		
		throw new UnsupportedOperationException(input);
	}
	
	public void handleConnection(Socket socket, User user) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        
        for (String line = in.readLine(); line!=null; line=in.readLine()) {
            String output = handleRequest(line, user);
            out.println(output);
        }
	}
	
	/**
	 * Adds a new Document to the list of currently active documents
	 * @param newDocument The document object of the new document being added to the collection
	 */
	public void addDocument(Document newDocument) {
		currentDocuments.add(newDocument);
	}
	
	/**
	 * Returns the list of documents that are stored in the server
	 * @return A List of document objects that are stored in the server
	 */
	public List<Document> getDocuments() {
		return currentDocuments;
	}
	
	private Document getDoc(String docName) {
		for (Document document: currentDocuments) {
			String name = document.getName();
			if (docName.equals(name)) {
				return document;
			}
		}
		throw new RuntimeException("Document not found");
	}
	
	/**
	 * Starts the etherpad server
	 * @param args Unused
	 */
	public static void main(String[] args) {
		try {
			EtherpadServer etherpadServer = new EtherpadServer();
			etherpadServer.serve();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
