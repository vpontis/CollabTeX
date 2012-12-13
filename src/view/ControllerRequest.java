package view;

import java.util.Map;

import server.Regex;

/**
 * Represents a request made to the server. Contains all data pertaining to the particular server request.
 */
public class ControllerRequest {
	public enum Type {
		CREATED,
		NOTCREATED, 
		OPENED, 
		LOGGEDOUT, 
		EXITEDDOC, 
		CORRECTED, 
		CHANGED, 
		UPDATE,
		LOGGEDIN,
		NOTLOGGEDIN,
		ID, 
		ENDDOCINFO, 
		DOCINFO,
		INVALID_REQUEST
	}

	private String requestLine;
	private Map<String, String> requestMap;
	private Type requestType; 
	
	/**
	 * Constructor for the ServerRequest class
	 * @param ID
	 * @param requestLine
	 */
	public ControllerRequest(String requestLine) {
		this.requestLine = requestLine;
		this.requestMap = Regex.parseRequest(requestLine);
		this.requestType = getType(requestLine);
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
	 * @return Type type of the instance of ServerRequest
	 */
	public Type getType() {
		return requestType;
	}
	
	/**
	 * Private helper method that returns the type of request made
	 * @param input The request made, represented as a string
	 * @return The request type 
	 */
	private Type getType(String input) {
		if(input.startsWith("created"))
			return Type.CREATED;
		if(input.startsWith("notcreated"))
			return Type.NOTCREATED;
		if(input.startsWith("opened"))
			return Type.OPENED;
		if(input.startsWith("loggedout"))
			return Type.LOGGEDOUT;
		if(input.startsWith("exiteddoc"))
			return Type.EXITEDDOC;
		if(input.startsWith("changed"))
			return Type.CHANGED;
		if(input.startsWith("corrected"))
			return Type.CORRECTED;
		if(input.startsWith("update"))
			return Type.UPDATE;
		if(input.startsWith("loggedin"))
			return Type.LOGGEDIN;
		if(input.startsWith("notloggedin"))
			return Type.NOTLOGGEDIN;
		if(input.startsWith("id"))
			return Type.ID;
		if(input.startsWith("enddocinfo"))
			return Type.ENDDOCINFO;
		if(input.startsWith("docinfo"))
			return Type.DOCINFO;
		return Type.INVALID_REQUEST;
	}
	
	
}
