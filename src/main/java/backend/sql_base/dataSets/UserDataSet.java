package backend.sql_base.dataSets;

import backend.User;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="users")
public class UserDataSet implements Serializable, User { // Serializable Important to Hibernate!
	private static final long serialVersionUID = -8706689714326132798L;

	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
    @Column(name="login")
    private String login;
    @Column(name="password")
    private String password;
    @Column(name="email")
    private String email;



    //Important to Hibernate!
    public UserDataSet(){
    }
	
	public UserDataSet(String login, String password, String email){
		this.id = -1;
        this.login = login;
        this.password = password;
        this.email = email;
	}

    public UserDataSet(long id, String login,String password, String email) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    @Override
    public String getLogin() {
        return login;
    }
    @Override
    public void setLogin(String login) {
        this.login = login;
    }
    @Override
    public String getPassword() {
        return password;
    }
    @Override
    public void setPassword(String password) {
        this.password = password;
    }
    @Override
    public String getEmail() {
        return email;
    }
    @Override
    public void setEmail(String email) {
        this.email = email;
    }

}

