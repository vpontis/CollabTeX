package view;

import static org.junit.Assert.*;

import org.junit.Test;

import server.Regex;



/*
 * Testing strategy for the GUI -->
 * Testing was first performed for single clients, and then modified for multiple clients
 * 
 * To test the login screen, the following cases were looked at -->
 * 	1. Users with different names were logged in.
 * 	2. Users with blank names are not logged in. 
 * 
 * To test the document table, the following cases were looked at -->
 * 	1. Multiple documents created with multiple different names. 
 * 	2. Duplicate documents are correctly dealt with.
 * 	3. Documents can be opened from the table.
 * 
 * To test the document, the following cases were looked at -->
 * 	1. Insertion of different ASCII characters into the document.
 * 	2. Deletion of characters from the document.
 * 	3. Checking that collaborators are added to the document header in real time.
 * 	4. Checking that LaTeX code is rendered correctly.
 * 	5. Document content is preserved across document openings and closings.
 *  
 *  
 * Testing for multiple clients was performed in the following manner -->
 *  1. Checking if multiple clients can login at the same time
 *  2. Checking if new document creation on one client updates document table on other clients
 *  3. Checking if last edit time is updated correctly in the document table
 *  4. Checking if simultaneous edits to a document are reflected across all clients. Edits can be both 
 *  insertions and deletions
 *  5. Checking if document table is always in sync with the different clients 
 */

/*
 * Testing strategy -->
 * 
 * The aim of this class is to test parsing of the messages from the server to the client
 * Actual GUI performance is tested manually; testing strategy for the GUI is as above.
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
