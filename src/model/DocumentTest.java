package model;

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
					doc.insertContent("a", i);
				}
			}
		});
		
		Thread otherChangingThread = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 100; i ++){
					doc.insertContent("b", i);
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
		Document testingDocument = new Document("Test3", "User"); //Initialize a new document with a fake document ID
		
		testingDocument.setLastEditDateTime();
		System.out.println(testingDocument.getDate());
		
	}

}
