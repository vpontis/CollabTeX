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
		this.currentDocuments = new ArrayList<Document> ();
		this.serverSocket = new ServerSocket(port);
		this.name_userMappings = new HashMap<String,User> ();
	}
	
	/**
	 * Initializes the EtherpadServer with the given port number
	 * @param port The port number to which the server publishes messages
	 * @throws IOException If there is an error creating the server socket
	 */
	public EtherpadServer(int port) throws IOException {
		this.port = port;
		this.currentDocuments = new ArrayList<Document> ();
		this.serverSocket = new ServerSocket(port);
		this.name_userMappings = new HashMap<String, User> ();
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
            Thread socketThread = new Thread(new RunnableMines(socket, user));
            socketThread.start();
        }
    }
    
    /**
     * Class defined to help start a new thread every time a new client connects to the server
     */
    private class RunnableMines implements Runnable {
    	Socket socket;
    	User user;
    	
    	public RunnableMines(Socket socket, User user) {
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
            if (output.startsWith("Success")) {
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
			String userName = tokens[1];
			String password = tokens[2];
			if (name_userMappings.containsKey(userName)) {
				User user = name_userMappings.get(userName);
				if (user.passwordMatch(password)) {
					return "Success " + userName;
				}
				return "Wrong password";
			}
			return "Username does not exist";
		} else if (input.startsWith("logon")) {
			String[] tokens = input.split(" ");
			String userName = tokens[1];
			String password = tokens[2];
			User newUser = new User(userName, password);
			name_userMappings.put(userName, newUser);
			return "Logon success";
		}
    	throw new RuntimeException("Should not reach here");
    }
	
    //Complete writing this method
	private String handleRequest(String input, User user) {
		String output = "";
		if (input.equals("table")) {
			for (Document document : currentDocuments) {
				output += document.getDocumentName();
				output += "\t";
				return output;
			}
		}
		
		throw new UnsupportedOperationException();
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
	public List<Document> getDocumentNames(String userName) {
		return currentDocuments;
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
