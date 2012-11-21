package server;

import static org.junit.Assert.*;

import org.junit.Test;

public class DocumentTest {

	@Test
	public void testSingleParagraphDocument() {
		Document testingDocument = new Document("123"); //Initialize a new document with a fake document ID
		Paragraph newParagraph = testingDocument.getParagraph("1");
		newParagraph.setParagraphText("Hi, I'm Deepak and I'm checking if this works");
		System.out.println(testingDocument);
	}
	
	@Test
	public void testMultipleParagraphDocument() {
		Document testingDocument = new Document("123"); //Initialize a new document with a fake document ID
		
		Paragraph newParagraph1 = testingDocument.getParagraph("1");
		newParagraph1.setParagraphText("Hi, I'm Deepak and I'm checking if this works");
		
		Paragraph newParagraph2 = testingDocument.getParagraph("2");
		newParagraph2.setParagraphText("This is awesome");
		
		System.out.println(testingDocument);
	}

}
