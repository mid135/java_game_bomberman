package backend;

/**
 * Created by narek on 21.11.14.
 */
public interface User {
    public String getLogin();

    public String getPassword();

    public String getEmail();

    public long getId();

    public void setLogin(String login);

    public void setPassword(String password);

    public void setEmail(String email);

}
