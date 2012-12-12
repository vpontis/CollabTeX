package model;

/**
 * This class represents a change to the document. 
 * Each document has a list of such changes. 
 * A change can be an insertion or deletion.
 * Deletions are represented by negative
 * characters inserted. We do not actually
 * store the string that was inserted or deleted. 
 */
public class Change {
	
	private int position;
	//number of characters inserted or deleted
	private int charInserted;
	private int version;
	
	public Change(int position, int charInserted, int version){
		this.position = position;
		this.charInserted = charInserted;
		this.version = version;
	}
	
	public int getPosition(){
		return position;
	}
	
	public int getCharInserted(){
		return charInserted;
	}
	
	public int getVersion(){
		return version;
	}
}
