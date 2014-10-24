package backend;

/**
 * Created by narek on 26.09.14.
 */
public class User {
    private final String login;
    private final String password;
    private final String email;

    public User (String password,String email, String login) {
        this.password = password;
        this.email = email;
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }



}
