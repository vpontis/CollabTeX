package server;

import java.util.ArrayList;
import java.util.List;

public class User {
	private String userName;
	private String password;
	
	private List<String> documents;
	
	public User(String userName, String password) {
		this.userName = userName;
		this.password = password;
		this.documents = new ArrayList<String> ();
	}
	
	public void addDocument(String documentID) {
		documents.add(documentID);
	}
	
	public void removeDocument(String documentID) {
		documents.remove(documentID);
	}

	public List<String> getDocuments() {
		return documents;
	}
	
	public boolean passwordMatch(String givenPassword) {
		return password.equals(givenPassword);
	}

}
