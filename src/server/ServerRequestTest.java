package server;

import static org.junit.Assert.*;

import java.util.Map;

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
	//TODO Fix these tests
	
	@Test
	public void loginRequestTest() {
		String inputRequest = "LOGIN&userName=deepak&";
		ServerRequest testingRequest = new ServerRequest(0, inputRequest);
		
		RequestType requestType = testingRequest.getType();
		Map<String,String> map = testingRequest.getMap();
		assertEquals(RequestType.LOGIN, requestType);
		assertEquals("deepak", map.get("userName"));
		
		String input = "LOGIN&userName=vic&";
		ServerRequest request = new ServerRequest(0, input);
		Map<String,String> requestMap = request.getMap();
		assertEquals("vic", requestMap.get("userName"));

	}
	
	@Test
	public void newdocRequestTest() {
		String inputRequest = "NEWDOC&userName=deepak&docName=doc1&";
		ServerRequest testingRequest = new ServerRequest(0, inputRequest);
		
		RequestType requestType = testingRequest.getType();
		assertEquals(RequestType.NEWDOC, requestType);
	}
	
	@Test
	public void opendocRequestTest() {
		String inputRequest = "OPENDOC&userName=deepak&docName=doc1&";
		ServerRequest testingRequest = new ServerRequest(0, inputRequest);
		
		RequestType requestType = testingRequest.getType();
		assertEquals(RequestType.OPENDOC, requestType);
	}
	
	@Test
	public void changedocRequestTest() {
		String inputRequest = "CHANGE&docName=deepak&docName=doc1&position=34&length=1&version=5&";
		ServerRequest testingRequest = new ServerRequest(0, inputRequest);
		
		RequestType requestType = testingRequest.getType();
		assertEquals(RequestType.CHANGEDOC, requestType);
	}
	
	@Test
	public void exitdocTest() {
		String inputRequest = "EXITDOC&userName=deepak&docName=doc1&";
		ServerRequest testingRequest = new ServerRequest(0, inputRequest);
		
		RequestType requestType = testingRequest.getType();
		assertEquals(RequestType.EXITDOC, requestType);
	}
	
	@Test
	public void logoutTest() {
		String inputRequest = "LOGOUT&userName=deepak&";
		ServerRequest testingRequest = new ServerRequest(0, inputRequest);
		
		RequestType requestType = testingRequest.getType();
		assertEquals(RequestType.LOGOUT, requestType);
	}
	
	@Test
	public void correctErrorTest() {
		String inputRequest = "CORRECTERROR&userName=deepak&docName=doc1&";
		ServerRequest testingRequest = new ServerRequest(0, inputRequest);
		
		RequestType requestType = testingRequest.getType();
		assertEquals(RequestType.CORRECT_ERROR, requestType);
	}
}
