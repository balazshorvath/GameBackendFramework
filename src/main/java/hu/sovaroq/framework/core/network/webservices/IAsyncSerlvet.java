package hu.sovaroq.framework.core.network.webservices;

import javax.servlet.AsyncContext;

/**
 * This interface helps to manage async http servlets.
 * 
 * @author Horvath_Gergo
 *
 */
public interface IAsyncSerlvet {
	
	/**
	 * Nonblocking check to get incoming requests async.
	 * 
	 * @return
	 */
	public AsyncContext poll();
	
	/**
	 * After processing requests from poll()
	 * @param context
	 */
	public void respond(AsyncContext context);
	
}
