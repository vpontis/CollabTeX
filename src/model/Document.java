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
	private List<String> collaborators;
	private int versionNumber;
		
	private List<Change> changeList;
	/*
	 * Idea for keeping up with changes -->
	 * Have a changelist which stores the list of changes and the positions of each change
	 * Each version of the document corresponds with a certain change
	 * 
	 * This list may become really long, we can flush the list when we are not editing the document
	 * 
	 * Do we want a list or an array? 
	 * 
	 * If we have an array we will have to dynamically resize it. Are there dynamically resizing
	 * arrays in java?
	 * 
	 * we could do a list and still iterate over the indices of the list by keeping track of the 
	 * first value of the list
	 * 
	 * now when we get a change, we look at the version number and if it is different
	 * we start at that version and look at all of the changes we have made since then
	 * and if they affect the thing we are going to change, then we append that change onto 
	 * our changelist, we append the modified change because we keep track of the modified
	 * value and check with regards to that
	 */

	
	
	/**
	 * Constructor of the class Document. Creates a new document with the given document ID, document name and collaborator
	 * @param documentName String representing the name of the document
	 * @param collaborator String representing the name of the user that is creating the document
	 */
	public Document(String documentName, String collaborator) {
		this.documentName = documentName;
		this.content = "";
		this.lastEditDateTime = Calendar.getInstance();
		this.collaborators = new ArrayList<String> ();
		this.collaborators.add(collaborator);
		this.versionNumber = 0;
		this.changeList = new ArrayList<Change>();
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
	public synchronized void insertContent(String newLetter, int position, int version) {
		synchronized(content) {
			
			//TODO use version information to modify position
			position = transformPosition(position, version);
			position = Math.min(position, content.length());
			content = content.substring(0, position) + newLetter + content.substring(position);
			updateVersion();
			changeList.add(new Change(position, newLetter.length(), version));
		}
	}
	

	/**
	 * Deletes old content from the document at the given position
	 * @param position Old content that is removed from the document
	 * @param length Length of text that is being deleted from the document
	 * @return New content of the document
	 */
	public void deleteContent(int position, int length, int version) {
		synchronized(content) {
			position = transformPosition(position, version);
			content = content.substring(0, position) + content.substring(position + length);
			updateVersion();
			changeList.add(new Change(position, -length, version));
		}
	}
	
	/**
	 * Finds the position at which edit should be made given version history
	 * @param position Position of edit at the initial version number
	 * @param version New version number
	 * @return Position of final edit
	 */
	private int transformPosition(int position, int version) {
		for (Change ch : changeList){
			if (ch.getVersion() > version){
				if (ch.getPosition() < position){
					position += ch.getCharInserted();
				}
			}
		}
		return position;
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
			int integerHour = lastEditDateTime.get(Calendar.HOUR);
			String currentHour = integerHour == 0 ? "12" : String.valueOf(integerHour);
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
		synchronized(collaborators){			
			if (! collaborators.contains(newCollaborator))
				collaborators.add(newCollaborator);
		}
	}
	
	/**
	 * Method that removes the name of a collaborator if the collaborator exits the document
	 * @param collaborator String representing name of the collaborator who just exited the
	 * document
	 */
	public void removeCollaborator(String collaborator) {
		synchronized(collaborators){
			collaborators.remove(collaborator);
		}
	}
	
	/**
	 * Returns a list of strings that represent the users that have edited this document
	 * @return List of names of the different collaborators of the document
	 */
	public String getCollab(){
		synchronized(collaborators){
			String collab = collaborators.toString();
			int collaboratorLength = collab.length();
			collab = collab.substring(1, collaboratorLength - 1);
			return collab;
		}
	}
	
	public List<String> getCollabList(){
		synchronized(collaborators){
			return this.collaborators;
		}
	}
		
}
