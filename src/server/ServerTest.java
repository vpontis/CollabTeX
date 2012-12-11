package server;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;


/*
 * Overall Testing Strategy: 
 * 
 * We will be testing this project using both automated JUnit tests and manual testing through the GUI. We use
 * both techniques because they both have their advantages and disadvantages. JUnit tests are precise and can be run
 * over and over again. They are run by the computer and their results are clear. But JUnit tests cannot appropriately
 * test concurrency and synchronization issues and are cumbersome when dealing with the GUI. Manual tests allow for 
 * easier interaction with the GUI and allow us to test for synchronization. 
 * 
 * Our model package only contains one class, document. We will test this using JUnit tests. We will test the changing
 * of the content and the updating of the time. Testing these we will implicitly be testing the getter and setter
 * methods of this class. More detailed testing strategy for the document can be found in the DocumentTest.java file.
 * 
 * To test the server we will use, as mentioned above, both JUnit and manual tests. The JUnit tests will test the basic 
 * functionality. We will make sure that we can run the server, connect a client to the server, and send requests from
 * that client to the server. All of our JUnit tests will be deterministic and will only have one thread when we are
 * checking that the changes persist. We will also have JUnit tests with multiple threads just to check that the server
 * can handle multiple clients. 
 * 
 * As for manually testing the server, we will run multiple clients at once connected to the same server. We will have the
 * multiple clients try and do the following things concurrently:
 * 		Try to login with the same username - only one should be allowed in
 * 		Try to create the same document - only one should be created
 * 		Try to edit the same document - we will have the users try to edit the document in various locations
 * 			the users should see the changes that the other users are currently committing, you should also be
 * 			able to change the same position as another user
 * 
 * We will test the server and model before testing the view. Once the server and model logic fully check out we will
 * be able to move on to the GUI and actually begin testing the program for usability. 
 * We will make sure that we test the system logic for connecting to one client before expanding to multiple clients open
 * at the same time. We will then have two clients who are both sending requests at the same time. From there we will progress
 * to having multiple clients open at the same time which are all sending requests. 
 * 
 * Coverage level
 * We will attempt to achieve a coverage level that tests every line of code. This should not be hard to do with JUnit
 * and manual testing. For most of the files JUnit testing will cover most of the lines. Coverage of lines tests that the 
 * basic usage works. We will also go one step further and try to test all lines of code in different circumstances. Two
 * other key circumstances are the number of client connections open and the frequency of requests to the server. We will 
 * try to test all lines of code while having multiple clients open, having requests being sent to the server continuously, 
 * and the combination of both where we have a lot of clients sending a lot of messages. We will be looking for the GUI
 * to still behave as expected and not to throw any exceptions. Ensuring that the entire system is stable for incorrect 
 * inputs as well is a very important part of our testing strategy. 
 * 
 * Location of bugs
 * We will design the JUnit tests to test for bugs in logic. We will step through the state machine in different orders
 * and check to make sure that all of our invariants are preserved. This again should define a working model for one client. 
 * The other types of bugs that we could have are concurrency issues or race conditions. We will test for these, as stated 
 * above, with manual testing. We will methodically go through different scenarios that could provoke race conditions and 
 * test each one. We will also write an in-depth thread safety argument that should prove to the user that concurrency issues
 * and race conditions do not exist. 
 * 
 * Prevalence of bugs and degree of reliability
 * We will not tolerate any bugs in the logic of the program with regards to one client. Thus we will thoroughly test this 
 * using JUnit tests. These tests have a high level of accuracy at getting deterministic bugs if we partition our test cases
 * appropriately. The JUnit test cases should be sufficient in catching deterministic bugs. The race conditions and so called
 * heisenbugs are harder to detect. Thus we will spend a couple hours each going through the series of test cases and
 * documenting the results of each test case. We can tolerate small bugs in conditions where there are a lot of clients connecting
 * at the same time and these cases are very hard to test. So we will handle the condition where there are a lot of clients primarily
 * from the thread-safety argument with a little testing as well. 
 */
public class ServerTest {
	//TODO Write tests
	
	
	public String getField(String field, String input){
		String regexPattern = "(?<=" + field + "\\=)(.*?)(?=((?<![\\\\])\\&))";
		Pattern regex = Pattern.compile(regexPattern);
		Matcher matcher = regex.matcher(input);
		matcher.find();
		String response = matcher.group();
		return response;
	}
	
	public String escapeText(String text){
		text.replaceAll("\\&", "\\\\\\&");
		text.replaceAll("\\=", "\\\\\\=");
		return text;
	}
	
	@Test
	public void regexTest(){
		String input = "opendoc&docName=document&userName=vpontis&version=2\\&adf3&\n";
		assertEquals("document", getField("docName", input));
		assertEquals("vpontis", getField("userName", input));
		assertEquals("2\\&adf3", getField("version", input));
	}
	
	@Test
	public void loginTest() throws IOException {
		Server serverInstance = new Server(1111);
		try {
			String loginResponse = serverInstance.logIn("deepak", 2);
			
			String userName = getField("userName", loginResponse);
			assertEquals("deepak", userName);
			
			String ID = getField("id", loginResponse);
			assertEquals("2", ID);
			
			loginResponse = serverInstance.logOut("deepak", "2");
			
			userName = getField("userName", loginResponse);
			assertEquals("deepak", userName);
		} finally {
			serverInstance.shutDown();
		}

	}
	
	@Test
	public void duplicateLoginTest() throws IOException {
		Server serverInstance = new Server(1111);
		try {
			String loginResponse = serverInstance.logIn("deepak", 2);
			
			String userName = getField("userName", loginResponse);
			assertEquals("deepak", userName);
			
			String ID = getField("id", loginResponse);
			assertEquals("2", ID);
			
			loginResponse = serverInstance.logIn("deepak", 3);
			
			assertEquals("notloggedin", loginResponse);
		} finally {
			serverInstance.shutDown();
		}		
		
	}
	
	@Test
	public void newDocTest() throws IOException {
		Server serverInstance = new Server(1111);
		try {
			String loginResponse = serverInstance.newDoc("deepak", "doc1");
			
			String userName = getField("userName", loginResponse);
			assertEquals("deepak", userName);
			
			String docName = getField("docName", loginResponse);
			assertEquals("doc1", docName);
			
			String date = getField("date", loginResponse);
			System.out.println("This is a visual check to see if date works..." + date);

		} finally {
			serverInstance.shutDown();
		}
	}
	
	@Test
	public void duplicateDocTest() throws IOException {
		Server serverInstance = new Server(1111);
		try {
			String loginResponse = serverInstance.newDoc("deepak", "doc1");
			loginResponse = serverInstance.newDoc("victor", "doc1");
			
			assertEquals("notcreatedduplicate", loginResponse);

		} finally {
			serverInstance.shutDown();
		}
	}
	
}















