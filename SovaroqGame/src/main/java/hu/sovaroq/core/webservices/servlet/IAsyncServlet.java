package hu.sovaroq.core.webservices.servlet;

import javax.servlet.AsyncContext;

/**
 * This interface helps to manage async http servlets.
 *
 * @author Horvath_Gergo
 */
public interface IAsyncServlet {

    /**
     * Nonblocking check to get incoming requests async.
     *
     * @return
     */
    public AsyncContext poll();

    /**
     * After processing requests from poll()
     *
     * @param context
     */
    public void respond(AsyncContext context, IWebResponse response);

}
