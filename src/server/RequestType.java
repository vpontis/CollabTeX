package server;

/**
 * Enum that represents the type of request made to the server by the client
 */
public enum RequestType {
	LOGIN,
	NEWDOC,
	OPENDOC,
	CHANGEDOC,
	EXITDOC,
	LOGOUT,
	INVALID_REQUEST
}