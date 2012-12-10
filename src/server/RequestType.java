package server;

/**
 * Enum that represents the type of request made to the server by the client
 * This is used by the ServerRequest class
 */
public enum RequestType {
	LOGIN,
	NEWDOC,
	OPENDOC,
	CHANGEDOC,
	EXITDOC,
	LOGOUT,
	INVALID_REQUEST,
	CORRECT_ERROR
}
