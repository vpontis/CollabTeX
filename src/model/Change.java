package model;

/**
 * This class represents a change to the document. 
 * Each document has a list of such changes. 
 * A change can be an insertion or deletion.
 * Deletions are represented by negative
 * characters inserted. We do not actually
 * store the string that was inserted or deleted. 
 * 
 * We do not need to worry about this class being modified
 * because it has no leakage and only private primitive variables
 * so we do not have to worry about rep leakage.
 */
public class Change {
	
	private int position;
	//number of characters inserted or deleted
	private int charInserted;
	private int version;
	
	/**
	 * Constructor for Change class
	 * @param position Position at which change is made
	 * @param charInserted Number of characters inserted/deleted in the change
	 * @param version Version number of document to which change is made
	 */
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
