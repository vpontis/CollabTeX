package server;


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
 */
public class ServerTest {
	
}
