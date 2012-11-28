package model;

import org.junit.Test;

public class DocumentTest {

	@Test
	public void testSingleParagraphDocument() {
		Document testingDocument = new Document("123", "Test1", "User"); //Initialize a new document with a fake document ID
		Paragraph newParagraph = testingDocument.getParagraph("1");
		newParagraph.setParagraphText("This is a testing document");
		System.out.println(testingDocument);
	}
	
	@Test
	public void testMultipleParagraphDocument() {
		Document testingDocument = new Document("123", "Test2", "User"); //Initialize a new document with a fake document ID
		
		Paragraph newParagraph1 = testingDocument.getParagraph("1");
		newParagraph1.setParagraphText("This is a line from Paragraph 1");
		
		Paragraph newParagraph2 = testingDocument.getParagraph("2");
		newParagraph2.setParagraphText("This is a line from Paragraph 2");
		
		System.out.println(testingDocument);
	}
	
	@Test
	public void testDate() {
		Document testingDocument = new Document("123", "Test3", "User"); //Initialize a new document with a fake document ID
		
		testingDocument.setLastEditDateTime();
		System.out.println(testingDocument.getDate());
		
	}

}
