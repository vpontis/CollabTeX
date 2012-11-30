package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Document {
	
	private String documentID;
	private String documentName;
	private Map<String, Paragraph> paragraphs;
	private String content;
	private Calendar lastEditDateTime;
	private List<String> onlineCollaborators;

	/**
	 * Constructor of the class Document
	 * @param documentID String representing the document ID of the document
	 * @param documentName String representing the name of the document
	 */
	public Document(String documentID, String documentName, String collaborator) {
		this.documentName = documentName;
		this.documentID = documentID;
		this.paragraphs = new HashMap<String, Paragraph> ();
		this.content = "";
		this.lastEditDateTime = Calendar.getInstance();
		this.onlineCollaborators = new ArrayList<String> ();
		this.onlineCollaborators.add(collaborator);
	}
	
	/**
	 * Returns the document ID of the particular document
	 * @return String representing the document ID of the document
	 */
	public String getDocumentID() {
		return documentID;
	}
	
	/**
	 * Returns the name of the document
	 * @return String representing the name of the document
	 */
	public String getName() {
		return documentName;
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
	
	/**
	 * 
	 * @param newContent
	 */
	public void updateContent(String newContent) {
		content = newContent;
	}
	
	/**
	 * Inserts new content into the given position in the document
	 * @param newContent New content to be inserted into the document
	 * @param position Position in the document at which new content should be inserted
	 * @return New content of the document
	 */
	public String insertContent(String newContent, int position) {
		content = content.substring(0, position) + newContent + content.substring(position, content.length());
		return content;
	}
	
	/**
	 * Deletes old content from the document at the given position
	 * @param position Old content that is removed from the document
	 * @param length Length of text that is being deleted from the document
	 * @return New content of the document
	 */
	public String deleteContent(int position, int length) {
		content = content.substring(0, position) + content.substring(position+ length, content.length());
		return content;
	}
	
	/**
	 * Sets the lastEditDateTime state of the class to a date object that represents the current date and time
	 */
	public void setLastEditDateTime() {
		lastEditDateTime = Calendar.getInstance();
	}
	
	/**
	 * @return String representation of the time of the last edit of the document
	 */
	public String getDate() {
		String AM_PM = lastEditDateTime.get(Calendar.AM_PM) == 0 ? "AM" : "PM";
		String currentHour = String.valueOf(lastEditDateTime.get(Calendar.HOUR));
		int integerMinute = lastEditDateTime.get(Calendar.MINUTE);
		String currentMinute = integerMinute < 10 ? "0" + String.valueOf(integerMinute) : String.valueOf(integerMinute);
		String currentMonth = String.valueOf(lastEditDateTime.get(Calendar.MONTH) + 1);
		String currentDay = String.valueOf(lastEditDateTime.get(Calendar.DAY_OF_MONTH));
		
		String date = currentHour + ":" + currentMinute + " " + AM_PM + " , " + currentMonth + "/" + currentDay;
		return date;
	}
	
	
	/**
	 * Method that adds the name of a new collaborator to the list of currently online
	 * collaborators
	 * @param newCollaborator String representing the name of new collaborator
	 */
	public void addCollaborator(String newCollaborator) {
		if (! onlineCollaborators.contains(newCollaborator))
			onlineCollaborators.add(newCollaborator);
	}
	
	/**
	 * Method that removes the name of a collaborator if the collaborator exits the document
	 * @param collaborator String representing name of the collaborator who just exited the
	 * document
	 */
	public void removeCollaborator(String collaborator) {
		onlineCollaborators.remove(collaborator);
	}
	
	/**
	 * Returns a list of strings that represent the users that have edited this document
	 * @return List of names of the different collaborators of the document
	 */
	public List<String> getCollab(){
		return onlineCollaborators;
	}
	
	/**
	 * Returns the content of the document
	 * @return String representation of the contents of the document
	 */
	public String getContent() {
		return content;
	}
	
	@Override
	public String toString() {
		return content;
		/*
		StringBuilder documentText = new StringBuilder();
		Set<String> paragraphKeys = paragraphs.keySet();
		for (String paragraphKey : paragraphKeys) {
			Paragraph paragraph = paragraphs.get(paragraphKey);
			documentText.append(paragraph.toString()).append("\n");
		}
		return documentText.toString();*/
	}
}
