package hu.sovaroq.core.network.exception;

import java.io.IOException;

/**
 * Is thrown, when while reading the input stream a message end marker is occurred.
 * @author Horvath_Gergo
 *
 */
public class UnexpectedMessageEndException extends IOException{

	private static final long serialVersionUID = 5242487006306576238L;
	
	public UnexpectedMessageEndException(String message){
		super(message);
	}
}
