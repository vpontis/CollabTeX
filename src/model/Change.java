package model;

public class Change {
	
	private int position;
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
