package hu.sovaroq.core.network.exception;

import java.io.IOException;

public class DataReadException extends IOException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1370646747372865784L;

	public DataReadException(String message){
		super(message);
	}
	
}
