package view;

import static org.junit.Assert.*;

import org.junit.Test;

import server.Regex;


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
		
		assertEquals("deletion", Regex.getField("type", input));
		assertEquals("deepak", Regex.getField("userName", input));
		assertEquals("doc1", Regex.getField("docName", input));
		assertEquals("7", Regex.getField("position", input));
		assertEquals("4", Regex.getField("length", input));
		assertEquals("2", Regex.getField("version", input));
		
	}
	
	@Test
	public void getInsertionFieldTest() {
		String input = "changed&type=insertion&userName=deepak&docName=doc1&change=abcde&position=7&" +
				"length=4&version=2&color=255,192,255&";
		
		assertEquals("insertion", Regex.getField("type", input));
		assertEquals("deepak", Regex.getField("userName", input));
		assertEquals("doc1", Regex.getField("docName", input));
		assertEquals("abcde", Regex.getField("change", input));
		assertEquals("7", Regex.getField("position", input));
		assertEquals("4", Regex.getField("length", input));
		assertEquals("2", Regex.getField("version", input));
		assertEquals("255,192,255", Regex.getField("color", input));
	}
	
	@Test
	public void getloginFieldTest() {
		String input = "loggedin&userName=deepak&id=1&";
		
		assertEquals("deepak", Regex.getField("userName", input));
		assertEquals("1", Regex.getField("id", input));		
	}
	
	@Test
	public void getNewDocFieldTest() {
		String input = "created&userName=deepak&docName=doc1&date=2:48 PM , 12/11&";
		
		assertEquals("deepak", Regex.getField("userName", input));
		assertEquals("doc1", Regex.getField("docName", input));
		assertEquals("2:48 PM , 12/11", Regex.getField("date", input));
	}
	
	@Test
	public void getUpdateTest() {
		String input = "update&docName=doc1&collaborators=abc,def,ghi&color=255,192,255&";
		
		assertEquals("doc1", Regex.getField("docName", input));
		assertEquals("abc,def,ghi", Regex.getField("collaborators", input));
		assertEquals("255,192,255", Regex.getField("color", input));
	}
	
	@Test
	public void getCorrectErrorTest() {
		String input = "corrected&userName=deepak&docName=doc1&content=abc,def,ghi\tjsskjfng;fsjfdnjkndf;lknslkfnslkdfnlkdsnflds.&";
		
		assertEquals("deepak", Regex.getField("userName", input));
		assertEquals("doc1", Regex.getField("docName", input));
		assertEquals("abc,def,ghi\tjsskjfng;fsjfdnjkndf;lknslkfnslkdfnlkdsnflds.", Regex.getField("content", input));
	}

}
