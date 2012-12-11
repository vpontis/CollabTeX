package view;

import static org.junit.Assert.*;

import org.junit.Test;


/*
 * Testing strategy -->
 * 
 * The aim of this class is to test parsing of the messages from the server to the client
 * This is tested by checking if all types of messages are parsed correctly.
 */

public class ControllerTest {

	@Test
	public void getDeletionFieldTest() {
		String input = "changed&type=deletion&userName=deepak&docName=doc1&position=7&" +
				"length=4&version=2&";
		
		assertEquals("deletion", Controller.getField("type", input));
		assertEquals("deepak", Controller.getField("userName", input));
		assertEquals("doc1", Controller.getField("docName", input));
		assertEquals("7", Controller.getField("position", input));
		assertEquals("4", Controller.getField("length", input));
		assertEquals("2", Controller.getField("version", input));
		
	}
	
	@Test
	public void getInsertionFieldTest() {
		String input = "changed&type=insertion&userName=deepak&docName=doc1&change=abcde&position=7&" +
				"length=4&version=2&color=255,192,255&";
		
		assertEquals("insertion", Controller.getField("type", input));
		assertEquals("deepak", Controller.getField("userName", input));
		assertEquals("doc1", Controller.getField("docName", input));
		assertEquals("abcde", Controller.getField("change", input));
		assertEquals("7", Controller.getField("position", input));
		assertEquals("4", Controller.getField("length", input));
		assertEquals("2", Controller.getField("version", input));
		assertEquals("255,192,255", Controller.getField("color", input));
	}
	
	@Test
	public void getloginFieldTest() {
		String input = "loggedin&userName=deepak&id=1&";
		
		assertEquals("deepak", Controller.getField("userName", input));
		assertEquals("1", Controller.getField("id", input));		
	}
	
	@Test
	public void getNewDocFieldTest() {
		String input = "created&userName=deepak&docName=doc1&date=2:48 PM , 12/11&";
		
		assertEquals("deepak", Controller.getField("userName", input));
		assertEquals("doc1", Controller.getField("docName", input));
		assertEquals("2:48 PM , 12/11", Controller.getField("date", input));
	}
	
	@Test
	public void getUpdateTest() {
		String input = "update&docName=doc1&collaborators=abc,def,ghi&color=255,192,255&";
		
		assertEquals("doc1", Controller.getField("docName", input));
		assertEquals("abc,def,ghi", Controller.getField("collaborators", input));
		assertEquals("255,192,255", Controller.getField("color", input));
	}
	
	@Test
	public void getCorrectErrorTest() {
		String input = "corrected&userName=deepak&docName=doc1&content=abc,def,ghi\tjsskjfng;fsjfdnjkndf;lknslkfnslkdfnlkdsnflds.&";
		
		assertEquals("deepak", Controller.getField("userName", input));
		assertEquals("doc1", Controller.getField("docName", input));
		assertEquals("abc,def,ghi\tjsskjfng;fsjfdnjkndf;lknslkfnslkdfnlkdsnflds.", Controller.getField("content", input));
	}

}
