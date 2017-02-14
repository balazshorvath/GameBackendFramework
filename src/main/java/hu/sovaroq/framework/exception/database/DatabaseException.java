package hu.sovaroq.framework.exception.database;

import hu.sovaroq.framework.exception.FrameworkException;

/**
 * Created by Oryk on 2017. 02. 14..
 */
public class DatabaseException extends FrameworkException {
    public DatabaseException() {
    }

    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public DatabaseException(Throwable cause) {
        super(cause);
    }

    public DatabaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
