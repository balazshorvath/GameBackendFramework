package hu.sovaroq.framework.data.user;

/**
 * Created by Oryk on 2017. 01. 23..
 */
public interface IUser {
    Long getUserId();
    String getLogin();
    String getPassword();
    boolean isLocked();
}
