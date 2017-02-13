package hu.sovaroq.framework.user;

/**
 * Created by Oryk on 2017. 01. 23..
 */
public interface IUser {
    int getId();
    String getLogin();
    String getPassword();
    boolean isLocked();
}
