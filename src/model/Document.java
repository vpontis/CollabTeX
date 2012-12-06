package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Representation of the document.
 * Contains important meta data regarding the document.
 * Also contains data of the document
 * 
 * Thread safety argument:
 * 
 * TODO fill this in
 */
public class Document {
	
	private final String documentName;
	private String content;
	private Calendar lastEditDateTime;
	private List<String> onlineCollaborators;
	private int versionNumber;

	/**
	 * Constructor of the class Document. Creates a new document with the given document ID, document name and collaborator
	 * @param documentName String representing the name of the document
	 * @param collaborator String representing the name of the user that is creating the document
	 */
	public Document(String documentName, String collaborator) {
		this.documentName = documentName;
		this.content = "";
		this.lastEditDateTime = Calendar.getInstance();
		this.onlineCollaborators = new ArrayList<String> ();
		this.onlineCollaborators.add(collaborator);
		this.versionNumber = 0;
	}
	
	/**
	 * Returns the name of the document
	 * @return String representing the name of the document
	 */
	public String getName() {
		return documentName;
	}
		
	/**
	 * Inserts new content into the given position in the document
	 * The content should be a letter that results from the user typing. 
	 * @param newLetter New letter to be inserted into the document
	 * @param position Position in the document at which new content should be inserted
	 * @return New content of the document
	 */
	public synchronized String insertContent(String newLetter, int position) {
		synchronized(content) {
			System.out.println(newLetter + " " + String.valueOf(position) + " " + content);
			content = content.substring(0, position) + newLetter + content.substring(position);
			return content;
		}
	}
	
	/**
	 * Deletes old content from the document at the given position
	 * @param position Old content that is removed from the document
	 * @param length Length of text that is being deleted from the document
	 * @return New content of the document
	 */
	public String deleteContent(int position, int length) {
		synchronized(content) {
			content = content.substring(0, position) + content.substring(position + length);
			return content;
		}
	}
	
	/**
	 * @return a String that represents the content of the document
	 */
	@Override
	public synchronized String toString() {
		synchronized(content){
			return content;
		}
	}

	/**
	 * Sets the lastEditDateTime state of the class to a date object that represents the current date and time
	 */
	public void setLastEditDateTime() {
		synchronized(content){
			lastEditDateTime = Calendar.getInstance();
		}
	}
	
	/**
	 * @return String representation of the time of the last edit of the document
	 */
	public String getDate() {
		synchronized(content){
			String AM_PM = lastEditDateTime.get(Calendar.AM_PM) == 0 ? "AM" : "PM";
			String currentHour = String.valueOf(lastEditDateTime.get(Calendar.HOUR));
			int integerMinute = lastEditDateTime.get(Calendar.MINUTE);
			String currentMinute = integerMinute < 10 ? "0" + String.valueOf(integerMinute) : String.valueOf(integerMinute);
			String currentMonth = String.valueOf(lastEditDateTime.get(Calendar.MONTH) + 1);
			String currentDay = String.valueOf(lastEditDateTime.get(Calendar.DAY_OF_MONTH));
			
			String date = currentHour + ":" + currentMinute + " " + AM_PM + " , " + currentMonth + "/" + currentDay;
			return date;
		}
	}
	
	/**
	 * Gets the version number of the document saved on the server
	 * @return Version number of the document saved on the server
	 */
	public int getVersion() {
		synchronized(content){
			return versionNumber;
		}
	}
	
	/**
	 * Updates the version number of the document saved on the server
	 */
	public void updateVersion() {
		synchronized(content){
			versionNumber++;
		}
	}
	/**
	 * Method that adds the name of a new collaborator to the list of currently online
	 * collaborators
	 * @param newCollaborator String representing the name of new collaborator
	 */
	public void addCollaborator(String newCollaborator) {
		synchronized(onlineCollaborators){			
			if (! onlineCollaborators.contains(newCollaborator))
				onlineCollaborators.add(newCollaborator);
		}
	}
	
	/**
	 * Method that removes the name of a collaborator if the collaborator exits the document
	 * @param collaborator String representing name of the collaborator who just exited the
	 * document
	 */
	public void removeCollaborator(String collaborator) {
		synchronized(onlineCollaborators){
			onlineCollaborators.remove(collaborator);
		}
	}
	
	/**
	 * Returns a list of strings that represent the users that have edited this document
	 * @return List of names of the different collaborators of the document
	 */
	public String getCollab(){
		synchronized(onlineCollaborators){
			String collaborators = onlineCollaborators.toString();
			int collaboratorLength = collaborators.length();
			collaborators = collaborators.substring(1, collaboratorLength - 1);
			return collaborators;
		}
	}
		
}
