package backend;

/**
 * Created by narek on 26.09.14.
 */
public class User {
    private final String login;
    private final String password;
    private final String email;

    public User (String login,String password, String email) {
        this.login = login;
        this.password = password;
        this.email = email;
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

    public boolean equals(User obj) {
        if (obj.getLogin().equals(this.getLogin())&&(obj.getPassword().equals(this.getPassword())&&(obj.getEmail().equals(this.getEmail())) )) {
            return true;
        } else {
            return false;
        }
    }


}
