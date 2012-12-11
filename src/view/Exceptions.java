package view;

public class Exceptions {
	
	//Example exception
	/**
	 * Exception used for catching client crashes
	 */
	@SuppressWarnings("serial")
	public static class ClientCrashException extends Exception{
		public ClientCrashException(String msg){
			super("Client Crashed: " + msg);
		}
	}

}
