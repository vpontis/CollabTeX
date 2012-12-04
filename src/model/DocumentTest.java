package model;

import org.junit.Test;

public class DocumentTest {
	
	@Test
	public void testDate() {
		Document testingDocument = new Document("Test3", "User"); //Initialize a new document with a fake document ID
		
		testingDocument.setLastEditDateTime();
		System.out.println(testingDocument.getDate());
		
	}

}
