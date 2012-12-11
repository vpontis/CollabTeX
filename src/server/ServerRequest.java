package server;

/**
 * Represents a request made to the server. Contains all data pertaining to the particular server request.
 */
public class ServerRequest {
	private int ID;
	private String requestLine;
	private String[] requestTokens;
	private RequestType requestType;
	
	/**
	 * Constructor for the ServerRequest class
	 * @param ID
	 * @param requestLine
	 */
	public ServerRequest(int ID, String requestLine) {
		this.ID = ID;
		this.requestLine = requestLine;
		this.requestType = getType(requestLine);
	}
	
	
	/**
	 * @return integer ID of the client that the request comes from
	 */
	public int getID() {
		return ID;
	}
	
	/**
	 * @return content of the request
	 */
	public String getLine() {
		return requestLine;
	}
	
	/**
	 * 
	 * @return Return line tokens
	 */
	public String[] getTokens() {
		return requestTokens;
	}
	
	/**
	 * @return RequestType type of the instance of ServerRequest
	 */
	public RequestType getType() {
		return requestType;
	}
	
	/**
	 * Private helper method that returns the type of request made
	 * @param input The request made, represented as a string
	 * @return The request type 
	 */
	private RequestType getType(String input) {
		if (input.startsWith("LOGIN ")) {
			requestLine = requestLine.substring(6);
			requestTokens = requestLine.split(" ");
			return RequestType.LOGIN;
			
		} else if (input.startsWith("NEWDOC ")){
			requestLine = requestLine.substring(7);
			requestTokens = requestLine.split(" ");
			return RequestType.NEWDOC;	
			
		} else if (input.startsWith("OPENDOC ")){
			requestLine = requestLine.substring(8);
			requestTokens = requestLine.split(" ");
			return RequestType.OPENDOC;
			
		} else if (input.startsWith("CHANGE|")){
			requestLine = requestLine.substring(7);
			requestTokens = requestLine.split("\\|");
			return RequestType.CHANGEDOC;
			
		} else if (input.startsWith("EXITDOC ")){
			requestLine = requestLine.substring(8);
			requestTokens = requestLine.split(" ");
			return RequestType.EXITDOC;
			
		} else if (input.startsWith("CORRECTERROR|")) {
			requestLine = requestLine.substring(13);
			requestTokens = requestLine.split("\\|");
			return RequestType.CORRECT_ERROR;
			
		} else if (input.startsWith("LOGOUT ")){
			requestLine = requestLine.substring(7);
			requestTokens = requestLine.split(" ");
			return RequestType.LOGOUT;			
		} 
		
		else {
			requestLine = "";
			return RequestType.INVALID_REQUEST;
		}
	}
}
