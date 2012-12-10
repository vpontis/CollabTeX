package model;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;


/*
 * Testing strategy:
 * 
 * In this JUnit test file, we test the Document class, which is the internal representation of a
 * document on the server. We will perform the following tests :
 * 	1. Test that insertion of content into the document works as expected. In particular, we consider the following cases:
 * 		a) Insertion of random text at the beginning of the document.
 * 		b) Insertion of random text in some arbitrary middle position in the document.
 * 		c) Insertion of random text at the end of the document.
 * 		d) Insertion of text with multiple lines.
 * 	Each of these tests will be performed with text consisting of alphabets, numbers and special characters.
 * 
 * 	2. Test that deletion of content from the document works as expected. In particular, we consider the following cases:
 * 		a) Deletion from the beginning of the text.
 * 		b) Deletion from the middle of the text.
 * 		c) Deletion from the end of the text.
 * 		d) Deletion of a new line.
 * 
 * 	3. Testing that the date changes every time an edit is made. It is hard to do this using JUnit tests, so we
 * will be doing this by inspection.
 * 
 * 	4. Checking that the getDate() method of this class returns the date in the desired format.
 * 
 * 	5. Check that updating the version number works.
 * 
 * 	6. Checking that adding and removing collaborators works. In particular, we look at the following cases:
 * 		a) Adding a collaborator who already exists
 * 		b) Removing a collaborator who doesn't exist
 * 		c) Adding / removing a new collaborator
 * 
 * 
 */
public class DocumentTest {
	
	@Test 
	public void quickChanges() {
		final Document doc = new Document("Test", "ViccyPont");
		
		Thread changingThread = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 100; i ++){
					doc.insertContent("a", i, i);
				}
			}
		});
		
		Thread otherChangingThread = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 100; i ++){
					doc.insertContent("b", i, i);
				}
			}
		});
		
		Thread deletingThread = new Thread(new Runnable() {
			@Override
			public void run() {
				
			}
		});
		
		changingThread.start();
		otherChangingThread.start();

	}
	
	@Test
	public void testDate() {
		Document testingDocument = new Document("TestDate", "User"); //Initialize a new document with a fake document ID
		
		testingDocument.setLastEditDateTime();
		System.out.println("This is a visual test to check whether updating the date works...");
		System.out.println("Current time is " + testingDocument.getDate());
		
	}
	
	@Test
	public void updateVersionTest() {
		Document testingDocument = new Document("TestVersion", "User");
		
		int expectedVersionNumber = 0;
		int actualVersionNumber = testingDocument.getVersion();
		assertEquals(expectedVersionNumber , actualVersionNumber);
		
		//This block of code ensures that the document's version number is incremented by 1 every time updateVersion is called
		for (int i = 0; i < 10000; ++i) {
			expectedVersionNumber = i;
			actualVersionNumber = testingDocument.getVersion();
			assertEquals(expectedVersionNumber, actualVersionNumber);
			testingDocument.updateVersion();
		}
	}
	
	@Test
	public void addCollaboratorTest() {
		Document testingDocument = new Document("TestCollaboratorAdding", "User");
		
		String expectedCollaborators = "User";
		String actualCollaborators = testingDocument.getCollab();
		assertEquals(expectedCollaborators, actualCollaborators);
		
		String collaborator = "User1";
		testingDocument.addCollaborator(collaborator);
		List<String> collaborators = testingDocument.getCollabList();
		String[] newCollaborators = collaborators.toArray(new String[collaborators.size()]);
		String[] listActualCollaborators = new String [] {"User", "User1"};
		assertArrayEquals(listActualCollaborators, newCollaborators);
	}

}
