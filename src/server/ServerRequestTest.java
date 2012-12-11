package server;

import static org.junit.Assert.*;

import org.junit.Test;

/*
 * Testing strategy:
 * 
 * In this JUnit test file, we test the ServerRequest.java file.
 * In particular, we perform the following checks to check if a server request is parsed properly-
 * 	1. Check that the server request is of the right type
 * 	2. Check that the identifier for each request is correctly stripped
 * 	3. Check that incorrect server requests are identified correctly
 */
public class ServerRequestTest {
	//TODO Write tests
	
	@Test
	public void invalidRequestTest() {
		String inputRequest = "Hello there";
		ServerRequest testingRequest = new ServerRequest(0, inputRequest);
		
		RequestType requestType = testingRequest.getType();
		assertEquals(RequestType.INVALID_REQUEST, requestType);
	}
}
