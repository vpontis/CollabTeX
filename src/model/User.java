package model;

import java.util.ArrayList;
import java.util.List;

public class User {
	@SuppressWarnings("unused")
	private String userName;
	private String password;
	
	private List<String> documents;
	
	/**
	 * Constructor for the User object, which contains data about each user
	 * @param userName The name of the user 
	 * @param password The password of the user
	 */
	public User(String userName, String password) {
		this.userName = userName;
		this.password = password;
		this.documents = new ArrayList<String>();
	}
	
	/**
	 * Adds a new document to the collection of documents being edited by the user
	 * @param documentID String representing the document ID of the document to be added
	 */
	public void addDocument(String documentID) {
		documents.add(documentID);
	}
	
	/**
	 * Removes a document from the collection of documents being edited by the user
	 * @param documentID String representing the document ID of the document to be deleted
	 */
	public void removeDocument(String documentID) {
		documents.remove(documentID);
	}

	/**
	 * Method that gets the list of document IDs associated with the particular user
	 * @return A list of document IDs associated with the particular user
	 */
	public List<String> getDocuments() {
		return documents;
	}
	
	/**
	 * Method that checks if the input password matches with the user's password
	 * @param givenPassword The input password
	 * @return A boolean valus, true if the passwords match, false if they don't
	 */
	public boolean passwordMatch(String givenPassword) {
		return password.equals(givenPassword);
	}

}
