package hu.sovaroq.framework.user;

/**
 * Created by Oryk on 2017. 01. 23..
 */
public interface IUser<ID> {
    ID getId();
    String getLogin();
    String getPassword();
    void setPassword(String password);
}
