package net;

public class CouldNotConnectToHost extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CouldNotConnectToHost(String Ip,int port) {
		super("Could not Connect To Host: "+Ip+":"+port);
	}

	public CouldNotConnectToHost(String message) {
		super(message);
	}

	public CouldNotConnectToHost(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public CouldNotConnectToHost(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public CouldNotConnectToHost(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
