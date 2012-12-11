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
	
	@Test
	public void loginRequestTest() {
		String inputRequest = "LOGIN deepak";
		ServerRequest testingRequest = new ServerRequest(0, inputRequest);
		
		RequestType requestType = testingRequest.getType();
		assertEquals(RequestType.LOGIN, requestType);
		
		String requestLine = testingRequest.getLine();
		assertEquals("deepak", requestLine);
	}
	
	@Test
	public void newdocRequestTest() {
		String inputRequest = "NEWDOC deepak doc1";
		ServerRequest testingRequest = new ServerRequest(0, inputRequest);
		
		RequestType requestType = testingRequest.getType();
		assertEquals(RequestType.NEWDOC, requestType);
		
		String requestLine = testingRequest.getLine();
		assertEquals("deepak doc1", requestLine);
	}
	
	@Test
	public void opendocRequestTest() {
		String inputRequest = "OPENDOC deepak doc1";
		ServerRequest testingRequest = new ServerRequest(0, inputRequest);
		
		RequestType requestType = testingRequest.getType();
		assertEquals(RequestType.OPENDOC, requestType);
	}
	
	@Test
	public void changedocRequestTest() {
		String inputRequest = "CHANGE|deepak|doc1|34|1|5";
		ServerRequest testingRequest = new ServerRequest(0, inputRequest);
		
		RequestType requestType = testingRequest.getType();
		assertEquals(RequestType.CHANGEDOC, requestType);
	}
	
	@Test
	public void exitdocTest() {
		String inputRequest = "EXITDOC deepak doc1";
		ServerRequest testingRequest = new ServerRequest(0, inputRequest);
		
		RequestType requestType = testingRequest.getType();
		assertEquals(RequestType.EXITDOC, requestType);
	}
	
	@Test
	public void logoutTest() {
		String inputRequest = "LOGOUT deepak";
		ServerRequest testingRequest = new ServerRequest(0, inputRequest);
		
		RequestType requestType = testingRequest.getType();
		assertEquals(RequestType.LOGOUT, requestType);
	}
	
	@Test
	public void correctErrorTest() {
		String inputRequest = "CORRECTERROR|deepak|doc1";
		ServerRequest testingRequest = new ServerRequest(0, inputRequest);
		
		RequestType requestType = testingRequest.getType();
		assertEquals(RequestType.CORRECT_ERROR, requestType);
	}
}
