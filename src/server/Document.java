package server;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Document {
	
	private String documentID;
	private Map<String, Paragraph> paragraphs;

	/**
	 * Constructor of the class Document
	 * @param documentID String representing the document ID of the document
	 */
	public Document(String documentID) {
		this.documentID = documentID;
		this.paragraphs = new HashMap<String, Paragraph> ();
	}
	
	/**
	 * Returns the document ID of the particular document
	 * @return String representing the document ID of the document
	 */
	public String getDocumentID() {
		return documentID;
	}
	
	/**
	 * Returns a paragraph with the given paragraph ID if it already exists.
	 * Otherwise, creates a new paragraph with the given paragraph ID and returns it. Updates document to contain the new paragraph as well.
	 * @param paragraphID String representing the paragraph ID of the Paragraph object in the document
	 * @return A Paragraph object that either already exists in the document, or is newly created with input paragraph ID
	 */
	public Paragraph getParagraph(String paragraphID) {
		if (paragraphs.containsKey(paragraphID)) {
			return paragraphs.get(paragraphID);
		} else {
			Paragraph newParagraph = new Paragraph(paragraphID);
			paragraphs.put(paragraphID, newParagraph);
			return newParagraph;
		}
		
	}
	
	@Override
	public String toString() {
		StringBuilder documentText = new StringBuilder();
		Set<String> paragraphKeys = paragraphs.keySet();
		for (String paragraphKey : paragraphKeys) {
			Paragraph paragraph = paragraphs.get(paragraphKey);
			documentText.append(paragraph.toString()).append("\n");
		}
		return documentText.toString();
	}
}
