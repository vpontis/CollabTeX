package view;

import static org.junit.Assert.*;

import org.junit.Test;

import server.Regex;



/*
 * USABILITY TESTING on GUI -->
 * We began doing this by first testing a single client and then moving onto multiple clients. 
 * 
 * For single client testing we tried logging in with 25 random user names with random capitalization that all worked. We 
 * then confirmed that it was unable to login when the field was left blank. We then moved onto testing the document table
 * We created multiple documents with multiple different names. 
 * We check that the correct error was thrown when a duplicate document name was created. We also tried clicking all 
 * the names and they all worked. Next we decided to check the document. We enter a document and typed the enter ASCII 
 * alphabet at slow speed, medium speed and fast speed. We also tested deleting in slow, medium, and fast speed. We also 
 * exited back out and made sure the name had been listed in the collab column. In our editor any person who has made a 
 * change to the document is added to the collab column. We also checked to make sure our LaTEx editor on various sample 
 * LaTex code. We also exited and entered the document 50 times very quickly to make sure it worked as expected which it did.
 * Lastly we created a document and logged off then logged back on to make sure we could reacess the document which we could
 * After all these tests we were satisfied that our GUI worked for single clients but still had to work for multiple users. 
 * We performed all these tasks on both a MacBook Air running OSX 10.7 and a ThinkPad running Windows 7 to make sure we had 
 * satisfied the specs.
 * 
 * For multiple client testing we had to make sure that everything worked as expected with multiple clients on the server.
 * We began by testing the login screen. We made sure that multiple users could login at similar times. We tried having 3 users to 
 * login in within a small time period and it worked well. We also tried to enter the same user name and the correct error message 
 * was shown. Once this was completed we moved on to testing the table. We had each user create a document and make sure that the 
 * document appeared on the other two clients table. We also checked at this point that the last modified time was as expected. 
 * We had each of the 3 users created 10 documents and everything worked as expected. We then moved onto testing the document itself 
 * which was the hardest to test. We first had all three users enter the same document. We began by making sure each user was added 
 * to the collab list at the top. Then we begin typing and deleting slowly on different lines to confirm the easiest functionality. 
 * This worked as expected with each client seeing the work of the other two people without a noticeable time lag. We then increased 
 * the typing and deleting speed to medium and then fast and got the results we expected. Now we had to worry about the tougher testing 
 * part with concurrency. First we had all users type on the same line but just alternating letters. We would tell user 1 
 * to type an a then user 2 to type a b then user 3 to type a c. We saw what we expected. Then we had each user move their cursor away 
 * but on the same line and start jamming on the keyboard again the results were expected. Next we had one user start typing and had the 
 * other two users typing on the same spot on the document. We did this with insertion as well as deletion. We repeated these tests at 
 * multiple speeds and with both OSX and Window and did not encounter very few errors. Sometimes at very high speed the cursor would skip 
 * back a spot but that only occurred at a low percentage in a unlikely test case, so we labeled it an acceptable error. After repeating 
 * these test 10's of time we were convinced it work. We then went back to the document table and made sure each user was added to the 
 * collab column in the table. We repeated this for a few different documents and was convinced our document had no major concurrency issues. 
 * We then disconnected each document from the server and then reconnected to make sure the changes were saved and that the server could 
 * recover the document.
 * 
 *  
 */

/*
 * Testing strategy -->
 * 
 * The aim of this class is to test parsing of the messages from the server to the client
 * This is tested by checking if all types of messages are parsed correctly. We then will manually test the GUI
 * to make sure it performs as expected.
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
