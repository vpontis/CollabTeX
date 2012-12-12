package server;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a request made to the server. Contains all data pertaining to the particular server request.
 */
public class ServerRequest {
	private int ID;
	private String requestLine;
	private RequestType requestType;
	private Map<String, String> requestMap;
	
	/**
	 * Constructor for the ServerRequest class
	 * @param ID
	 * @param requestLine
	 */
	public ServerRequest(int ID, String requestLine) {
		this.ID = ID;
		this.requestLine = requestLine;
		this.requestMap = parseRequest(requestLine);
		this.requestType = getType(requestLine);
	}
	
	
	/**
	 * @return integer ID of the client that the request comes from
	 */
	public int getID() {
		return ID;
	}
	
	/**
	 * Returns a clone of the map to make sure that we are not leaking anything
	 * @return map of keys to values
	 */
	public Map<String, String> getMap() {
		return requestMap;
	}
	
	/**
	 * @return content of the request
	 */
	public String getLine() {
		return requestLine;
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
		if (input.startsWith("LOGIN&")) {
			return RequestType.LOGIN;
		} else if (input.startsWith("NEWDOC&")){
			return RequestType.NEWDOC;				
		} else if (input.startsWith("OPENDOC&")){
			return RequestType.OPENDOC;
		} else if (input.startsWith("CHANGE&")){
			return RequestType.CHANGEDOC;
		} else if (input.startsWith("EXITDOC&")){
			return RequestType.EXITDOC;
		} else if (input.startsWith("CORRECTERROR&")) {
			return RequestType.CORRECT_ERROR;
		} else if (input.startsWith("LOGOUT&")){
			return RequestType.LOGOUT;			
		} else {
			return RequestType.INVALID_REQUEST;
		}
	}
	
	/**
	 * Changes the request line into a dictionary
	 * @param request with key values in the form of a get request
	 * @return a dictionary which maps all keys to values
	 */
	public Map<String, String> parseRequest(String request){
		int index = 0;
		String regexPattern = "(?<=\\&)(.*?)(?=((?<![\\\\])\\=))";
		Pattern pattern = Pattern.compile(regexPattern);
		Matcher matcher = pattern.matcher(request);
		boolean found = matcher.find(index);
		Map<String, String> map = new HashMap<String, String>();
		
		while(found){
			String key = matcher.group();
			map.put(key, Regex.getField(key, request));
			
			index = matcher.end();
			found = matcher.find(index);
		}
		return map;
	}
}
