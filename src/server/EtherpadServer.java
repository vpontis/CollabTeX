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
	private Map<String, String> user_passwordMappings;
	
	public EtherpadServer() throws IOException {
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
            Thread socketThread = new Thread(new RunnableMines(socket));
            socketThread.start();
        }
    }
    
    /**
     * Class defined to help start a new thread every time a new client connects to the server
     * @author Deepak
     *
     */
    private class RunnableMines implements Runnable {
    	Socket socket;
    	
    	public RunnableMines(Socket socket) {
    		this.socket = socket;
    	}
    	
		@Override
		public void run() {
			try {
				handleConnection(socket);
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}
    	
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
				return "Wrong password";
			}
			return "Username does not exist";
		} else if (input.startsWith("logon")) {
			String[] tokens = input.split(" ");
			String userName = tokens[1];
			String password = tokens[2];
			user_passwordMappings.put(userName, password);
			return "Logon success";
		}
		throw new UnsupportedOperationException();
	}
	
	public void handleConnection(Socket socket) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        
        for (String line = in.readLine(); line!=null; line=in.readLine()) {
            String output = handleRequest(line);
            out.print(output);
            out.flush();
        }
	}
	
	public void addDocument(Document newDocument) {
		currentDocuments.add(newDocument);
	}
	
	public List<Document> getDocumentNames(String userName) {
		return currentDocuments;
	}

}
